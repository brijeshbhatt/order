# Order Service

This is a spring boot based application.

## Build

Prerequisites:
 - Java must be install in system.
 - Maven must be present in system.

Run `mvn clean install` to build the project.

## Running the application

Process to start application:

   - Run OrderApplication.java from any IDE
    Or
   - java -jar  order-1.0-SNAPSHOT.jar

## Running API

- Create order api call
```
curl --location --request POST 'http://localhost:8082/online/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "total": 2002,
    "shippingAddress": "B29, Sector 14, Gurugram",
    "customerName": "Brijesh Bhatt",
    "itemTOList": [
        {
            "code": "Item-1",
            "name": "Item Name 1",
            "quantity": 10
        },
        {
            "code": "Item-2",
            "name": "Item Name 2",
            "quantity": 11
        },
        {
            "code": "Item-3",
            "name": "Item Name 3",
            "quantity": 12
        }
    ]
}'

```

- Get all existing order api call
```
curl --location --request GET 'http://localhost:8082/online/orders'

```

- Get particular order api call
```
curl --location --request GET 'http://localhost:8082/online/orders/1'

```


- Update order item api call
```
curl --location --request PUT 'http://localhost:8082/online/orders' \
--header 'Content-Type: application/json' \
--data-raw '{   "orderId": 1,
    "total": 2002,
    "shippingAddress": "B29, Sector 14, Gurugram",
    "customerName": "Brijesh Bhatt",
    "itemTOList": [
        {
            "code": "Item-1",
            "name": "Item Name 1",
            "quantity": 10
        },
        {
            "code": "Item-2",
            "name": "Item Name 2",
            "quantity": 11
        },
        {
            "code": "Item-3",
            "name": "Item Name 3",
            "quantity": 12
        }
    ]
}'

```


- Delete particular order api call
```
curl --location --request DELETE 'http://localhost:8082/online/orders/1'
```

