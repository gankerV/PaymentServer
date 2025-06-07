# Stage 1: Build the jar using Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar (skip tests to nhanh hơn, bạn có thể bỏ -DskipTests nếu muốn test)
RUN mvn clean package -DskipTests

# Stage 2: Run the jar
FROM eclipse-temurin:17-jre-alpine

# Copy jar từ stage build
COPY --from=build /app/target/payment-0.0.1-SNAPSHOT.jar /app/payment.jar

# Expose port ứng dụng Spring Boot mặc định
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/payment.jar"]
