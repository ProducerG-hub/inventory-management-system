# Inventory Management System

A full-stack inventory management application designed for businesses that need a reliable way to manage products, suppliers, stock movement, sales, and reporting from a single platform. The system combines a Java Spring Boot backend with a React frontend, providing a clean and maintainable foundation for day-to-day operations.

## Overview

This project is built to support core inventory workflows such as:

- User authentication and role-based access
- Product and category management
- Supplier and customer tracking
- Stock movement monitoring
- Sales processing and receipt generation
- Dashboard insights and reporting

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

### Frontend
- React
- Vite
- React Router DOM
- Axios
- Bootstrap
- Recharts

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

Database setup scripts are available in the database/ folder. Use them to initialize the schema and supporting tables in your PostgreSQL instance before running the app.

## API Documentation

Once the backend is running, Swagger UI can be used to explore the REST endpoints:

- Swagger UI: /swagger-ui/index.html

## Development Notes

- Keep the backend and frontend concerns separated to preserve maintainability.
- Prefer small, focused changes and validate them locally before merging.
- Use environment-specific configuration for database and security settings.
- Follow existing project conventions when adding new modules, services, or UI components.

## Contributors

- [Gwamaka Mwakabuta](https://github.com/ProducerG-hub)
