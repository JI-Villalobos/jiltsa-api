CREATE TABLE `branches` (
  `id` int(11) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`id`, `is_active`, `name`) VALUES
(1, b'1', 'NAZAS'),
(2, b'1', 'PEÑON'),
(3, b'1', 'COYOTE');

CREATE TABLE `seller` (
  `id` int(11) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `full_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `is_active` bit(1) NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `seller`
--

INSERT INTO `seller` (`id`, `branch_id`, `full_name`, `is_active`, `password`) VALUES
(1, 1, 'Mariana Rodriguez', b'0', '1234'),
(2, 1, 'Diana Hernandez', b'1', '1234'),
(3, 2, 'Daniela Moreno', b'1', '1234'),
(4, 1, 'Valeria Rodriguez', b'1', '1234'),
(5, 3, 'José Villalobos', b'1', '1234');


CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `seller_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `accounts`
--
INSERT INTO `accounts` (`id`, `branch_id`, `date`, `seller_id`) VALUES
(1, 1, '2023-06-05 14:02:58.412754', 1),
(2, 1, '2023-06-05 14:25:13.512888', 2),
(3, 1, '2023-06-05 14:26:23.647339', 1);

CREATE TABLE `app_users` (
  `id` int(11) NOT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pass` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `app_users`
--

INSERT INTO `app_users` (`id`, `branch_id`, `email`, `pass`, `role`, `username`) VALUES
(1, 1, 'administracion@fmipueblito.com', '', 'USER', 'MATRIZ'),
(2, 2, 'penon@fmipueblito.com', '', 'USER', 'MATRIZ'),
(3, 3, 'coyote@fmipueblito.com', '', 'ADMIN', 'MATRIZ');

CREATE TABLE `branch_config` (
  `id` int(11) NOT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `initial_balance` double NOT NULL,
  `modified_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `expense_type` (
  `id` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `expense_type`
--

INSERT INTO `expense_type` (`id`, `type`) VALUES
(1, 'SUELDOS'),
(2, 'RENTA'),
(3, 'OTROS');

CREATE TABLE `income_type` (
  `id` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `income_type`
--

INSERT INTO `income_type` (`id`, `type`) VALUES
(1, 'PRONTIPAGOS'),
(2, 'MEDICAMENTO');

CREATE TABLE `cash_registry` (
  `id` int(11) NOT NULL,
  `accounting_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `income_type_id` int(11) NOT NULL,
  `tag` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `cash_withdrawals` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `branch` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `concept` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `seller_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `expense_registry` (
  `id` int(11) NOT NULL,
  `accounting_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `expense_type_id` int(11) NOT NULL,
  `time` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlvie0u3it7010dlcw1w84c327` (`branch_id`),
  ADD KEY `FK8vk3f1u90xxi4xcw1y2f3hyq9` (`seller_id`);

--
-- Indexes for table `app_users`
--
ALTER TABLE `app_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branches`
--
ALTER TABLE `branches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branch_config`
--
ALTER TABLE `branch_config`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cash_registry`
--
ALTER TABLE `cash_registry`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmbvm1xr1t5m7u4705hv4w8hcs` (`accounting_id`),
  ADD KEY `FK5t1mif79ghaif4dlj9fb0uw0x` (`income_type_id`);

--
-- Indexes for table `cash_withdrawals`
--
ALTER TABLE `cash_withdrawals`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expense_registry`
--
ALTER TABLE `expense_registry`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjrbmhm9107hxh0dh8drcm1c43` (`accounting_id`),
  ADD KEY `FKudxu6x29hvc8unlceaklqxmf` (`expense_type_id`);

--
-- Indexes for table `expense_type`
--
ALTER TABLE `expense_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `income_type`
--
ALTER TABLE `income_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seller`
--
ALTER TABLE `seller`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6pyg30vwoxjdvce48kqr2m1bb` (`branch_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `app_users`
--
ALTER TABLE `app_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `branches`
--
ALTER TABLE `branches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `branch_config`
--
ALTER TABLE `branch_config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cash_registry`
--
ALTER TABLE `cash_registry`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `cash_withdrawals`
--
ALTER TABLE `cash_withdrawals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `expense_registry`
--
ALTER TABLE `expense_registry`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `expense_type`
--
ALTER TABLE `expense_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `income_type`
--
ALTER TABLE `income_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `seller`
--
ALTER TABLE `seller`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `FK8vk3f1u90xxi4xcw1y2f3hyq9` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`id`),
  ADD CONSTRAINT `FKlvie0u3it7010dlcw1w84c327` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);

--
-- Constraints for table `cash_registry`
--
ALTER TABLE `cash_registry`
  ADD CONSTRAINT `FK5t1mif79ghaif4dlj9fb0uw0x` FOREIGN KEY (`income_type_id`) REFERENCES `income_type` (`id`),
  ADD CONSTRAINT `FKmbvm1xr1t5m7u4705hv4w8hcs` FOREIGN KEY (`accounting_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `expense_registry`
--
ALTER TABLE `expense_registry`
  ADD CONSTRAINT `FKjrbmhm9107hxh0dh8drcm1c43` FOREIGN KEY (`accounting_id`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKudxu6x29hvc8unlceaklqxmf` FOREIGN KEY (`expense_type_id`) REFERENCES `expense_type` (`id`);

--
-- Constraints for table `seller`
--
ALTER TABLE `seller`
  ADD CONSTRAINT `FK6pyg30vwoxjdvce48kqr2m1bb` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`id`);