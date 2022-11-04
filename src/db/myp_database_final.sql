-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 04. Nov 2022 um 11:23
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
  `A_ID` int(11) NOT NULL COMMENT 'Arbeitsmodell ID',
  `A_Beschreibung` varchar(50) NOT NULL COMMENT 'Beschreibung des Arbeitsmodell',
  `A_Sollstunden` int(11) NOT NULL COMMENT 'Sollstunden des Arbeitsmodell',
  `A_Solltage` int(11) NOT NULL COMMENT 'Beschreibt wie viel Tage gearbeitet werden soll',
  `A_G_ID` int(11) NOT NULL COMMENT 'Grenzwerte ID',
  `A_Starzeit` int(11) NOT NULL COMMENT 'Frühste Starzeit der Arbeitszeit',
  `A_Endzeit` int(11) NOT NULL COMMENT 'Späteste Endzeit der Arbeitszeit'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `a_arbeitsmodelle`
--

INSERT INTO `a_arbeitsmodelle` (`A_ID`, `A_Beschreibung`, `A_Sollstunden`, `A_Solltage`, `A_G_ID`, `A_Starzeit`, `A_Endzeit`) VALUES
(1, 'Vollzeit', 40, 5, 1, 6, 22),
(2, 'Vollzeit redz. I', 35, 5, 1, 6, 22),
(3, 'Teilszeit I', 30, 3, 2, 6, 22),
(4, 'Extern - Auf Rechnung', 0, 0, 1, 0, 0),
(5, 'Azubi ', 40, 5, 2, 6, 22),
(6, 'Azubi u18', 35, 5, 3, 6, 22);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bz_zeitsteintrag`
--

CREATE TABLE `bz_zeitsteintrag` (
  `BZ_ID` int(11) NOT NULL COMMENT 'Buchung-Zeiteintrag ID',
  `BZ_B_ID` int(11) NOT NULL COMMENT 'Buchungs ID',
  `BZ_Zeiteintrag` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'Timestamp des Zeieintrag'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `bz_zeitsteintrag`
--

INSERT INTO `bz_zeitsteintrag` (`BZ_ID`, `BZ_B_ID`, `BZ_Zeiteintrag`) VALUES
(1, 1, '2022-09-09 08:26:57'),
(2, 1, '2022-09-09 12:26:57'),
(3, 1, '2022-09-09 14:27:42'),
(4, 1, '2022-09-09 19:28:06'),
(6, 4, '2022-09-12 09:51:44'),
(8, 9, '2022-09-15 04:37:50'),
(9, 9, '2022-09-15 08:37:50'),
(10, 9, '2022-09-15 10:00:21'),
(11, 9, '2022-09-15 18:00:21'),
(12, 10, '2022-09-16 13:47:04'),
(14, 10, '2022-09-16 14:00:51'),
(15, 11, '2022-09-19 09:40:50'),
(16, 12, '2022-09-18 06:00:00'),
(17, 12, '2022-09-18 11:00:04'),
(18, 12, '2022-09-18 11:50:45'),
(19, 12, '2022-09-18 18:18:45'),
(25, 23, '2022-10-17 05:26:00'),
(26, 23, '2022-10-17 18:26:00'),
(27, 24, '2022-11-03 11:01:00'),
(28, 24, '2022-11-03 07:00:00'),
(29, 25, '2022-11-02 07:00:00'),
(30, 25, '2022-11-02 15:00:00'),
(31, 26, '2022-11-01 09:47:00'),
(32, 26, '2022-11-01 17:00:00'),
(33, 26, '2022-11-01 14:00:00');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `b_buchung`
--

CREATE TABLE `b_buchung` (
  `B_ID` int(11) NOT NULL COMMENT 'Buchungs ID',
  `B_M_ID` int(11) NOT NULL COMMENT 'Mitarbeiter ID',
  `B_Tag` date NOT NULL COMMENT 'Tag der Buchung',
  `B_Stunden` double NOT NULL DEFAULT -99 COMMENT 'Stunden in Industriestunden'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `b_buchung`
--

INSERT INTO `b_buchung` (`B_ID`, `B_M_ID`, `B_Tag`, `B_Stunden`) VALUES
(1, 1, '2022-09-09', 9),
(4, 7, '2022-09-12', -99),
(8, 1, '2022-09-12', -99),
(9, 6, '2022-09-15', 12),
(10, 9, '2022-09-16', 0.21),
(11, 9, '2022-09-19', -1),
(12, 6, '2022-09-18', 11.466666666666667),
(23, 6, '2022-10-17', 13),
(24, 6, '2022-11-03', 4.016666666666667),
(25, 6, '2022-11-02', 8),
(26, 6, '2022-11-01', 7.216666666666667);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `f_feiertage`
--

CREATE TABLE `f_feiertage` (
  `F_ID` int(11) NOT NULL COMMENT 'Feiertags ID',
  `F_Beschreibung` varchar(50) NOT NULL COMMENT 'FeiertagsBeschreibung',
  `F_Tag` date NOT NULL COMMENT 'Feiertagsdatum'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `f_feiertage`
--

INSERT INTO `f_feiertage` (`F_ID`, `F_Beschreibung`, `F_Tag`) VALUES
(1, 'Weihnachten', '2022-12-31'),
(2, 'Silvester', '2022-12-24');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `g_grenzwerte`
--

CREATE TABLE `g_grenzwerte` (
  `G_ID` int(11) NOT NULL COMMENT 'Grenzwerte ID',
  `G_Tag` int(11) NOT NULL DEFAULT 10 COMMENT 'Max. Arbeitszeit pro Tag',
  `G_Woche` int(11) NOT NULL DEFAULT 60 COMMENT 'Max. Arbeitszeit pro Woch',
  `G_Gleitzeit_Gelb` int(11) NOT NULL DEFAULT 100 COMMENT 'Schwellwert für Ampel Gelb',
  `G_Gleitzeit_Rot` int(11) NOT NULL DEFAULT 150 COMMENT 'Schwellwert für Ampel Rot'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `g_grenzwerte`
--

INSERT INTO `g_grenzwerte` (`G_ID`, `G_Tag`, `G_Woche`, `G_Gleitzeit_Gelb`, `G_Gleitzeit_Rot`) VALUES
(1, 10, 60, 100, 150),
(2, 10, 30, 50, 100),
(3, 8, 40, 80, 120);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ma_abteilung`
--

