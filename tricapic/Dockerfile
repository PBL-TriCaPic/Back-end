# Use an official Maven image as a parent image
FROM maven:latest

# Set the working directory in the container
WORKDIR /tricapic

# Copy the Maven project files into the container
COPY . .

# Build the Maven project with a specific goal (e.g., package)
RUN mvn clean install