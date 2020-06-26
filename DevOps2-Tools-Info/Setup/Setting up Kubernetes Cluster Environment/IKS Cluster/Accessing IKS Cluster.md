#Accessing the DevOps-Tools Kubernetes Cluster

It is assumed you have setup your development environment described in the _Setting up Development Environment_ folder and have accessed a command prompt (`$`)

It is also assumed you have
1. Created a Resource Group in IBM Cloud called `DevOps-Tools`. Just in case: The `ibmcloud` command to change to the specific resource group is `ibmcloud target -g DevOps-Tools`
1. Your Kubernetes Cluster is named `devops-tools-cluster`

To login to your DevOps-Tools IKS instance follow the instructions found in the in the _"Access"_ tab in the `devops-tools-cluster` IKS cluster console.

The full set of commands to login to the DevOps-Tools IKS cluster are

	ibmcloud login -a https://cloud.ibm.com -r us-south -g DevOps-Tools
	ibmcloud ks cluster-config --cluster devops-tools-cluster

One of the last lines will say ``export KUBECONFIG=/Users/$USER/.bluemix/plugins/container-service/clusters/devops-cluster/kube-config-dal10-devops-cluster.yml``
	 	
Copy and paste this line into the terminal
	
	export KUBECONFIG=/Users/$USER/.bluemix/plugins/container-service/clusters/devops-cluster/kube-config-dal10-devops-cluster.yml
	
To test you have connection type

	kubectl cluster-info