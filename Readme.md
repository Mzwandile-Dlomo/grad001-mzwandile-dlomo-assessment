# **Spring Boot Waste Management API**

## **Project Overview**
The **Spring Boot Waste Management API** is a RESTful web service designed to manage data related to waste categories, disposal guidelines, and recycling tips. The application provides endpoints for creating, reading, updating, and deleting records in an in-memory H2 database. This API is designed with best practices in mind, including proper input validation, error handling, and adherence to object-oriented principles.

---

## **Setup Instructions**

### **Prerequisites**
- Java Development Kit (JDK) 11 or higher
- Maven 3.6+

### **Steps to Run the Application Locally**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd springboot-waste-management-api
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the API at `http://localhost:8080`

5. To inspect the database, access the H2 console at `http://localhost:8080/h2-console`:
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: (leave empty)

---

## **API Documentation**

### **Waste Category Endpoints**

#### **1. Retrieve All Categories**
**Endpoint**: `GET /categories`

**Response**:
```json
[
  {
    "id": 1,
    "name": "Plastic",
    "description": "All types of plastic waste"
  }
]
```

#### **2. Create a New Category**
**Endpoint**: `POST /categories`

**Request Body**:
```json
{
  "name": "Paper",
  "description": "All types of paper waste"
}
```

**Response**:
```json
{
  "id": 2,
  "name": "Paper",
  "description": "All types of paper waste"
}
```

#### **3. Update a Category**
**Endpoint**: `PUT /categories/{id}`

**Request Body**:
```json
{
  "name": "Plastic",
  "description": "Recyclable plastic waste"
}
```

**Response**:
```json
{
  "id": 1,
  "name": "Plastic",
  "description": "Recyclable plastic waste"
}
```

#### **4. Delete a Category**
**Endpoint**: `DELETE /categories/{id}`

**Response**:
```json
{
  "message": "Category deleted successfully"
}
```

### **Disposal Guideline Endpoints**

#### **1. Retrieve All Guidelines**
**Endpoint**: `GET /guidelines`

**Response**:
```json
[
  {
    "id": 1,
    "categoryId": 1,
    "instructions": "Rinse plastic containers before recycling"
  }
]
```

#### **2. Create a New Guideline**
**Endpoint**: `POST /guidelines`

**Request Body**:
```json
{
  "categoryId": 1,
  "instructions": "Do not recycle contaminated plastic"
}
```

**Response**:
```json
{
  "id": 2,
  "categoryId": 1,
  "instructions": "Do not recycle contaminated plastic"
}
```

#### **3. Update a Guideline**
**Endpoint**: `PUT /guidelines/{id}`

**Request Body**:
```json
{
  "categoryId": 1,
  "instructions": "Recycle clean plastic only"
}
```

**Response**:
```json
{
  "id": 1,
  "categoryId": 1,
  "instructions": "Recycle clean plastic only"
}
```

#### **4. Delete a Guideline**
**Endpoint**: `DELETE /guidelines/{id}`

**Response**:
```json
{
  "message": "Guideline deleted successfully"
}
```

### **Recycling Tip Endpoints**

#### **1. Retrieve All Tips**
**Endpoint**: `GET /tips`

**Response**:
```json
[
  {
    "id": 1,
    "categoryId": 1,
    "tip": "Use reusable bags instead of plastic"
  }
]
```

#### **2. Create a New Tip**
**Endpoint**: `POST /tips`

**Request Body**:
```json
{
  "categoryId": 1,
  "tip": "Avoid single-use plastic"
}
```

**Response**:
```json
{
  "id": 2,
  "categoryId": 1,
  "tip": "Avoid single-use plastic"
}
```

#### **3. Update a Tip**
**Endpoint**: `PUT /tips/{id}`

**Request Body**:
```json
{
  "categoryId": 1,
  "tip": "Recycle plastic bottles properly"
}
```

**Response**:
```json
{
  "id": 1,
  "categoryId": 1,
  "tip": "Recycle plastic bottles properly"
}
```

#### **4. Delete a Tip**
**Endpoint**: `DELETE /tips/{id}`

**Response**:
```json
{
  "message": "Tip deleted successfully"
}
```

---

## **Database**
This application uses an **H2 in-memory database** to store the data. The database is reset every time the application is restarted. The schema includes three main tables:

1. **Categories**
2. **Guidelines** (linked to Categories via `categoryId`)
3. **Tips** (linked to Categories via `categoryId`)

You can access the H2 console at `http://localhost:8080/h2-console` using the following credentials:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

---

## **Validation Rules**
1. **Waste Category**:
    - `name`: Required, must not be empty.
    - `description`: Required, must not be empty.

2. **Disposal Guideline**:
    - `categoryId`: Required, must be an existing category.
    - `instructions`: Required, must not be empty.

3. **Recycling Tip**:
    - `categoryId`: Required, must be an existing category.
    - `tip`: Required, must not be empty.

---

## **Error Handling**
The API returns appropriate HTTP status codes and error messages for different scenarios:

- **400 Bad Request**: Invalid input or missing required fields.
- **404 Not Found**: Resource not found.
- **500 Internal Server Error**: Unexpected server error.

Example error response:
```json
{
  "timestamp": "2024-01-13T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Name field is required",
  "path": "/categories"
}
```

---

## **Future Improvements**
1. Add authentication and authorization.
2. Implement pagination and sorting for large datasets.
3. Add support for exporting data in CSV or Excel format.

---

## **Contributors**
- **Mzwandile Dlomo** - Developer

