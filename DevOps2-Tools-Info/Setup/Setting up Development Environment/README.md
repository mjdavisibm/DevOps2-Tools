# Setting up the Development Environment on IBM cloud

This section describes how to setup your development environment to be able to communicate with the Kubernetes cluster of your choice using the Command Line Interface (CLI) commands. 

There are several ways to setup your development environment. In all cases you are setting up the ability to use a terminal/console running a Unix shell (usually `bash`) on a laptop or inside an ubuntu image. Where and how this ubuntu image runs is where the choices comes in. Using the ubuntu image instead of directly on your laptop is a way to not "mess up" your laptop with all the tools and software needed to be installed for DevOpsTools.

There are three ways described to create the ubuntu image

1. **Using Docker on your laptop to host the ubuntu image**. This requires you to install docker on your laptop. Follow the instructions in the subfolder called "*Docker on Laptop.md*"

1. **Using IKS to host your Ubuntu Image**. Installing the ubuntu image into IKS on IBm Cloud [I have not yet written up the one to install ubuntu in OpenShift]. However, using this option requires you to install either the `kubectl` CLI or better still `ibmcloud` CLI. Follow the instructions in the subfolder called "*Ubuntu on IKS.md*"

1. ** Using an IBM Cloud VSI to host your ubuntu image**. This is bar far the simplest solution as all you need do on your laptop is to `ssh` into the ubuntu image.<br>
**This is our recommended approach**.<br
Follow the instructions in the subfolder called "*Ubuntu on IBM Cloud.md*". 

> VSI is stands for 'Virtual Server Instance', and is the way you create a virtual server  on IBM's Classic infrastructure. The cost pennies to create a run and you can easily blow them away if needs be and start again if you make any mistakes.

Each subfolder, mentioned above, explains how to install ubuntu and how to access the ubuntu image to get to a ``bash`` shell prompt

## Installing the necessary DevOps-Tools CLI tools
This section describes the setup steps after a fresh ubuntu image has been created, or you open terminal on a mac OS. [I have not had time to look at Microsoft Windows to date]

A list of the tools you will be installing is ...

+ Kubernetes tools
    - [kubectl](https://kubernetes.io/docs/reference/kubectl/overview/). Kubectl is a command line tool for controlling Kubernetes clusters
    - [ibmcloud](https://cloud.ibm.com/docs/cli?topic=cloud-cli-ibmcloud_cli). ibmcloud provides the command line interface for managing resources in IBM Cloud.
    - [helm](https://helm.sh/docs/). Helm helps you manage Kubernetes applications — Helm Charts help you define, install, and upgrade even the most complex Kubernetes application.
    - [chartmuseum](https://chartmuseum.com/docs/#configuration). ChartMuseum is an open-source Helm Chart Repository written in Go (Golang), with support for cloud storage backends, including Google Cloud Storage, Amazon S3, Microsoft Azure Blob Storage, Alibaba Cloud OSS Storage and Openstack Object Storage. 
    - [oc]()

+ Development Tools
    - [git](https://git-scm.com/docs). Git is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency.
    - [docker](https://docs.docker.com/engine/reference). Docker is a tool designed to make it easier to create, deploy, and run applications by using containers
    - [curl](https://ec.haxx.se/cmdline-options.html). Curl is a Unix command line tool and library for transferring data with URLs
    - Homebrew (Mac only). Homebrew installs the stuff you need that Apple (or your Linux system) didn’t.

In addition, the following plug-ins will have been installed
+ IBM Cloud Developer Tools plug-in
+ IBM Cloud Functions plug-in
+ IBM Cloud Container Registry plug-in
+ IBM Cloud Kubernetes Service plug-in
+ sdk-gen plug-in

###Setting up your environment
Once at the command prompt

##### 1. Install curl (if not already present)
	sudo apt update
	sudo apt-get -y install curl

##### 2. Install IBM Cloud tools
  More information on the [CLIs installed](https://cloud.ibm.com/docs/cli?topic=cloud-cli-ibmcloud-cli)

	curl -sL https://ibm.biz/idt-installer | bash

##### 3. Initialize and add Helm repositories

	helm init --client-only``
	helm repo add ibm-charts https://icr.io/helm/ibm-charts
	helm repo add iks-charts https://icr.io/helm/iks-charts
	helm repo add ibm-community https://icr.io/helm/ibm-community
	helm repo update

##### 4. Install Chart Museum.
  See [GitHub](https://github.com/helm/chartmuseum) for more details on installing ChartMuseum

* On MacOS use: ``https://s3.amazonaws.com/chartmuseum/release/latest/bin/darwin/amd64/chartmuseum``
* On `ubuntu` use: ``https://s3.amazonaws.com/chartmuseum/release/latest/bin/linux/amd64/chartmuseum``

	curl -LO <url from above>
	chmod +x ./chartmuseum
	mv ./chartmuseum /usr/local/bin
	chartmuseum --version
    
##### 5. Clone DevOps-Tools Repository

    git clone https://github.com/mjdavisibm/devops-tools