# complaints-ticketing-system
This is an automated ticketing system that raises a ticket on the basis of some priorities when delivering food. <br />

So I have made two services 1. delivery-service using H2-Database, 2. ticketing-service using H2-Database and <br />
for them to interact with each other I have kafka as a queue. <br />

## Delivery-Service
1) Populates Delivery Details in H2-database <br />
2) Sends Priority Deliveries to kafka based on some filters. <br />

## Ticketing-Service
1) Applies JWT sessions for security <br />
2) Fetches Delivery data from topic in kafka <br />
3) Saves the Delivery data in h2-database <br />
4) Gives a list of High Priority tickets based on values in a sorted way to `/tickets` api call <br />

## To run the application in docker
1. in terminal `cd /complaints-ticketing-system` <br />
2. `docker-compose up -d --build` <br />

### Deployed on Port `localhost:8080`

## Api's
1.) For SignUp `localhost:8080/signup` it's http POST method, <br />
Json : `{ 
"username" : "james",
"password" : "admin123"
}` <br />

2.) For SignIn `localhost:8080/signin` it's http POST method, <br />
Json : `{
"username" : "arsal",
"password" : "admin123"
}` <br />

Response is a token :

    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcnNhbCIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0NTUiJ9XSwiaWF0IjoxNjQyMDk3MzE1LCJleHAiOjE2NDIwOTc0MTV9.O7SwdRTvWunUowzd8YB1OwkYwuns5uAhKGv_WcIIZJo

3.) For Tickets `localhost:8080/tickets` its http GET method, <br />
Authorization Bearer : Token

Response is a list of Tickets : `[{TicketJson1}, {TicketJson2}, ...]` <br />
