/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : school_finish

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2021-03-29 17:47:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `classId` int(11) NOT NULL,
  `className` varchar(20) NOT NULL,
  `createDate` date NOT NULL,
  `teacher` varchar(20) NOT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('1711', 'GT11', '2021-03-25', '卤蛋');
INSERT INTO `t_class` VALUES ('1722', 'GT22', '2021-04-15', '张三');
INSERT INTO `t_class` VALUES ('1823', 'GT23', '2021-04-15', '菜更花');
INSERT INTO `t_class` VALUES ('1925', 'GT25', '2021-03-17', '膨胀');
INSERT INTO `t_class` VALUES ('1926', 'GT26', '2021-03-21', '花花');
INSERT INTO `t_class` VALUES ('48725', 'GT28', '2021-03-10', '李红');

-- ----------------------------
-- Table structure for `t_room`
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room` (
  `roomId` int(11) NOT NULL,
  `f_roomAddress` varchar(50) NOT NULL,
  `f_recipientNumber` int(11) NOT NULL,
  `f_status` enum('正常','设施损坏') NOT NULL DEFAULT '正常',
  `f_roomType` enum('男生宿舍','女生宿舍') NOT NULL DEFAULT '男生宿舍',
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_room
-- ----------------------------
INSERT INTO `t_room` VALUES ('1470', 'b1-B104', '6', '正常', '男生宿舍');
INSERT INTO `t_room` VALUES ('4560', 'b1-A101', '6', '正常', '女生宿舍');

-- ----------------------------
-- Table structure for `t_roomfacility`
-- ----------------------------
DROP TABLE IF EXISTS `t_roomfacility`;
CREATE TABLE `t_roomfacility` (
  `facilityId` int(11) NOT NULL,
  `f_damageInfo` longtext NOT NULL,
  `f_damageDate` date NOT NULL,
  `f_resolveStatus` enum('已解决','未解决') NOT NULL DEFAULT '未解决',
  `f_roomId` int(11) NOT NULL,
  PRIMARY KEY (`facilityId`),
  KEY `f_roomId` (`f_roomId`),
  CONSTRAINT `t_roomfacility_ibfk_1` FOREIGN KEY (`f_roomId`) REFERENCES `t_room` (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_roomfacility
-- ----------------------------
INSERT INTO `t_roomfacility` VALUES ('5', '的女孩奶u拿电脑电脑上电脑你说的都弄i为你放弃我IC电脑i的', '2021-03-09', '已解决', '1470');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `studentId` int(20) NOT NULL,
  `f_name` varchar(20) NOT NULL,
  `f_sex` enum('男','女') NOT NULL,
  `f_classId` int(11) NOT NULL,
  `f_roomId` int(11) NOT NULL,
  `f_phone` varchar(11) NOT NULL,
  `f_face` longtext NOT NULL,
  `f_joinDate` date NOT NULL,
  PRIMARY KEY (`studentId`),
  KEY `f_classId` (`f_classId`),
  KEY `f_roomId` (`f_roomId`),
  CONSTRAINT `t_student_ibfk_1` FOREIGN KEY (`f_classId`) REFERENCES `t_class` (`classId`),
  CONSTRAINT `t_student_ibfk_2` FOREIGN KEY (`f_roomId`) REFERENCES `t_room` (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('165165', '长安常安', '男', '1925', '1470', '1654165131', 'F:\\IDEwork\\School_Finish_1\\target\\School_Finish_1\\uploads\\4646d2b999b2448f8d16a66a5320de40-510112199804023016_处理.jpg', '2021-03-24');
INSERT INTO `t_student` VALUES ('4857527', '江明好地方关闭', '男', '1722', '1470', '13699439273', 'F:\\IDEwork\\School_Finish_1\\target\\School_Finish_1\\uploads\\39a9d0ee05794ee4a7742b44f256311f-510112199804023016_处理.jpg', '2021-03-31');
INSERT INTO `t_student` VALUES ('14562122', '张翠花', '女', '1823', '4560', '13699439273', 'F:\\IDEwork\\School_Finish_1\\target\\School_Finish_1\\uploads\\e237dfbbf0924bd6ad6dbe71c80fb935-510112199804023016_处理.jpg', '2021-03-17');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL,
  `f_userName` varchar(20) NOT NULL,
  `f_password` varchar(20) NOT NULL,
  `f_realName` varchar(20) NOT NULL,
  `f_grade` enum('班主任','房间管理员','系统管理员','超级管理员') NOT NULL DEFAULT '班主任',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('5654', '西西', '123456', '张三', '系统管理员');
INSERT INTO `t_user` VALUES ('85771', '陈真', '123456', '田甜', '房间管理员');
