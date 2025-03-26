docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:latest
docker run --name mysql-container2 -e MYSQL_ROOT_PASSWORD=root -d -p 3307:3307 mysql:latest
docker ps -a
docker start mysql-container
docker exec -it mysql-container bash
mysql -u root -p

docker /etc/my.cnf 포트 수정
