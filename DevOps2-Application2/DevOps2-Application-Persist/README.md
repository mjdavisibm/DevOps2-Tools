# This folder contains the DevopsTools Data application
The data application is a dockerized set of REST services against the Customer Domain object

Using this folder we can create a docker image that we can execute GET/POST/PUT/PATCH/DELETE api calls to our DevOpsTools database

such as 

* `GET /customers` - retrieve all the customer in the database
* `GET /customers/1` - retrieve the customer with Id 1
* `POST /customer Body: Customer` - create a new customer in the database
* `PUT /Customers/1 Body: Customer` - update customer (replace entire customer) with Id 1
* `PATCH /customer/1 Body: Address` update customer with ID 1's Address
* `DELETE /customers/1` - delete customer with Id 1 from the database


**The customer JSON
##The Code
##Data
###Exceptions
###Rest Interface

##Running Unit and Developer Integration Tests


##Building a Docker file

