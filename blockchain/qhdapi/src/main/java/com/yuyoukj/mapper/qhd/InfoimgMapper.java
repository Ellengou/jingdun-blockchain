package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Infoimg;

public interface InfoimgMapper {
	public Infoimg getInfoimg(Map<String, Object> pmap);

	public List<Infoimg> getInfoimgList(Map<String, Object> pmap);

	public void saveInfoimg(Infoimg infoimg);

	public void updateInfoimg(Infoimg infoimg);

	public void updateInfoimg_map(Map<String, Object> pmap);

	public void delInfoimg(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
