#IBM Kubernetes Cluster Setup

This section describes how to set up the DevOps-Tools IBM Kubernetes Service (IKS) cluster. Once the cluster is set up you can decide which of the DevOps tools you want to install into the cluster.

All of the following is performed within the IBM Cloud Console, which is accessed via

	https://cloud.ibm.com
	
It is assumed you already have an IBM ID an know how to login into IBM Cloud.

> Note: DevOps-Tools uses a _standard_ kubernetes subscription

Settings
* Resource Group name: `DevOps-Tools`
* IKS Cluster name: `devops-cluster`

##2. Create Kubernetes cluster
1. Once on the main IBM Cloud console (Pressing `IBM Cloud` in the top left), select `Create Resource`
1. Search for _Kubernetes Service_ and select it
1. Press `create` and follow the steps to create a cluster with the following values. If none are given use the default values provided.

    - Select a plan: **Standar**
    - Cluster Name:**devops-tools-cluster**
    - Resource Group: **DevOps-Tools**
    - Location - Availability: **Single Zone**. You can create a multizone cluster if you want a more highly available cluster. Single zone is the cheapest option, but has no redundancy if the cluster fails.
    - Default worker pool - Kubernetes version: **1.13.** or whichever is the latest. This is was the "Stable, Default` version at the time of writing]

1. Size and number of a worker nodes. We us the cheapest option

    - Flavor: **2 Cores 4Gig RAM**
    - Worker nodes: **3**. You could use 1 if only a few DevOps tools are being added. For this solution we are installing quite a few, hence 3
	
1. Press ``Create cluster``. It will take a couple of minutes to provision the cluster
	