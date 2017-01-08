package www.yuyoukj.third.ratio;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import com.yuyoukj.util.StrUtils;

public class Getratio {
	private static Logger logger = Logger.getLogger(Getratio.class);
	// 公用
	private static final String ENCODE_UTF8 = "UTF-8";
	private static final String ENCODE_GBK = "gbk";
	private static final String ENCODE_ISO8859 = "iso-8859-1";

	private static final String surl = "http://srh.bankofchina.com/search/whpj/search.jsp";

	public static void main(String[] args) throws Exception {
		getration();
	}

	public static Retratio getration() {

		Retratio ret = null;

		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient httpClient = new HttpClient(connectionManager);

		HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		managerParams.setConnectionTimeout(30 * 1000);
		managerParams.setSoTimeout(120 * 1000);
		managerParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODE_UTF8);

		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter("http.protocol.single-cookie-header", true);
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

		HttpMethod postMethod = null;
		HttpRet httpret = null;

		Map<String, String> pmap = new HashMap<String, String>();
		pmap.clear();
		pmap.put("erectDate", "");
		pmap.put("nothing", "");
		pmap.put("pjname", 1316 + "");

		httpret = new HttpRet();
		httpret = HttpGetPostMethod.postUrl(surl, pmap, httpClient, postMethod);
		if (httpret == null || httpret.getStatuscode() != HttpStatus.SC_OK) {
			logger.error("===error: 访问失败！" + surl);
			return null;
		}
		if (httpret.getResponseInfo() != null && httpret.getResponseInfo().length() > 0) {
			try {
				ret = new Retratio();
				ret = doRation(httpret.getResponseInfo());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// //////////////////////////////////////////////////////////////
		if (httpClient != null)
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		managerParams.clear();
		connectionManager.shutdown();

		return ret;
	}

	private static Retratio doRation(String responseinfo) {
		Retratio ret = null;

		if (StrUtils.isBlank(responseinfo)) {
			return null;
		}

		Parser parser = null;
		NodeFilter filter = null;
		NodeList list = null;

		parser = Parser.createParser(responseinfo, "GBK");
		filter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "BOC_main publish"));
		try {
			list = parser.extractAllNodesThatMatch(filter);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Node node = list.elementAt(i);

					parser = Parser.createParser(node.toHtml(), "GBK");
					filter = new NodeClassFilter(TableTag.class);
					list = parser.extractAllNodesThatMatch(filter);
					for (int ia = 0; ia < list.size(); ia++) {
						TableTag table = (TableTag) list.elementAt(ia);

						if (table.getRowCount() > 1) {
							TableRow row = table.getRow(1);
							TableColumn[] columns = row.getColumns();

							if (columns.length == 8) {
								ret = new Retratio();
								ret.setD1(columns[0].toPlainTextString().trim());
								ret.setD2(columns[1].toPlainTextString().trim());
								ret.setD3(columns[2].toPlainTextString().trim());
								ret.setD4(columns[3].toPlainTextString().trim());
								ret.setD5(columns[4].toPlainTextString().trim());
								ret.setD6(columns[5].toPlainTextString().trim());
								ret.setD7(columns[6].toPlainTextString().trim());
								ret.setD8(columns[7].toPlainTextString().trim());

								return ret;
							}
						}

						//						for (int ib = 0; ib < table.getRowCount(); ib++) {
						//							TableRow row = table.getRow(ib);
						//							TableColumn[] columns = row.getColumns();
						//							for (int ic = 0; ic < columns.length; ic++) {
						//								System.out.println(columns[ic].toPlainTextString().trim());
						//							}
						//						}
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
