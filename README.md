# 📦 Product Service

The **Product Service** is a core microservice within the e-commerce ecosystem responsible for managing the inventory, product catalog, and categories. It provides a robust RESTful API for creating, retrieving, updating, and searching products with high performance and reliability.


## 🚀 Key Features

### Core Functionality
* **CRUD Operations:** Full management (Create, Read, Update, Delete) for Products and Categories.
* **Advanced Patching:** Supports partial updates using **JSON Patch** for efficient resource modification.
* **Search & Filtering:** Implements advanced search capabilities with **Pagination** and **Sorting**.
* **Projections:** Optimized data retrieval using Spring Data JPA Projections and JPQL.

### Performance & Scalability
* **Caching:** Integrated **Redis** cache to reduce database load and improve response times for frequently accessed data.
* **Database Migration:** Uses **Flyway** for versioned and safe database schema evolution.
* **Load Balancing:** Client-side load balancing implemented (via RestTemplate) for communicating with other services.

### Quality & Reliability
* **Global Exception Handling:** Centralized error handling using `@ControllerAdvice`.
* **Testing:** Comprehensive test suite including:
    * **Unit Tests:** For Service and Controller layers (JUnit/Mockito).
    * **Integration Tests:** `@WebMvcTest` for testing the web layer flow.
* **Security Integration:** Token validation via inter-service communication with the **User Service**.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Data Access:** Spring Data JPA, Hibernate
* **Database:** MySQL
* **Caching:** Redis
* **Migration:** Flyway
* **Testing:** JUnit 5, Mockito
* **Build Tool:** Maven

## 🔌 API Endpoints

The service exposes the following REST endpoints (examples):

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/products` | Get all products (supports pagination & sorting) |
| `GET` | `/products/{id}` | Get specific product details |
| `POST` | `/products` | Create a new product |
| `PUT` | `/products/{id}` | Replace a product entirely |
| `PATCH` | `/products/{id}` | Update specific fields (JSON Patch) |
| `GET` | `/products/search` | Search products with specific criteria |

## ⚙️ Configuration

To run this application locally, you need to configure the database and Redis settings. Update your `application.properties` or `application.yml`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=your_password

# Flyway
spring.flyway.enabled=true

# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Service Discovery / Load Balancing
user.service.url=http://USER-SERVICE/