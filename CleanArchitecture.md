\# Clean Architecture Layers

\## Overview

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

\## 1. Domain Layer (Enterprise Business Rules)

\### Purpose
- Contains the core business logic and rules
- Independent of all external concerns
- Represents the heart of the application
- Defines interfaces for external dependencies

\### Components
```
domain/
├── entity/          # Business entities
├── valueobject/     # Immutable value objects
├── event/           # Domain events
├── port/            # Interfaces for external dependencies
│   ├── input/       # Use case interfaces
│   └── output/      # Repository/Service interfaces
└── exception/       # Domain-specific exceptions
```

\### Key Characteristics
- No dependencies on frameworks or external libraries
- Pure Java/Kotlin code
- Contains business logic validation
- Houses business invariants
- Defines contracts for external dependencies

\### Example
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

// Domain Port (Repository Interface)
public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
}
```

\## 2. Application Layer (Application Business Rules)

\### Purpose
- Orchestrates the flow of data and business rules
- Implements use cases
- Coordinates domain objects

\### Components
```
application/
├── service/        # Use case implementations
└── dto/           # Data Transfer Objects
```

\### Key Characteristics
- Depends only on the domain layer
- Implements interfaces defined in domain
- Contains no business rules
- Coordinates domain objects

\### Example
```java
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

[Rest of the content remains the same, but with references to ports being in the domain layer rather than application layer]

\## Dependencies Flow

The dependencies flow inward, following the Dependency Inversion Principle:

```
Controller → UseCase ← RepositoryImpl
     ↓         ↓            ↓
 Request → Command →      Entity
     ↓         ↓            ↑
Response ← DTO    ← Repository (interface in domain)
```