# MyRetail App
- Fetch the product details using Product ID
- Product details are fetched from External APIand product title is retrieved from it
- Price Information are fetched from Cassandra Database
- Modify the product price information by taking the product id and price details.

# Development
Java 8+, Spring Boot 2.6.0 ,Cassandra 4.0.2, Apache Maven 3.8.4 for build, Swagger Open Api for accessing Rest Services, Tableplus or any IDE of your Choice for cassandra(Optional)

# Installation
`git clone` this repository

Import it as standard maven project in IDE of your choice. As a last step, run "mvn clean package" and find the jar in the target folder

# Running
From the terminal, start Cassandra by
`cassandra -f`
Then in a new terminal, run `cqlsh`

Use the below command to run the jar and bring up the application

`java -jar myretail-0.0.1-SNAPSHOT.jar`

Application will be accessible in localhost:8080

# How to use it
# Fetch Products
This service will take the product id as path parameter, Currency code as optional parameter. If no Currency Code,"USD" will be taken by default. This api will call 
external API to fetch product information, Price information of that product will be fetched from Cassandra which will then be combined to a single response. This response will then be updated in In-Memory cache for faster accessing.
![image](https://user-images.githubusercontent.com/46640172/154864698-77609ca2-72eb-4371-8907-8266f375f220.png)


# Upsert Price Information

This service will take the product id, value(price of the product), currencyCode, createUser as input payload and update the Information in Cassandra Table.
If the Product is alreeady available in Cache, Cache Information will be evicted.

![image](https://user-images.githubusercontent.com/46640172/154864519-c5c378c3-bcfa-4d58-9d8f-1dd92d8f7500.png)

# Utility API (For Demo Purpose)

Utility API is available is see the product Information available in Cache after fetching the information from external API and cassandra db

# Table Structure for Cassandra
Cassandra table to upsert/retrieve pricing information is created with the following structure(Keyspace - myretail)

CREATE TABLE myretail.PRODUCT_PRICE
  (tcin BIGINT, 
  price double, 
  currency_code text, 
  create_user text, 
  create_ts TIMESTAMP,
 PRIMARY KEY (tcin,currency_code));



