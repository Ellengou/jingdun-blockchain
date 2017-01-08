package com.yuyoukj.mapper.qhd;

import java.util.List;
import java.util.Map;
import com.yuyoukj.model.qhd.Confratio;

public interface ConfratioMapper {
	public Confratio getConfratio(Map<String, Object> pmap);

	public List<Confratio> getConfratioList(Map<String, Object> pmap);

	public void saveConfratio(Confratio confratio);

	public void updateConfratio(Confratio confratio);

	public void updateConfratio_map(Map<String, Object> pmap);

	public void delConfratio(Map<String, Object> pmap);

	public Integer checkSname(Map<String, Object> pmap);

	public void delConfratio_map(Map<String, Object> pmap);

}
