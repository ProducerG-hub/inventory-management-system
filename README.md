# Inventory Management System

A full-stack inventory management application designed for businesses that need a reliable way to manage products, suppliers, stock movement, sales, communication within their business system, auditing, and reporting from a single platform. The system combines a Java Spring Boot backend with a React frontend, providing a clean and maintainable foundation for day-to-day operations.

## Overview

This project is built to support core inventory workflows such as:

- User authentication and role-based access
- Product and category management
- Supplier and customer tracking
- Stock movement monitoring
- Sales processing and receipt generation
- Dashboard insights and reporting
- Profile management
- Messaging and notifications
- Auditing and logging of critical operations

The application is structured as a modular monorepo with separate backend and frontend services, making it easier to evolve each layer independently.

## Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- JWT authentication
- PostgreSQL
- OpenAPI / Swagger documentation
- Maven for build and dependency management
- Lombok for boilerplate reduction
- Inet Address for IP handling
- Multipart file upload support such as profile images

### Frontend
- React for building the user interface
- Vite for fast development and build tooling
- React Router DOM for client-side routing
- Axios for API requests
- Bootstrap for responsive UI components
- Recharts for data visualization
- React Toastify for notifications.
- React Hook Form for form handling and validation


## Project Structure

- backend service: inventory-management/
- frontend application: inventory-management-frontend/
- database scripts: database/
- ERD documentation: Documentation/

## Features

- Secure login and authentication flow
- CRUD operations for products, categories, suppliers, customers, and users
- Inventory movement tracking for stock updates
- Sales workflow with invoice/receipt support
- Reporting and dashboard summaries for operational visibility
- REST API documentation through Swagger UI
- Messaging system for internal communication
- Auditing and logging of critical operations for compliance and traceability
- Profile management for users including image uploads

## Prerequisites

Before running the application locally, ensure you have:

- Java 21 or newer
- Maven or the included Maven wrapper
- Node.js and npm
- PostgreSQL running locally

## Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd inventory-management
   ```

2. Copy the example properties file and update it with your local database and JWT settings:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

3. Update the values in application.properties for your PostgreSQL instance and JWT secret.

4. Start the backend:
   ```bash
   ./mvnw spring-boot:run
   ```

The API should start on the default Spring Boot port.

## Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd inventory-management-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend will be available in the local Vite development environment.

## Database

Name: inventorydb

Database setup scripts are available in the database/ folder. Use them to initialize the schema and supporting tables in your PostgreSQL instance before running the app.

NOTE: Remember to update the database connection settings in the backend application.properties file to match your local PostgreSQL configuration.

## API Documentation

Once the backend is running, Swagger UI can be used to explore the REST endpoints:

- Swagger UI: /swagger-ui/index.html

Also Postman can be used to test the endpoints.

## Development Notes

- Keep the backend and frontend concerns separated to preserve maintainability.
- Prefer small, focused changes and validate them locally before merging.
- Use environment-specific configuration for database and security settings.
- Follow existing project conventions when adding new modules, services, or UI components.

## Contributors

- [Gwamaka Mwakabuta](https://github.com/ProducerG-hub)
