#Docker Plugin for Jenkins Overview
Docker Plugin  is allows a docker host to dynamically provision a slave, run a single build, then tear-down that slave, without the build process (or Jenkins job definition) requiring any awareness of docker.

* Main Web Page:	[Jenkins.io](https://plugins.jenkins.io/docker-plugin)
* DockerHub:		NONE
* Github:		[jenkinsci/docker-plugin](https://github.com/jenkinsci/docker-plugin)

## Devops-Tools Settings
* Access Port: 30001
* Namespace: jenkins
* Storage requirements: 5 Gig for /var/jenkins_home

### Additional Services required (installed in same namespace)
1. Docker
1. Docker plugin

##Useful Articles
* 



# Installation of Jenkins Container onto a Kubernetes cluster in IBM Cloud
_Written on May 15th 2019 using IBM Kubernestes Service (IKS) with Kubernetes version 1.13.6_1521_

This Readme describes how to install the official Jenkins container  on DockerHub (http:hub.docker.com) or (http://dockerhub.com) into an instance of a *Standard* IBM Kubernetes Service (IKS) Cluster. 

> Note the free instance is not recommended as this only lasts to 30 days.
In addition we use IKS's persistent storage capabilities to store the Jenkins' artifacts.
This ensures that the Jenkins artifacts are not destroyed between pod recreation or crashes.
Free does not allow for this.

It is assumed the reader knows how to create an IKS *Standard* instance.

Once the Kubernetes cluster has been created follow these steps to install the official **Jenkins** container stored on DockerHub.

We will be _deploying_ the DockerHub.com container using a Kubernetes Deployment artifact that will automatically create a Pod and ReplicaSet

* DockerHub Location: Official site [jenkins/jenkins](https://hub.docker.com/r/jenkins/jenkins/)
* GitHub Location: [jenkinsci/jenkins](https://github.com/jenkinsci/jenkins)


##1. Create a Kubernetes namespace for all Jenkins artifacts.

Using IBM Cloud's built in `Web Terminal (beta)` create a `jenkins` namespace by typing
	
	$ kubectl create ns jenkins
##2. Create persistent storage for Jenkins artifacts. 

> 
This will ensure that if the Pod goes down that your Jenkins artifacts will not also be blown away.

Using the Kubernetes Dashboard click the `+Create` button in the top right. We will be pasting YAML text into the `CREATE FROM TEXT INPUT` tab.

	1. Paste the text from the 1_PVC_Setup.yaml file found in in this directory into the text box
	2. Press the upload button
	
##3. Installing the Jenkins container from GitHub

Following the same cutting and pasting model decribed above

	1. Paste the text from the 2_Install_Jenkin.yaml file found in in this directory into the text box
	2. Press the upload button
	
######Couple of points to note
* The official Jenkins DockerHub container is called [jenkins/jenkins](https://hub.docker.com/r/jenkins/jenkins/)
* `/var/jenkins_home` is where all the Jenkins artifacts are stored within the container
* Both the Unix UID and GID for the Jenkins user is set to 1000
* The `InitContainer:` section is executed first and installs [BusyBox](https://hub.docker.com/_/busybox). BusyBox combines tiny versions of many common UNIX utilities into a single small container (1-5 MB). It's fast to download and install. The `command:` section that changes the ownership of `/var/jenkins_home` to the Jenkins user and group. Thus when the main `container:` section executes and tries to write configuration information into the home directory it is actually authorized to do that.

##4. Allowing access to the Jenkins instance so you can Login.
Kubernetes requires  a `Service` object to be created in order for users to access the Jenkins application. 

Again using the `+Create` button in the top right of the Kubernetes Dashboard.

	1. Paste the text from the 3_Allow_Jenkins_Access.yaml file found in in this directory into the text box
	2. Press the upload button

##5. Login to Jenkins for the first time
To login to Jenkins for the first time you need two things

1. The correct url and port to the Jenkins service
2. The admin password that is stored in `/var/jenkins_home`. 

###Finding the correct http url.
This is a simple task of going back to the main IKS dashboard and selecting he `overview` tab. IKS automatically allocates a DNS name to the cluster. You will find the DNS name in `ingress subdomain'. The port number is what we set up in the services object in previous step, i.e. 30000. To access Jenkins you will type in a web browser something like this

	http://devops-cluster.us-south.containers.appdomain.cloud:30000/

This will give you a login screen

###Finding the admin password
This is stored in the Jenkins home directory of the pod. Using the `Web Terminal (beta)` in the IKS dashboard type the following


	$ kubectl get pods --namespace=jenkins
	-->> The output will look something like this 
	NAME                                 READY   STATUS    RESTARTS   AGE
	jenkins-deployment-596bd4555-pnf8m   1/1     Running   0          24h
	
	$ kubectl exec --namespace=jenkins -it jenkins-deployment-596bd4555-pnf8m -- /bin/bash
	--> This will give you a shell prompt within the POD. The terminal should say something like
	jenkins@jenkins-deployment-596bd4555-pnf8m:/$
	
	$ cat /var/jenkins_home/secrets/initialAdminPassword
	--> The output is the password. The terminal will look something like
	11eefb9b3741413382b65989d1217579
	
Now all you need to do is copy that password and use it in the dialog box that pops up when you open the the URL above
* user name: `admin`
* password: `11eefb9b3741413382b65989d1217579` from above