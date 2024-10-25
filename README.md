# Spring Boot REST API Project

## Project Overview
This is a Spring Boot microservice architecture project using Java 17, Maven 3.6.3, and Spring Boot 3.3.4. The project implements a REST API that allows CRUD operations for managing doctors' information.

## How to Run the Project

1. **Initial Set Up**:
   ```bash
    git clone 'https://github.com/linguinichico/api_dermtest.git'
    cd api
    ```

2.1. **Run the Project using only Maven**:
  ``` bash
   mvn clean spring-boot:run
  ```

2.2. **Run the Project with Docker image**:
   ``` bash
   mvn clean package
   docker build . -t api-dermtest
   docker container run api-dermtest
  ```

3.1 **Interact with the API using the following endpoint**:
   ``` bash
   http://localhost:8080/doctors
  ```

3.2 **Interact with the API using UI swagger**:
   ``` bash
   http://localhost:8080/swagger-ui/index.html
  ```

3.3 **Interact with database**:
  ``` bash
   http://localhost:8080/h2
  ```

## Tasks Completed
### 1. Configuration Tasks
I chose the following configuration tasks:
- **[Task 1: Add file-based database (5 min)]**  
  Integrated a file-based database using H2 for easy setup and to simulate persistence in a development environment. I configured it in the `application.properties` file.
   ```properties
  spring.datasource.url=jdbc:h2:file:~/spring-boot-h2-db
  spring.datasource.username=sa
  spring.datasource.password=
  spring.datasource.driver-class-name=org.h2.Driver
  spring.jpa.hibernate.ddl-auto=update
  ```

- **[Task 2: Add Lombok (2 min)]**  
  Integrated Lombok adding the correspondent dependency into `pom.xml` file and used `@Getter` and `@Setter` in `Doctor` class.
     ```xml
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  ```

- **[Task 3: Create runnable Docker image (10 min)]** 
  I created a `Dockerfile` to produce the docker image. Instructions were already given on how to run the image.

- **[Task 4: Add OpenApi documentation (10 min)]** 
  It was configured in `pom.xml` and `application.properties` the possibility of using Open API documentation. The documentation can be seen using the following endpoint.
  ``` bash
   http://localhost:8080/api-docs
  ```

### 2. Development Tasks
I chose the following development tasks:
- **[Task 1: Create Data Model (Doctor) (30 min)]**  
  Created a `Doctor` data model class with fields such as `id`, `name`, `email`, `numberOfPatients` and respective getters and setters to further use in the CRUD operations.

- **[Task 2: Create a CRUD-based Controller (DoctorController) (3 hours)]**  
  Developed an API controller `DoctorController` class that provides endpoints for CRUD operations (`GET`, `POST`, `PUT`, and `DELETE`) for `Doctor` objects.
  The operations that I implemented were the following:
  - `GET /doctors` Get all doctors.
  - `POST /doctors` Post a doctor (I didn't created the constrain that the information under the request had to be different from previous `POST` operations aside from `id` field which is always different and generated independent from the request).
  - `DELETE /doctors` Delete all the doctors.
  - `GET /doctors/{id}` Get a doctor by `id`, while handling the error if not found.
  - `PUT /doctors/{id}` Update information of a doctor, given his `id`.
  - `DELETE /doctors/{id}` Delete a doctor, given his `id`.

  For each method, it was created a integration test in `./api/src/test/java/com/example/api/DoctorControllerIntegrationTest.java` using the class `TestRestTemplate` provided by Spring Boot.

- **[Task 3: Create one JPA repository for connecting to database (5 min)]**

  In order to execute the CRUD operations and interact with the database, it was created the interface `DoctorRepository`, which simply extends `JPARepository` considering the `Doctor` class.

## Total time spent
| Task                                           | Time Taken  |
|------------------------------------------------|-------------|
| Add file-based database                        | 10 min      |
| Add Lombok                                     | 15 min      |
| Create runnable Docker image                   | 10 min      |
| Add OpenApi documentation                      | 10 min      |
| Create Data Model (Doctor)                     | 30 min      |
| Create CRUD-based Controller (DoctorController)| 3 hours     |
| Create one JPA repository for connecting to database | 5 min    |
| **Total Time**                                 |**~4.5 hours**|