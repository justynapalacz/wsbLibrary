## Library Management System


This Java web application applies the concepts from Domain-DrivenDesign with many module extracted as a microservice.
<br>
<br>
Utilized MySQL for efficient database querying and management and applied Liquibase for efficient database schema management.
<br>
<br>
Implemented basic authentication for enhanced security and managed relationships between entities to ensure data consistency and integrity.
<br>
<br>
Established a global exception handling mechanism to enhance the application's fault tolerance.
<br>
<br>
Conducted extensive JUnit and Mockito tests, including integration and mutation tests, to validate the functionality and reliability of the system.
<br>
<br>

## Technologies Used
- Java 21
- Spring
- Spring Boot
- Spring Data
- Spring Secuirty
- Kafka
- Docker
- Hibernate
- JPA
- REST Controllers
- MySQL
- H2
- Liquibase
- Lombok
- MapStruct
- Basic Auth
- JUnit
- Mockito
- Maven
- Git

## Prerequisites

- Java 21
- Maven (for building the project)
- MySql database
- Database client for example: MySQL Workbench
- Docker (to run Kafka)

## How to Run the Project

1. Clone the repository:
    ```bash
    git clone https://github.com/justynapalacz/wsbLibrary
    ```

2. Run database script in database client
    ```bash
    schema_01.sql
    ```

3. Navigate to the docker directory:
    ```bash
    cd docker
    ```

4. Download containers:
   ```bash
   docker-compose up
   ```
   
5. Run containers: 
   - zookeeper,
   - kafka.
   

6. Navigate to the project directory:
    ```bash
    cd library
    ```

7. Build the project:
    ```bash
    mvn clean install
    ```

8. Run application in IntelliJ.
