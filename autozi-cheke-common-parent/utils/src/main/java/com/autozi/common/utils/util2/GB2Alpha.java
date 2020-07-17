package com.autozi.common.utils.util2;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * GB 2312java汉字转拼音-80 把收录的汉字分成两级。第一级汉字是常用汉字，计 3755 个，<br/>
 * 
 * 置于 16～55 区，java 汉字 拼音按汉语拼音字母／笔形顺序排列；第二级汉字是次常用汉字，<br/>
 * 
 * 计 3008 个，置于 56～87 区，按部汉字转拼音首字母首／笔画顺序排列，所以本程序只能查到<br/>
 * 
 * 对一汉字拼音首字母级汉字的声母。同时对符合声母（zh，ch，sh）只能取首字母（z，c，s）
 */
// 汉字拼音字母表
public class GB2Alpha {

	private static Logger logger = Logger.getLogger(GB2Alpha.class);
	//国标汉字拼音字母码和区位码转换常量
	private static final int GB_SP_DIFF = 160;

	//存放国标一级汉字不同读获取汉字首字母音的起始区位码
	private static final int[] secPosValueList = {
	1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
	3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
	4390, 4558, 4684, 4925, 5249, 5600 };

	//存放国标一级汉字不同读音的起始区位码对应读音
	private static final char[] firstLetter = {
	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
	'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
	't', 'w', 'x', 'y', 'z'};


    public static void main(String[] args) {
        System.out.println(getFirstLetter("AQ109地方"));
    }

    /**
       * 提取每个汉字的首字母
       *
       * @param str
       * @return String
       */
       public static String getFirstLetter(String str) {
           String convert = "";
           for (int j = 0; j < str.length(); j++) {
               char word = str.charAt(j);
               // 提取汉字的首字母
               String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
               if (pinyinArray != null) {
                   convert += pinyinArray[0].charAt(0);
               } else {
                   convert += word;
               }
           }
           return convert.toLowerCase();
       }
       

	/**
	 * 获取一个字符串的首字母拼音码<br/>
	 * 
	 * @param oriStr 不能为null<br/>
	 * 
	 * 例如： GB2Alpha.getFirstLetter("I love 北京天安门")，输出 结果为：i love bjtam
	 * */
	public static String getFirstLetterOld(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = null;
			try {
				uniCode = new String(temp).getBytes("GB2312");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。<br/>
	 * 
	 * GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码<br/>
	 * 
	 * 例如汉字"你"的GB码是0xC4/0xE3，分别减去0xA0（160）就是0×24/0×43		<br/>
	 * 
	 * 0×24转成10进制就是36，0×43是67，那么它的区位码就是3667，在对照表中读音为'n'
	 */
	private static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}
	
	/**
	 * 判断输入是否为汉字，只检查字符串的第一个字符
	 * @param input 待检查字符串
	 * @author 金龙
	 * @return	
	 * false:非汉字
	 * true：汉字
	 */
	public static boolean checkInputIsChinese(String input) {
		if (input == null || input.equals("")) {
			return false;
		}
		char ch = input.charAt(0);
		if (ch >= 0x0000 && ch <= 0x00FF) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public static String getFirstChar(String str) {
 		 if(org.apache.commons.lang.StringUtils.isBlank(str)) return null;
 		 if(str.length()==1) return str;
 		 if(checkInputIsChinese(str)){
 			char word = str.charAt(0);
 	 	    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
 	 	    return String.valueOf(pinyinArray[0].charAt(0));
 		 }else{
 			return String.valueOf(str.charAt(0));
 		 }
 	 }
	
}
