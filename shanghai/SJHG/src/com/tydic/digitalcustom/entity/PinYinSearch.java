package com.tydic.digitalcustom.entity;


/**
 * 拼音模糊搜索
 * 
 * @author work
 * 
 */
public class PinYinSearch {

	private static String[] pinyin = { "a", "ai", "an", "ang", "ao", "ba",
			"bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian",
			"biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can",
			"cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang",
			"chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu",
			"chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong",
			"cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan",
			"dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding",
			"diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "en",
			"er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu",
			"ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng",
			"gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun",
			"guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen",
			"heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui",
			"hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin",
			"jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai",
			"kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku",
			"kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai",
			"lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian",
			"liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu",
			"lv", "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang",
			"mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie",
			"min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan",
			"nang", "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang",
			"niao", "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan",
			"nue", "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei",
			"pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po",
			"pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing",
			"qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao",
			"re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui",
			"run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen",
			"seng", "sha", "shai", "shan", "shang", "shao", "she", "shen",
			"sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang",
			"shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui",
			"sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng",
			"ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan",
			"tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen",
			"weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie",
			"xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya",
			"yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong",
			"you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang",
			"zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang",
			"zhao", "zhe", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu",
			"zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi",
			"zong", "zou", "zu", "zuan", "zui", "zun", "zuo" };

	
	/**
	 * 
	 * 列表是否有关键词项
	 * 
	 * @param key
	 * 
	 * @param result
	 * 
	 * @return
	 */
/*
	private boolean containKey(String key) {

		if (key == null || key.equals(""))
			return false;

		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < menuList.length; i++) {

			if (menuInPinyin == null) {
				menuInPinyin = new String[menuList.length];
			}
			menuInPinyin[i] = HanZiToPinYin.toPinYin(menuList[i]);
			if (pyMatches(menuInPinyin[i], key)) {

				list.add(menuList[i]);
			}
		}

		if (list.size() > 0) {
			result = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				result[i] = list.get(i);
			}
			return true;

		}

		return false;

	}
	
*/
	/**
	 * 拼音匹配
	 * 
	 * @param src
	 *            含有中文的字符串  例如 wang 王 bao 宝 qiang 墙
	 * @param des
	 *            查询的拼音 例如 wbq
	 * @return 是否能匹配拼音
	 */
	public static boolean pyMatches(String src, String des) {
		if (src != null) {
			//除空格、大小写字母外 都去掉，并转换为小写字母
			src = src.replaceAll("[^ a-zA-Z]", "").toLowerCase();
			//多个空格转换为一个
			src = src.replaceAll("[ ]+", " ");
			
			//根据输入的查询拼音 获取表达式
			String condition = getPYSearchRegExp(des, "[a-zA-Z]* ");

			/*
			 * Pattern pattern = Pattern.compile(condition); Matcher m =
			 * pattern.matcher(src);// return m.find();
			 */
			//将多个字符拆分成字符数组
			String[] tmp = condition.split("[ ]");
			String[] tmp1 = src.split("[ ]");

			for (int i = 0; i + tmp.length <= tmp1.length; ++i) {
				String str = "";
				for (int j = 0; j < tmp.length; j++)
					str += tmp1[i + j] + " ";
				if (str.matches(condition))
					return true;
			}
		}
		return false;
	}

	
	/**
	 * 拼音搜索的核心算法使用正则表达判断
	 * 
	 * @param str
	 *            搜索字符串
	 * @param exp
	 *            追加的正则表达式
	 * @return 拼音搜索正则表达式
	 */
	public static String getPYSearchRegExp(String str, String exp) {
		int start = 0;
		String regExp = "";
		str = str.toLowerCase();
		//判断首写字母
		boolean isFirstSpell = true;
		for (int i = 0; i < str.length(); ++i) {
			String tmp = str.substring(start, i + 1);
			//是拼音值为false，否则看做首写字母
			isFirstSpell = binSearch(tmp) ? false : true;

			if (isFirstSpell) {
				
				regExp += str.substring(start, i) + exp;
				start = i;
			} else {
				isFirstSpell = true;
			}

			//首写字母初次验证会截取该字母返回
			if (i == str.length() - 1)
				regExp += str.substring(start, i + 1) + exp;
		}
		return regExp;
	}

	/**
	 * 2分法查找拼音列表,判断搜索字符是否为拼音
	 * 
	 * @param str
	 *            拼音字符串
	 * @return 是否是存在于拼音列表
	 */
	private static boolean binSearch(String str) {
		int mid = 0;
		int start = 0;
		int end = pinyin.length - 1;

		while (start < end) {
			mid = start + ((end - start) / 2);
			if (pinyin[mid].matches(str + "[a-zA-Z]*"))
				return true;

			// 如果参数字符串等于此字符串，则返回 0 值；如果按字典顺序此字符串小于字符串参数，则返回一个小于 0
			// 的值；如果按字典顺序此字符串大于字符串参数，则返回一个大于 0 的值。

			if (pinyin[mid].compareTo(str) < 0)
				start = mid + 1;
			else
				end = mid - 1;
		}
		return false;
	}
}
