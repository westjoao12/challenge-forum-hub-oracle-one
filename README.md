# Forum Hub - REST API

> A secure REST API for managing forum topics and responses, developed as a challenge for Oracle's ONE program.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## 📝 Overview

Forum Hub is a REST API developed with Java 25, Spring Boot 4.0.2, and Spring Security 7. It provides a complete solution for managing forum topics and responses with enterprise-grade security through JWT authentication.

This project was developed as a challenge proposed by Oracle's ONE program, demonstrating best practices in API development, security, and clean code architecture.

## ✨ Features

- **JWT Authentication** - Secure token-based authentication
- **Complete CRUD Operations** - Full Create, Read, Update, Delete functionality for topics and responses
- **Spring Security 7** - Enterprise-level security framework
- **Swagger UI Documentation** - Interactive API documentation
- **RESTful API Design** - Following REST conventions and best practices
- **Error Handling** - Comprehensive exception handling and validation
- **Database Integration** - Persistent data storage
- **Input Validation** - Request validation using Spring validation annotations
- **Response Pagination** - Efficient data retrieval with pagination support

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 25 | Programming Language |
| Spring Boot | 4.0.2 | Application Framework |
| Spring Security | 7 | Authentication & Authorization |
| Spring Data JPA | Latest | Database ORM |
| JWT (JSON Web Tokens) | - | Token-based Authentication |
| Swagger/OpenAPI | 3.0 | API Documentation |
| Maven | 3.6+ | Build Tool |
| Database | (Configure in application.properties) | Data Persistence |

## 📦 Prerequisites

Before running the application, ensure you have installed:

