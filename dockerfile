# Dockerfile

# Use an official MariaDB image as the base image
FROM mariadb:10.6

# Other Dockerfile instructions...

# (他の命令を追加)

# MySQLクライアントが不要な場合は、以下の行を削除
# RUN apt-get update && apt-get install -y mysql-client
