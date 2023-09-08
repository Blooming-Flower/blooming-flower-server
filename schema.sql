CREATE TABLE `choose` (
  `choose_id` bigint NOT NULL,
  `choose_content` varchar(255) NOT NULL,
  `choose_seq` int NOT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`choose_id`),
  KEY `FKk0dnwqt9lemqirbimwhewsivg` (`question_id`),
  CONSTRAINT `FKk0dnwqt9lemqirbimwhewsivg` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `exam` (
  `exam_id` bigint NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `exam_format` varchar(255) DEFAULT NULL,
  `exam_left_footer` varchar(255) DEFAULT NULL,
  `exam_right_footer` varchar(255) DEFAULT NULL,
  `exam_sub_title` varchar(255) NOT NULL,
  `exam_title` varchar(255) NOT NULL,
  PRIMARY KEY (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `exam_orders` (
  `exam_order_id` bigint NOT NULL,
  `order_seq` int NOT NULL,
  `exam_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`exam_order_id`),
  KEY `FK9qhlh6w581cqxf9y6bso5elxc` (`exam_id`),
  KEY `FK6c5ly1ga3j2wynp5nhtq39tsa` (`question_id`),
  CONSTRAINT `FK6c5ly1ga3j2wynp5nhtq39tsa` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`),
  CONSTRAINT `FK9qhlh6w581cqxf9y6bso5elxc` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `passage` (
  `passage_id` bigint NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `passage_content` longtext NOT NULL,
  `passage_name` varchar(255) NOT NULL,
  `passage_number` varchar(255) NOT NULL,
  `passage_type` varchar(255) NOT NULL,
  `passage_unit` varchar(255) NOT NULL,
  `passage_year` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`passage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `question` (
  `question_id` bigint NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `past_yn` bit(1) NOT NULL,
  `question_answer` varchar(255) NOT NULL,
  `question_content` longtext NOT NULL,
  `question_title` varchar(255) NOT NULL,
  `question_type` varchar(255) NOT NULL,
  `passage_id` bigint DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `FK25j4log0y7y17qdyardmjf6lp` (`passage_id`),
  CONSTRAINT `FK25j4log0y7y17qdyardmjf6lp` FOREIGN KEY (`passage_id`) REFERENCES `passage` (`passage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


