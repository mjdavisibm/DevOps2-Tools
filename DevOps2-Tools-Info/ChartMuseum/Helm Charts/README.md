[Chart Museum](https://chartmuseum.com/) is a Repository for Helm Charts. Using a Chart Museum server will allow you to load/store you own helm charts.

For DevOps-Tools we will install an instance of Chart Museum into our DevOps-Tools IKS instance, and load it with Helm charts that can be used to install the DevOps-Tools

* Main Web Page:		[ChartMuseum](https://chartmuseum.com/)
* Dockerhub repo:	[chartmuseum/chartmuseum](https://hub.docker.com/r/chartmuseum/chartmuseum)
* Github repo:		[helm/chartmuseum](https://github.com/helm/chartmuseum)

#To Install Chart Museum into your IKS instance perform the following steps

Good Article --> https://developer.ibm.com/recipes/tutorials/deploy-chartmuseum-into-ibm-cloud-kubernetes-service-iks/

This assumes you are using setup environment described in the DevOps-Tools-Image folder
1. Once logged - see [Readme.md](https://github.com/mjdavisibm/DevOps-Tools/tree/master/DevOps-Tools/DevOps-Tools-Image) in the 	DevOps-Tools-Image folder
1. Confirm you have the necessary Helm repository
	
		helm repo list
			---> confirm you have at least the following
			stable       	https://kubernetes-charts.storage.googleapis.com
			---> if not type
		helm repo add stable https://kubernetes-charts.storage.googleapis.com
3. Install Chart Museum with the necessary values into you IKS instance
	
	helm install -f '/devops-tools/DevOps-Tools/Helm-Scripts/Chart Museum/chartmuseum.yaml' stable/chartmuseum