package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Confarea;

public interface ConfareaMapper {
	public Confarea getConfarea(Map<String, Object> pmap);

	public List<Confarea> getConfareaList(Map<String, Object> pmap);

	public void saveConfarea(Confarea confarea);

	public void updateConfarea(Confarea confarea);

	public void updateConfarea_map(Map<String, Object> pmap);

	public void delConfarea(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

}
