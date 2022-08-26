-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 26-Ago-2022 às 22:40
-- Versão do servidor: 10.4.24-MariaDB
-- versão do PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `help-desk`
--
CREATE DATABASE IF NOT EXISTS `help-desk` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `help-desk`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `chamado`
--

CREATE TABLE `chamado` (
  `id` int(11) NOT NULL,
  `data_abertura` date DEFAULT NULL,
  `data_fechamento` date DEFAULT NULL,
  `observacoes` varchar(255) DEFAULT NULL,
  `prioridade` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `tecnico_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfis`
--

CREATE TABLE `perfis` (
  `pessoa_id` int(11) NOT NULL,
  `perfis` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa`
--

CREATE TABLE `pessoa` (
  `dtype` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `chamado`
--
ALTER TABLE `chamado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKivlss0iq4e3dcjqwgfar8i77a` (`cliente_id`),
  ADD KEY `FKkc34p1kfv0acpiymukv930dd9` (`tecnico_id`);

--
-- Índices para tabela `perfis`
--
ALTER TABLE `perfis`
  ADD KEY `FKlnesgnyiynjyqx8ks8cyhv6il` (`pessoa_id`);

--
-- Índices para tabela `pessoa`
--
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_nlwiu48rutiltbnjle59krljo` (`cpf`),
  ADD UNIQUE KEY `UK_mc87q8fpvldpdyfo9o5633o5l` (`email`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `chamado`
--
ALTER TABLE `chamado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `pessoa`
--
ALTER TABLE `pessoa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `chamado`
--
ALTER TABLE `chamado`
  ADD CONSTRAINT `FKivlss0iq4e3dcjqwgfar8i77a` FOREIGN KEY (`cliente_id`) REFERENCES `pessoa` (`id`),
  ADD CONSTRAINT `FKkc34p1kfv0acpiymukv930dd9` FOREIGN KEY (`tecnico_id`) REFERENCES `pessoa` (`id`);

--
-- Limitadores para a tabela `perfis`
--
ALTER TABLE `perfis`
  ADD CONSTRAINT `FKlnesgnyiynjyqx8ks8cyhv6il` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
