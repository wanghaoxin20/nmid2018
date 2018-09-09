/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-09 12:25:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog_info
-- ----------------------------
DROP TABLE IF EXISTS `blog_info`;
CREATE TABLE `blog_info` (
  `id` bigint(10) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_info
-- ----------------------------
INSERT INTO `blog_info` VALUES ('4', '这是标签', '2018-08-30 19:17:37');
INSERT INTO `blog_info` VALUES ('6', '这是编辑测试', '2018-08-30 00:00:00');
INSERT INTO `blog_info` VALUES ('7', '这是个测试', '2018-08-30 00:00:00');
INSERT INTO `blog_info` VALUES ('8', '这是个测试', '2018-08-30 00:00:00');
INSERT INTO `blog_info` VALUES ('9', '这是个测试', '2018-08-30 00:00:00');
INSERT INTO `blog_info` VALUES ('18', '人民日报海外版：警惕日本借“威胁”之名威胁世界', '2018-09-01 18:38:00');
INSERT INTO `blog_info` VALUES ('19', '首款5G手机要来了！华为发布麒麟980，创造6项全球第一，支持5G！', '2018-09-01 19:08:13');
INSERT INTO `blog_info` VALUES ('21', 'testsetsetset', '2018-09-05 12:57:33');
INSERT INTO `blog_info` VALUES ('22', '日官员称北海道地震致21人死亡 救援人员全力搜救', '2018-09-08 23:04:09');

-- ----------------------------
-- Table structure for blog_text
-- ----------------------------
DROP TABLE IF EXISTS `blog_text`;
CREATE TABLE `blog_text` (
  `blog_id` bigint(10) DEFAULT NULL,
  `text` text,
  KEY `blog_text_ibfk_1` (`blog_id`),
  CONSTRAINT `blog_text_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog_text
-- ----------------------------
INSERT INTO `blog_text` VALUES ('4', '										1、谁创建servlet？\r\n\r\nA：由读取web.xml文件的服务器软件来进行创建\r\n\r\n2、何时创建servlet对象？\r\n\r\nA：1）web程序，服务器端只有一个，但是访问的客户端数量没法控制\r\n\r\n2）客户端访问的时间没有办法限制\r\n\r\n在Tomcat服务器端，一个Servlet类一次只创建一个servlet对象，该对象通过线程（对用户使用该servlet的时间进行分段）使用\r\n\r\n3）servlet对象创建的时间有两个：\r\n\r\n（1）服务器启动时创建，多出现在项目运行阶段，会检查所有当前服务器上的项目，检测项目中web.xml文件，在检测的同时，看到<servlet-class>，就会自动的创建对象，并且保持在内存中。\r\n\r\n（2）当用户第一次访问服务器上一个新的servlet时，在开发过程中。\r\n\r\n4）生命周期：\r\n\r\nservlet的生命周期：只servlet对象在服务器内存中从创建到调用，到销毁的整个过程，主要研究高过程中哪些方法对我们开发有用。\r\n\r\n（1）实例化：当客户端通过URL请求的时候，web容器根据web.xml配置自动调用该servlet的构造方法，实例化对象。\r\n\r\n（2）初始化：该servlet对象调用init()方法，读取web.xml中该servlet的配置信息，为service()方法提供相关数据。 \r\n\r\n（3）服务：通过该对象调用service()方法，如果是继承HttpServlet，则根据请求头信息中的请求方法，调用对象的doGet()/doPost()\r\n\r\n（4）销毁：不是在service（）方法调用完后，立即调用，而是由JVM来决定。当JVM需要销毁一些对象，释放内存空间的时候，才会去调用该实例的destroy()方法。\r\n									');
INSERT INTO `blog_text` VALUES ('6', '【环球网综合报道】8月31日，中国驻美大使崔天凯参加了美国战略与国际问题研究中心(CSIS)与人大重阳圆桌论坛，并在午餐会发表了讲话。崔天凯在讲话中称，“我奉劝那些认为可以将另一个“广场协议”强加给中国，认为中国会屈服于恐吓、威胁和无端指责的人放弃幻想。”崔天凯同时也谈到了中美关系。\r\n									');
INSERT INTO `blog_text` VALUES ('7', '这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈');
INSERT INTO `blog_text` VALUES ('8', '这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈');
INSERT INTO `blog_text` VALUES ('9', '这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈这是个测试哈哈');
INSERT INTO `blog_text` VALUES ('18', '原标题：警惕日本借“威胁”之名威胁世界（环球热点）\r\n\r\n9月，日本迎来自民党总裁选举。现任日本首相安倍晋三正在争取历史性的第3次连任。目前民调显示，一向怀揣修宪“梦想”的安倍连任可能性极大。于是，安倍政府开始紧锣密鼓地推动修宪进程。反复炒作周边威胁，为强军修宪铺路，安倍政府带领着一个“绝不道歉的日本”一路狂奔。\r\n\r\n借口，都是借口\r\n\r\n日本又在炒作周边威胁。近日，日本政府批准2018年版《防卫白皮书》，继续渲染日本周边安保环境日趋严峻。报道称，日本政府将以引进陆上部署型导弹拦截系统“陆基宙斯盾系统”为中心，力争2018年年末推进新防卫力建设指针《防卫计划大纲》的制定工作。\r\n\r\n朝鲜与中国再次成为日本主要针对的“靶子”。日本明治学院大学国际和平研究所研究员石田隆至表示，2018年《防卫白皮书》大肆渲染“朝鲜与中国的威胁”，这构成了整个白皮书的基调。第一部分第二章介绍各国防卫政策共118页，其中朝鲜22页，中国35页，共占了将近一半篇幅。\r\n\r\n除了借海洋问题渲染所谓的“中国威胁”，朝鲜也受到了“重点关照”。针对已趋缓和的朝鲜半岛局势，《防卫白皮书》虽肯定了朝方在美朝峰会中“以文书形式明确表达实现无核化意愿”的意义，但仍不忘继续鼓吹朝鲜对日本是“史无前例且迫在眉睫的威胁”，强调日本对朝鲜的核及导弹威胁的认识并未改变。\r\n\r\n在渲染周边威胁的同时，安倍政府的修宪步子也越来越实。最近，日本首相安倍晋三正式宣布将参加9月的自民党总裁选举，争取历史性的第3次连任。《东京新闻》报道称，令人担忧的是，已经确保七成议员票、再次连任几乎没有悬念的安倍对修改宪法第九条变得积极起来。安倍表示，他计划在2018年秋季的临时国会上提交修正案。\r\n\r\n野心，还是野心\r\n\r\n加强军备，为成为名副其实的军事大国铺路；修改宪法，为成为所谓的正常国家努力。日本安倍政府一意孤行的背后是昭然若揭的野心。\r\n\r\n日本此轮炒作周边威胁，日本媒体都看不下去了。《东京新闻》指出，白皮书之所以煽动朝鲜威胁，这是为了从美国高额购买并部署陆基宙斯盾反导装备。《日本经济新闻》称，如果改变朝鲜对日本造成威胁的论述，安倍政府一直以来以应对朝鲜威胁为由增强防卫能力的做法将失去依据。日本防卫费自2015年以来连续4年创新高，防卫省计划在2019财年支出再创新高的5.3万亿日元。\r\n\r\n此次提高调门炒作“朝鲜威胁”还有另一个重要因素。据美国有线电视新闻网报道，日前，朝鲜媒体指责日本“不惜一切代价”破坏朝鲜半岛的和平努力。日本上智大学政治学教授中野浩一一针见血地指出：“安倍生怕自己在朝鲜半岛问题上被边缘化，为此他真的非常努力。他担心，在半岛问题上他没有任何决定权，却要承担后果。”\r\n\r\n除了针对朝鲜，日本《防卫白皮书》继续渲染“中国威胁论”，对中国的常规军事活动及正当国防建设说三道四、妄加评论，恶意炒作军事透明度等问题，肆意歪曲中国海警船巡航钓鱼岛海域等正当行为，称中国“单方面升级”在日本周边的军事活动。\r\n\r\n分析普遍指出，日本渲染周边威胁，其实是为了自身强军修宪制造借口。\r\n\r\n反对，很多反对\r\n\r\n日裔美国国家发展学家、美国约翰·霍普金斯大学高级国际关系学院院长弗朗西斯·福山曾这样评价安倍与前任的区别：“如果说他们（安倍晋三与小泉纯一郎）之间有什么不同的话，那么安倍这次复出是更有决心要建立一个固执己见并且绝不道歉的日本。”\r\n\r\n这正是令周边国家乃至世界警惕的地方。\r\n\r\n日本市民团体“继承与发展村山谈话会”理事长藤田高景表示：“《防卫白皮书》颠倒事实、继续炒作‘中国威胁论’，这必将遭到两国人民的强烈反对。”');
INSERT INTO `blog_text` VALUES ('19', '昨日，华为麒麟980在德国柏林2018消费电子展正式发布。据华为官方消息公布，麒麟980创造了6项全球第一，且支持5G。此次麒麟980各方面卓越的性能，看了让人热血沸腾，不禁大呼：厉害了华为！\r\n\r\n麒麟980这6方面均全球第一！\r\n此次华为麒麟980给我们带来的六项全球第一分别是：\r\n全球第一个商用7nm工艺SoC。\r\n此前早有传闻麒麟980采用的是目前最先进的台积电7nm制程工艺，随着麒麟980的发布证实了确实如此。此前已发布的手机芯片基本上都是采用10nm制程工艺，麒麟980则是全球首款采用7nm制程工艺的手机芯片。\r\n\r\n可能会有人疑惑7nm、10nm是什么意思呢？这是芯片制程工艺的具体数值，这个数值是手机性能关键的指标，制程工艺提升意味着手机性能能效将得以增强。麒麟980较上一代性能提升20%，能效提升40%，晶体管密度提升了1.6倍！\r\n全球第一个基于ARM A76架构开发的CPU。\r\n此前ARM公开CPU业务路线图时，大家就可以窥见Cortex - A76的诞生将可以带来多大的改变，它可以将设备性能水平提升至笔记本电脑级别，让更多功能可以在手机上得以实现。\r\n麒麟980在全球首次实现基于Cortex-A76的开发商用，带来了处理器表现提升75%，能效提升58%的改变，让手机无论是运行速度还是持久度上都有了质的提升。');
INSERT INTO `blog_text` VALUES ('21', '																				sdfjflks\r\n测试测试\r\n									\r\n									');
INSERT INTO `blog_text` VALUES ('22', '2018年09月08日 12:26　来源：中国新闻网 参与互动　\r\n\r\n\r\n视频：日本北海道地震引发的次生灾害加剧破坏程度  来源：央视新闻\r\n\r\n　　中新网9月8日电 综合报道，当地时间8日早上，日本官房长官菅义伟在记者会上表示，北海道地震目前已经导致21人死亡，6人心肺功能停止，另有13人失联。\r\n\r\n　　菅义伟公布的数字与北海道方面有所区别。此前，北海道政府消息称，地震导致19人死亡，11人心肺功能停止，另有9人失联。\r\n\r\n点击进入下一页\r\n当地时间2018年9月6日，日本北海道地区发生强震，图为地震导致多处建筑倒塌。\r\n　　北海道强震发生后已经进入第3日，逼近黄金救援72小时期限，自卫队、警察和消防队等单位全力搜救中。\r\n\r\n　　另一方面，余震频发发生。据日本气象厅公布，截至当地时间7日下午6时，共观测到有感地震100多次。气象厅指出，在震感强烈的地区，今后一周左右应严加防范。\r\n\r\n　　此外，受锋面影响，胆振地区等上空有雨云笼罩，7日晚到8日，北海道各地可能出现间歇性雷雨。气象厅指出，在震感强烈的地区，由于地基松动，降雨引发地质灾害的可能性进一步增大，因此，呼吁人们保持高度警惕。\r\n\r\n点击进入下一页\r\n当地时间9月6日凌晨3时8分左右，日本北海道发生6.7级地震。当日此次地震致北海道全境约295万户居民停电。\r\n　　6日，强震一度造成整个北海道停电，北海道电力公司表示，经过其他未受灾的发电厂重启运作，今天上午整个北海道将可恢复供电，不过，北海道政府认为未来还可能有大规模停电的风险，除进行相关准备工作，也呼吁民众尽可能节约用电。\r\n\r\n　　日本放送协会(NHK)报导，北海道电力公司在8日凌晨2时宣布，目前已可供应300万千瓦的电力，整个北海道295万户家庭，已有2922000户有电可用，复电比率达99%。\r\n\r\n　　而在交通方面，7日上午北海道新千岁机场国内航线已恢复，国际航线预计最早在8日便可恢复。北海道新干线列车7日中午再次开始运营，札幌市市营地铁也于当天下午全线恢复运营。\r\n\r\n');

-- ----------------------------
-- Table structure for id
-- ----------------------------
DROP TABLE IF EXISTS `id`;
CREATE TABLE `id` (
  `next_id` bigint(10) NOT NULL,
  PRIMARY KEY (`next_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of id
-- ----------------------------
INSERT INTO `id` VALUES ('23');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('test1', 'test1');
INSERT INTO `user` VALUES ('test2', 'test2');

-- ----------------------------
-- Table structure for userblog
-- ----------------------------
DROP TABLE IF EXISTS `userblog`;
CREATE TABLE `userblog` (
  `username` varchar(10) DEFAULT NULL,
  `blog_id` bigint(10) DEFAULT NULL,
  KEY `userblog_ibfk_1` (`username`),
  KEY `userblog_ibfk_2` (`blog_id`),
  CONSTRAINT `userblog_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE,
  CONSTRAINT `userblog_ibfk_2` FOREIGN KEY (`blog_id`) REFERENCES `blog_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userblog
-- ----------------------------
INSERT INTO `userblog` VALUES ('test1', '4');
INSERT INTO `userblog` VALUES ('test1', '6');
INSERT INTO `userblog` VALUES ('test1', '7');
INSERT INTO `userblog` VALUES ('test1', '8');
INSERT INTO `userblog` VALUES ('test1', '9');
INSERT INTO `userblog` VALUES ('test1', '18');
INSERT INTO `userblog` VALUES ('test1', '19');
INSERT INTO `userblog` VALUES ('test1', '21');
INSERT INTO `userblog` VALUES ('test1', '22');
