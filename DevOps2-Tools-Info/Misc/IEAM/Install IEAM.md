# Installing the IBM Edge Application Manager V4.


# 1. Create Ubuntu VSI
It is recommended you use an ubuntu image to perform the install. This will keep your laptop clean from all the install process. We recommend creating a Virtual Server on IBM Cloud to do this.

> Note the standard 25Gig ubuntu image is not big enough to install the IEAM. Use the 100Gig version. 

> You may need more than 100Gig. To do this add an additional disk to the image and then mount it. 

To add disks to an ubuntu image on IBM Cloud
1. To find the disk that was created for you use ``lsblk`` command and look for the disk with the adddtional size

2. Format the disk using ``mkfs -t ext4 /dev/<device>``

3. Follow [this link](https://www.cyberciti.biz/faq/mount-drive-from-command-line-ubuntu-linux/). 


Public Key

1. Use your laptops public key to be able to login to your image
2. Make sure you give access via SSH

To Login to the ubuntu image use 
> ``ssh root@<Public IP Address>``


# 2 Setting up the Ubuntu image 
One you have looged into the ubuntu image you need to install several CLIs and also download the appropriate IEAM files

## 2.1 Install IBM recommneded CLIs
To install the necesary CLI the first step is to install the IBM< recommended set useing the following curl command
1. ``curl -sL https://ibm.biz/idt-installer | bash``

What is installed is the following...
- docker
- kubectl
- helm
- ibmcloud. Plugins installed

	- cloud functions
	- cloud object storage
	- container registry
	- container service
	- dev

### 2.1.1 Replace the helm installed
The ``helm`` installed in the above process is the **wrong** version. It is important to replace the client version, but not disturb the server version in the cluster. The common services installs version **v2.12.3+icp**. As of this writing the above script installs version **2.16.6** of the client. 

[Version 2.12.30(https://github.com/helm/helm/releases/tag/v2.12.3) can be found on github
To replace the client **only** type the following

1. ``curl -o helm-v2.12.3-linux-amd64.tar.gz https://get.helm.sh/helm-v2.12.3-linux-amd64.tar.gz``
3. ``tar xvf helm-v2.12.3-linux-amd64.tar.gz linux-amd64/helm --strip-components 1``
5. ``mv ./helm /usr/local/bin/helm``

## 2.2. Install *oc* (openshift control)
``kubectl`` is the standard way to communicate with a kubernetes cluster. With OpenShift you usually use ``oc`` which offers pretty much the same syntax + additional capabilities

1. Find the URL for OC from your cluster instance. Go into the OpenShift Cluster Manager and click the "__?__" help icon and select **Command Line Tools**. There you will find the URL cor the correct OC to use. ``Use the x86 versions``
2. Use CURL to get this into the ubuntu image using ``curl -o oc.tar <http://<url from previous step>``
3. ``tar xvf oc.tar``
4. ``chmod +x ./oc``
5. ``mv ./oc /usr/local/bin/oc``

To login to the cluster using ``oc`` got to the the OpenShift Console on IBM Cloud click on the user ID icon (Top Right) and select the **Copy Login Command** and paste the ``oc login ....`` in the console. You are not logged into the cluster

## 2.3. Install **cloudctl**
To install IEAM you will also need to use the ``cloudctl`` command. This can only be installed **AFTER** the Common Services is installed, as you download the command from the running common services you install in the cluster. See the section below for full instructions

## 2.4. Install **jq**
Go [here](https://stedolan.github.io/jq/download/) to install jq - a lightweight and flexible command-line JSON processor

## 2.5. Install **make**
``apt install make``

# 3 Installing IEAM
There are several steps to install IEAM
1. Download the appropiate files into the ubuntu image
2. For version IEAM 4.0 install the "common servcies"
3. Install the actual IEAM product

## 3.1 Downloading the appropriate files
The following files need to be downloaded into the ubuntu image
1. ibm-ecm-4.0.0-x86_64.tar.gz. 
2. common-services-boeblingen-2002-x86_64.tar.gz

## 3.2 Installing the common services
Follow the directions [here](https://www-03preprod.ibm.com/support/knowledgecenter/c-s_test/installer/3.2.4/cloud_pak_foundation.html#valid_route) to install the common services.

> Note this document is augmented by the install instructions in the [document center](https://www.ibm.com/support/knowledgecenter/SSFKVV_4.0/servers/install_edge.html)

### 3.2.1. Installing **cloudctl** 
``cloudctl`` is the command used to talk to the common services. You will need to install it as it is used during the IEAM install.

Look [here](https://www-03preprod.ibm.com/support/knowledgecenter/c-s_test/cloudctl/install_cli.html#curl) for help. **BUT IT IS CONFUSING!!!**

Follow these steps

1. Using ``oc`` login to your cluster - Gget this from the openshift dashboard.
2. Type ``oc get route -n kube-system`` and take note of the _Host/Port_ for the **icp-console**. You will need this to download the correct version of ``cloudctl``
3. Now download the cloudctl command using ``curl -kLo cloudctl https://<Your icp-console Host Name>>:443/api/cli/cloudctl-linux-amd64``
4. ``chmod +x cloudctl``
5. ``mv ./cloudctl /usr/local/bin/cloudctl``


## 3.2 Installing the IEAM Product

Note in step 6 in the installation process the filereferences in the script is **wrong**

	cloudctl catalog load-archive --archive ibm-ecm-prod-catalog-archive-4.0.tgz --registry $REGISTRY_HOST/ibmcom

should read (Look at the extra 0 at the end of the .tgz file name)

	cloudctl catalog load-archive --archive ibm-ecm-prod-catalog-archive-4.0.0.tgz --registry $REGISTRY_HOST/ibmcom

# Appendix

## Already Installed
### Install *kubectl*
1. ``curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl``
2. ``chmod +x ./kubectl``
3. ``mv ./kubectl /usr/local/bin/kubectl``
4. ``kubectl``

### Install *docker*
1. Follow instruction on the [docker website](https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-docker-engine---community-1#install-docker-engine---community)


## Install EAM
Follow this [article](https://www.ibm.com/support/knowledgecenter/SSFKVV_4.0/servers/install_edge.html)

## Accessing the IEAM Files
To get the files you need into the ubuntu image there are several ways. All of which will take some time.

1. If you downloaded the files onto your local desktop first you can use ``scp <file> root@<ip address of VSI>:~``. See [here](https://help.ubuntu.com/community/SSH/TransferFiles) for description. 
2. If it is object store you can access COS (see below)].
3. Accessing them directly from IBM Internal site
> Note this is a trick

## Accessing IBM Internal Downloads
1. Goto the following [internal IBM page](https://w3.ibm.com/software/xl/download) and click *I agree*
2. Search for *IBM Edge Application Manager*
3. Select to download via http (This will require you to download one file at a time.
4. Select the file to download and begin the download. Then goto the browsers download page and stop the download. Then request the url for the download.
5. Go to the ubuntu image ``curl -o <file name> <pastelink here>``

## Accessing IBM COS
1. ``ibmcloud login --apikey 466sHyDypetS9xeXy28s5n3VWRU_gFqIM1hxssahI-Li``
2. Find the current config ``ibmcloud cos config list``
3. Change region ``ibmcloud cos config region``
2. ``ibmcloud cos config crn ``. To find the crn look at the credentials for the bucket you need. *NOTE*: crn is the one in the 'resource_instance_id' when you "view credentials"
    > Example  ``"resource_instance_id": "crn:v1:bluemix:public:cloud-object-storage:global:a/1ea27dce593f7ea32717faf209329505:c7990c87-0cb7-4983-b1e9-7c120e3cb7c9::"``


3. ``ibmcloud cos config ddl``. Set it to ``/root``
4. Check everything is set ``ibmcloud cos config list``
5. List contents of buckets ``ibmcloud cos list-objects --bucket <BUCKET_NAME> ``
    
   - example ``ibmcloud cos list-objects --bucket mjdavis-cos-software-downloads``
5. To download from the bucket ``ibmcloud cos download --bucket mjdavis-cos-software-downloads --key ibm-ecm-4.0.0-x86_64.tar.gz``. Depending on size of file this can take a while 

> Note there is NO feedback

tiller image: image: 'gcr.io/kubernetes-helm/tiller:v2.12.3'

