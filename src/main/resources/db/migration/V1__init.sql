CREATE TABLE `competition` (
  `id` bigint(20) NOT NULL,
  `area_name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `player` (
  `id` bigint(20) NOT NULL,
  `country_of_birth` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `team` (
  `id` bigint(20) NOT NULL,
  `area_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `tla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `team_player` (
  `team_id` bigint(20) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  PRIMARY KEY (`team_id`,`player_id`),
  KEY `FK61af6hcog98vhvpvrypcp3liu` (`player_id`),
  CONSTRAINT `FK61af6hcog98vhvpvrypcp3liu` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FKgadi21l58c1a65823rn8cgrps` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `competition_team` (
  `competition_id` bigint(20) NOT NULL,
  `team_id` bigint(20) NOT NULL,
  PRIMARY KEY (`competition_id`,`team_id`),
  KEY `FKf7d8fxjufbc06wppegf6wt7su` (`team_id`),
  CONSTRAINT `FK3frfpraj02mi4lhk11mrbmc18` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`),
  CONSTRAINT `FKf7d8fxjufbc06wppegf6wt7su` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;