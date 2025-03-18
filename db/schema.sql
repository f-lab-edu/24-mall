docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:latest
docker run --name mysql-container2 -e MYSQL_ROOT_PASSWORD=root -d -p 3307:3307 mysql:latest
docker ps -a
docker start mysql-container
docker exec -it mysql-container bash
mysql -u root -p

docker /etc/my.cnf 포트 수정


CREATE TABLE user (
                      id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name             VARCHAR(100) NOT NULL,
                      email            VARCHAR(255) NOT NULL UNIQUE,
                      phone_number     VARCHAR(20) NOT NULL UNIQUE,
                      password   VARCHAR(255) NOT NULL,
                      address          TEXT DEFAULT NULL,
                      is_deleted       BOOLEAN DEFAULT FALSE,
                      created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

# 1a9f6b8c3d7e4f2a5c6d9e0b7a4f3c2e
# MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK

CREATE TABLE system_key (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            enc_key TEXT NOT NULL,            -- 서명에 사용할 SecretKey (Base64 인코딩 저장)
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 키 생성 시간
                            is_active BOOLEAN DEFAULT TRUE       -- 현재 활성화된 키 여부
);
