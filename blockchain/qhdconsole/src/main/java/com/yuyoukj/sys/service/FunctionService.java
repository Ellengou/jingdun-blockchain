package com.yuyoukj.sys.service;

import java.util.List;
import java.util.Map;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.model.Function;
import com.yuyoukj.sys.model.FunctionSimple;

public interface FunctionService {

	public Page<Function> getFunctionList(Page<Function> page, Map<String, Object> pmap);

	public List<Function> getFunctionList(Map<String, Object> pmap);

	public Function getFunction(Long fid);

	public void delFunction(Long fid);

	public void saveFunction(Function function);

	public void addZtree(Function function);

	public void renameZtree(Function function);

	public List<FunctionSimple> getFunctionSimpleList(Map<String, Object> pmap);

	/* public void removeZtree(Map<String, Object> pmap); */

	public void removeZtree(String id);

	public Function getFunctionById(Long id);

	public void delInterFunRole(Long fid, Long slevel);

	public void delFunction(Long fid, Long slevel);

}
