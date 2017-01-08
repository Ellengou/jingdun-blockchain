package com.yuyoukj.util;
public class DBConstant {
	// ==========================================================================
	public final static Integer ITEMTYPE_0 = 0; // trend
	public final static Integer ITEMTYPE_1 = 1; // photo pic
	public final static Integer TRENDTYPE_0 = 0; // trend text
	public final static Integer TRENDTYPE_1 = 1; // trend img
	public final static Integer TRENDTYPE_2 = 2; // trend voice
	public final static Integer ISSHOW_0 = 0; // class show
	public final static Integer ISSHOW_1 = 1; // class hide
	public final static Integer ISNEW_0 = 0; // 0-未加入班级
	public final static Integer ISNEW_1 = 1; // 1-加入班级
	public final static Integer SEX_0 = 0; // 0-女
	public final static Integer SEX_1 = 1; // 1-男
	public final static Integer REGISTERTYPE_0 = 0; // 0-手机
	public final static Integer REGISTERTYPE_1 = 1; // 1-邮箱
	// ==========================================================================
	public final static Integer ICONINFO_STYPE_USERICON_0 = 0; // 0-usericon；
	public final static Integer ICONINFO_STYPE_CLASSICON_1 = 1; // 1-classicon；
	public final static Integer ICONINFO_STYPE_HONOURICON_2 = 2; // 2-honouricon；
	public final static Integer ICONINFO_STYPE_BBUSERICON_3 = 3; // 3-bbusericon；
	public final static Integer ICONINFO_STYPE_ORGICON_4 = 4; // 1-orgicon；

	public final static Integer IMGINFO_CTYPE_XCARD_5 = 5; // 1-orgicon；
	// ==========================================================================
	public static final String SEVENNIU_ACCESS_KEY = "SEVENNIU_ACCESS_KEY";
	public static final String SEVENNIU_SECRET_KEY = "SEVENNIU_SECRET_KEY";
	public static final String SEVENNIU_BRXY_DOMAIN = "SEVENNIU_BRXY_DOMAIN";
	public static final String SEVENNIU_BRXY_BUCKETNAME = "SEVENNIU_BRXY_BUCKETNAME";
	// ==========================================================================
	public static final String MEM_KEY_CONFIG = "CONFIG_";
	public final static String MEM_KEY_USERID = "USERID_";
	public final static String MEM_KEY_APPKEY = "APPKEY_";
	public final static String MEM_KEY_IMGFILENAME = "MEM_IMGFILENAME_";
	// ==========================================================================
	public final static int DOWNLOAD_EXPIRES_VOICE = 60 * 60 * 24;
	public final static int DOWNLOAD_EXPIRES_IMG = 60 * 60 * 24;
	public final static int DOWNLOAD_EXPIRES_ICON = 60 * 60 * 24;
	public final static int DOWNLOAD_DEADLINE = 60 * 30;
}
