First load the dockerhub image into Kitematic (it will automatically run). Press `+ New` in the Kitematic interface, search for the below and and press its `create` button

Dockerhub Container name: [chartmuseum/chartmuseum](https://hub.docker.com/r/chartmuseum/chartmuseum)

Then add the following through the containers `Settings` tab. This is not documented very well.

1. Add environment variables in the Settings-->General tab.

> Remember to press the `+` icon after adding each name/value. And press `save` once all the variables have been added - this will automatically restart the container.

	Key: STORAGE			value: local
	Key: STORAGE_LOCAL_ROOTDIR	value: /var/charts

2. Configure the ports in the Settings-->Hostname/Ports tab
	
	Docker Port: 8080
	Published IP Port: 3000		[Assuming this port is free on your system - use another if not]
3. To Access ChartMuseum in a browser type

	http://localhost:3000
		---> it should display the welcome page