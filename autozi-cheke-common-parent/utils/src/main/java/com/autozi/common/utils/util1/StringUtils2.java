package com.autozi.common.utils.util1;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.util.Assert;

/**
 * 字符工具类
 * 
 * @author Ropod
 * 
 */
public final class StringUtils2 extends org.springframework.util.StringUtils {

	public static String[] letters={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	/**
	 * 转化字符编码
	 * 
	 * @param str
	 *            字符串
	 * @param fromEncoding
	 *            源编码
	 * @param toEncoding
	 *            转化编码
	 * @return
	 */
	public static String convertStringEncoding(String str, String fromEncoding,
			String toEncoding) {
		try {
			String s = new String(str.getBytes(fromEncoding), toEncoding);
			return s;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 将字符转换为UTF-8编码
	 * 
	 * @param str
	 * @return
	 */
	public static String convertStringToUTF8(String str) {
		return convertStringEncoding(str, "ISO8859-1", "UTF-8");
	}

	/**
	 * 将一个字符串转换为字符串数组
	 * 
	 * @param str
	 *            被转换的字符串
	 * @param regex
	 *            分隔符
	 * @return
	 */
	public static String[] strConvertArray(String str, String regex) {
		Assert.hasText(str, "转换的字符串不能为空");
		Assert.hasText(regex, "分隔符不能为空");
		return str.split(regex);
	}

	/**
	 * 查询数组中是否存在指定的元素
	 * 
	 * @param array
	 * @param str
	 * @return
	 */
	public static boolean arrayHaveStr(Object[] array, Object obj) {
		Assert.noNullElements(array, "数组中没有任何元素");
		Assert.notNull(obj, "要查找的元素不能为空");
		for (Object o : array) {
			if (o.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public static String camel2underline(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_"
					+ mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	public static String getFirstLetter(String str) {
		if (org.apache.commons.lang.StringUtils.isBlank(str))
			return null;
//		if (str.length() == 1)
//			return str;
		char word = str.charAt(0);
		if (String.valueOf(word).matches("[\\u4E00-\\u9FA5]+")) {
			
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			return String.valueOf(pinyinArray[0].charAt(0));
		}else{
			return String.valueOf(word);
		}
	}


	/**
	 * 字符串集合转换字符串(逗号分隔)
	 */
	public static String makeStringByStringSet(Set<String> stringSet) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + ",");
			}
			i++;
		}
		return str.toString().toLowerCase();
	}

	/**
	 * 获取拼音集合
	 * 
	 * @param src
	 * @return Set<String>
	 */
	public static Set<String> getPinyin(String src) {
		if (org.apache.commons.lang.StringUtils.isBlank(src)) {
			return null;
		}
		char[] srcChar = src.toCharArray();

		// 汉语拼音格式输出类
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

		// 输出设置，大小写，音标方式等
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		String[][] temp = new String[src.length()][];
		for (int i = 0; i < srcChar.length; i++) {
			char c = srcChar[i];
			// 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
			if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
				try {
					temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i],
							hanYuPinOutputFormat);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				temp[i] = new String[] { String.valueOf(srcChar[i]) };
			}

		}
		String[] pingyinArray = Exchange(temp);
		Set<String> pinyinSet = new HashSet<String>();
		for (int i = 0; i < pingyinArray.length; i++) {
			pinyinSet.add(pingyinArray[i]);
		}
		return pinyinSet;
	}

	public static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}
}
