# Herfty (Artisanat Marketplace)

The backend API for the Herfty platform, a marketplace connecting artisans with clients. Built with Java and Spring Boot, this service handles user authentication, product management, shopping carts, order processing, and PayPal payments.

## Features

- **Authentication & Authorization:** Secure JWT-based authentication with role-based access control (ARTISAN, CLIENT, ADMIN).
- **Artisan Management:** Artisans can manage their profiles, add/update/delete articles (products), track revenue, and view incoming orders.
- **Client Management:** Clients can manage their shopping cart, place orders, and view order history.
- **E-commerce Logic:** Complete cart-to-order workflow, including quantity updates and price calculations.
- **Competitions:** Artisans can participate in competitions by submitting their articles and receiving votes.
- **Payment Integration:** PayPal SDK integration for processing payments (Sandbox mode configured).
- **Security:** Stateless session management using custom JWT filters.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17+ |
| Framework | Spring Boot |
| Security | Spring Security, JSON Web Tokens (JWT) |
| Data Access | Spring Data JPA |
| Build Tool | Maven |
| Payment Gateway | PayPal SDK |
| Database | MySQL / PostgreSQL (Configured via JPA) |

## Project Structure

```text
herfty-backend/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/artisanat/artisanat/
│   │   │   ├── Controllers/       # REST API endpoints
│   │   │   ├── model/
│   │   │   │   ├── DTOs/          # Data Transfer Objects
│   │   │   │   ├── Entities/      # JPA Entities (Database models)
│   │   │   │   ├── Filters/       # JWT Authentication Filter
│   │   │   │   ├── Repositories/  # Spring Data JPA Repositories
│   │   │   │   └── Services/      # Business logic
│   │   │   ├── Security/          # Security Configuration
│   │   │   └── ArtisanatApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/                      # Unit and integration tests
├── .gitignore
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java Development Kit (JDK) 17 or higher
- Maven
- A running relational database (e.g., MySQL, PostgreSQL)

### 1. Clone the Repository

```bash
git clone https://github.com/novuterapro/Herfty-Backend.git
```

### 2. Configure the Database and Environment

Open `src/main/resources/application.properties` and configure your database connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/herfty_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Configure Security and Payment Secrets
Add the following to your `application.properties`:

```properties
# JWT Configuration
jwt.secret=your_base64_encoded_secret_key_here
jwt.expiration=3600

# PayPal Configuration
paypal.client.id=your_paypal_client_id
paypal.client.secret=your_paypal_client_secret
paypal.mode=sandbox
```

## API Endpoints Overview

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Authenticate user and receive JWT |
| POST | `/add/Artisan` | Register a new Artisan |
| POST | `/add/Client` | Register a new Client |

### Artisan Routes (Requires ARTISAN role)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/artisan/articles` | List all articles by the artisan |
| POST | `/artisan/addArticle` | Add a new article |
| PUT | `/artisan/updateArticle` | Update an existing article |
| DELETE | `/artisan/deleteArticle/{id}` | Delete an article |
| GET | `/artisan/totalRevenue` | Get total revenue for the artisan |
| GET | `/artisan/commandes` | View orders assigned to the artisan |
| POST | `/artisan/participer/{competitionId}` | Participate in a competition |

### Client Routes (Requires CLIENT role)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/client/cart` | View current shopping cart |
| POST | `/client/panier` | Add an article to the cart |
| PUT | `/client/cart/articles/{id}` | Update cart item quantity |
| DELETE | `/client/cart/articles/{id}` | Remove item from cart |
| POST | `/client/commander` | Place an order |
| GET | `/client/commandes` | View order history |


## Author

Novutera

- GitHub: [@novuterapro](https://github.com/novuterapro)