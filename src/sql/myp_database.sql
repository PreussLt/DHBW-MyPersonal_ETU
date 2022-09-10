-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 10. Sep 2022 um 21:19
-- Server-Version: 10.4.21-MariaDB
-- PHP-Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `myp_database`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `a_arbeitsmodelle`
--

CREATE TABLE `a_arbeitsmodelle` (
  `A_ID` int(11) NOT NULL,
  `A_Beschreibung` varchar(50) NOT NULL,
  `A_Sollstunden` int(11) NOT NULL,
  `A_G_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bz_zeitsteintrag`
--

CREATE TABLE `bz_zeitsteintrag` (
  `BZ_ID` int(11) NOT NULL,
  `BZ_B_ID` int(11) NOT NULL,
  `BZ_Zeiteintrag` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `b_buchung`
--

CREATE TABLE `b_buchung` (
  `B_ID` int(11) NOT NULL,
  `B_M_ID` int(11) NOT NULL,
  `B_Tag` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `g_grenzwerte`
--

CREATE TABLE `g_grenzwerte` (
  `G_ID` int(11) NOT NULL,
  `G_Tag` int(11) NOT NULL DEFAULT 10,
  `G_Woche` int(11) NOT NULL DEFAULT 60,
  `G_Gleitzeit_Gelb` int(11) NOT NULL DEFAULT 100,
  `G_Gleitzeit_Rot` int(11) NOT NULL DEFAULT 150
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mk_konto`
--

CREATE TABLE `mk_konto` (
  `MK_ID` int(11) NOT NULL,
  `MK_M_ID` int(11) NOT NULL,
  `MK_UK_ID` int(11) NOT NULL,
  `MK_A_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Definiert die ';

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `m_mitarbeiter`
--

CREATE TABLE `m_mitarbeiter` (
  `M_ID` int(11) NOT NULL,
  `M_Vorname` varchar(50) NOT NULL,
  `M_Nachname` varchar(50) NOT NULL,
  `M_Personalnummer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `u_userklassen`
--

CREATE TABLE `u_userklassen` (
  `UK_ID` int(4) NOT NULL COMMENT 'Id der Userklasse',
  `UK_Bezeichnung` varchar(50) NOT NULL COMMENT 'Bezeichnung der Userklasse'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Verantwortlich für Rechte Klassen';

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `a_arbeitsmodelle`
--
ALTER TABLE `a_arbeitsmodelle`
  ADD PRIMARY KEY (`A_ID`);

--
-- Indizes für die Tabelle `bz_zeitsteintrag`
--
ALTER TABLE `bz_zeitsteintrag`
  ADD PRIMARY KEY (`BZ_ID`);

--
-- Indizes für die Tabelle `b_buchung`
--
ALTER TABLE `b_buchung`
  ADD PRIMARY KEY (`B_ID`);

--
-- Indizes für die Tabelle `g_grenzwerte`
--
ALTER TABLE `g_grenzwerte`
  ADD PRIMARY KEY (`G_ID`);

--
-- Indizes für die Tabelle `mk_konto`
--
ALTER TABLE `mk_konto`
  ADD PRIMARY KEY (`MK_ID`);

--
-- Indizes für die Tabelle `m_mitarbeiter`
--
ALTER TABLE `m_mitarbeiter`
  ADD PRIMARY KEY (`M_ID`);

--
-- Indizes für die Tabelle `u_userklassen`
--
ALTER TABLE `u_userklassen`
  ADD PRIMARY KEY (`UK_ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `a_arbeitsmodelle`
--
ALTER TABLE `a_arbeitsmodelle`
  MODIFY `A_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `bz_zeitsteintrag`
--
ALTER TABLE `bz_zeitsteintrag`
  MODIFY `BZ_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `b_buchung`
--
ALTER TABLE `b_buchung`
  MODIFY `B_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `g_grenzwerte`
--
ALTER TABLE `g_grenzwerte`
  MODIFY `G_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `mk_konto`
--
ALTER TABLE `mk_konto`
  MODIFY `MK_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `m_mitarbeiter`
--
ALTER TABLE `m_mitarbeiter`
  MODIFY `M_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `u_userklassen`
--
ALTER TABLE `u_userklassen`
  MODIFY `UK_ID` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id der Userklasse';
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
