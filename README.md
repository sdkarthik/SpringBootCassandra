# myretail
myRetail springboot application which aggregates data from multiple sources and return the reposnse as a JSON to the user.
Currently aggregates pricing data from local cassandra cluster and product information from external api redsky.target.com.
Allows to modify the product pricinf information in local cassandra cluster after verifying that its a valid Redsky supported product.

#### Local testing

Setup the Cassandra container

Note : uses docker-compose.yml to install cassandra service on docker instance and uses /src/main/resources/cassandra_schema.cql script to create the table needed and populate the sample data.

```
docker-compose up
```
Build and run the Springboot jar using gradle

```
./gradlew clean build bootRun
```

hit the endpoints from swagger

[http://localhost:8080/swagger-ui.html#/my-retail-controller](http://localhost:8080/swagger-ui.html#/my-retail-controller)

For GET request at /products/{id}, please use id=13860428, since the data corresponding to this id is already populated in local cassandra table.

For HTTP PUT request at the same path (/products/{id}), use the same id=13860428, which is already a valid id in Redsky service to update to any price and currency of your choice.


#### Instructions
myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps.  The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller.

Your goal is to create a RESTful service that can retrieve product and price details by ID.  The URL structure is up to you to define, but try to follow some sort of logical convention.

Build an application that performs the following actions:

Responds to an HTTP GET request at /products/{id} and delivers product data as JSON, where {id} will be a number.  Example product IDs: `15117729, 16483589, 16696652, 16752456, 15643793`

Example response: 
```
{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray) (Widescreen)",
  "currentPrice": {
    "value": 13.49,
    "currencyCode": "USD"
  }
}
```
Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)

Example:

http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

Reads currentPrice information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.
- BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.



#### How to set up Docker

1. install docker and docker-compose

2. how to install docker and docker-compose ==> use brew install <docker|docker-compose>