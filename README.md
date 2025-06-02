# Excel Batch Processing with Spring Batch and MySQL

A batch processing project that reads and processes Excel files using Spring Batch, persisting data to a MySQL database.

## Table of Contents

- [Description](#description)  
- [Technologies](#technologies)  
- [Prerequisites](#prerequisites)  
- [How to Run](#how-to-run)  
- [How to Test](#how-to-test)  
- [Important Configurations](#important-configurations)  
- [Contributions](#contributions)  
- [License](#license)  

## Description

This project implements batch processing for reading employee data from Excel/CSV files using Spring Batch. The processed data is saved into a MySQL database, enabling efficient bulk imports.

## Technologies

- Java 17+  
- Spring Boot  
- Spring Batch  
- Spring Data JPA  
- MySQL  
- Maven  
- JUnit 5 & Mockito (for unit and integration testing)  
- HikariCP (connection pooling)  

## Prerequisites

- Java 17 or higher installed
- Maven 3.6 or higher installed
- MySQL database configured and running
- Docker and Docker Compose installed
- GitHub account

## How to Run

Docker allows you to run the entire application including the database in containers, isolating dependencies and environment setup.

1) Create a .env file in the project root (and add it to .gitignore) with:

```SPRING_DATASOURCE_MYSQL_ROOT_PASSWORD=x```
```SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/x?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC```
```SPRING_DATASOURCE_USERNAME=x```
```SPRING_DATASOURCE_PASSWORD=x```

2) Run Docker Compose to build and start the app and PostgreSQL database:

```docker compose up --build```

3) To stop and clean containers: 

```docker compose down -v```

## How to Test

Run unit and integration tests along with coverage report generation: `mvn clean verify`

## Important Configurations

1) Spring Batch schema initialization controlled by `spring.batch.jdbc.initialize-schema=always` to auto-create batch metadata tables.
2) JPA auto-ddl controlled via `spring.jpa.hibernate.ddl-auto=update` (or `create-drop` during development).
3) Database credentials set in `application.properties` or `application.yml`.
4) CSV input file placed under `src/main/resources/` named `employees.csv`.
5) SQL logging enabled via `spring.jpa.show-sql=true` for troubleshooting.
6) Dockerfile and Docker Compose files are provided for containerized builds and deployments.
7) GitHub Actions workflow is configured to build, test, and deploy using Docker Compose.

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.

