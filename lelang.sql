/*
Navicat MySQL Data Transfer

Source Server         : MYLOKAL
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : lelang

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2017-12-21 16:42:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `kode` varchar(32) NOT NULL,
  `nama` varchar(64) NOT NULL DEFAULT '',
  `alamat` varchar(300) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `telp` varchar(64) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('ADM009', '23859b583a6d0669b957830a223c8d46', '20dfe14552d466ec8a1d00246f58ebaa', 'a729ca23826cf12a51acb907c5101d82', '0f918610c9e42e3428d320af8b0d4632', 'd4c63737a8c619f47a6c1da0a1d868cb', '59561ba61a57c7c3331a44d5dc98b464', 'Aktif');
INSERT INTO `admin` VALUES ('ADM007', '7c7e7f3db6c8869b50c62249f6eb7f4b', '19fb516c4eb26738b329cf71392b67a4', '6f19608d8cb16dc00a21d1482cc75bdb', '0b8b5877509a319a4c63986cc153e037', 'e788b6fa8e24d9ae5d62d8dc93b88bbc', 'e788b6fa8e24d9ae5d62d8dc93b88bbc', 'Aktif');
INSERT INTO `admin` VALUES ('ADM008', '865124774e0324e7090d31e6c445bb42', 'af85d209050a7a6c832090844e93ce8a', '85a6cec94335cad457b5d1d48fb64698', '1d9186be295e74efc699897597926ad1', '865124774e0324e7090d31e6c445bb42', '865124774e0324e7090d31e6c445bb42', 'Aktif');

-- ----------------------------
-- Table structure for `barang`
-- ----------------------------
DROP TABLE IF EXISTS `barang`;
CREATE TABLE `barang` (
  `kode` varchar(16) NOT NULL,
  `nama` varchar(128) NOT NULL DEFAULT '',
  `harga` varchar(64) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`kode`,`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of barang
-- ----------------------------
INSERT INTO `barang` VALUES ('BRG002', 'd1d883ec81188fa3930dc66d7b0a6418', '66ee7b8b1a8d0cdacfdcf2fd0224324e', 'Aktif');
INSERT INTO `barang` VALUES ('BRG003', '8b3d1758a9aedb5f204ea7ce7f049e48', '21db3b38fcd1d47362ba88f773deaf30', 'Aktif');
INSERT INTO `barang` VALUES ('BRG004', '8a97efc8358b9523e3aad02c84e31a23', '61f33218d5349a706e6ca31f292780e6', 'Aktif');
INSERT INTO `barang` VALUES ('BRG005', 'ee37a8f6b992bbfa57911a664120d465', '2392a241efb467f4c1e880386a3ba783', 'Aktif');
INSERT INTO `barang` VALUES ('BRG006', '238ccb951865abc5ef33059675c7e952', '2cf4be0347171f0f52450785438c12f6', 'Aktif');

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `kode` varchar(16) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `nama_fb` varchar(100) NOT NULL DEFAULT '',
  `alamat` varchar(500) DEFAULT NULL,
  `kota` varchar(300) DEFAULT NULL,
  `provinsi` varchar(300) DEFAULT NULL,
  `kode_pos` varchar(32) DEFAULT NULL,
  `no_telp1` varchar(32) DEFAULT NULL,
  `no_telp2` varchar(32) DEFAULT NULL,
  `url_fb` varchar(500) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`kode`,`nama_fb`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('CST001', '9284e69e37c329f42d6e9536fd7ea5b0', '1018cd38461811ed2f6939729e232d7f', 'f1766466cbb4d34682cb2dda6fa883a0430669d1ebef0e9b0dd3df43fae8813fba45d27aebe4712cd4936e29cd7d8729', '1ccd97db6a41ea8ef1949c4c43da1c9a', 'a5878b05560be0e706d6a2263af109cc', 'ffbdaaf5d8623505a35164b384240fcd', 'e9bcfc1ef9bd52212795af1be0176587', '88c6126c4a97fc4afc9cafc6589b12d0', 'c288304a086aa599821e336026b92b4dc1816c18c187560613aaf7b3d0438bc5', 'e1275010b60a11a4000cfcfcc54ebd7b');
INSERT INTO `customer` VALUES ('CST002', '9b4856b8f80b67486681e08edab2ddbd', '819b705a452b9d0fe6d14a1bc1fad39f', 'b73e6582510d5aead7851ae3f3067c1d', 'fede220d47037037c1ddb4827c607bd5', '07c5b956f774a40873b58d5ffb4806f5', 'a37e4832c152999b9d3e45b2b2c204e9', '323dc48cb5f1618fbc660e235397ffc8', '9321fa680412bd93aa14d268281dd1b8', '7447f54169b1e57c447abb1e76a73f5f8a180312b77beba37a59880239ea6fa4', '6c7585b6149907d9a288a1bf051b48d6');

-- ----------------------------
-- Table structure for `detail_lelang`
-- ----------------------------
DROP TABLE IF EXISTS `detail_lelang`;
CREATE TABLE `detail_lelang` (
  `kode_lelang` varchar(16) NOT NULL,
  `kode_barang` varchar(64) NOT NULL,
  `harga` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`kode_lelang`,`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of detail_lelang
-- ----------------------------
INSERT INTO `detail_lelang` VALUES ('AUC003', 'BRG002', '66ee7b8b1a8d0cdacfdcf2fd0224324e');
INSERT INTO `detail_lelang` VALUES ('AUC003', 'BRG005', '2392a241efb467f4c1e880386a3ba783');
INSERT INTO `detail_lelang` VALUES ('AUC003', 'BRG006', '2cf4be0347171f0f52450785438c12f6');
INSERT INTO `detail_lelang` VALUES ('AUC004', 'BRG002', '66ee7b8b1a8d0cdacfdcf2fd0224324e');
INSERT INTO `detail_lelang` VALUES ('AUC004', 'BRG003', '21db3b38fcd1d47362ba88f773deaf30');
INSERT INTO `detail_lelang` VALUES ('AUC004', 'BRG004', '61f33218d5349a706e6ca31f292780e6');
INSERT INTO `detail_lelang` VALUES ('AUC004', 'BRG006', '2cf4be0347171f0f52450785438c12f6');

-- ----------------------------
-- Table structure for `lelang`
-- ----------------------------
DROP TABLE IF EXISTS `lelang`;
CREATE TABLE `lelang` (
  `kode_lelang` varchar(16) NOT NULL,
  `kode_customer` varchar(32) DEFAULT NULL,
  `tgl_lelang` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`kode_lelang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of lelang
-- ----------------------------
INSERT INTO `lelang` VALUES ('AUC003', 'a06a1a86f04d217b5b82e91edc9eef7c', '38bca1ce9cb0e93051807da1a42e4643');
INSERT INTO `lelang` VALUES ('AUC004', 'a06a1a86f04d217b5b82e91edc9eef7c', 'e8f6c2c831c03b26e732a68f93aa108c');