- **Java 25 or higher** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6 or higher** - [Download Maven](https://maven.apache.org/download.cgi)
- **Git** - [Download Git](https://git-scm.com/downloads)
- **Database** - MySQL, PostgreSQL, or H2 (see configuration section)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/westjoao12/challenge-forum-hub-oracle-one.git
cd challenge-forum-hub-oracle-one
```

### 2. Verify Java Installation

```bash
java -version
```

Should output Java 25 or higher.

### 3. Install Dependencies

```bash
mvn clean install
```

## ⚙️ Configuration

### 1. Environment Variables

Copy the example environment file and configure it:

```bash
cp .env.example .env
```

Edit `.env` with your configuration:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/forum_hub
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRATION=86400000
```

### 2. Application Properties

Update `src/main/resources/application.properties` or `application.yml`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# JWT Configuration
app.jwt.secret=your_very_secure_secret_key_change_this_in_production
app.jwt.expirationMs=86400000

# Logging
logging.level.root=INFO
logging.level.com.forumhub=DEBUG
```

## ▶️ Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Java Command

```bash
mvn clean package
java -jar target/forum-hub-*.jar
```

### Using IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, or VS Code)
2. Run the main application class: `ForumHubApplication.java`

The application will start on `http://localhost:8080` (or your configured port).

## 📚 API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/api/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/api/v3/api-docs)

## 🔐 Authentication

### JWT Token Flow

1. **Register/Login** - Send credentials to obtain JWT token
2. **Store Token** - Client stores the JWT token
3. **Include Token** - Add token to `Authorization` header for subsequent requests
4. **Token Validation** - Server validates token on each request
5. **Token Refresh** - Request new token when current one expires

### Authorization Header Format

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Token Structure

JWT tokens include:
- **Header** - Algorithm and token type
- **Payload** - User information and claims
- **Signature** - Encrypted verification

### Token Expiration

Default token expiration is 2 hours (configurable via `JWT_EXPIRATION`).

## 📡 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/usuarios` | Register new user |
| POST | `/login` | Login and get JWT token |

### Topics Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/topicos` | List all topics (paginated) |
| GET | `/topicos/{id}` | Get specific topic |
| POST | `/topicos` | Create new topic |
| PUT | `/topicos/{id}` | Update topic |
| DELETE | `/topicos/{id}` | Delete topic |

### Responses Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/topicos/{topicId}/respostas` | List responses for topic |
| GET | `/respostas/{id}` | Get specific response |
| POST | `/respostas` | Create new response |
| PUT | `/respostas/{id}` | Update response |
| DELETE | `/respostas/{id}` | Delete response |

## 💡 Usage Examples

### 1. Register User

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "westjoao", "email": "westjoao@example.com", "senha": "SecurePassword123!"}'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email": "westjoao@gmail.com", "senha": "SecurePassword123!"}'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Create Topic

```bash
curl -X POST http://localhost:8080/api/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"titulo": "How to Learn Java?", "mensagem": "I want to learn Java programming from scratch", "idAutor": "1", "idCurso": "1"}'
```

### 4. List Topics

```bash
curl -X GET "http://localhost:8080/api/topicos?page=0&size=10" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 5. Create Response

```bash
curl -X POST http://localhost:8080/api/respostas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"message": "Start with the basics of OOP and practice daily", "topicId": 1, "idAutor": "1"}'
```

### 6. Update Topic

```bash
curl -X PUT http://localhost:8080/api/topicos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"titulo": "How to Learn Java Effectively?", "mensagem": "Updated description"}'
```

### 7. Delete Topic

```bash
curl -X DELETE http://localhost:8080/api/topics/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📁 Project Structure

```
challenge-forum-hub-oracle-one/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/forumhub/
│   │   │       ├── controller/       # REST controllers
│   │   │       ├── service/          # Business logic
│   │   │       ├── repositores/      # Database access layer
│   │   │       ├── domain/           # JPA entities
│   │   │       ├── infra/            # JWT and security config
|   |   |       |   └── exception/    # Custom exceptions
|   |   |       |   └── security/     # JWT and security config
|   |   |       |   └── springdoc/    # Spring Documentation
│   │   │       └── ForumHubApplication.java  # Main entry point
│   │   └── resources/
│   │       ├── application.properties    # Configuration
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/forumhub/         # Unit and integration tests
├── pom.xml                             # Maven dependencies
├── mvnw & mvnw.cmd                     # Maven wrapper
├── .env.example                        # Environment variables template
├── .gitignore                          # Git ignore rules
└── README.md                           # This file
```

## 🔒 Security

### Security Features Implemented

- **JWT Authentication** - Token-based stateless authentication
- **Spring Security** - Role-based access control (RBAC)
- **Password Encryption** - BCrypt password hashing
- **CORS Configuration** - Cross-Origin Resource Sharing
- **Input Validation** - Server-side request validation
- **Exception Handling** - Secure error responses without exposing sensitive info
- **HTTPS Ready** - Can be deployed with SSL/TLS

### Best Practices

1. **Never** commit sensitive data like JWT secrets or database passwords
2. Use environment variables for configuration
3. Always use HTTPS in production
4. Implement rate limiting for login endpoints
5. Regularly update dependencies
6. Use strong JWT secret keys (minimum 32 characters)
7. Implement request validation
8. Use appropriate HTTP status codes

### CORS Configuration

Configure allowed origins in `SecurityConfig`:

```java
.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    return configuration;
}))
```

## 🧪 Testing

Run tests with Maven:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TopicControllerTest

# Generate coverage report
mvn test jacoco:report
```

## 🚢 Deployment

### Docker Support

Create a `Dockerfile`:

```dockerfile
FROM openjdk:25-slim
COPY target/forum-hub-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
docker build -t forum-hub:latest .
docker run -p 8080:8080 forum-hub:latest
```

### Production Checklist

- [ ] Set strong JWT secret
- [ ] Configure database for production
- [ ] Enable HTTPS/SSL
- [ ] Set appropriate logging levels
- [ ] Configure CORS properly
- [ ] Set up monitoring and alerts
- [ ] Implement backup strategy
- [ ] Use environment variables for secrets
- [ ] Enable rate limiting
- [ ] Set up CI/CD pipeline

## 📞 Troubleshooting

### Issue: Connection refused

**Solution**: Ensure database is running and connection properties are correct.

### Issue: JWT token invalid

**Solution**: Check token expiration and JWT secret configuration.

### Issue: Port already in use

**Solution**: Change port in application.properties or kill process using port 8080.

### Issue: Build fails

**Solution**: Run `mvn clean` and ensure Java 25+ is installed.

## 📖 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT.io - JSON Web Tokens](https://jwt.io)
- [REST API Best Practices](https://restfulapi.net)
- [Maven Documentation](https://maven.apache.org/guides/)

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License. See the LICENSE file for more details.

## 👤 Author

**João Afonso Fukiau** code name **West João**
- GitHub: [@westjoao12](https://github.com/westjoao12)
- Project: [Forum Hub - REST API](https://github.com/westjoao12/challenge-forum-hub-oracle-one)

## 🙏 Acknowledgments

- Oracle's ONE Program
- Spring Framework Team
- Community contributors

---

Made with ❤️ by West João
