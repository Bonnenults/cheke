package com.autozi.cheke.util.web;

/**
 * User: long.jin
 * Date: 2018-02-08
 * Time: 15:07
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 敏感词过滤
 * @Project：test
 * @Author : chenming
 * @Date ： 2014年4月20日 下午4:17:15
 * @version 1.0
 */
public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则

	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitivewordFilter(){
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}

	/**
	 * 判断文字是否包含敏感字符
	 * @author chenming
	 * @date 2014年4月20日 下午4:28:30
	 * @param txt  文字
	 * @param matchType  匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * @author chenming
	 * @date 2014年4月20日 下午5:10:52
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();

		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * @author chenming
	 * @date 2014年4月20日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * @author chenming
	 * @date 2014年4月20日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}

		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * @author chenming
	 * @date 2014年4月20日 下午4:31:03
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true
					if(SensitivewordFilter.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}

	public static void main(String[] args) {
		SensitivewordFilter filter = new SensitivewordFilter();
		System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
		String string = "\n" +
                "\n" +
                "    百度首页\n" +
                "    long_tony\n" +
                "    消息\n" +
                "    商城\n" +
                "\n" +
                "网页 新闻 贴吧 知道 音乐 图片 视频 地图 文库 百科\n" +
                "帮助\n" +
                "声明：百科词条人人可编辑，词条创建和修改均免费，绝不存在官方及代理商付费代编，请勿上当受骗。详情>>\n" +
                "\n" +
                "首页\n" +
                "\n" +
                "分类\n" +
                "    艺术\n" +
                "    科学\n" +
                "    自然\n" +
                "    文化\n" +
                "    地理\n" +
                "    生活\n" +
                "    社会\n" +
                "    人物\n" +
                "    经济\n" +
                "    体育\n" +
                "    历史\n" +
                "\n" +
                "特色百科\n" +
                "    历史上的今天\n" +
                "    数字博物馆\n" +
                "    史记·2016\n" +
                "    城市百科\n" +
                "    二战百科\n" +
                "    非遗百科\n" +
                "\n" +
                "用户\n" +
                "    蝌蚪团\n" +
                "    燃梦计划\n" +
                "    百科任务\n" +
                "    百科商城\n" +
                "\n" +
                "权威合作\n" +
                "    合作模式\n" +
                "    常见问题\n" +
                "    联系方式\n" +
                "\n" +
                "手机百科\n" +
                "    网页版\n" +
                "\n" +
                "个人中心\n" +
                "收藏\n" +
                "36\n" +
                "83\n" +
                "\n" +
                "    Spring Boot 编辑 \n" +
                "\n" +
                "本词条缺少名片图，补充相关内容使词条更完整，还能快速升级，赶紧来编辑吧！\n" +
                "Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。通过这种方式，Spring Boot致力于在蓬勃发展的快速应用开发领域(rapid application development)成为领导者。\n" +
                "\n" +
                "外文名\n" +
                "    Spring Boot \n" +
                "语    言\n" +
                "    Java \n" +
                "\n" +
                "设计目的\n" +
                "    简化新Spring应用的搭建及开发 \n" +
                "应用领域\n" +
                "    应用开发 \n" +
                "\n" +
                "目录\n" +
                "\n" +
                "    1 特点\n" +
                "    2 安装步棸\n" +
                "\n" +
                "特点\n" +
                "编辑\n" +
                "1. 创建独立的Spring应用程序\n" +
                "2. 嵌入的Tomc成人BT\n" +
                "免注册淫色电影\n" +
                "UltraSurf\n" +
                "鐵血三國志\n" +
                "性爱图库\n" +
                "公安\n" +
                "777黄站\n" +
                "大龟头顶住嫂嫂的花心深处\n" +
                "插 穴 射 喷 精液\n" +
                "兽奸at，无需部署WAR文件\n" +
                "3. 简化Maven配置\n" +
                "4. 自动配置Spring\n" +
                "5. 提供生产就绪型功能，如指标，健康检查和外部配置\n" +
                "6. 绝对没有代码生成和对XML没有要求配置[1] \n" +
                "安装步棸\n" +
                "编辑\n" +
                "从最根本上来讲，Spring Boot就是一些库的集合，它能够被任意项目的构建系统所使用。简便起见，该框架也提供了命令行界面，它可以用来运行和测试Boot应用。框架的发布版本，包括集成的CLI（命令行界面），可以在Spring仓库中手动下载和安装。一种更为简便的方式是使用Groovy环境管理器（Groovy enVironment Manager，GVM），它会处理Boot版本的安装和管理。Boot及其CLI可以通过GVM的命令行gvm install springboot进行安装。在OS X上安装Boot可以使用Homebrew包管理器。为了完成安装，首先要使用brew tap pivotal/tap切换到Pivotal仓库中，然后执行brew install springboot命令。\n" +
                "要进行打包和分发的工程会依赖于像Maven或Gradle这样的构建系统。为了简化依赖图，Boot的功能是模块化的，通过导入Boot所谓的“starter”模块，可以将许多的依赖添加到工程之中。为了更容易地管理依赖版本和使用默认配置，框架提供了一个parent POM，工程可以继承它。\n" +
                "\n" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" \n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"         \n" +
                "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 \n" +
                "http://maven.apache.org/xsd/maven-4.0.0.xsd\">   \n" +
                " <modelVersion>4.0.0</modelVersion>  \n" +
                "  <groupId>com.example</groupId>    \n" +
                "<artifactId>myproject</artifactId>    \n" +
                "<version>1.0.0-SNAPSHOT</version>  \n" +
                "  <!-- Inherit defaults from Spring Boot -->    <parent>      \n" +
                "  <groupId>org.springframework.boot</groupId>       \n" +
                " <artifactId>spring-boot-starter-parent</artifactId>       \n" +
                "\n" +
                " <version>1.0.0.RC1</version>    </parent>   \n" +
                " <!-- Add typical dependencies for a web application -->  \n" +
                "  <dependencies>        <dependency>           \n" +
                " <groupId>org.springframework.boot</groupId>         \n" +
                "   <artifactId>spring-boot-starter-web</artifactId>     \n" +
                "   </dependency>        <dependency>        \n" +
                "    <groupId>org.springframework.boot</groupId>      \n" +
                "      <artifactId>spring-boot-starter-actuator</artifactId> \n" +
                "       </dependency>    </dependencies>  \n" +
                "  <repositories>        <repository>         \n" +
                "\n" +
                "   <id>spring-snapshots</id>            \n" +
                "<url>http://repo.spring.io/libs-snapshot</url>       \n" +
                " </repository>    </repositories>   \n" +
                " <pluginRepositories>   \n" +
                "     <pluginRepository>      \n" +
                "      <id>spring-snapshots</id>         \n" +
                "   <url>http://repo.spring.io/libs-snapshot</url>    \n" +
                "    </pluginRepository>   \n" +
                " </pluginRepositories>    <build>    \n" +
                "    <plugins>            <plugin>             \n" +
                "   <groupId>org.springframework.boot</groupId>             \n" +
                "   <artifactId>spring-boot-maven-plugin</artifactId>     \n" +
                "       </plugin>        </plugins>    </build></project>\n" +
                "\n" +
                "参考资料\n" +
                "\n" +
                "        1.\n" +
                "          \n" +
                "        Spring Boot  ．Spring官网[引用日期2016-12-02]\n" +
                "\n" +
                "V百科\n" +
                "往期回顾\n" +
                "\n" +
                "相关人物\n" +
                "纠错\n" +
                "克里斯廷娜·碧曼诺娃\n" +
                "\n" +
                "克里斯廷娜·碧曼诺娃\n" +
                "黎活明\n" +
                "\n" +
                "黎活明\n" +
                "java之父\n" +
                "\n" +
                "java之父\n" +
                "gavin king\n" +
                "\n" +
                "gavin king\n" +
                "范凯\n" +
                "\n" +
                "范凯\n" +
                "洪强宁\n" +
                "\n" +
                "洪强宁\n" +
                "张孝祥\n" +
                "\n" +
                "张孝祥\n" +
                "方立勋\n" +
                "\n" +
                "方立勋\n" +
                "相关术语\n" +
                "纠错\n" +
                "跑步膝\n" +
                "\n" +
                "跑步膝\n" +
                "groovy\n" +
                "\n" +
                "groovy\n" +
                "maven\n" +
                "\n" +
                "maven\n" +
                "postgresql\n" +
                "\n" +
                "postgresql\n" +
                "paas\n" +
                "\n" +
                "paas\n" +
                "spring batch\n" +
                "\n" +
                "spring batch\n" +
                "计算机类书籍\n" +
                "纠错\n" +
                "groovy入门经典\n" +
                "\n" +
                "groovy入门经典\n" +
                "redis入门指南\n" +
                "\n" +
                "redis入门指南\n" +
                "restful web services中文版\n" +
                "\n" +
                "restful web services中文版\n" +
                "相关问题\n" +
                "\n" +
                "    spring boot和spring的区别\n" +
                "    如何构建spring boot\n" +
                "    spring boot在eclipse中怎么用\n" +
                "    spring boot有什么用\n" +
                "    如何部署Spring Boot应用\n" +
                "\n" +
                "来自百度知道|查看更多>\n" +
                "\n" +
                "词条统计\n" +
                "\n" +
                "        浏览次数：354987次\n" +
                "        编辑次数：7次历史版本\n" +
                "        最近更新：2018-01-31\n" +
                "        创建者：轩辕冰烆\n" +
                "\n" +
                "猜你关注\n" +
                "\n" +
                "    b级车排行榜\n" +
                "    北京口腔医院排名\n" +
                "    b级车推荐\n" +
                "    结婚快一年了不怀孕怎么回事\n" +
                "    结婚两年没有怀孕怎么办\n" +
                "    结婚5年半老婆不怀孕\n" +
                "    结婚多久没怀孕需要检查\n" +
                "    真实可靠的网络兼职\n" +
                "    精通java需要多久\n" +
                "    结婚一年为什么没有怀孕\n" +
                "\n" +
                "新手上路\n" +
                "    成长任务\n" +
                "    编辑入门\n" +
                "    编辑规则\n" +
                "    百科术语\n" +
                "\n" +
                "我有疑问\n" +
                "    我要质疑\n" +
                "    在线客服\n" +
                "    参加讨论\n" +
                "    意见反馈\n" +
                "\n" +
                "投诉建议\n" +
                "    举报不良信息\n" +
                "    未通过词条申诉\n" +
                "    投诉侵权信息\n" +
                "    封禁查询与解封\n" +
                "\n" +
                "©2018 Baidu 使用百度前必读 | 百科协议 | 百度百科合作平台 | 京ICP证030173号 \n" +
                "\n" +
                "京公网安备11000002000001号\n" +
                "分享\n";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = filter.getSensitiveWord(string, 1);
		long endTime = System.currentTimeMillis();
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共消耗时间为：" + (endTime - beginTime));

		System.out.println(6 % 2);
	}
}