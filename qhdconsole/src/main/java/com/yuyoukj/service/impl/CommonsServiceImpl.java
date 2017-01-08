package com.yuyoukj.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.service.CommonsService;
import com.yuyoukj.sys.mapper.CKVMapper;
import com.yuyoukj.sys.model.CKV;

@Service
public class CommonsServiceImpl implements CommonsService {

	private static Logger log = Logger.getLogger(CommonsServiceImpl.class);

	@Autowired
	private CKVMapper ckvMapper;

	//	@Autowired
	//	private InfoadsMapper infoadsMapper;

	@Override
	public List<CKV> getCKVList(Map<String, Object> pmap) {
		return ckvMapper.getCKVList(pmap);
	}

	//	@Override
	//	public void saveposition(Map<String, Object> pmap) {
	//		if (pmap.get("posflag") == null) {
	//			return;
	//		}
	//
	//		String posflag = (String) pmap.get("posflag");
	//		if (posflag.equals("pos_wxmenu")) { //
	//			pos_wxmenu(pmap);
	//		} else {
	//
	//		}
	//	}

	//	private void pos_wxmenu(Map<String, Object> pmap) {
	//		Map<String, Object> pmap2 = new HashMap<String, Object>();
	//		try {
	//			Integer sortMethod = (Integer) pmap.get("SORT_METHOD");
	//
	//			Wxmenu wxmenu = null;
	//			List<Wxmenu> wxmenulist = null;
	//			int pos = -1;
	//
	//			switch (sortMethod) {
	//				case 0: //置顶
	//					pmap2.clear();
	//					pmap2.put("menuid", pmap.get("menuid"));
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenu = wxmenuMapper.getWxmenu(pmap2);
	//					if (wxmenu == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenulist = wxmenuMapper.getWxmenuList(pmap2);
	//					if (wxmenulist == null || wxmenulist.size() == 0) {
	//						return;
	//					}
	//
	//					if (wxmenu.getMenuid().longValue() == wxmenulist.get(0).getMenuid().longValue()) {
	//						return;
	//					}
	//
	//					pmap2.clear(); //
	//					pmap2.put("menuid", wxmenu.getMenuid());
	//					if (wxmenulist.get(0).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", (wxmenulist.get(0).getOrderby().longValue() - 1));
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					wxmenuMapper.updateWxmenu_map(pmap2);
	//
	//					break;
	//				case 1: //上移动一位'
	//					pmap2.clear();
	//					pmap2.put("menuid", pmap.get("menuid"));
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenu = wxmenuMapper.getWxmenu(pmap2);
	//					if (wxmenu == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenulist = wxmenuMapper.getWxmenuList(pmap2);
	//					if (wxmenulist == null || wxmenulist.size() == 0) {
	//						return;
	//					}
	//
	//					pos = -1;
	//					for (int i = 0; i < wxmenulist.size(); i++) {
	//						if (wxmenu.getMenuid().longValue() == wxmenulist.get(i).getMenuid().longValue()) {
	//							pos = i;
	//							break;
	//						}
	//					}
	//
	//					if (pos == -1 || pos == 0) {
	//						return;
	//					}
	//					pos = pos - 1;
	//
	//					//
	//					pmap2.clear(); //本身
	//					pmap2.put("menuid", wxmenu.getMenuid());
	//					if (wxmenulist.get(pos).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", wxmenulist.get(pos).getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					wxmenuMapper.updateWxmenu_map(pmap2);
	//
	//					//
	//					pmap2.clear(); //上一条
	//					pmap2.put("menuid", wxmenulist.get(pos).getMenuid());
	//					if (wxmenu.getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", wxmenu.getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					wxmenuMapper.updateWxmenu_map(pmap2);
	//
	//					break;
	//				case 2:
	//					pmap2.clear();
	//					pmap2.put("menuid", pmap.get("menuid"));
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenu = wxmenuMapper.getWxmenu(pmap2);
	//					if (wxmenu == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("slevel", pmap.get("slevel"));
	//					pmap2.put("appid", pmap.get("appid"));
	//					wxmenulist = wxmenuMapper.getWxmenuList(pmap2);
	//					if (wxmenulist == null || wxmenulist.size() == 0) {
	//						return;
	//					}
	//
	//					pos = -1;
	//					for (int i = 0; i < wxmenulist.size(); i++) {
	//						if (wxmenu.getMenuid().longValue() == wxmenulist.get(i).getMenuid().longValue()) {
	//							pos = i;
	//							break;
	//						}
	//					}
	//
	//					if (pos == -1 || pos == (wxmenulist.size() - 1)) {
	//						return;
	//					}
	//					pos = pos + 1;
	//
	//					//
	//					pmap2.clear(); //本身
	//					pmap2.put("menuid", wxmenu.getMenuid());
	//					if (wxmenulist.get(pos).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", wxmenulist.get(pos).getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					wxmenuMapper.updateWxmenu_map(pmap2);
	//
	//					//
	//					pmap2.clear(); //下一条
	//					pmap2.put("menuid", wxmenu.getMenuid());
	//					if (wxmenu.getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", wxmenu.getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					wxmenuMapper.updateWxmenu_map(pmap2);
	//
	//					break;
	//				default:
	//					break;
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			return;
	//		}
	//	}

