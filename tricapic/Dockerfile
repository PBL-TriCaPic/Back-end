# # ベースイメージを指定
# FROM maven:latest

# # 作業ディレクトリを設定
# WORKDIR /demo

# # ホストのプロジェクトファイルをコンテナにコピー
# COPY ../ .

# # Mavenの依存関係を解決してビルド
# RUN mvn clean install

# Use an official Maven image as a parent image
FROM maven:latest

# Set the working directory in the container
WORKDIR /tricapic

# Clear any existing files in the container's working directory
RUN rm -rf ./*

# Copy the entire application to the container
COPY . .

# Build the Maven project with a specific goal (e.g., package)
RUN mvn clean install
