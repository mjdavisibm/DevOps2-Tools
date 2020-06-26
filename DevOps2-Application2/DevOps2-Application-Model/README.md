# This folder contains the DevopsTools Domain
This project contains the objects and service definitions (and a json implementation) for the DevOpsTools Data and View applications. Both of these projects use the domain to define the objects etc that they use.
> 
A strict definition of a microservice would mean each microservice would not share any code so they can be independently updated and enhanced by separate teams. We do not follow this strict definition in DevOpsTools as our View and Data microservices share this domain model. This is simply to make it easier and faster to code and understand and should not be taken as the correct way to follow a microservices architecture.

#The Domain Objects

There is only one main domain object: `Customer`. It is comprised of a Name, Address and several other person characteristics

#The Json Implmentation

The DevOpsTools application follows best practices of architecture principles. This somewhat enforced by the `spring-boot` architecture used to develop the application

* **Domain Layer**: There is a `domain` package that holds the domain objects. Because we intend to use JSON extensively throughout the application a lot of JSON Java annotations can be found in the Customer domain object

* **Data Layer**: There is a `data` package that holds the spring-boot interfaces and classes to access a Customer repository of Customer objects. You will also find a JsonImpl package that creates a concrete implementation of the interface that can be used in testing. The JSON implementation can load customer from a JSON file and then manipulates the database in-memory.

* **Service Layer**: There is a `service`	 package that contains the services users should use to access and manipulate the repository

#Testing 


#DDL

Name:
	first
	middle
	last
	preferred
Customer:
	Name: Type<Name>
	Gender: Enum(Male, Female)
	DateOfBirth: LocalDateTime
	Address:  Type<Address>[] (1 to many relationship)
