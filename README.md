# Meow Care Service

## Overview

Meow Care Service is a Spring Boot application designed to manage booking orders for pet care services. It includes features for creating, updating, and retrieving booking orders with detailed information about the services and pets involved.

## Technologies Used

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Cloud OpenFeign
- PostgreSQL
- MapStruct
- Lombok
- OpenAPI (Springdoc)

## Getting Started

### Prerequisites

- Java 17
- Gradle
- PostgreSQL

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/meow-care-service.git
    cd meow-care-service
    ```

2. Configure the database in `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3. Build the project:
    ```sh
    ./gradlew build
    ```

4. Run the application:
    ```sh
    ./gradlew bootRun
    ```

## License

This project is licensed under the MIT License.
