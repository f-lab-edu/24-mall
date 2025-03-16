# 24-mall
화면없이 백엔드 기능만을 구현한 이커머스  프로젝트입니다.


docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:latest
docker ps -a
docker start mysql-container
docker exec -it mysql-container bash
mysql -u root -p
