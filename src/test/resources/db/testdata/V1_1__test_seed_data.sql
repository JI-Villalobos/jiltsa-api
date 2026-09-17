-- Reference rows the integration tests rely on (branches 1-3, sellers, income/expense
-- types and a few accountings). Test classpath only.

INSERT INTO `branches` (`id`, `is_active`, `name`) VALUES
(1, b'1', 'NAZAS'),
(2, b'1', 'PEÑON'),
(3, b'1', 'COYOTE');

INSERT INTO `seller` (`id`, `branch_id`, `full_name`, `is_active`, `password`) VALUES
(1, 1, 'Mariana Rodriguez', b'0', '1234'),
(2, 1, 'Diana Hernandez', b'1', '1234'),
(3, 2, 'Daniela Moreno', b'1', '1234'),
(4, 1, 'Valeria Rodriguez', b'1', '1234'),
(5, 3, 'José Villalobos', b'1', '1234');

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
