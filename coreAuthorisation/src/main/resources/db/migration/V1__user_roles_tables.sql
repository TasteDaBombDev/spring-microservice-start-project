CREATE TABLE roles
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20)        NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id INT    NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id         BIGINT       NOT NULL,
    username   VARCHAR(20)  NOT NULL,
    email      VARCHAR(50)  NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at datetime     NULL,
    updated_at datetime     NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

INSERT INTO `roles` (`id`, `name`) VALUES
                                       (1, 'ROLE_USER'),
                                       (2, 'ROLE_ADMIN');

INSERT INTO `users` (`id`, `email`, `password`, `username`, `created_at`, `updated_at`) VALUES
                                                                                            (2, 'raresdragutu@gmail.com', '$2a$10$GEd6BnVjzy2v.OvsJjyxpeYRNH8/QpDJyuxfpoE31sLtalPj3yJKy', 'raresD', '2022-05-28 10:36:18', '2022-05-28 10:36:18'),
                                                                                            (5, 'dragutu@gmail.com', '$2a$10$6keYVvqA8mLRkurDFbY0e.7jte6BuKS3xNWEdqJUQ29hwikIAhdMe', 'rares.dragutu', '2022-07-12 10:20:26', '2022-07-12 10:20:26');
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
                                                    (2, 2),
                                                    (5, 1);


ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_77584fbe74cc86922be2a3560 UNIQUE (username);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);