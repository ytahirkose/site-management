# SmartShopAI Site Management Backend

Professional, enterprise-grade backend application for apartment/site management system built with Spring Boot 3.5.x, Java 24, and MongoDB.

## 🚀 Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
  - Email and phone number login support
  - Secure password management
  - Role-based access (Admin/Resident)
- **User Management**: Complete user lifecycle management (residents and administrators)
  - Contact information management
  - Emergency contact details
  - Apartment/building assignment
- **Announcements**: Site-wide announcement system with targeting capabilities
  - Admin-only announcements
  - Resident-visible announcements
  - Priority-based notification system
- **Payment Management**: Dues and payment tracking with receipt uploads
  - Monthly and yearly payment summaries
  - Bank receipt and EFT upload support
  - Late fee calculation
  - Payment method selection (Bank, Cash, Check, Online)
- **Issue Tracking**: Maintenance issue reporting and resolution tracking
  - Photo upload support (camera or file selection)
  - Automatic admin notification system
  - Escalation management
  - Status tracking (Open, In Progress, Resolved, Closed)
- **File Management**: Secure file upload and storage integration
  - Multiple storage providers (Local, Cloudinary, Firebase, Supabase)
  - Support for various file types (images, documents)
  - Category-based organization
- **Push Notifications**: FCM integration for real-time notifications
  - Issue alerts to administrators
  - Payment reminders
  - Announcement notifications
  - Role and location-based targeting
- **API Documentation**: Comprehensive Swagger/OpenAPI documentation
- **Security**: Enterprise-grade security with validation and exception handling

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.x
- **Language**: Java 24
- **Database**: MongoDB with Spring Data MongoDB
- **Security**: Spring Security with JWT
- **Validation**: Bean Validation (Jakarta)
- **Mapping**: MapStruct for DTO-Entity conversion
- **Documentation**: OpenAPI 3.0 (Swagger)
- **Build Tool**: Gradle
- **Logging**: SLF4J with Logback

## 📋 Prerequisites

- Java 24 or higher
- MongoDB 6.0 or higher
- Gradle 8.0 or higher

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd site-management
```

### 2. Configure Environment Variables

Create a `.env` file in the root directory:

```bash
# MongoDB Configuration
MONGODB_URI=mongodb://localhost:27017/site_management

# JWT Configuration
JWT_SECRET=your-256-bit-secret-key-here-change-in-production
JWT_EXPIRATION=86400000

# File Storage (choose one)
STORAGE_TYPE=cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Push Notifications
FCM_SERVER_KEY=your_fcm_server_key
FCM_PROJECT_ID=your_fcm_project_id
```

### 3. Run the Application

```bash
# Using Gradle
./gradlew bootRun

# Or build and run
./gradlew build
java -jar build/libs/site-management-1.0.0.jar
```

### 4. Access the Application

- **API Base URL**: http://localhost:8080/api/v1
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Health Check**: http://localhost:8080/actuator/health

## 🏗️ Project Structure

```
src/main/java/com/smartshopai/
├── config/                 # Configuration classes
├── controller/            # REST controllers
├── domain/               # Domain layer
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # MongoDB entities
│   └── mapper/          # MapStruct mappers
├── exception/            # Custom exceptions
├── repository/           # Data access layer
├── security/             # Security components
└── service/              # Business logic layer
    └── impl/            # Service implementations
```

## 🔐 Security

- **JWT Authentication**: Stateless authentication with refresh tokens
- **Role-Based Access Control**: ADMIN and RESIDENT roles
- **Input Validation**: Comprehensive validation using Bean Validation
- **CORS Configuration**: Configurable cross-origin resource sharing
- **Password Security**: BCrypt password hashing

## 📚 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/refresh` - Refresh JWT token
- `POST /api/v1/auth/change-password` - Change password
- `POST /api/v1/auth/logout` - User logout

### User Management
- `GET /api/v1/users/profile` - Get current user profile
- `PUT /api/v1/users/profile` - Update current user profile
- `GET /api/v1/users/{id}` - Get user by ID (Admin only)
- `GET /api/v1/users` - Get all users with pagination (Admin only)
- `POST /api/v1/users` - Create new user (Admin only)
- `PUT /api/v1/users/{id}` - Update user (Admin only)
- `DELETE /api/v1/users/{id}` - Delete user (Admin only)

## 🗄️ Database Schema

### Collections

- **users**: User accounts and profiles
- **announcements**: Site announcements
- **payments**: Dues and payment records
- **issues**: Maintenance issues and complaints
- **files**: File metadata and storage information

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport

# Run integration tests
./gradlew integrationTest
```

## 📊 Monitoring & Health

- **Actuator Endpoints**: Health checks, metrics, and application info
- **Logging**: Structured logging with SLF4J
- **Metrics**: Prometheus metrics export

## 🚀 Deployment

### Docker

```bash
# Build Docker image
docker build -t smartshopai-site-management .

# Run container
docker run -p 8080:8080 smartshopai-site-management
```

### Cloud Deployment

The application is designed to be deployed on:
- **Render.com** (Free tier available)
- **Railway.app** (Free tier available)
- **Heroku** (Paid)
- **AWS ECS/Fargate**
- **Google Cloud Run**

## 🔧 Configuration

### Application Properties

Key configuration options in `application.yml`:

- **MongoDB**: Database connection and settings
- **JWT**: Token configuration and expiration
- **File Storage**: Cloud storage provider settings
- **Push Notifications**: FCM configuration
- **Logging**: Log levels and patterns

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact: info@smartshopai.com
- Documentation: [API Docs](http://localhost:8080/swagger-ui/index.html)

## 🔮 Roadmap

- [ ] Real-time notifications with WebSocket
- [ ] Advanced reporting and analytics
- [ ] Multi-tenant support
- [ ] Mobile app API optimization
- [ ] Advanced search and filtering
- [ ] Bulk operations support
- [ ] Audit logging
- [ ] Performance monitoring

---

**Built with ❤️ by SmartShopAI Team**
