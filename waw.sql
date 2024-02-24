-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 24 fév. 2024 à 17:00
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `waw`
--

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nom_user` varchar(255) NOT NULL,
  `email_user` varchar(255) NOT NULL,
  `role_user` varchar(255) NOT NULL,
  `tel_user` int(11) NOT NULL,
  `mdp_user` varchar(255) NOT NULL,
  `adresse_user` varchar(255) NOT NULL,
  `status_user` varchar(20) NOT NULL,
  `photo_user` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id_user`, `nom_user`, `email_user`, `role_user`, `tel_user`, `mdp_user`, `adresse_user`, `status_user`, `photo_user`) VALUES
(9, 'roua', 'roua@roua.tn', 'USER', 23958023, '34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', 'eefrgrgz', 'ACTIVE', 'file:/C:/Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/email.png'),
(10, 'Admin', 'EVH0HVE@gmail.com', 'ADMIN', 55420690, '34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', 'ok', 'ACTIVE', 'file:/C:/Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/voiture-electrique%20(1).png'),
(11, 'BEDHIAFI Okba', 'okba.bedhiafi@esprit.tn', 'USER', 55420690, '34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', 'zouhour', 'INACTIVE', 'file:/C:/Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/employe.png'),
(12, 'MAITI Farah', 'ttttfarah@gmail.com', 'FORMATEUR', 95841109, 'f525d174c72021c752b2d6d17259f643ebcc457b6c0982fb618c18bf6deef8ff', 'beja', 'INACTIVE', 'file:/C:/Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/exit.png'),
(13, 'houssem', 'houssem@gmail.com', 'USER', 45545454, '34f2dc63b2c3a36f26103e912a3ddbbb34cfc95be8f7f03ea29339cccb2772e6', 'sdfsdsd', 'ACTIVE', 'file:/C:/Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/add.png');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
