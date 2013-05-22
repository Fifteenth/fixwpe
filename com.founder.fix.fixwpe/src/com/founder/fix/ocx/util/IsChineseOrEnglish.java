/**
 * @Title: IsChineseOrEnglish.java
 * @Package com.founder.fix.studio.platformdesigner.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2012 
 * Company:方正国际软件有限公司
 * 
 * @author Comsys-Administrator
 * @date 2012-7-6 上午10:28:31
 * @version v1.0
 */
package com.founder.fix.ocx.util;

import java.util.Random;

/**
 * @ClassName: IsChineseOrEnglish
 * @Description: TODO
 * @author Comsys-wangzhiwei
 * @date 2012-7-6 上午10:28:31
 * 
 */
public class IsChineseOrEnglish {

	/**
	 * 创建一个新的实例 IsChineseOrEnglish.
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public IsChineseOrEnglish() {
		// TODO Auto-generated constructor stub
	}

	// GENERAL_PUNCTUATION 判断中文的“号
	// CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
	// HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static void isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
				System.out.println(isChinese(c));
				return;
			} else {
				System.out.println(isChinese(c));
				return;
			}
		}
	}

	public static void main(String[] args) {
		Random r = new Random();
		for (int i = 0; i < 20; i++)
			System.out.println(r.nextInt(10) + 1);
		isChinese("き");
		isChinese("中国");
	}
}
