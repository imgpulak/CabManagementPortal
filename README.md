# In-memory CAB Management Service
An in-memory Cab Management service in Java
 
# How to run it locally
* You will need **Java 8** and **Maven** installed.
* Execute `mvn clean package && target/cab-management-portal-1.0.0.jar`
* You can access the application in [http://localhost:8080](http://localhost:8080) 

## A test suite
curl -isbX POST \
    http://localhost:8080/registerCity \
    -H 'content-type: application/json' \
    -d '{
        "cityId": "1",
        "cityName": "Bangalore"
    }'

curl -isbX POST \
    http://localhost:8080/registerCity \
    -H 'content-type: application/json' \
    -d '{
        "cityId": "2",
        "cityName": "Pune"
    }'


curl -isbX POST \
    http://localhost:8080/registerCab \
    -H 'content-type: application/json' \
    -d '{
        "cabId": "1",
        "cabState": "IDLE",
        "cityId": "1"
    }'


curl -isbX POST \
    http://localhost:8080/initiateTrip \
    -H 'content-type: application/json' \
    -d '{
        "customerId": "101",
        "sourceCityId": "1",
        "destinationCityId": "2"
    }'

curl -isbX POST \
    http://localhost:8080/initiateTrip \
    -H 'content-type: application/json' \
    -d '{
        "customerId": "101",
        "sourceCityId": "1",
        "destinationCityId": "2"
    }'

curl -isbX http://localhost:8080/endTrip/0f4d439e-811b-4617-8ac7-69c453355b52

curl -isbX POST \
    http://localhost:8080/initiateTrip \
    -H 'content-type: application/json' \
    -d '{
        "customerId": "101",
        "sourceCityId": "1",
        "destinationCityId": "2"
    }'


curl -isbX POST \
    http://localhost:8080/initiateTrip \
    -H 'content-type: application/json' \
    -d '{
        "customerId": "101",
        "sourceCityId": "2",
        "destinationCityId": "1"
    }'

curl -isbX POST \
    http://localhost:8080/initiateTrip \
    -H 'content-type: application/json' \
    -d '{
        "customerId": "101",
        "sourceCityId": "2",
        "destinationCityId": "1"
    }'
