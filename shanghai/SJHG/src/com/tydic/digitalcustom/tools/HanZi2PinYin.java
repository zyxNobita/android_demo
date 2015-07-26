/**
 * HanZi2PinYin.java [v 1.0.0]
 * classes: com.tydic.digitalcustom.tools.HanZi2PinYin
 * 兮兮 Create at 2013-7-9 上午10:09:32
 */
package com.tydic.digitalcustom.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * com.tydic.digitalcustom.tools.HanZi2PinYin
 * 
 * @author 兮兮（bai） <br>
 *         creat at 2013-7-9 上午10:09:32
 * 
 */
public class HanZi2PinYin {

	/**
	 * 
	 * @param hanziString 中国梦
	 * @return pinyin 中 zhong 国 guo 梦 meng 
	 */
	public static String toMixPinYin(String hanziString) {
		char hanzi[] = hanziString.toCharArray();
		HanyuPinyinOutputFormat hanyuPinyin = new HanyuPinyinOutputFormat();
		// 大小写
		hanyuPinyin.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 尼玛，这个可以获得声调
		hanyuPinyin.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		// 汉语拼音中的v该怎么处理
		hanyuPinyin.setVCharType(HanyuPinyinVCharType.WITH_V);
		String[] pinyinArray = null;
		String result = null;
		for (int i = 0; i < hanzi.length; i++) {
			try {

				// 是否在汉字范围内
				if (hanzi[i] >= 0x4e00 && hanzi[i] <= 0x9fa5) {
					pinyinArray = PinyinHelper.toHanyuPinyinStringArray(
							hanzi[i], hanyuPinyin);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			if (result != null) {
				result = result + " " + hanzi[i] + " " + pinyinArray[0];
			} else {
				result = hanzi[i] + " " + pinyinArray[0];
			}
		}

		// 将获取到的拼音返回
		return result;
	}

}
