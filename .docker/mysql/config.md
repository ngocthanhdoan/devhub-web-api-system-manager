# Thiết Lập Biến Môi Trường Trên Máy Ubuntu lần đầu
## Thêm Biến Môi Trường Vào Tập Tin ~/.bashrc hoặc ~/.profile
 Mở tập tin ~/.bashrc hoặc ~/.profile bằng một trình soạn thảo văn bản. Ví dụ:
```
nano ~/.bashrc
```
Thêm các biến môi trường vào cuối tập tin. Ví dụ:
```
export MYSQL_ROOT_PASSWORD=*****
export MYSQL_DATABASE=yourdatabase
export MYSQL_USER=youruser
export MYSQL_PASSWORD=****
export MYSQL_PORT=3306
export MYSQL_MOUNT_DIR=/path/to/mysql/data
export PHPMYADMIN_PORT=8888
export PMA_HOST=mysql
export PMA_PORT=3306
export PMA_USER=youruser
export PMA_PASSWORD=****
export SPRING_APP_PORT=8080
```
Để áp dụng các thay đổi ngay lập tức, chạy lệnh sau:
```
source ~/.bashrc
```
