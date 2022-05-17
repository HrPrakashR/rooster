INSERT INTO `rooster`.`team` (`id`, `friday_from`, `friday_to`, `min_break_time`, `monday_from`, `monday_to`, `name`,
                              `rest_days`, `rest_hours`, `saturday_from`, `saturday_to`, `sunday_from`, `sunday_to`,
                              `thursday_from`, `thursday_to`, `tuesday_from`, `tuesday_to`, `wednesday_from`,
                              `wednesday_to`)
VALUES ('1', '2022-05-11 17:34:23.000000', '2022-05-12 04:54:34.000000', '1.5', '2022-05-11 17:35:04.000000',
        '2022-05-12 04:54:39.000000', 'Sales', '3', '8', '2022-05-11 17:35:23.000000', '2022-05-11 17:35:24.000000',
        '2022-05-11 17:35:24.000000', '2022-05-11 17:35:25.000000', '2022-05-11 17:35:25.000000',
        '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000',
        '2022-05-11 17:35:27.000000', '2022-05-11 17:35:27.000000');
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('10', '15', '1', 'email@email.de', 'Max', '40', 'Mustermann', 'TRAINEE', '1');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (8, '2022-05-12 18:00:38.000000', '2022-05-13 18:01:04.000000', 0, 10);
INSERT INTO `rooster`.`team` (`id`, `friday_from`, `friday_to`, `min_break_time`, `monday_from`, `monday_to`, `name`,
                              `rest_days`, `rest_hours`, `saturday_from`, `saturday_to`, `sunday_from`, `sunday_to`,
                              `thursday_from`, `thursday_to`, `tuesday_from`, `tuesday_to`, `wednesday_from`,
                              `wednesday_to`)
VALUES ('2', '2022-05-11 17:34:23.000000', '2022-05-12 04:54:34.000000', '1.5', '2022-05-11 17:35:04.000000',
        '2022-05-12 04:54:39.000000', 'Marketing', '3', '8', '2022-05-11 17:35:23.000000', '2022-05-11 17:35:24.000000',
        '2022-05-11 17:35:24.000000', '2022-05-11 17:35:25.000000', '2022-05-11 17:35:25.000000',
        '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000',
        '2022-05-11 17:35:27.000000', '2022-05-11 17:35:27.000000');
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('11', '-10', '1', 'email@email.net', 'Elias', '40', 'Akbari', 'STAFF', '2');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (1, '2022-05-15 18:00:38.000000', '2022-05-20 18:01:04.000000', 3, 10);
INSERT INTO `rooster`.`team` (`id`, `friday_from`, `friday_to`, `min_break_time`, `monday_from`, `monday_to`, `name`,
                              `rest_days`, `rest_hours`, `saturday_from`, `saturday_to`, `sunday_from`, `sunday_to`,
                              `thursday_from`, `thursday_to`, `tuesday_from`, `tuesday_to`, `wednesday_from`,
                              `wednesday_to`)
VALUES ('3', '2022-05-11 17:34:23.000000', '2022-05-12 04:54:34.000000', '1.5', '2022-05-11 17:35:04.000000',
        '2022-05-12 04:54:39.000000', 'HR', '3', '8', '2022-05-11 17:35:23.000000', '2022-05-11 17:35:24.000000',
        '2022-05-11 17:35:24.000000', '2022-05-11 17:35:25.000000', '2022-05-11 17:35:25.000000',
        '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000', '2022-05-11 17:35:26.000000',
        '2022-05-11 17:35:27.000000', '2022-05-11 17:35:27.000000');
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('12', '-3', '1', 'email@email.info', 'Philipp', '40', 'Bomers', 'STAFF', '1');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (2, '2022-05-18 18:00:38.000000', '2022-05-21 18:01:04.000000', 2, 11);
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('13', '22', '1', 'email@email.co.uk', 'Pia', '40', 'Stitzing', 'STAFF', '1');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (3, '2022-05-21 18:00:38.000000', '2022-05-23 18:01:04.000000', 0, 13);
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('14', '5', '1', 'email@email.org', 'Prakash', '40', 'Rathinasamy', 'STAFF', '2');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (4, '2022-05-22 18:00:38.000000', '2022-05-31 18:01:04.000000', 0, 14);
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('15', '99', '1', 'email@email.edu', 'Mehmet', '40', 'Katran', 'TRAINEE', '1');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (5, '2022-06-03 18:00:38.000000', '2022-06-04 18:01:04.000000', 0, 14);
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('16', '0', '1', 'email@email.com', 'Karl', '40', 'Marx', 'MANAGER', '3');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (6, '2022-06-12 18:00:38.000000', '2022-06-13 18:01:04.000000', 0, 14);
INSERT INTO `rooster`.`employee` (`id`, `balance_hours`, `break_time`, `email`, `first_name`, `hours_per_week`,
                                  `last_name`, `role`, `team_id`)
VALUES ('17', '-73', '1', 'email@email.biz', 'Adam', '40', 'Smith', 'OWNER', '1');
INSERT INTO `rooster`.`period` (`id`, `date_from`, `date_to`, `purpose`, `employee_id`)
VALUES (7, '2022-07-15 18:00:38.000000', '2022-07-17 18:01:04.000000', 0, 14);