CREATE TABLE `ma_abteilung` (
  `MA_ID` int(11) NOT NULL COMMENT 'Abteilungs ID',
  `MA_Abteilung` varchar(50) NOT NULL COMMENT 'Name der Abteilung des Mitarbeiters'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `ma_abteilung`
--

INSERT INTO `ma_abteilung` (`MA_ID`, `MA_Abteilung`) VALUES
(1, 'Vertrieb'),
(2, 'IT'),
(3, 'Buchhaltung'),
(4, 'Externer Berater');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mg_freietage`
--

CREATE TABLE `mg_freietage` (
  `MG_ID` int(11) NOT NULL COMMENT 'Freitage ID',
  `MG_M_ID` int(11) NOT NULL COMMENT 'Mitarbeiter ID',
  `MG_Urlaub` tinyint(4) DEFAULT 0 COMMENT 'ISt der Tag ein Urlaubstag',
  `MG_TAG` date NOT NULL COMMENT 'Tag des Freientags'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `mg_freietage`
--

INSERT INTO `mg_freietage` (`MG_ID`, `MG_M_ID`, `MG_Urlaub`, `MG_TAG`) VALUES
(19829, 6, 0, '2022-11-04'),
(19831, 6, 1, '2022-11-07');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `mk_konto`
--

CREATE TABLE `mk_konto` (
  `MK_ID` int(11) NOT NULL,
  `MK_M_ID` int(11) NOT NULL COMMENT 'Mitarbeiter ID',
  `MK_UK_ID` int(11) NOT NULL COMMENT 'Userklasse ID (Rechte Im System)',
  `MK_A_ID` int(11) NOT NULL COMMENT 'Arbeitsmodell ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Definiert die ';

--
-- Daten für Tabelle `mk_konto`
--

INSERT INTO `mk_konto` (`MK_ID`, `MK_M_ID`, `MK_UK_ID`, `MK_A_ID`) VALUES
(2, 1, 2, 1),
(3, 7, 1, 1),
(5, 9, 1, 1),
(7, 10, 2, 5),
(8, 6, 2, 6);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ms_sso`
--

CREATE TABLE `ms_sso` (
  `ms_m_id` int(11) NOT NULL COMMENT 'Mitarbeiter ID',
  `ms_uname` varchar(50) NOT NULL COMMENT 'Userkennung Windoof',
  `ms_domain` varchar(50) NOT NULL COMMENT 'Domäne'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `ms_sso`
--

INSERT INTO `ms_sso` (`ms_m_id`, `ms_uname`, `ms_domain`) VALUES
(6, 'khema', 'ENVYX360');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `m_mitarbeiter`
--

CREATE TABLE `m_mitarbeiter` (
  `M_ID` int(11) NOT NULL COMMENT 'Mitarbeiter ID',
  `M_Vorname` varchar(50) NOT NULL COMMENT 'M Vorname',
  `M_Nachname` varchar(50) NOT NULL COMMENT 'M Nachname',
  `M_Personalnummer` int(11) NOT NULL COMMENT 'Personalnummer',
  `M_MA_ID` int(11) NOT NULL COMMENT 'Abteilungs ID',
  `M_Passwort` varchar(50) NOT NULL COMMENT 'Passworthash',
  `M_Salt` varchar(60) NOT NULL COMMENT 'Passwort Salt',
  `M_Geburtstag` date NOT NULL DEFAULT '2000-01-01'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `m_mitarbeiter`
--

INSERT INTO `m_mitarbeiter` (`M_ID`, `M_Vorname`, `M_Nachname`, `M_Personalnummer`, `M_MA_ID`, `M_Passwort`, `M_Salt`, `M_Geburtstag`) VALUES
(1, 'Max', 'Mayer', 5456921, 1, '', '', '0000-00-00'),
(2, 'Tester', 'Wili', 56654651, 1, '', '', '0000-00-00'),
(3, 'Tester', 'Wili', 56654651, 1, '', '', '0000-00-00'),
(4, 'Tester2', 'Willi', 51651, 1, '', '', '0000-00-00'),
(5, 'Jamie', 'Henselmann', 110, 3, 'b42bac1081ef294d66607a774115336f3018b7a9', '[B@101df177', '0000-00-00'),
(6, 'Kyle', 'Henselmann', 1604, 1, '25a96ad1fdca653039ca6271ebcd9cff78ce7e14', '[B@23bb8443', '0000-00-00'),
(7, 'Nils', 'Klenk', 1888, 2, 'b4954cd77fd15509141fa383563e7aff7fa9020a', '[B@30946e09', '0000-00-00'),
(9, 'Chris', 'Henselmann', 1608, 1, '508dfffdee2536aa75990be2b6b92aaa7d3807d0', '[B@101df177', '0000-00-00'),
(10, 'Hans', 'Solo', 666, 1, '111b888755ea3ecd39780cfc2a82b0d001d98b59', '[B@10b48321', '0000-00-00');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `u_userklassen`
--

CREATE TABLE `u_userklassen` (
  `UK_ID` int(4) NOT NULL COMMENT 'Id der Userklasse',
  `UK_Bezeichnung` varchar(50) NOT NULL COMMENT 'Bezeichnung der Userklasse'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Verantwortlich für Rechte Klassen';

--
-- Daten für Tabelle `u_userklassen`
--

INSERT INTO `u_userklassen` (`UK_ID`, `UK_Bezeichnung`) VALUES
(1, 'Admin'),
(2, 'Mitarbeiter');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `a_arbeitsmodelle`
--
ALTER TABLE `a_arbeitsmodelle`
  ADD PRIMARY KEY (`A_ID`),
  ADD KEY `A_G_ID` (`A_G_ID`);

--
-- Indizes für die Tabelle `bz_zeitsteintrag`
--
ALTER TABLE `bz_zeitsteintrag`
  ADD PRIMARY KEY (`BZ_ID`),
  ADD KEY `BZ_B_ID` (`BZ_B_ID`);

--
-- Indizes für die Tabelle `b_buchung`
--
ALTER TABLE `b_buchung`
  ADD PRIMARY KEY (`B_ID`),
  ADD KEY `B_M_ID` (`B_M_ID`);

--
-- Indizes für die Tabelle `f_feiertage`
--
ALTER TABLE `f_feiertage`
  ADD PRIMARY KEY (`F_ID`);

--
-- Indizes für die Tabelle `g_grenzwerte`
--
ALTER TABLE `g_grenzwerte`
  ADD PRIMARY KEY (`G_ID`);

--
-- Indizes für die Tabelle `ma_abteilung`
--
ALTER TABLE `ma_abteilung`
  ADD PRIMARY KEY (`MA_ID`);

--
-- Indizes für die Tabelle `mg_freietage`
--
ALTER TABLE `mg_freietage`
  ADD PRIMARY KEY (`MG_ID`),
  ADD KEY `MG_M_ID` (`MG_M_ID`);

--
-- Indizes für die Tabelle `mk_konto`
--
ALTER TABLE `mk_konto`
  ADD PRIMARY KEY (`MK_ID`),
  ADD KEY `MK_M_ID` (`MK_M_ID`),
  ADD KEY `MK_A_ID` (`MK_A_ID`),
  ADD KEY `MK_UK_ID` (`MK_UK_ID`);

--
-- Indizes für die Tabelle `ms_sso`
--
ALTER TABLE `ms_sso`
  ADD KEY `ms_uname` (`ms_uname`),
  ADD KEY `ms_sso_ibfk_1` (`ms_m_id`);

--
-- Indizes für die Tabelle `m_mitarbeiter`
--
ALTER TABLE `m_mitarbeiter`
  ADD PRIMARY KEY (`M_ID`),
  ADD KEY `M_MA_ID` (`M_MA_ID`);

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
  MODIFY `A_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Arbeitsmodell ID', AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT für Tabelle `bz_zeitsteintrag`
--
ALTER TABLE `bz_zeitsteintrag`
  MODIFY `BZ_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Buchung-Zeiteintrag ID', AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT für Tabelle `b_buchung`
--
ALTER TABLE `b_buchung`
  MODIFY `B_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Buchungs ID', AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT für Tabelle `f_feiertage`
--
ALTER TABLE `f_feiertage`
  MODIFY `F_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Feiertags ID', AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `g_grenzwerte`
--
ALTER TABLE `g_grenzwerte`
  MODIFY `G_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Grenzwerte ID', AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `ma_abteilung`
--
ALTER TABLE `ma_abteilung`
  MODIFY `MA_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Abteilungs ID', AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `mg_freietage`
--
ALTER TABLE `mg_freietage`
  MODIFY `MG_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Freitage ID', AUTO_INCREMENT=19832;

--
-- AUTO_INCREMENT für Tabelle `mk_konto`
--
ALTER TABLE `mk_konto`
  MODIFY `MK_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT für Tabelle `m_mitarbeiter`
--
ALTER TABLE `m_mitarbeiter`
  MODIFY `M_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Mitarbeiter ID', AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `u_userklassen`
--
ALTER TABLE `u_userklassen`
  MODIFY `UK_ID` int(4) NOT NULL AUTO_INCREMENT COMMENT 'Id der Userklasse', AUTO_INCREMENT=3;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `a_arbeitsmodelle`
--
ALTER TABLE `a_arbeitsmodelle`
  ADD CONSTRAINT `a_arbeitsmodelle_ibfk_1` FOREIGN KEY (`A_G_ID`) REFERENCES `g_grenzwerte` (`G_ID`);

--
-- Constraints der Tabelle `bz_zeitsteintrag`
--
ALTER TABLE `bz_zeitsteintrag`
  ADD CONSTRAINT `bz_zeitsteintrag_ibfk_1` FOREIGN KEY (`BZ_B_ID`) REFERENCES `b_buchung` (`B_ID`);

--
-- Constraints der Tabelle `b_buchung`
--
ALTER TABLE `b_buchung`
  ADD CONSTRAINT `b_buchung_ibfk_1` FOREIGN KEY (`B_M_ID`) REFERENCES `m_mitarbeiter` (`M_ID`);

--
-- Constraints der Tabelle `mg_freietage`
--
ALTER TABLE `mg_freietage`
  ADD CONSTRAINT `mg_freietage_ibfk_1` FOREIGN KEY (`MG_M_ID`) REFERENCES `m_mitarbeiter` (`M_ID`);

--
-- Constraints der Tabelle `mk_konto`
--
ALTER TABLE `mk_konto`
  ADD CONSTRAINT `mk_konto_ibfk_1` FOREIGN KEY (`MK_M_ID`) REFERENCES `m_mitarbeiter` (`M_ID`),
  ADD CONSTRAINT `mk_konto_ibfk_2` FOREIGN KEY (`MK_A_ID`) REFERENCES `a_arbeitsmodelle` (`A_ID`),
  ADD CONSTRAINT `mk_konto_ibfk_3` FOREIGN KEY (`MK_UK_ID`) REFERENCES `u_userklassen` (`UK_ID`);

--
-- Constraints der Tabelle `ms_sso`
--
ALTER TABLE `ms_sso`
  ADD CONSTRAINT `ms_sso_ibfk_1` FOREIGN KEY (`ms_m_id`) REFERENCES `m_mitarbeiter` (`M_ID`);

--
-- Constraints der Tabelle `m_mitarbeiter`
--
ALTER TABLE `m_mitarbeiter`
  ADD CONSTRAINT `m_mitarbeiter_ibfk_1` FOREIGN KEY (`M_MA_ID`) REFERENCES `ma_abteilung` (`MA_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
