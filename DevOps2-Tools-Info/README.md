This repository contains instruction to set up a DevOps-Tools Kubernetes solution onto IKS. It describe two ways to perform the task. (Title is the same as the folder)


[Info on Jenkins on a Kubernetes](https://cloud.google.com/solutions/jenkins-on-container-engine)

1. **YAML-Scripts** - this walks you through step by step to install each DevOps-Tool using yaml scripts,  commands to the `Web Console (beta)`, recommended instructions to setup each tool, and education modules to learn how to use the DevOps tool
1. **Helm-Scripts** - this walks you through installing the DevOps-Tools using Helm Charts - A faster and less error prone way of installing the tools as it combines the many yaml files into a chart to install the DevOps tools.

[added from a difference readme]
This repository describes two ways to install the DevOps tools
1. Using yaml files to setup the DevOps tool in the cluster. Most of this will be done via the Kubernetes Dashboard and command line using Command Line Interface (CLI) tools such as `kubectl`.
2. Using Helm charts - usually just one - to install all the necessary 



## The DevOps tools currently described are as follows
1. Chart Museum - A Helm Chart Repository to allow you to use your own Helm Charts. we also use it to install some of the DevOps-Tools
1. Jenkins for automated building of ...
1. Docker For Jenkins
1. Nexus - For Java Imaghe storage prior to deploying it to the IKS server instance
1. Microclimate - A Cloud Native IDE (Integrated Development Environment)
1.



## Introduction
To install and configure your own DevOps-Tools Kubernetes instance you require the following

1. Create an IKS instance on IBM Cloud - [Docs of IKS](https://cloud.ibm.com/docs/containers?topic=containers-getting-started). We recommend using a new resource group to keep the IKS instance in. Using 'default' is ok but to keep this all to do with DesOps-tools we are gong to create a DevOps resource group to use 
1. Install a various set of tools on your desktop - STOP See Bootstrap Image



One of the biggest problems I faced creating this article was a finding a place that consistently described how to install each tool. I have tried for each tool to use the same set of yaml files with few differences to help people understand how Kubernetes works. For example, when using individual YAML scripts to create the DevOps tool I have tried to use the same pattern, i.e.
1. Login
1. Set up a namespace for the tool. In Jenkions case we create a `jenkins` namespace
1. Set up persistence storage claims that will be used by the installation. These are usually called `1_PVS_Setup.yaml` and use _persistent volume claims_ against IBM's storage classes to set up permanent storage for the tool. For example in Jenkins case we create a `jenkins-data` PVC of 50 Gig
1. Install the tool, usually called `2_Install.yml`. This script install and initializes the container - often ensuring the PVC folder is accessible to the application UID and GID
1. Add a Service so end users can access the tool. For convenience, teh port number startys at 30000 and goes up from there for each tool. Thus Jenkins is running on Port 30000 and can be accessed using

	http://<IKS's Ingress subdomain>:30000/
1. Have to think about security