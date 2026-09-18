-- Reference rows the integration tests rely on (branches 1-3, sellers, income/expense
-- types and a few accountings). Test classpath only.

INSERT INTO `branches` (`id`, `is_active`, `name`) VALUES
(1, b'1', 'NAZAS'),
(2, b'1', 'PEÑON'),
(3, b'1', 'COYOTE');

INSERT INTO `seller` (`id`, `branch_id`, `full_name`, `is_active`) VALUES
(1, 1, 'Mariana Rodriguez', b'0'),
(2, 1, 'Diana Hernandez', b'1'),
(3, 2, 'Daniela Moreno', b'1'),
(4, 1, 'Valeria Rodriguez', b'1'),
(5, 3, 'José Villalobos', b'1');

INSERT INTO `accounts` (`id`, `branch_id`, `date`, `seller_id`) VALUES
(1, 1, '2023-06-05 14:02:58.412754', 1),
(2, 1, '2023-06-05 14:25:13.512888', 2),
(3, 1, '2023-06-05 14:26:23.647339', 1);

INSERT INTO `expense_type` (`id`, `type`) VALUES
(1, 'SUELDOS'),
(2, 'RENTA'),
(3, 'OTROS');

INSERT INTO `income_type` (`id`, `type`) VALUES
(1, 'PRONTIPAGOS'),
(2, 'MEDICAMENTO');

-- Rows written while enums were still stored by ordinal (converted to names by V3):
-- profile 2 = NURSERY, check_type 1 = CHECK_OUT
INSERT INTO `branch_config` (`id`, `branch_id`, `initial_balance`, `can_edit_account`, `can_open_outdated_account`, `profile`) VALUES
(1, 3, 500.0, b'1', b'0', 2);

INSERT INTO `check_list` (`id`, `accounting_id`, `cash_balance`, `cellphone_charge`, `cellphone_condition`, `check_type`, `date`, `furniture_clean_conditions`, `installation_state`, `seller_id`, `tranbox_balance`) VALUES
(1000, 1, 100.0, 80, b'1', 1, '2023-06-05', 1, b'1', 1, 50.0);
