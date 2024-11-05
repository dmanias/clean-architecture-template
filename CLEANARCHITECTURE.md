# Clean Architecture Layers

## Overview

Clean Architecture organizes code into concentric circles representing different layers of the application, where:
- Inner circles contain business logic (domain)
- Outer circles contain implementation details
- Dependencies can only point inwards
- Inner circles don't know about outer circles

```
┌──────────────────────────────────────┐
│ Presentation Layer (Interface)       │
│  ┌──────────────────────────────┐   │
│  │ Infrastructure Layer         │   │
│  │  ┌──────────────────────┐   │   │
│  │  │ Application Layer    │   │   │
│  │  │  ┌──────────────┐   │   │   │
│  │  │  │ Domain Layer │   │   │   │
│  │  │  └──────────────┘   │   │   │
│  │  └──────────────────────┘   │   │
│  └──────────────────────────────┘   │
└──────────────────────────────────────┘
```

## 1. Domain Layer (Enterprise Business Rules)

### Purpose
- Contains the core business logic and rules
- Independent of all external concerns
- Represents the heart of the application

### Components
```
domain/
├── entity/          # Business entities
├── valueobject/     # Immutable value objects
├── event/           # Domain events
└── exception/       # Domain-specific exceptions
```

### Key Characteristics
- No dependencies on frameworks or external libraries
- Pure Java/Kotlin code
- Contains business logic validation
- Houses business invariants

### Example
```java
// Domain Entity
public class User {
    private final UserId id;
    private Email email;
    private Name name;
    private Status status;

    public void deactivate() {
        if (this.status != Status.ACTIVE) {
            throw new InvalidUserStateException("User must be active to deactivate");
        }
        this.status = Status.INACTIVE;
    }
}

// Value Object
public record Email(String value) {
    public Email {
        if (!isValid(value)) {
            throw new InvalidEmailException(value);
        }
    }
}
```

## 2. Application Layer (Application Business Rules)

### Purpose
- Orchestrates the flow of data and business rules
- Implements use cases
- Defines interfaces for external operations

### Components
```
application/
├── port/
│   ├── input/      # Use case interfaces
│   └── output/     # Repository/Service interfaces
├── service/        # Use case implementations
└── dto/           # Data Transfer Objects
```

### Key Characteristics
- Depends only on the domain layer
- Defines interfaces for external dependencies
- Contains no business rules
- Coordinates domain objects

### Example
```java
// Use Case Interface
public interface CreateUserUseCase {
    UserDto createUser(CreateUserCommand command);
}

// Use Case Implementation
@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    @Override
    public UserDto createUser(CreateUserCommand command) {
        User user = User.create(command.email(), command.name());
        User savedUser = userRepository.save(user);
        eventPublisher.publish(new UserCreatedEvent(savedUser));
        return UserDto.from(savedUser);
    }
}
```

## 3. Infrastructure Layer (Frameworks & Drivers)

### Purpose
- Implements interfaces defined in application layer
- Contains all external dependencies
- Handles technical concerns

### Components
```
infrastructure/
├── persistence/    # Database implementations
├── config/        # Framework configurations
└── security/      # Security implementations
```

### Key Characteristics
- Contains framework-specific code
- Implements data persistence
- Handles external service communication
- Configures technical aspects

### Example
```java
@Repository
public class JpaUserRepository implements UserRepository {
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);
        UserJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
```

## 4. Presentation Layer (Interface Adapters)

### Purpose
- Handles HTTP requests/responses
- Converts data between formats
- Provides API endpoints
- Manages user interface concerns

### Components
```
presentation/
└── api/
    └── rest/
        ├── controller/  # REST controllers
        ├── request/     # Request DTOs
        └── response/    # Response DTOs
```

### Key Characteristics
- Contains controllers and presenters
- Handles HTTP-specific logic
- Manages request/response formats
- Implements API documentation

### Example
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
            
        CreateUserCommand command = new CreateUserCommand(
            request.getEmail(),
            request.getName()
        );
        
        UserDto user = createUserUseCase.createUser(command);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(UserResponse.from(user)));
    }
}
```

## Dependencies Flow

The dependencies flow inward, following the Dependency Inversion Principle:

```
Controller → UseCase ← RepositoryImpl
     ↓         ↓            ↓
 Request → Command →      Entity
     ↓         ↓            ↑
Response ← DTO    ← Repository
```

## Benefits

1. **Independence of Frameworks**
    - Business logic isn't tied to external libraries
    - Easy to change frameworks/tools

2. **Testability**
    - Business rules can be tested without UI, database, etc.
    - Easy to mock dependencies

3. **Independence of UI**
    - UI can change without changing business rules
    - Multiple UIs can use same business logic

4. **Independence of Database**
    - Business rules don't know about database
    - Can switch databases easily

5. **Maintainability**
    - Clear separation of concerns
    - Easy to locate and modify code
    - Protected from external changes