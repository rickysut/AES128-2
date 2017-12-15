/*
Navicat MySQL Data Transfer

Source Server         : MYLOKAL
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : lelang

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2017-12-15 16:13:28
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
INSERT INTO `admin` VALUES ('ADM007', '7c7e7f3db6c8869b50c62249f6eb7f4b', '19fb516c4eb26738b329cf71392b67a4', '6f19608d8cb16dc00a21d1482cc75bdb', '0b8b5877509a319a4c63986cc153e037', 'e788b6fa8e24d9ae5d62d8dc93b88bbc', 'e788b6fa8e24d9ae5d62d8dc93b88bbc', 'Aktif');
INSERT INTO `admin` VALUES ('ADM008', '865124774e0324e7090d31e6c445bb42', '13e2aac38a54bb787c229d6b5ceecd65', '85a6cec94335cad457b5d1d48fb64698', '2943b33c96c2261aeb5789b63377cbb8', '865124774e0324e7090d31e6c445bb42', '865124774e0324e7090d31e6c445bb42', 'Aktif');

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
  `kode_pos` varchar(16) DEFAULT NULL,
  `no_telp1` varchar(32) DEFAULT NULL,
  `no_telp2` varchar(32) DEFAULT NULL,
  `url_fb` varchar(500) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`kode`,`nama_fb`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `detail_lelang`
-- ----------------------------
DROP TABLE IF EXISTS `detail_lelang`;
CREATE TABLE `detail_lelang` (
  `kode_lelang` varchar(16) NOT NULL,
  `kode_barang` varchar(16) NOT NULL,
  `harga` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`kode_lelang`,`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of detail_lelang
-- ----------------------------

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
