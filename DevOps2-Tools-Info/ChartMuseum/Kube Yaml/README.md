####Useful Articles
* (https://blog.pactosystems.com/steps-to-transfer-your-ci-cd-production-environment-to-kubernetes-engine-in-gcp/)
* (https://developer.ibm.com/recipes/tutorials/deploy-chartmuseum-into-ibm-cloud-kubernetes-service-iks/)


##1. Create a Kubernetes namespace for all ChartMuseum artifacts.
Using IBM Cloud's built in `Web Terminal (beta)` create a `jenkins` namespace by typing
	
	kubectl create ns chartmuseum
##2. Create persistent storage for ChartMuseum artifacts
Using the Kubernetes Dashboard click the `+Create` button in the top right. Paste the `1_PVC_Setup.yaml` file into the `CREATE FROM TEXT INPUT` tab and press upload.
	
##3. Installing the ChartMuseum container from GitHub
Using the Kubernetes Dashboard click the `+Create` button in the top right. Paste the `2_Install.yaml` file into the `CREATE FROM TEXT INPUT` tab and press upload.

##4. Allowing access to the ChartMuseum instance so you can Login.
Kubernetes requires  a `Service` object to be created in order for users to access the applications. 
Using the Kubernetes Dashboard click the `+Create` button in the top right. Paste the `3_Allow_Access.yaml` file into the `CREATE FROM TEXT INPUT` tab and press upload.

##5. Allow helm to Access your ChartMuseum
ChartMuseum is running on your DevOps-Tools IKS instance. To access it in helm you need to add this ChartMuseum to your helm repo. 

First Find the correct http url to use
> This is a simple task of going back to the main IKS dashboard and selecting he `overview` tab. IKS automatically allocates a DNS name to the cluster. You will find the DNS name in `ingress subdomain'. The port number is what we set up in the services object in previous step, i.e. 30006.

	http://devops-cluster.us-south.containers.appdomain.cloud:30006
		-->> You should get the standard ChartMuseum welcome page
![ChartMuseum Welcome Page](ChartMuseum Welcome Page.png "ChartMuseum Welcome Page") 


To add your repo type

	helm repo add devops-tools-charts http://devops-cluster.us-south.containers.appdomain.cloud:30006

Add plugin	
	
	helm plugin install https://github.com/chartmuseum/helm-push
	
https://github.com/chartmuseum/helm-push


GUI
https://github.com/chartmuseum/ui
