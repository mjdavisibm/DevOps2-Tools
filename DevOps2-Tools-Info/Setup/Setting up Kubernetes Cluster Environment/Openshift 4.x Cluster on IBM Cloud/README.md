#IBM OPenshift Cluster Setup

This section describes how to set up the a Kubernetes cluster on IBM Cloud using OpenShift known as ROKS - Red Hat OpenShift CLuster Service. 

Once the cluster is set up you can decide which of the DevOps tools you want to install into the cluster.

All of the following is performed within the IBM Cloud Console, which is accessed via

	https://cloud.ibm.com
It is assumed you already have an IBM ID an know how to login into IBM Cloud.

> Note: DevOps-Tools uses a _standard_ subscription

Settings
* Resource Group name: `DevOps-Tools`
* ROKS Cluster name: `devops-cluster`

Steps once logged in
##1. Create a Resource Group
First create a `DevOps-Tools` resource group. We will create the IKS cluster in this resource group. You could use default but our recommendation is to create a separate one. This means all the DevOps-Tools can be billed, managed and secured separately from all other resources and services.

To do this ......

##2. Create OpenShift cluster
1. Once on the main IBM Cloud console (Pressing `IBM Cloud` in the top left), select `Create Resource`
1. Search for _cluster_ and select _Red Hat OpenShift Cluster on IBM Cloud_
1. Follow the steps to create a cluster with the following values. If none are described below use the default values provided.


blah blah blah

all this is from IKS readme

	
	Select a plan: Standard
	Cluster Name: devops-cluster
	Resource Group: DevOps-Tools
	Location - Availability: Single Zone.		[You can create a multizone cluster if you want a more highly available cluster. Single zone is the cheapest option, but has no redundancy if the cluster fails.]
	Default worker pool - Kubernetes version: 1.13.6	[This is was the  "Stable, Default` version at the time of writing]

At this point the reader will need to make a decision on the size and number of a worker nodes. For this solution we are using the cheapest option again

	Flavor: 2 Cores 4Gig RAM
	Worker nodes: 3 		[ You could use 1 if only a few DevOps tools are being added. For this solution we are installing quite a few, hence 3]
	
	Press "Create cluster"
	