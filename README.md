# bookstore
Bookstore application is built using Spring Boot framework. 
- PostgreSql is used for storing data
- Elasticsearch is used for searching of data.
- Junit and Mockito is used for unit tests

**High Level Design**
https://app.lucidchart.com/documents/view/356c9478-8ae0-40ff-972c-a57068379d37/0_0

**Steps for Running locally**
1. $ mvn clean install
2. $ mvn spring-boot:run

**Steps for running Docker image**
1. docker pull jitenss/demo-bookstore:latest
2. docker run -p 8080:8080 jitenss/demo-bookstore

**PostgreSQl Tables**
1. books - It is used to store the details of the book
2. inventory - Is is used to store the quantity of the books available
3. orders - Is is used to store the order details

**Elasticsearch Index**
1. books_index - Index to store documents of the books
2. media_coverage_index - Index to store the media coverage of the books fetched from remote server

**APIs**
1. POST /books
    - Used to add new books in database and elasticsearch.
2. GET /books/search
    - Used to search books based on ISBN, title and author of the book. It gives results on partial matches too.
3. POST /orders
    - Used to place an order for a book in the database. It uses Pessimistic write lock to handle multiple requests.
4. GET /media_coverage_books
    - Used to fetch media coverage of books and stores the results in elasticsearch.
5. GET /media_coverage_books/search
    - Used to search a book based on ISBN given in the media coverage search.
6. PUT /inventory/{id}
    - Used to update the quantity of the book based on the id given. It uses Pessimistic write lock to avoid corruption of data.



Curls for APIs can be imported from the given Postman Collection: https://www.getpostman.com/collections/77fb63b3e0595197461d

