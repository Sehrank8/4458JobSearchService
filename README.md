# 📣 Notification Service

This microservice is responsible for managing job alert notifications within the SE4458 Job Search Web Application ecosystem. It listens to new job postings via RabbitMQ and sends relevant alerts to users who have configured matching Job Alerts.

---

## 🚀 Overview

- Listens to job posting events from a **RabbitMQ queue**.
- Matches new job postings with user-defined **Job Alerts** stored in the database.
- Sends notifications (e.g., logs, future email/SMS).
- Exposes an endpoint to create and manage alerts via REST API.

---

## 🧱 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring AMQP (RabbitMQ)**
- **Spring Data JPA**
- **MongoDB**
- **Docker**

---

## 🗂 Project Structure

```
src/main/java/org/example/
├── controller/
│   └── AlertController.java       # Exposes REST APIs for job alerts
├── service/
│   └── AlertService.java          # Core business logic for job alert notifications
├── config/
│   └── RabbitConfig.java          # RabbitMQ queue + listener config
├── model/
│   ├── Job.java
│   └── JobAlert.java              # Entity for job alert criteria
├── repository/
│   └── JobAlertRepository.java
├── RabbitMQListener.java          # Consumes queue and matches alerts
└── NotificationServiceApplication.java
```

---

## ⚙️ Configuration

Set in `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/notificationdb
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

---

## 🧪 Endpoints

### Create Alert
```
POST /api/v1/alerts
```
Request Body:
```json
{
  "userId": "abc123",
  "city": "Izmir",
  "position": "Developer"
}
```

### Get Alerts for User
```
GET /api/v1/alerts?userId=abc123
```

---

## 🐇 Queue Listener

- Listens on: `job-posting-queue`
- Matches new `Job` entries with stored `JobAlert` records.
- Sends notification (e.g., logs matching jobs with `System.out.println()`).

---

## 🐳 Docker

**Dockerfile** is included:
```dockerfile
FROM openjdk:17-jdk-alpine
COPY target/notification-service.jar notification-service.jar
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]
```

Build and run:
```bash
docker build -t notification-service .
docker run -p 8083:8083 notification-service
```

