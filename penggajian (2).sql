-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2023 at 07:31 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `penggajian`
--

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `karyawanID` varchar(7) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jk` varchar(15) NOT NULL,
  `alamat` text NOT NULL,
  `noHP` varchar(12) DEFAULT NULL,
  `jabatan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`karyawanID`, `nama`, `tgl_lahir`, `jk`, `alamat`, `noHP`, `jabatan`) VALUES
('00006', 'Heldin rwansah', '2015-05-07', 'Laki-laki', 'jakarta timur,ciracas', '0871317313', 'Supir'),
('0125', 'wawan', '2023-04-06', 'Laki-laki', 'qwd', '012515', 'Manager'),
('08121', 'weni', '2023-05-11', 'Perempuan', 'jakarta', '0813134155', 'Supir'),
('08767', 'wahyu ade saputra', '2023-04-18', 'Laki-laki', 'jagakarsa ', '086414112', 'Bag Kebersihan'),
('09423', 'Kidut', '2023-05-11', 'Laki-laki', 'depok jawaw', '086365', 'Bag Kebersihan');

-- --------------------------------------------------------

--
-- Table structure for table `kesejahteraan`
--

CREATE TABLE `kesejahteraan` (
  `id_karyawan` int(25) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `kelas_bpjs` int(15) NOT NULL,
  `jumlah` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kesejahteraan`
--

INSERT INTO `kesejahteraan` (`id_karyawan`, `nama`, `jabatan`, `kelas_bpjs`, `jumlah`) VALUES
(125, 'wawan', 'Manager', 3, 75000),
(9423, 'Kidut', 'Bag Kebersihan', 3, 2000);

-- --------------------------------------------------------

--
-- Table structure for table `lembur`
--

CREATE TABLE `lembur` (
  `lemburID` int(5) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `jabatan` varchar(15) NOT NULL,
  `golongan` varchar(3) NOT NULL,
  `jml_jam` int(3) NOT NULL,
  `gaji_perjam` int(10) NOT NULL,
  `total` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `lembur`
--

INSERT INTO `lembur` (`lemburID`, `nama`, `jabatan`, `golongan`, `jml_jam`, `gaji_perjam`, `total`) VALUES
(7, 'Heldin Irwansah', 'Staff IT', 'II', 2, 10000000, 20000000);

-- --------------------------------------------------------

--
-- Table structure for table `penggajian`
--

CREATE TABLE `penggajian` (
  `gajiID` int(5) NOT NULL,
  `tgl` date DEFAULT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `jabatan` varchar(15) DEFAULT NULL,
  `golongan` varchar(3) DEFAULT NULL,
  `gapok` int(10) DEFAULT NULL,
  `tunjangan` int(10) DEFAULT NULL,
  `potongan` int(10) DEFAULT NULL,
  `gaji_bersih` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `penggajian`
--

INSERT INTO `penggajian` (`gajiID`, `tgl`, `nama`, `jabatan`, `golongan`, `gapok`, `tunjangan`, `potongan`, `gaji_bersih`) VALUES
(5, '2023-05-27', 'wahyu ade', 'Bag Kebersihan', NULL, 5000000, NULL, 51, 4999949),
(6, '2023-05-09', 'Heldin', 'Bag Kebersihan', NULL, 20000000, NULL, 100000, 19900000),
(8, '2023-06-03', 'roni', 'Bag Kebersihan', NULL, 5000000, NULL, 300000, 4700000),
(10, '2023-06-10', 'Kidut', 'Bag Kebersihan', NULL, 5000000, NULL, 500000, 4500000);

-- --------------------------------------------------------

--
-- Table structure for table `penilaian_karyawan`
--

CREATE TABLE `penilaian_karyawan` (
  `id_karyawan` varchar(25) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jabatan` varchar(25) NOT NULL,
  `jumlah_masuk` varchar(25) NOT NULL,
  `penilaian` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penilaian_karyawan`
--

INSERT INTO `penilaian_karyawan` (`id_karyawan`, `nama`, `jabatan`, `jumlah_masuk`, `penilaian`) VALUES
('001', 'wahyu pratama', 'juru parkir', '30', 'Baik'),
('002', 'heldin irwansah', 'bendahara', '27', 'Baik'),
('0125', 'wawan', 'Manager', '30', 'Sangat Baik'),
('09423', 'Kidut', 'Bag Kebersihan', '25', 'Baik');

-- --------------------------------------------------------

--
-- Table structure for table `presensi`
--

CREATE TABLE `presensi` (
  `id` bigint(20) NOT NULL,
  `waktu_absen` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `jenis_absen` varchar(50) DEFAULT NULL,
  `karyawanID` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `presensi`
--

INSERT INTO `presensi` (`id`, `waktu_absen`, `jenis_absen`, `karyawanID`) VALUES
(1, '2023-05-01 19:28:31', 'Masuk', '0125'),
(2, '2023-05-01 19:29:01', 'Keluar', '0125'),
(3, '2023-05-03 15:43:39', 'Masuk', '09423'),
(4, '2023-05-25 00:53:21', 'Masuk', '0125'),
(5, '2023-06-03 08:49:40', 'Masuk', '0125'),
(6, '2023-06-03 08:49:53', 'Keluar', '0125'),
(7, '2023-06-03 09:13:02', 'Masuk', '09423'),
(8, '2023-06-03 09:13:04', 'Keluar', '09423'),
(9, '2023-06-07 22:19:40', 'Masuk', '0125'),
(10, '2023-06-08 04:16:06', 'Masuk', '0125');

-- --------------------------------------------------------

--
-- Table structure for table `tunjangan`
--

CREATE TABLE `tunjangan` (
  `id_karyawan` varchar(30) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `tunjangan` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tunjangan`
--

INSERT INTO `tunjangan` (`id_karyawan`, `nama`, `jabatan`, `tunjangan`) VALUES
('00006', 'Heldin rwansah', 'Supir', 200000),
('09423', 'Kidut', 'Bag Kebersihan', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `noID` int(3) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`noID`, `username`, `password`) VALUES
(2, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`karyawanID`);

--
-- Indexes for table `kesejahteraan`
--
ALTER TABLE `kesejahteraan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `lembur`
--
ALTER TABLE `lembur`
  ADD PRIMARY KEY (`lemburID`);

--
-- Indexes for table `penggajian`
--
ALTER TABLE `penggajian`
  ADD PRIMARY KEY (`gajiID`);

--
-- Indexes for table `penilaian_karyawan`
--
ALTER TABLE `penilaian_karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `presensi`
--
ALTER TABLE `presensi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tunjangan`
--
ALTER TABLE `tunjangan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`noID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lembur`
--
ALTER TABLE `lembur`
  MODIFY `lemburID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `penggajian`
--
ALTER TABLE `penggajian`
  MODIFY `gajiID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `presensi`
--
ALTER TABLE `presensi`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `noID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
