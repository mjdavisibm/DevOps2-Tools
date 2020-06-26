> Note: This assumes you have first installed ChartMuseum into Docker Kitematic.

First load the dockerhub image into Kitematic (it will automatically run). Press `+ New` in the Kitematic interface, search for the below and and press its `create` button

Dockerhub Container name: [idobry/chartmuseumui](https://hub.docker.com/r/idobry/chartmuseumui)

Then add the following through the containers `Settings` tab. This is not documented very well.

1. Add environment variables in the Settings-->General tab

	Key: CHART_MUSESUM_URL	Value: http://chartmuseum:8080
		---> Yes that is really how Museum was spelled in the release I used
		
2. Configure the `links` section in the Settings-->Network tab
	
	Name: 	<select chartmuseum>	[You must have chartmuseum already installed]
	Alias :	chartmuseum		[ Remember to press the `+` button`
3. To Access ChartMuseumui in a browser first look up the port docker has used under `Settings-->Hostname/Ports` tab. Using the published IP:POrt value for Docker port 8080, type

	http://localhost: <Published IP:Port>
		---> it should display the welcome to ChartMuseumUI welcome page