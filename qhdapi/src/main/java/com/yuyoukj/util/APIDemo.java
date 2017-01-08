package com.yuyoukj.util;
/*
 * package com.ks.flurry.main;
 *
 * import java.io.BufferedReader; import java.io.IOException; import
 * java.io.InputStream; import java.io.InputStreamReader; import java.util.List;
 *
 * import org.apache.http.client.HttpClient; import org.apache.log4j.Logger;
 * import org.springframework.http.HttpMethod;
 *
 * public class APIDemo {
 *
 * private static Logger logger = Logger.getLogger(APIDemo.class);
 *
 * public static void main(String args[]) {
 *
 * Long starttime = System.currentTimeMillis();
 *
 * MultiThreadedHttpConnectionManager connectionManager = new
 * MultiThreadedHttpConnectionManager(); HttpClient client = new
 * HttpClient(connectionManager); HttpConnectionManagerParams managerParams =
 * client.getHttpConnectionManager().getParams();
 * managerParams.setConnectionTimeout(30 * 1000);// 设置连接超时时间(单位毫秒)
 * managerParams.setSoTimeout(120 * 1000);// 设置读数据超时时间(单位毫秒)
 *
 * managerParams.setParameter(HttpMethodParams.USER_AGENT,
 * "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803"); //
 * 设置代理服务器地址和端口 //
 * client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
 *
 * String s =
 * "http://s2s.adwaken.com:8090/wqs2s_test/getad?dev=test&as=b2cb835fe8fd761a60e42038f08a7847&pf=android&pkg=com.wqmobile.s2s&key=b29910bd773fdd5b4a2442b384e84b40"
 * ;
 *
 * StringBuffer resBuffer = new StringBuffer(); try { HttpMethod method = new
 * GetMethod(s.toString()); // HttpMethod method = new PostMethod(s.toString());
 * client.executeMethod(method);
 *
 * InputStream resStream = method.getResponseBodyAsStream(); BufferedReader br =
 * new BufferedReader(new InputStreamReader( resStream));
 *
 * String resTemp = ""; while ((resTemp = br.readLine()) != null) {
 * resBuffer.append(resTemp); }
 *
 * br.close(); resStream.close();
 *
 * method.releaseConnection(); managerParams.clear();
 * connectionManager.shutdown(); } catch (IOException ex) {
 * System.out.println(s.toString()); logger.error(s.toString()); } finally {
 * managerParams.clear(); connectionManager.shutdown(); }
 *
 * String response = resBuffer.toString(); System.out.println(response);
 *
 * AdsData adsdata = new Gson().fromJson(response, AdsData.class);
 *
 * System.out.println(adsdata.getStatus());
 *
 * System.out.println("time(ms): " + (System.currentTimeMillis() - starttime));
 * logger.debug("time: " + (System.currentTimeMillis() - starttime)); } }
 *
 * class AdsData { private String status; private boolean test_mode; private
 * Extend extend; private List<Ads> ads;
 *
 * public String getStatus() { return status; }
 *
 * public void setStatus(String status) { this.status = status; }
 *
 * public boolean isTest_mode() { return test_mode; }
 *
 * public void setTest_mode(boolean test_mode) { this.test_mode = test_mode; }
 *
 * public Extend getExtend() { return extend; }
 *
 * public void setExtend(Extend extend) { this.extend = extend; }
 *
 * public List<Ads> getAds() { return ads; }
 *
 * public void setAds(List<Ads> ads) { this.ads = ads; }
 *
 * }
 *
 * class Ads { private Long ad_id; private String type; private Integer width;
 * private Integer height; private String url; private String duration; private
 * String start_time; private String end_time; private String start_date;
 * private String end_date; private Integer weight; private Integer limit_view;
 * private Long adgroup_id; private Integer adgroup_limit_view; private Integer
 * ad_limit_weight; private List<Beacons> beacons; private List<String[]>
 * time_frame;
 *
 * public List<String[]> getTime_frame() { return time_frame; }
 *
 * public void setTime_frame(List<String[]> time_frame) { this.time_frame =
 * time_frame; }
 *
 * public Long getAd_id() { return ad_id; }
 *
 * public void setAd_id(Long ad_id) { this.ad_id = ad_id; }
 *
 * public String getType() { return type; }
 *
 * public void setType(String type) { this.type = type; }
 *
 * public Integer getWidth() { return width; }
 *
 * public void setWidth(Integer width) { this.width = width; }
 *
 * public Integer getHeight() { return height; }
 *
 * public void setHeight(Integer height) { this.height = height; }
 *
 * public String getUrl() { return url; }
 *
 * public void setUrl(String url) { this.url = url; }
 *
 * public String getDuration() { return duration; }
 *
 * public void setDuration(String duration) { this.duration = duration; }
 *
 * public String getStart_time() { return start_time; }
 *
 * public void setStart_time(String start_time) { this.start_time = start_time;
 * }
 *
 * public String getEnd_time() { return end_time; }
 *
 * public void setEnd_time(String end_time) { this.end_time = end_time; }
 *
 * public String getStart_date() { return start_date; }
 *
 * public void setStart_date(String start_date) { this.start_date = start_date;
 * }
 *
 * public String getEnd_date() { return end_date; }
 *
 * public void setEnd_date(String end_date) { this.end_date = end_date; }
 *
 * public Integer getWeight() { return weight; }
 *
 * public void setWeight(Integer weight) { this.weight = weight; }
 *
 * public Integer getLimit_view() { return limit_view; }
 *
 * public void setLimit_view(Integer limit_view) { this.limit_view = limit_view;
 * }
 *
 * public Long getAdgroup_id() { return adgroup_id; }
 *
 * public void setAdgroup_id(Long adgroup_id) { this.adgroup_id = adgroup_id; }
 *
 * public Integer getAdgroup_limit_view() { return adgroup_limit_view; }
 *
 * public void setAdgroup_limit_view(Integer adgroup_limit_view) {
 * this.adgroup_limit_view = adgroup_limit_view; }
 *
 * public Integer getAd_limit_weight() { return ad_limit_weight; }
 *
 * public void setAd_limit_weight(Integer ad_limit_weight) {
 * this.ad_limit_weight = ad_limit_weight; }
 *
 * public List<Beacons> getBeacons() { return beacons; }
 *
 * public void setBeacons(List<Beacons> beacons) { this.beacons = beacons; }
 *
 * }
 *
 * class Extend { private Integer _floor;
 *
 * public Integer get_floor() { return _floor; }
 *
 * public void set_floor(Integer _floor) { this._floor = _floor; } }
 *
 * class Beacons { private String type; private String url;
 *
 * public String getType() { return type; }
 *
 * public void setType(String type) { this.type = type; }
 *
 * public String getUrl() { return url; }
 *
 * public void setUrl(String url) { this.url = url; } }
 */
