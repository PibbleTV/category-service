# Use Oracle Linux 8 as the base image
FROM oraclelinux:8

# Install OpenJDK
RUN dnf install -y java-17-openjdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY  build/libs/category_service-0.0.1-SNAPSHOT.jar category_service.jar

# Expose the application's port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "category_service.jar"]

#docker build -t category-service -f Dockerfile .
#docker run -d --name category-service -p 8082:8082 category-service