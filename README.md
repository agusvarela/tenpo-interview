# Tenpo Interview Challenge

![](https://img.shields.io/badge/build-success-brightgreen.svg)

## Authors
- [Agustin Varela](https://github.com/agusvarela)

## Stack

![](https://img.shields.io/badge/java_11-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/postgrestsql-✓-blue.svg)
![](https://img.shields.io/badge/redis-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/maven-✓-blue.svg)
![](https://img.shields.io/badge/postman-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)
![](https://img.shields.io/badge/docker-✓-blue.svg)

-------------------

## Postgresql DB

This application uses postgresql for data-base. Note that `spring.jpa.hibernate.ddl-auto=create-drop` will drop and create a DB in each deploy of the application.

-------------------

## Redis Cache

This application uses redis cache to store token with expired time (Note that `jwt.expiration.time=300000` is setting the expiration time in 5 minutes. The redis be dropped and created in each deploy of the application.

-------------------

## AuthenticationRequestFilter

The `AuthenticationRequestFilter` filter is applied to the followings endpoints (Note that to use them, you have to log in before to use the endpoints):

    "/history/**"
    "/math/**"
    "/users/logout/**"

-------------------

## How to run this API locally

1. Make sure [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html), [Maven](https://maven.apache.org) and [docker](https://docs.docker.com/) are installed.

2. Fork and clone this repository

```
git clone https://github.com/agusvarela/tenpo-interview.git
```

3. Navigate to the project folder

```
cd tenpo-interview
```

4. Install Maven dependencies

```
mvn install
```

5. In the project folder, run the project on docker containers using docker-compose. It will pull and run images required from dockerHub (Make sure that port 8080 is not in use)

```
docker-compose up
```

----------

## Documentation
Once the application is running, you can find all the information to test the application via swagger:
http://localhost:8080/swagger-ui/#/

Or you could see and import the Postman collection:
https://documenter.getpostman.com/view/10728290/UVJfjbFr