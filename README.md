# eMall - E-commerce Microservices Platform

A comprehensive e-commerce platform built with Spring Cloud microservices architecture, featuring user management, product catalog, shopping cart, order processing, and payment services.

## Architecture Overview

eMall follows a microservices architecture pattern with the following key components:

- **API Gateway**: Single entry point for all client requests with authentication and routing
- **Service Registry**: Nacos-based service discovery and configuration management
- **Microservices**: Domain-specific services for different business capabilities
- **Database per Service**: Each service maintains its own database for data isolation
- **Inter-service Communication**: REST APIs with Feign clients and message queues

##  Technology Stack


## ⚙️ Technology Stack

###  Core Technologies
![Java](https://img.shields.io/badge/Java_11-007396?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_2.7.12-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud_2021.0.3-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Cloud Alibaba](https://img.shields.io/badge/Spring_Cloud_Alibaba_2021.0.4.0-6DB33F?style=flat-square)

---

###  Infrastructure & Tools
![Nacos](https://img.shields.io/badge/Nacos-0E8A16?style=flat-square)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring_Cloud_Gateway-6DB33F?style=flat-square)
![Seata](https://img.shields.io/badge/Seata-EE9D28?style=flat-square)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=flat-square&logo=rabbitmq&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)

---

###  Database & ORM
![MySQL](https://img.shields.io/badge/MySQL_8.0.23-4479A1?style=flat-square&logo=mysql&logoColor=white)
![MyBatis Plus](https://img.shields.io/badge/MyBatis_Plus_3.4.2-000000?style=flat-square)
![HikariCP](https://img.shields.io/badge/HikariCP-1E90FF?style=flat-square)

---

###  Security & Validation
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)
![Hibernate Validator](https://img.shields.io/badge/Hibernate_Validator-59666C?style=flat-square)

---

### 🛠️ Development Tools
![Lombok](https://img.shields.io/badge/Lombok-DC382D?style=flat-square)
![Hutool](https://img.shields.io/badge/Hutool-4A90E2?style=flat-square)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![Knife4j](https://img.shields.io/badge/Knife4j-00897B?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-1565C0?style=flat-square&logo=apachemaven&logoColor=white)

## Project Structure

```
eMall/
├── mall-gateway/          # API Gateway (Port: 8080)
├── user-service/          # User Management (Port: 8085)
├── item-service/          # Product Catalog (Port: 8081)
├── cart-service/          # Shopping Cart (Port: 8083)
├── order-service/         # Order Processing (Port: 8086)
├── payment-service/       # Payment Processing (Port: 8087)
├── mall-common/           # Shared utilities and configurations
├── mall-feign/            # Feign client interfaces
└── logs/                  # Application logs
```

## Services Overview

### 1. API Gateway (`mall-gateway`)
- **Port**: 8080
- **Purpose**: Single entry point for all client requests
- **Features**:
  - Request routing to appropriate microservices
  - JWT token validation
  - Load balancing
  - Cross-cutting concerns (logging, monitoring)

### 2. User Service (`user-service`)
- **Port**: 8085
- **Database**: mall-user
- **Endpoints**: `/users/**`, `/addresses/**`
- **Features**:
  - User registration and authentication
  - JWT token generation and validation
  - User profile management
  - Address management
  - Password encryption

### 3. Item Service (`item-service`)
- **Port**: 8081
- **Database**: mall-item
- **Endpoints**: `/items/**`, `/search/**`
- **Features**:
  - Product catalog management
  - Product search with Elasticsearch
  - Category management
  - Inventory tracking
  - Product recommendations

### 4. Cart Service (`cart-service`)
- **Port**: 8083
- **Database**: mall-cart
- **Endpoints**: `/carts/**`
- **Features**:
  - Add/remove items from cart
  - Update item quantities
  - Cart persistence
  - Integration with Item Service for product details

### 5. Order Service (`order-service`)
- **Port**: 8086
- **Database**: mall-trade
- **Endpoints**: `/orders/**`
- **Features**:
  - Order creation and management
  - Order status tracking
  - Order history
  - Integration with Cart, Item, and User services
  - Distributed transaction management with Seata

### 6. Payment Service (`payment-service`)
- **Port**: 8087
- **Database**: mall-pay
- **Endpoints**: `/pay-orders/**`
- **Features**:
  - Payment processing
  - Payment status tracking
  - Integration with Order service
  - Payment gateway integration

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 11** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Redis**
- **RabbitMQ**
- **Elasticsearch 7.12.1**
- **Docker** (for containerized services)

## Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd eMall
```

### 2. Start Infrastructure Services

#### Using Docker (Recommended)

**Deploy Nacos on container:**
```bash
docker run -d --name nacos --network mall-net --env-file /path/to/nacoscustom.env \
  -p 8848:8848 -p 9848:9848 -p 9849:9849 --restart=always nacos/nacos-server:v2.1.0-slim
```

**Deploy Seata (Distributed Transaction Management):**
```bash
docker stop seata
docker rm seata
docker run --platform linux/arm64/v8 --name seata -p 8099:8099 -p 7099:7099 \
  -e SEATA_IP=127.0.0.1 -v /path/to/seata:/seata-server/resources \
  --privileged=true --network mall-net -d seataio/seata-server:1.6.0
```

#### Manual Setup
- **MySQL**: Create databases: `mall-user`, `mall-item`, `mall-cart`, `mall-trade`, `mall-pay`
- **Redis**: Start Redis server on default port 6379
- **RabbitMQ**: Start RabbitMQ server on default port 5672
- **Elasticsearch**: Start Elasticsearch on port 9200

### 3. Configure Application Properties

Update the configuration files in each service with your database and service URLs:

- Database connections
- Redis configuration
- RabbitMQ settings
- Nacos server address
- Elasticsearch connection

### 4. Build the Project
```bash
mvn clean install
```

### 5. Start Services

Start services in the following order:

```bash
# 1. Start API Gateway
cd mall-gateway
mvn spring-boot:run

# 2. Start User Service
cd ../user-service
mvn spring-boot:run

# 3. Start Item Service
cd ../item-service
mvn spring-boot:run

# 4. Start Cart Service
cd ../cart-service
mvn spring-boot:run

# 5. Start Order Service
cd ../order-service
mvn spring-boot:run

# 6. Start Payment Service
cd ../payment-service
mvn spring-boot:run
```

### 6. Verify Installation

- **API Gateway**: http://localhost:8080
- **Nacos Console**: http://localhost:8848/nacos (nacos/nacos)
- **Service Health**: Check individual service endpoints

##  API Documentation

Each service provides Swagger/Knife4j documentation:

- **User Service**: http://localhost:8085/doc.html
- **Item Service**: http://localhost:8081/doc.html
- **Cart Service**: http://localhost:8083/doc.html
- **Order Service**: http://localhost:8086/doc.html
- **Payment Service**: http://localhost:8087/doc.html

## Application Flow

### Authentication Flow
1. User sends login credentials to API Gateway
2. Gateway forwards request to User Service
3. User Service authenticates and generates JWT token
4. Token returned to client for subsequent requests
5. Gateway validates token before forwarding requests

### Shopping Flow
1. **Browse Products**: User accesses Item Service through Gateway
2. **Add to Cart**: Cart Service manages shopping cart items
3. **Checkout**: Order Service processes order creation
4. **Payment**: Payment Service handles payment processing
5. **Confirmation**: Order status updated and confirmed

## Database Schema

Each service maintains its own database:

- **mall-user**: User accounts, profiles, addresses
- **mall-item**: Products, categories, inventory
- **mall-cart**: Shopping cart items
- **mall-trade**: Orders, order items, order status
- **mall-pay**: Payment records, payment status

## Configuration Management

The application uses Nacos for centralized configuration management:

- Service discovery and registration
- Configuration externalization
- Dynamic configuration updates
- Environment-specific settings

## Testing

Run tests for individual services:

```bash
# Test specific service
cd user-service
mvn test

# Test all services
mvn test
```

## Docker Deployment

The application supports Docker deployment:

```bash
# Build Docker images
mvn clean package docker:build

# Run with Docker Compose
docker-compose up -d
```

## Troubleshooting

### Common Issues

1. **Service Discovery Issues**
   - Ensure Nacos is running and accessible
   - Check service registration in Nacos console

2. **Database Connection Issues**
   - Verify MySQL is running
   - Check database credentials and URLs
   - Ensure databases are created

3. **Port Conflicts**
   - Check if required ports are available
   - Update port configurations if needed

4. **JWT Token Issues**
   - Verify JWT secret configuration
   - Check token expiration settings


## Future Enhancements

- [ ] Frontend application integration
- [ ] Advanced search capabilities
- [ ] Recommendation engine
- [ ] Real-time notifications
- [ ] Advanced monitoring and alerting
- [ ] CI/CD pipeline setup
- [ ] Kubernetes deployment support

---

**Note**: This is a comprehensive e-commerce microservices platform designed for learning and development purposes. For production use, additional security, monitoring, and scalability considerations should be implemented.
