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

## How to Run

1) Clone the repository: `git clone <REPOSITORY_URL> && cd excel-batch-processing`
2) Configure database connection properties in `src/main/resources/application.properties`
3) Build and run the application using Maven: `mvn clean install && mvn spring-boot:run`
4) The batch job will automatically run on application startup, reading the employees CSV and persisting data into the database.

## How to Test

Run unit and integration tests along with coverage report generation: `mvn clean verify`

## Important Configurations

1) Spring Batch schema initialization controlled by `spring.batch.jdbc.initialize-schema=always` to auto-create batch metadata tables.
2) JPA auto-ddl controlled via `spring.jpa.hibernate.ddl-auto=update` (or `create-drop` during development).
3) Database credentials set in `application.properties` or `application.yml`.
4) CSV input file placed under `src/main/resources/` named `employees.csv`.
5) SQL logging enabled via `spring.jpa.show-sql=true` for troubleshooting.

## Contributions

This is a personal project; external contributions are not planned.

## License

This project is private, and all rights are reserved by the author.

No part of this code may be copied, modified, distributed, or used without the express permission of the author.

© 2025 [Mariana Ramacciotti]. All rights reserved.