	//	@Override
	//	public void saveadsposition(Map<String, Object> pmap) {
	//		if (pmap.get("posflag") == null) {
	//			return;
	//		}
	//
	//		String posflag = (String) pmap.get("posflag");
	//		if (posflag.equals("pos_adsimg")) { //
	//			pos_adsimg(pmap);
	//		} else {
	//
	//		}
	//
	//	}

	//	private void pos_adsimg(Map<String, Object> pmap) {
	//		Map<String, Object> pmap2 = new HashMap<String, Object>();
	//		try {
	//			Integer sortMethod = (Integer) pmap.get("SORT_METHOD");
	//
	//			Infoads infoads = null;
	//			List<Infoads> infoadslist = null;
	//			int pos = -1;
	//
	//			switch (sortMethod) {
	//				case 0: //置顶
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					pmap2.put("imgid", pmap.get("imgid"));
	//					infoads = infoadsMapper.getInfoadsimg(pmap2);
	//					if (infoads == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					infoadslist = infoadsMapper.getInfoadsimgList(pmap2);
	//					if (infoadslist == null || infoadslist.size() == 0) {
	//						return;
	//					}
	//
	//					if (infoads.getSeq().longValue() == infoadslist.get(0).getSeq().longValue()) {
	//						return;
	//					}
	//
	//					pmap2.clear(); //
	//					pmap2.put("seq", infoads.getSeq());
	//					if (infoadslist.get(0).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", (((Integer) infoadslist.get(0).getOrderby()).longValue() - 1));//
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					infoadsMapper.updateInfoadsimg_map(pmap2);
	//
	//					break;
	//				case 1: //上移动一位'
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					pmap2.put("imgid", pmap.get("imgid"));
	//					infoads = infoadsMapper.getInfoadsimg(pmap2);
	//					if (infoads == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					infoadslist = infoadsMapper.getInfoadsimgList(pmap2);
	//					if (infoadslist == null || infoadslist.size() == 0) {
	//						return;
	//					}
	//
	//					pos = -1;
	//					for (int i = 0; i < infoadslist.size(); i++) {
	//						if (infoads.getSeq().longValue() == infoadslist.get(i).getSeq().longValue()) {
	//							pos = i;
	//							break;
	//						}
	//					}
	//
	//					if (pos == -1 || pos == 0) {
	//						return;
	//					}
	//					pos = pos - 1;
	//
	//					//
	//					pmap2.clear(); //本身
	//					pmap2.put("seq", infoads.getSeq());
	//					if (infoadslist.get(pos).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", infoadslist.get(pos).getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					infoadsMapper.updateInfoadsimg_map(pmap2);
	//
	//					//
	//					pmap2.clear(); //上一条
	//					pmap2.put("seq", infoadslist.get(pos).getSeq());
	//					if (infoads.getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", infoads.getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					infoadsMapper.updateInfoadsimg_map(pmap2);
	//
	//					break;
	//				case 2:
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					pmap2.put("imgid", pmap.get("imgid"));
	//					infoads = infoadsMapper.getInfoadsimg(pmap2);
	//					if (infoads == null) {
	//						return;
	//					}
	//
	//					pmap2.clear();
	//					pmap2.put("adsid", pmap.get("adsid"));
	//					pmap2.put("localid", pmap.get("localid"));
	//					infoadslist = infoadsMapper.getInfoadsimgList(pmap2);
	//					if (infoadslist == null || infoadslist.size() == 0) {
	//						return;
	//					}
	//
	//					pos = -1;
	//					for (int i = 0; i < infoadslist.size(); i++) {
	//						if (infoads.getSeq().longValue() == infoadslist.get(i).getSeq().longValue()) {
	//							pos = i;
	//							break;
	//						}
	//					}
	//
	//					if (pos == -1 || pos == (infoadslist.size() - 1)) {
	//						return;
	//					}
	//					pos = pos + 1;
	//
	//					//
	//					pmap2.clear(); //本身
	//					pmap2.put("seq", infoads.getSeq());
	//					if (infoadslist.get(pos).getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", infoadslist.get(pos).getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					infoadsMapper.updateInfoadsimg_map(pmap2);
	//
	//					//
	//					pmap2.clear(); //下一条
	//					pmap2.put("seq", infoadslist.get(pos).getSeq());
	//					if (infoads.getOrderby() == null) {
	//						pmap2.put("orderby", 0);
	//					} else {
	//						pmap2.put("orderby", infoads.getOrderby());
	//					}
	//					pmap2.put("updatedate", System.currentTimeMillis());
	//					infoadsMapper.updateInfoadsimg_map(pmap2);
	//
	//					break;
	//				default:
	//					break;
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			return;
	//		}
	//	}

}
