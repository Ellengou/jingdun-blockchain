package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.Function;
import com.yuyoukj.sys.model.FunctionSimple;

public interface FunctionMapper {

	public List<Function> getFunctionList(Map<String, Object> map);

	public void updateFunction(Function function);

	public void saveFunction(Function function);

	public void delFunction(Long id);

	public Function getFunction(Long fid);

	public Function getFunctionById(Long id);

	public void addZtree(Function function);

	public void renameZtree(Function function);

	public List<FunctionSimple> getFunctionSimpleList(Map<String, Object> pmap);

	/* public void removeZtree(Map<String, Object> pmap); */

	public void removeZtree(String pids);

	public void delInterFunRole(Long fid);

}
