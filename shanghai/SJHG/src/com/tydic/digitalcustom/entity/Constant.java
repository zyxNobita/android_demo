package com.tydic.digitalcustom.entity;

/**
 * 所有的有意义的数字都写在这个类来。<br>
 * p.s. 写这个的时候，累死我了。O__O"…
 * 
 */
public class Constant {
	public static final int LOGINVIEW = 0;

	public static final int TAB_YWL = 10000;
	public static final int MENU_YWL = 100;
	public static final int CHILD1 = 1;
	public static final int THING1 = 40001;
	public static final int TAB1MENU1CHILD1 = TAB_YWL + MENU_YWL + CHILD1;
	public static final int TAB1MENU1CHILD1THING1 = TAB_YWL+ MENU_YWL + CHILD1
			+ THING1;
	


	public static final String[] pinyin = { "a", "ai", "an", "ang", "ao", "ba",
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

	public static final int WEBSERVICE_OVER = 0x0001;
	public static final int CONNECT_FAIL = 0x0002;
	public static final int VPN_INIT_FAIL = 0x0003;
	public static final int VPN_CONNECT_FAIL = 0x0004;
	public static final int DATE_FROM = 0;// 设置起始时间dialog编号
	public static final int DATE_TO = 1;// 设置结束时间dialog编号
	public static final int CUSTOM_CYRB = 2;// 设置结束时间dialog编号
	public static final int CUSTOM_TGWZH = 3;// 设置结束时间dialog编号
	public static final int CUSTOM_SEARCH = 4;// 对话框查询
	public static final int ZOOM_IN = 0x0003;// 放大
	public static final int ZOOM_OUT = 0x0004;// 缩小
	public static final int CHART = 0x0010;// 图表
	public static final int SHEET_IN = 0x0011;// 列表放大 
	public static final int SHEET_OUT= 0x0012;//列表缩小
	public static final int VPN= 0x0013;//vpn接入方式
	public static final int PC= 0x0014;//本地服务接入方式
	public static final String IP_81 = "http://10.62.1.81:8090/";
	//public static final String IP_BAIPC = "http://192.168.0.155:8080/";
	public static final String IP_BAIPC = "http://192.168.0.103:8080/";
	//菜单项
	public static final String ywl_zswg_lazs="23033589-AE7D-4FC8-A4AE-30B8E4444A5B";//立案走私行为案件总数
	public static final String ywl_zswg_jckhwzl="40DBE7A1-FC07-490C-97A0-A545C8C33DD3";//进出口货物重量
	
	public static final String zxgz_tgwzh_qgqrbb="271A6503-88E2-4F26-8931-AFFEBBFEDCE1";//全关区日报表
	
}
