# Documentation - 20211104 박규원

## Project Structure
This folder is structured as follows:

```
├── Dockerfile
├── milestone1
├── README.md
│   ├── data
│   │   ├── README
│   │   ├── movies.csv
│   │   ├── movies.dat
│   │   ├── ratings.csv
│   │   ├── ratings.dat
│   │   ├── users.csv
│   │   └── users.dat
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── database
│           │           ├── RestServiceApplication.java
│           │           ├── jpa
│           │           │   ├── Employee.java
│           │           │   ├── EmployeeController.java
│           │           │   ├── EmployeeNotFoundAdvice.java
│           │           │   ├── EmployeeNotFoundException.java
│           │           │   ├── EmployeeRepository.java
│           │           │   └── LoadDatabase.java
│           │           └── mongodb
│           │               ├── DataLoaderService.java
│           │               ├── DatabaseController.java
│           │               ├── Movie.java
│           │               ├── MovieRepository.java
│           │               ├── Rating.java
│           │               ├── RatingRepository.java
│           │               ├── User.java
│           │               └── UserRepository.java
│           └── resources
│               └── application.properties
└── run.sh
```

## How to Run

Build and run the application with the following commands:

```bash
docker build -t flickfriends/milestone1 .
docker run -it -p 8080:8080 flickfriends/milestone1
root@35c0f748dd60:~/project# ./make.sh OR sh make.sh
```

## HTTP Requests

In another terminal, you can test the application using `curl` commands. Below are the commands for testing the Part 2 and Part 3 implementations.

### Part 2: REST APIs for Employee Management

- To get a list of employees:

  ```bash
  curl -X GET http://localhost:8080/employees
  ```

  Output:
  ```
  {"id":1,"name":"Bilbo Baggins","role":"burglar"},{"id":2,"name":"Frodo Baggins","role":"thief"}
  ```

- To get an employee by ID:

  ```bash
  curl -X GET http://localhost:8080/employees/99
  ```

  Output:
  ```
  Could not find employee 99
  ```

- To add a new employee:

  ```bash
  curl -X POST http://localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
  ```

  Output:
  ```
  {"id":3,"name":"Samwise Gamgee","role":"gardener"}
  ```

- To update an employee's information:

  ```bash
  curl -X PUT http://localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
  ```

  Output:
  ```
  {"id":3,"name":"Samwise Gamgee","role":"ring bearer"}
  ```

### Part 3: REST APIs for Movie Ratings

- To get movies with a rating greater than or equal to 4:

  ```bash
  curl -X GET http://localhost:8080/ratings/4
  ```

  Output: (example list of movies with average ratings >= 4)

- To get movies with a rating greater than or equal to 0:

  ```bash
  curl -X GET http://localhost:8080/ratings/0
  ```

  Output: {"error":"The ratings are only available on a scale of 1 to 5"}

- To add a new movie:

  ```bash
  curl -X POST http://localhost:8080/movies -H 'Content-Type: application/json' -d '{"movieId": "4000", "title": "New Movie", "genres": "Action"}'
  ```

  Output:
  ```
  {"movieId":"4000","title":"New Movie","genres":"Action"}
  ```

- To update movie information:

  ```bash
  curl -X PUT http://localhost:8080/movies/4000 -H 'Content-Type: application/json' -d '{"title": "Update Movie", "genres": "Love"}'
  ```

  Output:
  ```
  {"movieId":"4000","title":"Update Movie","genres":"Love"}
  ```

In the Docker container, you can verify the different outcomes of GET/POST/PUT operations as reflected in the outputs above.

### Database Statistics After Operations

- Before POST:
  ```
  Total number of movies in the database before POST: 3883
  ```
  
- After POST:
  ```
  Total number of movies in the database after POST: 3884
  ```

-