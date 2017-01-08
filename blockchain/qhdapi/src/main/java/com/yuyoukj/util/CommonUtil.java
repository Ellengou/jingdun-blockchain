package com.yuyoukj.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class CommonUtil {

	public static void printmap(Map<String, Object> pmap) {
		if (pmap == null || pmap.size() == 0 || pmap.isEmpty()) {
			return;
		}

		try {
			System.out.println();
			Iterator<String> it = pmap.keySet().iterator();
			while (it.hasNext()) {
				String st = it.next();
				System.out.println("======" + st + ": " + pmap.get(st));
			}
			System.out.println();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	/**
	 * 字符填充
	 * 
	 * @param len
	 * @return
	 */
	public static String fillchar(int len) {
		if (len <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append("*");
		}
		return sb.toString();
	}

	public static void forceMkdir(File directory) throws IOException {

		if (directory.exists()) {
			if (directory.isFile()) {
				String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
				throw new IOException(message);
			}
		} else {
			if (!directory.mkdirs()) {
				String message = "Unable to create directory " + directory;
				throw new IOException(message);
			}
		}
	}

	/**
	 * 隐藏手机号
	 * 
	 * @param phonenum
	 * @return
	 */
	public static String hidePhonenum(String phonenum) {
		String str = "";

		if (phonenum == null || phonenum.length() == 0) {
			return "";
		}

		int len = phonenum.length();
		if (len >= 7) {
			return phonenum.substring(0, 3) + fillchar(4) + phonenum.substring(len - 4, len);
		} else if (len < 7 && len >= 3) {
			return phonenum.substring(0, 3) + fillchar(8);
		} else if (len < 3) {
			return phonenum + fillchar(11 - len);
		}

		return str;
	}

	/**
	 * 判断字符串是否全部为数字（ascii码）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_ascii(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否全部为数字（java自带函数）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_java(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否全部为数字（正则）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_regular(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean uploadFile(File src, String uploadpath, String targetname, boolean delflag) {

		File fDir = new File(uploadpath);
		if (!fDir.exists()) {
			try {
				forceMkdir(fDir);
			} catch (IOException e) {
				return false;
			}
		}

		if (!src.isFile() || !src.exists()) {
			return false;
		}

		try {
			copyFile(src, new File(uploadpath + targetname));
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		} catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}

		if (delflag) {
			src.delete();
		}

		return true;
	}
}
