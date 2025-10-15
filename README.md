# kanionland-user-system

A BFF service in charge of user-based operations for Kanionland project, mainly providing user
creation, authentication and session handling.

## Set-up

### Local

0. Create a JWT secret key using the following command:

```bash
openssl rand -base64 32
```

1. Create a `.env` file in the root directory of the project with the following content:

```env
JWT_SECRET=your-generated-secret-here
JWT_EXPIRATION=86400000
```

2. Compile and verify the application:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local 
```
    