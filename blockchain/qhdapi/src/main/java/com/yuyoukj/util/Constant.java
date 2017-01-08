package com.yuyoukj.util;
public class Constant {
	public static final Integer NOTICE_MAX_LENGTH = 120; // 班级公告最大长度。超过生成公告详细内容
	public static final String NOTICE_STUFF = "..."; // 超出班级公告最大长度，简短公告内容后缀填充
	public static final Long MESSAGE_INTERVAL = 20 * 1000L; // 间隔20s
	public final static long SEVENNIU_UPTOKEN_EXPIRES = 3600 * 1L; // 7牛文件上传凭证有效时间：s,默认1H
	public final static long SEVENNIU_UPTOKEN_FSIZELIMIT = 1024 * 1024 * 10L; // 7牛文件上传凭证文件大小限制：默认10M
	public final static String TABLE_INFO_TREND = "info_trend";
	public final static String TABLE_INFO_TRENDCOMMENT = "info_trendcomment";
	public final static String TABLE_INFO_TRENDIMG = "info_trendimg";
	public final static String TABLE_INFO_TRENDCOLLECT = "info_trendcollect";
	public final static String TABLE_INFO_USER = "info_user";
	public final static String TABLE_INFO_USERNAME = "info_username";
	public final static String TABLE_INTER_CLASS_USER = "inter_class_user";
	public final static String TABLE_INTER_USER_CLASS = "inter_user_class";
	public final static String TABLE_INFO_MESSAGE = "info_message";
	public final static String TABLE_SEPARATE = "_";
	public final static Integer TABLENUM = 100;
	public final static String WORD_CLASS_CREATE = "恭喜nickname创建班级成功！";
	public final static String WORD_CLASS_JOIN = "欢迎nickname加入班级！";
	public final static String WORD_CLASS_JOIN_YES = "同意加入班级";
	public final static String WORD_CLASS_JOIN_NO = "拒绝加入班级";
	public final static String WORD_CLASS_ASK_JOIN = "nickname申请加入classname。";
	public final static String WORD_CLASS_INVITE_JOIN = "nickname邀请您加入classname。";
	public final static String SYSTEM_NOTICE_USERID = "SYSTEM_NOTICE_USERID"; // 系统虚拟用户：贝儿公告
	public final static String SYSTEM_NOTICE_NICKNAME = "SYSTEM_NOTICE_NICKNAME"; // 系统虚拟用户：贝儿公告
	public final static String SYSTEM_NOTICE_USERICON = "SYSTEM_NOTICE_USERICON"; // 系统虚拟用户：贝儿公告

	public final static int CLASS_TEACHER_MAX_NUM = 5; // 每个班最多5个老师

	public final static long SAMPLE_CLASSID = 1; // 示范班级

}
