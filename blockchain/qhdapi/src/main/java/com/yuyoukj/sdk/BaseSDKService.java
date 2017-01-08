package com.yuyoukj.sdk;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.PortUnreachableException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.yuyoukj.util.MD5Digest;

public class BaseSDKService {
	/**
	 * @param reader
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String convertReaderToString(BufferedReader reader) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * @param params
	 * @param secret
	 * @param encoder
	 * @param excludeParam
	 * @return
	 */
	public static String createAppsig(Map<String, String> params, String secret, String encoder, Map<String, String> excludeParam,
			Map<String, String> encodeParam) {
		StringBuffer sb = getParamToStr(params, encoder, excludeParam, encodeParam);
		String clearStr = sb.toString();
		String totalParamStr = secret + "&" + clearStr;
		MD5Digest md5 = MD5Digest.getInstance(totalParamStr);
		return md5.toString();
	}

	/**
	 * @param hostUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static BaseSDKResponse excuteGetMethod(String hostUrl, String params) throws Exception {
		// GetMethod getMethod = new GetMethod(urlSb);//
		// 创建GET方法的实例,可同时对请求的头,等等，做相应的设置
		BaseSDKResponse retRes = new BaseSDKResponse();
		String resContent = "";
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 代理的设置
			// http://192.168.16.174:8081/osm-user/osmuser?method=auth.register&appkey=test&phnum=13123231235&password=123456
			/*
			 * HttpHost proxy = new HttpHost("192.168.16.174", 8081); httpclient. getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			 */
			// 目标地址
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);// 连接超时
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);// 设置读数据超时时间(单位毫秒)
			HttpPost httppost = new HttpPost(hostUrl);
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(params);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httppost.setEntity(reqEntity);
			// 执行
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			// 显示结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			resContent = convertReaderToString(reader);
			if (resContent != null && (!"".equalsIgnoreCase(resContent))) {
				resContent = resContent.replaceAll("[\\s*]+", " ");
				// resContent = resContent.replaceAll("&", "&amp;");
				ParseResContent(retRes, resContent);// 解析返回结果
				retRes.setXmlcontent(resContent);
			} else {
				retRes.setRc(9001);// 返回结果为空
			}
		} catch (PortUnreachableException e) {
			retRes.setRc(9005);// 网络端口无法到达异常;
		} catch (BindException e) {
			retRes.setRc(9004);// 网络绑定异常;
		} catch (ConnectException e) {
			retRes.setRc(9003);// 网络连接异常;
		} catch (Exception e) {
			e.printStackTrace();// 发生网络异常
		} finally {
			// getMethod.releaseConnection();// 释放连接
		}
		return retRes;
	}

	public static String getAppPath(Class cls) {
		// 检查用户传入的参数是否为空
		if (cls == null) {
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		}
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax.")) {
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			}
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0) {
				path = packName + "/";
			} else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!")) {
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}
		/*------------------------------------------------------------
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的
		  中文及空格路径
		-------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}// getAppPath定义结束

	/**
	 * @param totalUrl
	 * @return
	 * @throws Exception
	 */
	public static BaseSDKResponse getMethod(String totalUrl) throws Exception {
		int indexPot = totalUrl.indexOf("?");
		String baseUrl = totalUrl;
		String params = "";
		if (indexPot > -1) {
			baseUrl = totalUrl.substring(0, indexPot);
			params = totalUrl.substring(indexPot + 1);
		}
		BaseSDKResponse baseRes = excuteGetMethod(baseUrl, params);
		return baseRes;
	}

	/**
	 * @description:把map里的参数重新排序组成字符串返回
	 * @return
	 */
	public static StringBuffer getParamToStr(Map<String, String> params, String encode, Map<String, String> excludeParam,
			Map<String, String> encodeParam) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		int icount = 0;
		// 对参数去掉appsig和returnvalue键值后，生成新参数串，用于后面md5校对
		for (String key : keys) {
			if (excludeParam != null && excludeParam.containsKey(key) || (params.get(key) == null)) {
				continue;
			}
			if (icount > 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			if (encode != null && (!"".equalsIgnoreCase(encode)) && encodeParam != null && encodeParam.containsKey(key)) {
				try {
					sb.append(URLEncoder.encode(params.get(key), encode));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				sb.append(params.get(key));
			}
			icount++;
		}
		return sb;
	}

	private static void ParseResContent(BaseSDKResponse retRes, String resContent) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(resContent);
		} catch (DocumentException e2) {
			retRes.setRc(9101);// 解析出错
			retRes.setMessage("Parse return xml error!response xmlstr:" + resContent);
			e2.printStackTrace();
			return;
		}
		if (doc != null) {
			Element root = doc.getRootElement();// 指向根节点 response
			if (root != null) {
				if (root.attributeValue("rc") != null && (!"".equalsIgnoreCase(root.attributeValue("rc")))) {
					retRes.setRc(Integer.parseInt(root.attributeValue("rc")));
				} else {
					retRes.setRc(Integer.parseInt(root.element("errorCode").getText()));
					// logger.error("line 312 resContent:"+resContent);
				}
				Element eleMsg = root.element("msg");
				if (eleMsg != null) {
					retRes.setMessage(eleMsg.getText());
				}
			}
		}
	}

	public static void postXMLToServer(String strUrl, String xmlStr) throws Exception {
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		try {
			url = new URL(strUrl);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setUseCaches(true);
			OutputStream raw = httpurlconnection.getOutputStream();
			OutputStream buf = new BufferedOutputStream(raw);
			OutputStreamWriter out = new OutputStreamWriter(buf);
			out.write(xmlStr);
			out.flush();
			out.close();
			raw.close();
			httpurlconnection.getInputStream();
		} catch (PortUnreachableException e) {
			// logger.error("9005");//网络端口无法到达异常;
		} catch (BindException e) {
			// logger.error("9004");//网络绑定异常;
		} catch (ConnectException e) {
			// logger.error("9003");//网络连接异常;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
		}
	}

	// static HttpClient httpClient = null;
	private String appkey;
	private String secret;
	private static final String PARAM_APPKEY = "appkey";// 参数appkey
	private static final String PARAM_METHOD = "method";// 参数method

	public BaseSDKService() throws Exception {
	}

	/**
	 * @param appkey
	 * @param secret
	 * @throws Exception
	 */
	public BaseSDKService(String appkey, String secret) throws Exception {
		if (appkey == null || secret == null) {
			throw new Exception("AppKey is invalid!" + ",appkey:" + appkey + ",secret:" + secret);
		}
		this.appkey = appkey;
		this.secret = secret;
	}

	/**
	 * @param appkey
	 *            应用名称
	 * @param secret
	 *            应用的secret
	 * @param connectionTimeout
	 *            连接超时时间，单位毫秒
	 * @param idleConnections
	 *            关闭空闲连接时间，单位毫秒
	 * @param soTimeout
	 *            读取数据超时，单位毫秒
	 * @throws Exception
	 */
	public BaseSDKService(String appkey, String secret, int connectionTimeout, int idleConnections, int soTimeout) throws Exception {
		if (appkey == null || secret == null) {
			throw new Exception("AppKey is invalid!" + ",appkey:" + appkey + ",secret:" + secret);
		}
		this.appkey = appkey;
		this.secret = secret;
	}

	/**
	 * @description:提交文件给服务器
	 */
	public BaseSDKResponse postFileToServer(Object queryObject, String baseURL, String postMethod, String mFile) {
		/*
		 * ClientHttpRequest mClientHttpRequest=null; HttpURLConnection mhuc=null; BaseSDKResponse retRes=new BaseSDKResponse(); try { URL url = new
		 * URL(baseURL);
		 * mClientHttpRequest = new ClientHttpRequest(url); mhuc = (HttpURLConnection)mClientHttpRequest.getConnection();
		 * Map<String, String> paramMap = BeanUtils.describe(queryObject); paramMap.put(PARAM_METHOD, postMethod); paramMap.put(PARAM_APPKEY,
		 * this.appkey);
		 *
		 * List<String> keys = new ArrayList<String>(paramMap.keySet()); for(String param:keys){ String keyValue=paramMap.get(param);
		 * if(keyValue!=null &&
		 * (!"".equalsIgnoreCase(keyValue)) && (!"class".equalsIgnoreCase(param))){ mClientHttpRequest.setParameter(param,keyValue); } }
		 * FileInputStream fileis = new FileInputStream(mFile); mClientHttpRequest.setParameter("inputFile", mFile,fileis); fileis.close();
		 *
		 * InputStream in = null; try { in = mClientHttpRequest.post(); } catch(PortUnreachableException e){ retRes.setRc(9005);//网络端口无法到达异常; }
		 * catch(BindException e){
		 * retRes.setRc(9004);//网络绑定异常; } catch(ConnectException e){ retRes.setRc(9003);//网络连接异常; } catch(IOException e) {
		 * e.printStackTrace(); } catch( NullPointerException e) { e.printStackTrace(); } try { if( mhuc.getResponseCode() == 200 ) { String
		 * resContent = convertStreamToString(in);
		 * resContent = resContent.replaceAll("[\\s*]+", " "); resContent = resContent.replaceAll("&",
		 * "&amp;"); ParseResContent(retRes, resContent);// 解析返回结果 if (retRes.getRc() == 0) {// 如果没有出现错误，则下一步转换成对象。 retRes =
		 * XmlUtil.toEntity(BaseSDKResponse.class, new
		 * ByteArrayInputStream(resContent.getBytes("UTF-8"))); } } } catch(IOException e) { e.printStackTrace(); } }
		 * catch (IOException e) { e.printStackTrace();
		 *
		 * } catch (Exception e) { e.printStackTrace(); } finally { if( null != mhuc) { mhuc.disconnect(); } if(mClientHttpRequest != null) {
		 * mClientHttpRequest.close();
		 * mClientHttpRequest = null; } mhuc = null;
		 *
		 * try {
		 *
		 * } catch (Throwable e) { e.printStackTrace(); } } return retRes;
		 */
		return null;
	}

	public String SendFile(String filePath, String filename) {
		String retM = " ";
		String httpUrl = "http://localhost:8088/kaixin/file.htm?fileName=" + filename;
		try {
			// 准备连接URL；
			URL myURL = new URL(httpUrl);
			HttpURLConnection myHttpCon = (HttpURLConnection) myURL.openConnection();
			myHttpCon.setRequestProperty("Content-type ", "application/octet-stream ");
			// myHttpCon.setRequestProperty( "Content-length ",
			// " "+buffer.length);
			myHttpCon.setDoOutput(true);
			myHttpCon.setDoInput(true);
			myHttpCon.setRequestMethod("POST");
			myHttpCon.setUseCaches(true);
			// 输出流；
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			OutputStream raw = myHttpCon.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			// 输出到缓冲区
			byte[] buffer = new byte[2056];
			FileInputStream filein = new FileInputStream(filePath);
			DataOutputStream dataOut = new DataOutputStream(myHttpCon.getOutputStream());
			while (filein.read() != -1) {
				filein.read(buffer, 0, 2056);
				// 输出信息到服务器;
				dataOut.write(buffer);
			}
			out.flush();
			out.close();
			raw.close();
			InputStream in = myHttpCon.getInputStream();
			retM = convertStreamToString(in);
		} catch (PortUnreachableException e) {
			// logger.error("9005");//网络端口无法到达异常;
		} catch (BindException e) {
			// logger.error("9004");//网络绑定异常;
		} catch (ConnectException e) {
			// logger.error("9003");//网络连接异常;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retM;
	}
	/*
	 * public static void main(String[] args) throws Exception { //weiboSource=1, accessToken=2.00yd392C_ZT4OD2e623e5b78qJAWSB,
	 * token=9206e7f4-6ba2-43a0-a373-f9dff436d33a,
	 * //sourceIp=127.0.0.1, appkey=LMVideo, method=social.bondweiboaccount, snsUserID=2149328950,
	 * appsig=f969268b6964e1415715808560f06764} Map<String, String> params=new HashMap(); params.put("weiboSource", "1"); params.put("accessToken",
	 * "2.00yd392C_ZT4OD2e623e5b78qJAWSB"); params.put("token", "6bde12a1-4391-44e4-aef0-72816803512e"); params.put("sourceIp",
	 * "127.0.0.1"); params.put("appkey", "LMVideo"); params.put("method", "social.bondweiboaccount"); params.put("snsUserID", "2149328950");
	 * params.put("appsig",
	 * "f969268b6964e1415715808560f06764"); Map<String, String> notAppsigParamMap=new HashMap();
	 * notAppsigParamMap.put("sourceIp", "sourceIp"); notAppsigParamMap.put("appsig", "appsig");
	 *
	 * String appsig=BaseSDKService.createAppsig(params,"1275753600000","UTF-8", notAppsigParamMap,null); System.out.println("appsig:"+appsig); }
	 */
	/*
	 * public static void main(String[] args) throws Exception{ E2eUserService userService = new E2eUserService("LMVideo","1275753600000"); E2eUser
	 * inputUser=new E2eUser();
	 * //E2eResponse retRes=userService.excutePost(inputUser, "auth.login"); //System.out.println
	 * ("retRes:"+retRes.getToken()+",rc:"+retRes.getRetValue()); //inputUser.setUserId(retRes.getE2eUser().getUserId());
	 * //inputUser.setUserCode("1109260938580001");
	 * //userService.excutePost(inputUser,"auth.sychronizeBestUser"); BaseSDKResponse
	 * res=BaseSDKService.excuteGetMethod( "http://192.168.16.176:8084/osm-syn/osmsyn",
	 * "appkey=LMVideo&tablename=info_user&synfields={user_gender='m',user_headIcon='http://sohu.com.cn/12234.png'}&condition={user_id=12943899}&method=syn.update"
	 * );
	 *
	 * System.out.println("retRes:"+res.getRc()); }
	 */
	/*
	 * public static void main(String[] args) throws Exception{ BestUser b=new BestUser(); b.setTc("123456123456"); b.setTtc("XT800");
	 * b.setPlatform("android");// .setAid(aid)
	 * b.setChannel("kascend"); b.setPid("3001"); b.setAction("Register"); b.setPage("background");
	 * b.setVersion("2.0.1"); b.setEmail("abc123451@123.com"); b.setPwd("12345123"); BestResponse res=E2eUserService.excuteHttpPost(b,
	 * "http://211.136.109.231/nba/init!regLogCan.action"); System.out.println("hell"+res.getErrorCode());
	 *
	 * }
	 */
	// http://www.kascend.com:8081/osm-user/osmuser?method=auth.login&imei=353016040052960&appkey=test
	/*
	 * Map params=new HashMap();58f7e382-fd15-4b49-98b1-e3252f272530 params.put("itemtype", "1"); params.put("token",
	 * "24b23e86-8198-4bda-8558-cc8ccdb807a2"); params.put("appkey",
	 * "LMVideo"); params.put("method", "video.addcomment"); params.put("itemid", "321671");
	 * params.put("comment", ",,,,,"); params.put("playtime", "0"); Map excluedMap=new HashMap(); excluedMap.put("class", "class");
	 * excluedMap.put("appsig", "appsig");
	 */
	// String appsig=E2eUserService.createAppsig(params,
	// "1275753600000","UTF-8",excluedMap);
	// ?itemtype=1&&appkey=LMVideo&method=video.addcomment&itemid=321671&comment=,,,,,&playtime=0&appsig=002a80e0c61df12f820b908766cee20a
	// 1275753600000&appkey=LMVideo&comment=%2C%2C%2C%2C%2C&itemid=468735&itemtype=1&method=video.addcomment&playtime=0&token=680169f3-1b62-4ca5-8e68-5e4f2657d2fc
	/*
	 * E2eUserService userService=new E2eUserService("LMVideo","1275753600000",3000,3000,6000); String xmlStr=
	 * "<userInfo><IMEI>351863047309415</IMEI><latitude></latitude><longtitude></longtitude><phInfo><apkSource>220</apkSource><apkVersion>2.0.2.8527</apkVersion><id>ECLAIR</id><memSpace>29816KB</memSpace><memory>282432KB</memory><model>SAMSUNG-SGH-I897&HotSay</model><netType></netType><operator>AT&T</operator><release>2.1-update1</release><screenHeight>800px</screenHeight><screenWidth>480px</screenWidth><sdSpace>13660864KB</sdSpace><sdkVersion>7</sdkVersion></phInfo></userInfo>"
	 * ; E2eUserService.excutePostXML(
	 * "http://localhost:8080/osm-user/osmuser?method=auth.getMoreInfo&token=0003b4f0-054a-44cb-b621-fdd0cc53c3e6&appkey=test" , xmlStr);
	 *
	 * E2eUserService userService=new E2eUserService("LMVideo","1275753600000");
	 *
	 * Map params=new HashMap(); params.put("method", "getCategory");//method=video.getCategory&token=&appkey=test params.put("token",
	 * "2f243e96-353d-4e3e-8cfd-0fa03936f170");
	 * String temp=userService.createSecret(params, "1275753600000");
	 */
	// inputUser.setEmail("tanhong197703@sina.com");
	// inputUser.setImei("353833045188114");
	// inputUser.setPhnum("13588056440");
	// MD5Digest md5 = MD5Digest.getInstance("111111");
	// inputUser.setPassword(md5.toString());//("13588056441");
	// userService.excuteGetMethod(urlSb);
	/*
	 * E2eUser inputUser=new E2eUser(); inputUser.setSnsUserID("25083016"); inputUser.setAccessSecret("1111111111111111111111111111111111");
	 * inputUser.setAccessToken("813240-2834143we-82341234ad-23412341ae"); inputUser.setWeiboSource("1"); E2eResponse
	 * retRes=userService.excuteLogin(inputUser); System.out.println("retStr:"+retRes.getRetValue()); E2eUser inputSearchUser=new E2eUser();
	 * inputSearchUser.setToken(retRes.getToken());
	 */
	// inputSearchUser.setUserId(new Long(12943861));
	// inputSearchUser.setKeyword("测试");
	// retRes=userService.excuteNotifyLogout(inputSearchUser);
	// retRes=userService.excuteSearchUserList(inputSearchUser);
	// System.out.println("retStr:"+retRes.getRetValue());
	// E2eUser inputFileUser=new E2eUser();
	// inputFileUser.setToken(retRes.getToken());
	// inputFileUser.setFileName("camera-upload-photo.png");
	// retRes=userService.postFileToServer(inputFileUser,
	// "http://localhost:8086/osm-user/osmuser","auth.uploadHeadIcon",
	// "D:\\temp\\camera-upload-photo.png");
	// retRes=userService.postFileToServer(inputFileUser,"http://localhost:8086/osm-user/osmuser?",
	// "auth.reConfig", "D:/camera-upload-photo.png");
	// System.out.println("retStr:"+retRes.getRetValue());
}
