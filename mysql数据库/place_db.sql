/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : place_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-12 18:25:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `leaveword`;
CREATE TABLE `leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment,
  `leaveTitle` varchar(80) default NULL,
  `leaveContent` longtext,
  `telephone` varchar(20) default NULL,
  `userObj` varchar(20) default NULL,
  `leaveTime` varchar(30) default NULL,
  PRIMARY KEY  (`leaveWordId`),
  KEY `FKDA870221C80FC67` (`userObj`),
  CONSTRAINT `FKDA870221C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leaveword
-- ----------------------------
INSERT INTO `leaveword` VALUES ('1', '本周六来学校体育馆比赛', '本战队还没输过，哪个团队敢来挑战我们？星期六下午不来是孙子', '13980202342', 'user1', '2019-08-07 11:23:23');
INSERT INTO `leaveword` VALUES ('3', '周六下午来篮球比赛哈', '要参加的联系我电话哈', '13408082432', 'user2', '2019-08-12 18:07:34');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` int(11) NOT NULL auto_increment,
  `newsType` varchar(20) default NULL,
  `title` varchar(80) default NULL,
  `newsPhoto` varchar(50) default NULL,
  `content` longtext,
  `publishTime` varchar(30) default NULL,
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '国内篮球趣闻', '111111', 'upload/noimage.jpg', '2222222', '2019-08-08 14:22:36');
INSERT INTO `news` VALUES ('2', '国外篮球趣闻', 'bbbbb', 'upload/noimage.jpg', 'cccccc', '2019-08-08 14:12:36');

-- ----------------------------
-- Table structure for `place`
-- ----------------------------
DROP TABLE IF EXISTS `place`;
CREATE TABLE `place` (
  `placeId` int(11) NOT NULL auto_increment,
  `placeName` varchar(50) default NULL,
  `placePhoto` varchar(50) default NULL,
  `placePos` varchar(100) default NULL,
  `telephone` varchar(20) default NULL,
  `placePrice` float default NULL,
  `placeDesc` longtext,
  `onlineTime` varchar(20) default NULL,
  `topFlag` int(11) default NULL,
  `addTime` varchar(20) default NULL,
  `sellNum` int(11) NOT NULL default '0',
  PRIMARY KEY  (`placeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of place
-- ----------------------------
INSERT INTO `place` VALUES ('1', '嗨霸篮球馆', 'upload/542943AD1FF0B6273D1A58ED9A470460.jpg', '浦东新区高行镇东高路680号', '400-662-5170 转 8158', '150', '上海嗨霸篮球羽毛球运动会所成立于2014年6月24日 球馆面积1600平米。经营篮球，羽毛球运动场地。我们一片整场篮球，一片半场篮球，6个标准塑胶羽毛球场地。wifi覆盖整个球馆，为来加油的球员家属提供最优质的服务。未来2月内我们将相继建立贵宾休息室，品茶区，影音游戏试听区等。欢迎加入我们这个大家庭。', '09:00-22:00', '1', '2019-08-05 14:23:23', '1');
INSERT INTO `place` VALUES ('2', '111', 'upload/noimage.jpg', '222', '33', '44', '55', '09:00-22:00', '0', '2019-08-05 14:23:23', '0');

-- ----------------------------
-- Table structure for `placeorder`
-- ----------------------------
DROP TABLE IF EXISTS `placeorder`;
CREATE TABLE `placeorder` (
  `orderId` int(11) NOT NULL auto_increment,
  `placeObj` int(11) default NULL,
  `orderDate` datetime default NULL,
  `timeSectionObj` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `orderTime` varchar(20) default NULL,
  `shenHeState` varchar(20) default NULL,
  `shenHeTime` varchar(20) default NULL,
  PRIMARY KEY  (`orderId`),
  KEY `FK661526A710FB6E95` (`timeSectionObj`),
  KEY `FK661526A7F71A9ED5` (`placeObj`),
  KEY `FK661526A7C80FC67` (`userObj`),
  CONSTRAINT `FK661526A710FB6E95` FOREIGN KEY (`timeSectionObj`) REFERENCES `timesection` (`sectionId`),
  CONSTRAINT `FK661526A7C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`),
  CONSTRAINT `FK661526A7F71A9ED5` FOREIGN KEY (`placeObj`) REFERENCES `place` (`placeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of placeorder
-- ----------------------------
INSERT INTO `placeorder` VALUES ('1', '1', '2019-08-30 00:00:00', '1', 'user1', '2019-08-08 14:22:34', '审核通过', '2019-08-12 17:20:24');
INSERT INTO `placeorder` VALUES ('2', '2', '2019-08-12 00:00:00', '2', 'user1', '2019-08-12 16:56:10', '待审核', '--');

-- ----------------------------
-- Table structure for `timesection`
-- ----------------------------
DROP TABLE IF EXISTS `timesection`;
CREATE TABLE `timesection` (
  `sectionId` int(11) NOT NULL auto_increment,
  `sectionName` varchar(40) default NULL,
  PRIMARY KEY  (`sectionId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of timesection
-- ----------------------------
INSERT INTO `timesection` VALUES ('1', '上午场');
INSERT INTO `timesection` VALUES ('2', '下午场');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '王夏明', '男', '2019-08-07 00:00:00', 'upload/noimage.jpg', '13508312432', 'xiaming@126.com', '成都红星路', '2019-08-06 14:11:12');
INSERT INTO `userinfo` VALUES ('user2', '123', '李明', '男', '2019-08-12 00:00:00', 'upload/noimage.jpg', '13408429342', 'liming@163.com', '四川成都滨江路', '2019-08-12 14:25:21');

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `videoId` int(11) NOT NULL auto_increment,
  `title` varchar(80) default NULL,
  `videoTypeObj` int(11) default NULL,
  `videoPhoto` varchar(50) default NULL,
  `content` longtext,
  `sportPos` varchar(20) default NULL,
  `videoFile` varchar(50) default NULL,
  `hitNum` int(11) default NULL,
  `publishTime` varchar(20) default NULL,
  PRIMARY KEY  (`videoId`),
  KEY `FK4ED245B310D0D35` (`videoTypeObj`),
  CONSTRAINT `FK4ED245B310D0D35` FOREIGN KEY (`videoTypeObj`) REFERENCES `videotype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '起跳勾手投篮', '2', 'upload/54CD085FA11BB63202C7652D28C506AA.jpg', '练习步骤\r\n①在篮筐下持球站立。\r\n②向篮板掷球，动作要领同“双手 接球”，在空中接住被 篮板弹下的篮球。落地时背部正 对篮筐。\r\n③随后“90度前转”，使 身体侧面对着篮筐，然后“起跳 勾手投篮”。', '111', 'upload/7B4653F3ABC58CE7F26122CF1CF9D0EB.mp4', '1', '2019-08-06 15:32:11');

-- ----------------------------
-- Table structure for `videotype`
-- ----------------------------
DROP TABLE IF EXISTS `videotype`;
CREATE TABLE `videotype` (
  `typeId` int(11) NOT NULL auto_increment,
  `typeName` varchar(20) default NULL,
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of videotype
-- ----------------------------
INSERT INTO `videotype` VALUES ('1', '运球');
INSERT INTO `videotype` VALUES ('2', '投篮');
INSERT INTO `videotype` VALUES ('3', '进阶教学');
