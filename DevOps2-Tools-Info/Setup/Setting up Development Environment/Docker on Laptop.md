#Installing DevOps-Tools in Docker

This section describes a way of creating an isolated `ubuntu` image to perform the setup commands described in the parent folders _README.md_. 

It describes a way of using [Docker app](https://www.docker.com/get-started) as a way to run a containerized `ubuntu` image on your laptop. 

This requires the `docker` CLI to be installed on your laptop.

##Docker hosting the Ubuntu Image  on you Laptop
The current README only contains a solution for Mac OS. 

###Setup on Mac OS
1. Install docker GUI from [docker.com](https://hub.docker.com/?overlay=onboarding). Follow the instructions there
2. Start Docker. A whale icon appears at the top of the screen.
3. Open the **Kitematic** application (right hand click on the whale)
4. Select `new+` and search for `ubuntu` 
5. Install the container - it will run automatically

###Accessing the Docker `ubuntu` Image
From a command prompt on you laptop access the Docker hosted `ubuntu` image using

	$ docker ps | grep ubuntu
		-->>aacf09117b12  ubuntu-upstart:latest "/sbin/init" 20 hours ago  Up 20 hours  0.0.0.0:32768->22/tcp  ubuntu-upstart
		          |
		           ----
		               |
		               V
	$ docker exec -it aacf09117b12 /bin/bash
		---> root@aacf09117b12:/#
	