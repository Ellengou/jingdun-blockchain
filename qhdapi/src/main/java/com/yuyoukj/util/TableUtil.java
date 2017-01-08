package com.yuyoukj.util;
public class TableUtil {
	private final static Integer SUFFIX_LEN = 3;

	public static String getTableName(String table_prefix, Integer classid) {
		if (table_prefix == null || table_prefix.length() == 0) {
			return table_prefix;
		}
		return table_prefix + Constant.TABLE_SEPARATE + getTableSuffix(Math.abs(classid) % Constant.TABLENUM);
	}

	/**
	 * 根据规则获取分表后的表名
	 * 
	 * @param table_prefix
	 * @param classid
	 * @return
	 */
	public static String getTableName(String table_prefix, Long classid) {
		if (table_prefix == null || table_prefix.length() == 0) {
			return table_prefix;
		}
		return table_prefix + Constant.TABLE_SEPARATE + getTableSuffix(Math.abs(classid) % Constant.TABLENUM);
	}

	private static String getTableSuffix(Integer mod) {
		if ((mod.toString()).length() > SUFFIX_LEN) {
			return mod.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < (SUFFIX_LEN - (mod.toString()).length()); i++) {
				sb.append("0");
			}
			sb.append(mod.toString());
			return sb.toString();
		}
	}

	/**
	 * 获取分表后的表名数字后缀
	 * 
	 * @param mod
	 * @return
	 */
	private static String getTableSuffix(Long mod) {
		if ((mod.toString()).length() > SUFFIX_LEN) {
			return mod.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < (SUFFIX_LEN - (mod.toString()).length()); i++) {
				sb.append("0");
			}
			sb.append(mod.toString());
			return sb.toString();
		}
	}
}
