-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 30, 2025 at 02:25 PM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mealdelivery`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`username`, `password`) VALUES
('Aziz', 'Aziz');

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`username`, `password`, `address`) VALUES
('Aziz', 'Aziz1234', 'MBZ'),
('Arij', 'Arij', 'MBZ');

-- --------------------------------------------------------

--
-- Table structure for table `delivery_workers`
--

DROP TABLE IF EXISTS `delivery_workers`;
CREATE TABLE IF NOT EXISTS `delivery_workers` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `delivery_workers`
--

INSERT INTO `delivery_workers` (`username`, `password`, `name`, `phone`) VALUES
('Aziz', 'Aziz1234', 'Aziz', '24329544'),
('Arij', 'Arij1234', 'Arij', '23704712');

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
CREATE TABLE IF NOT EXISTS `invoices` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `client_username` varchar(50) DEFAULT NULL,
  `total` double NOT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  PRIMARY KEY (`invoice_id`),
  KEY `client_username` (`client_username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`invoice_id`, `client_username`, `total`, `status`) VALUES
(2, 'Aziz', 383.4, 'Fake Client');

-- --------------------------------------------------------

--
-- Table structure for table `meals`
--

DROP TABLE IF EXISTS `meals`;
CREATE TABLE IF NOT EXISTS `meals` (
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `meals`
--

INSERT INTO `meals` (`name`, `price`) VALUES
('California Sushi Roll', 16),
('Margherita Pizza', 10),
('Cheeseburger', 7.5),
('Spaghetti Carbonara', 12),
('Caesar Salad', 8),
('Turkey Sandwich', 6.5),
('Beef Tacos', 9),
('Grilled Steak', 20),
('Chicken Shawarma', 8.5),
('Vegetable Stir Fry', 7),
('Pepperoni Pizza', 11),
('Fish and Chips', 13.5),
('Pad Thai', 14),
('BBQ Ribs', 18),
('Greek Salad', 9.5),
('Club Sandwich', 7.8),
('Chicken Burrito', 10.5),
('Lamb Kebab', 15),
('Mushroom Risotto', 12.5),
('Falafel Wrap', 6.8),
('Hawaiian Pizza', 11.5),
('Fried Chicken Wings', 9.8),
('Shrimp Tempura', 17),
('Lasagna', 13),
('Caprese Salad', 8.2),
('BLT Sandwich', 7.2),
('Pork Tacos', 9.5),
('Salmon Fillet', 19),
('Beef Shawarma', 9),
('Vegetable Curry', 8),
('Four Cheese Pizza', 12),
('Fish Tacos', 10.8),
('Chicken Pad See Ew', 14.5),
('Pulled Pork Sandwich', 11.2),
('Cobb Salad', 9.8),
('Philly Cheesesteak', 10),
('Chicken Quesadilla', 9.2),
('Grilled Lamb Chops', 21),
('Pesto Pasta', 11.8),
('Hummus Plate', 7.5),
('Supreme Pizza', 13.5),
('Crab Cakes', 18.5),
('Tuna Sushi Roll', 15.5),
('Beef Stroganoff', 14.8),
('Garden Salad', 7),
('Ham and Cheese Sandwich', 6.9),
('Steak Burrito', 12.2),
('Roast Chicken', 16.5),
('Vegetable Sushi Roll', 13),
('Meatball Sub', 8.8);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) DEFAULT NULL,
  `meal_name` varchar(50) DEFAULT NULL,
  `quantity` int(11) DEFAULT '1',
  PRIMARY KEY (`order_id`),
  KEY `invoice_id` (`invoice_id`),
  KEY `meal_name` (`meal_name`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `invoice_id`, `meal_name`, `quantity`) VALUES
(6, 2, 'Caesar Salad', 1),
(5, 2, 'Lamb Kebab', 8),
(4, 2, 'Cheeseburger', 2),
(7, 2, 'Club Sandwich', 10),
(8, 2, 'Cobb Salad', 8),
(9, 2, 'Pad Thai', 5),
(10, 2, 'Vegetable Stir Fry', 2);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
