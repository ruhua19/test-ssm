用户信息表

sql
Copy code
CREATE TABLE user_info (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           username VARCHAR(255) NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           create_at DATETIME NOT NULL,
                           update_at DATETIME NOT NULL
);
代码信息表

sql
Copy code
CREATE TABLE code_info (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           title VARCHAR(255) NOT NULL,
                           content TEXT NOT NULL,
                           create_user INT NOT NULL,
                           create_at DATETIME NOT NULL,
                           update_at DATETIME NOT NULL
);
评论信息表

sql
Copy code
CREATE TABLE comment_info (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              content TEXT NOT NULL,
                              create_user INT NOT NULL,
                              code_id INT NOT NULL,
                              create_at DATETIME NOT NULL,
                              update_at DATETIME NOT NULL
);
团队信息表

sql
Copy code
CREATE TABLE team_info (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           owner INT NOT NULL,
                           create_at DATETIME NOT NULL,
                           update_at DATETIME NOT NULL
);
团队代码关联表

sql
Copy code
CREATE TABLE team_code_relation (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    team_id INT NOT NULL,
                                    code_id INT NOT NULL,
                                    create_at DATETIME NOT NULL
);
测试结果信息表

sql
Copy code
CREATE TABLE test_result (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             code_id INT NOT NULL,
                             test_user INT NOT NULL,
                             test_time DATETIME NOT NULL,
                             result TEXT NOT NULL,
                             create_at DATETIME NOT NULL,
                             update_at DATETIME NOT NULL
);
用户-代码收藏关联表

sql
Copy code
CREATE TABLE user_code_collection_relation (
                                               id INT PRIMARY KEY AUTO_INCREMENT,
                                               user_id INT NOT NULL,
                                               code_id INT NOT NULL,
                                               create_at DATETIME NOT NULL
);