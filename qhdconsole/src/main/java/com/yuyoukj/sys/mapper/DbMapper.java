package com.yuyoukj.sys.mapper;

import java.util.List;
import java.util.Map;
import com.yuyoukj.sys.model.DbTables;
import com.yuyoukj.sys.model.DbTbColumns;
import com.yuyoukj.sys.model.Dbs;

public interface DbMapper {
	public List<Dbs> getDbList();

	public DbTables getDbTables(Map<String, Object> pmap);

	public List<DbTables> getDbTablesList(Map<String, Object> pmap);

	public List<DbTbColumns> getDbTableColumnList(Map<String, Object> pmap);

}
