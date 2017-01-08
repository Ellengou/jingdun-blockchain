package com.yuyoukj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuyoukj.ao.model.Page;
import com.yuyoukj.sys.mapper.FunctionMapper;
import com.yuyoukj.sys.model.Function;
import com.yuyoukj.sys.model.FunctionSimple;
import com.yuyoukj.sys.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionMapper functionMapper;

	Map<String, Object> pmap2 = new HashMap<String, Object>();

	@Override
	public Page<Function> getFunctionList(Page<Function> page, Map<String, Object> pmap) {

		pmap.put("page", page);
		getFunctionList(pmap);
		return page;
	}

	@Override
	public List<Function> getFunctionList(Map<String, Object> pmap) {

		return functionMapper.getFunctionList(pmap);
	}

	@Override
	public Function getFunction(Long fid) {
		return functionMapper.getFunction(fid);
	}

	@Override
	public void delFunction(Long fid) {
		functionMapper.delFunction(fid);
	}

	@Override
	public void saveFunction(Function function) {

		if (function.getFid() != null) {
			functionMapper.updateFunction(function);
		} else {
			functionMapper.saveFunction(function);

			Function function_ordery = new Function();
			function_ordery.setFid(function.getFid());
			function_ordery.setCreatedate(Long.toString(System.currentTimeMillis()));
			function_ordery.setOrderby(function.getFid());

			functionMapper.updateFunction(function_ordery);
		}
	}

	@Override
	public void addZtree(Function function) {
		functionMapper.addZtree(function);

		Function function_ordery = new Function();
		function_ordery.setFid(function.getFid());
		function_ordery.setCreatedate(Long.toString(System.currentTimeMillis()));
		function_ordery.setOrderby(function.getFid());

		functionMapper.updateFunction(function_ordery);

	}

	@Override
	public void renameZtree(Function function) {
		functionMapper.renameZtree(function);

	}

	@Override
	public List<FunctionSimple> getFunctionSimpleList(Map<String, Object> pmap) {
		return functionMapper.getFunctionSimpleList(pmap);
	}

	@Override
	public void removeZtree(String id) {
		functionMapper.removeZtree(id);
	}

	@Override
	public Function getFunctionById(Long id) {
		return functionMapper.getFunctionById(id);
	}

	@Override
	public void delInterFunRole(Long fid, Long slevel) {
		//
		if (slevel.longValue() == 1) { // 父级，先删除子级
			//
			pmap2.clear();
			pmap2.put("pid", fid);
			List<Function> functionList = functionMapper.getFunctionList(pmap2);

			if (functionList != null && functionList.size() > 0) {
				for (Function function : functionList) {
					functionMapper.delInterFunRole(function.getFid());
				}
			}
			functionMapper.delInterFunRole(fid);
		} else if (slevel.longValue() == 2) {
			functionMapper.delInterFunRole(fid);
		}
	}

	@Override
	public void delFunction(Long fid, Long slevel) {
		if (slevel.longValue() == 1) { // 父级，先删除子级
			//
			pmap2.clear();
			pmap2.put("pid", fid);
			List<Function> functionList = functionMapper.getFunctionList(pmap2);

			if (functionList != null && functionList.size() > 0) {
				for (Function function : functionList) {
					functionMapper.delFunction(function.getFid());
				}
			}
			functionMapper.delFunction(fid);
		} else if (slevel.longValue() == 2) {
			functionMapper.delFunction(fid);
		}

	}
}
