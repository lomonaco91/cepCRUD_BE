# User Address Management System

A full-stack application for managing users and their addresses, with integration to a CEP (Brazilian postal code) API.

## Features

- **User Management**
  - CRUD operations for users
  - Role-based access control (Admin/User)
  - JWT authentication
- **Address Management**
  - CRUD operations for addresses
  - Integration with ViaCEP API
  - Address validation
- **Frontend Features**
  - Responsive Angular 17+ interface
  - Material Design components
  - Form validation
  - Pagination and sorting
  - Toast notifications

## Technologies

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security
- JWT authentication
- PostgreSQL
- JPA/Hibernate
- ViaCEP API integration

### Frontend
- Angular 17+
- Angular Material
- RxJS
- JWT authentication
- Responsive design

## Prerequisites

- Node.js 18+
- Angular CLI 17+
- Java 17+
- PostgreSQL 15+

## Installation

### Backend
1. Clone the repository
2. Configure PostgreSQL connection in `application.properties`
3. Run the Spring Boot application

### Frontend
1. Navigate to the frontend directory
2. Install dependencies:
3. Run code. Open your browser at http://localhost:4200
   ```bash
   npm install
   ng serve