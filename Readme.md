[//]: # (# Waste Management REST API)

[//]: # ()
[//]: # (## Overview)

[//]: # (This Spring Boot application provides a REST API for waste management operations, including waste categories, disposal guidelines, and recycling tips management. The API uses H2 in-memory database for data persistence and follows RESTful principles.)

[//]: # ()
[//]: # (## Technical Stack)

[//]: # (- Java 17)

[//]: # (- Spring Boot 3.2.x)

[//]: # (- Spring Data JPA)

[//]: # (- H2 Database)

[//]: # (- Maven/Gradle)

[//]: # (- Spring Validation)

[//]: # ()
[//]: # (## Database Configuration)

[//]: # (The application uses H2 in-memory database. Configuration can be found in `application.properties`:)

[//]: # ()
[//]: # (```properties)

[//]: # (spring.datasource.url=jdbc:h2:mem:wastedb)

[//]: # (spring.datasource.driverClassName=org.h2.Driver)

[//]: # (spring.datasource.username=sa)

[//]: # (spring.datasource.password=)

[//]: # (spring.jpa.database-platform=org.hibernate.dialect.H2Dialect)

[//]: # (spring.h2.console.enabled=true)

[//]: # (spring.h2.console.path=/h2-console)

[//]: # (```)

[//]: # ()
[//]: # (## API Endpoints)

[//]: # ()
[//]: # (### Waste Categories)

[//]: # ()
[//]: # (#### Get All Categories)

[//]: # (- **URL**: `/api/v1/categories`)

[//]: # (- **Method**: GET)

[//]: # (- **Response**: Array of waste categories)

[//]: # (- **Status Codes**:)

[//]: # (    - 200: Success)

[//]: # (    - 500: Internal Server Error)

[//]: # ()
[//]: # (#### Get Category by ID)

[//]: # (- **URL**: `/api/v1/categories/{id}`)

[//]: # (- **Method**: GET)

[//]: # (- **Parameters**: id &#40;path variable&#41;)

[//]: # (- **Status Codes**:)

[//]: # (    - 200: Success)

[//]: # (    - 404: Category not found)

[//]: # (    - 500: Internal Server Error)

[//]: # ()
[//]: # (#### Create Category)

[//]: # (- **URL**: `/api/v1/categories`)

[//]: # (- **Method**: POST)

[//]: # (- **Request Body**:)

[//]: # (```json)

[//]: # ({)

[//]: # (    "name": "Plastic",)

[//]: # (    "description": "Plastic waste materials",)

[//]: # (    "guidelines": "Clean and dry before disposal")

[//]: # (})

[//]: # (```)

[//]: # (- **Status Codes**:)

[//]: # (    - 201: Created)

[//]: # (    - 400: Bad Request)

[//]: # (    - 500: Internal Server Error)

[//]: # ()
[//]: # (#### Update Category)

[//]: # (- **URL**: `/api/v1/categories/{id}`)

[//]: # (- **Method**: PUT)

[//]: # (- **Parameters**: id &#40;path variable&#41;)

[//]: # (- **Status Codes**:)

[//]: # (    - 200: Success)

[//]: # (    - 404: Category not found)

[//]: # (    - 400: Bad Request)

[//]: # (    - 500: Internal Server Error)

[//]: # ()
[//]: # (#### Delete Category)

[//]: # (- **URL**: `/api/v1/categories/{id}`)

[//]: # (- **Method**: DELETE)

[//]: # (- **Parameters**: id &#40;path variable&#41;)

[//]: # (- **Status Codes**:)

[//]: # (    - 204: No Content)

[//]: # (    - 404: Category not found)

[//]: # (    - 500: Internal Server Error)

[//]: # ()
[//]: # (### Disposal Guidelines)

[//]: # ()
[//]: # ([Similar endpoint documentation for Disposal Guidelines endpoints...])

[//]: # ()
[//]: # (### Recycling Tips)

[//]: # ()
[//]: # ([Similar endpoint documentation for Recycling Tips endpoints...])

[//]: # ()
[//]: # (## Data Models)

[//]: # ()
[//]: # (### WasteCategory)

[//]: # (```java)

[//]: # (public class WasteCategory {)

[//]: # (    @Id)

[//]: # (    @GeneratedValue&#40;strategy = GenerationType.IDENTITY&#41;)

[//]: # (    private Long id;)

[//]: # (    )
[//]: # (    @NotBlank)

[//]: # (    @Size&#40;max = 100&#41;)

[//]: # (    private String name;)

[//]: # (    )
[//]: # (    @Size&#40;max = 500&#41;)

[//]: # (    private String description;)

[//]: # (    )
[//]: # (    private String guidelines;)

[//]: # (    )
[//]: # (    // getters, setters, etc.)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### DisposalGuideline)

[//]: # (```java)

[//]: # (public class DisposalGuideline {)

[//]: # (    @Id)

[//]: # (    @GeneratedValue&#40;strategy = GenerationType.IDENTITY&#41;)

[//]: # (    private Long id;)

[//]: # (    )
[//]: # (    @NotBlank)

[//]: # (    @Size&#40;max = 200&#41;)

[//]: # (    private String title;)

[//]: # (    )
[//]: # (    @NotBlank)

[//]: # (    private String content;)

[//]: # (    )
[//]: # (    @ManyToOne)

[//]: # (    private WasteCategory category;)

[//]: # (    )
[//]: # (    // getters, setters, etc.)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (## Error Handling)

[//]: # (The API uses a global exception handler to manage errors:)

[//]: # ()
[//]: # (```java)

[//]: # (@ControllerAdvice)

[//]: # (public class GlobalExceptionHandler {)

[//]: # (    @ExceptionHandler&#40;ResourceNotFoundException.class&#41;)

[//]: # (    public ResponseEntity<?> handleResourceNotFoundException&#40;ResourceNotFoundException ex&#41; {)

[//]: # (        ErrorResponse error = new ErrorResponse&#40;)

[//]: # (            HttpStatus.NOT_FOUND.value&#40;&#41;,)

[//]: # (            ex.getMessage&#40;&#41;)

[//]: # (        &#41;;)

[//]: # (        return new ResponseEntity<>&#40;error, HttpStatus.NOT_FOUND&#41;;)

[//]: # (    })

[//]: # (    )
[//]: # (    // Other exception handlers...)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (## Validation)

[//]: # (The API uses Spring Boot validation annotations to ensure data integrity:)

[//]: # (- @NotNull: Field cannot be null)

[//]: # (- @NotBlank: Field cannot be blank)

[//]: # (- @Size: Specifies the size constraints)

[//]: # (- @Valid: Validates nested objects)

[//]: # ()
[//]: # (## Security)

[//]: # (Basic security measures implemented:)

[//]: # (- CORS configuration)

[//]: # (- Input validation)

[//]: # (- Error handling)

[//]: # (- Rate limiting &#40;optional&#41;)

[//]: # ()
[//]: # (## Testing)

[//]: # (The project includes unit tests and integration tests using:)

[//]: # (- JUnit 5)

[//]: # (- Mockito)

[//]: # (- Spring Boot Test)

[//]: # ()
[//]: # (## Building and Running)

[//]: # ()
[//]: # (### Prerequisites)

[//]: # (- Java 17 or higher)

[//]: # (- Maven 3.6+ or Gradle 7+)

[//]: # ()
[//]: # (### Build)

[//]: # (```bash)

[//]: # (# Using Maven)

[//]: # (mvn clean install)

[//]: # ()
[//]: # (# Using Gradle)

[//]: # (gradle clean build)

[//]: # (```)

[//]: # ()
[//]: # (### Run)

[//]: # (```bash)

[//]: # (# Using Maven)

[//]: # (mvn spring-boot:run)

[//]: # ()
[//]: # (# Using Gradle)

[//]: # (gradle bootRun)

[//]: # (```)

[//]: # ()
[//]: # (The application will start on `http://localhost:8080`)

[//]: # ()
[//]: # (## Contributing)

[//]: # (1. Fork the repository)

[//]: # (2. Create your feature branch)

[//]: # (3. Commit your changes)

[//]: # (4. Push to the branch)

[//]: # (5. Create a Pull Request)

[//]: # ()
[//]: # (## License)

[//]: # (This project is licensed under the MIT License.)







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
- **Your Name** - Developer

