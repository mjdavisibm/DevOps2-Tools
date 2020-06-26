#Setting up the Development and DevOps-Tools Cluster Environment

We recommend setting up two environment before you continue your exploration of DevOpsTools. These are

1. **Development Environment**. This will be the environment you will use to access your cluster environment and perform all the necessary installations and configurations on the kubernetes cluster
2. **Cluster Environment** This will contain the Kubernetes cluster environment where all the DevOps-Tools will be installed and run.

There are several options for each of these environments.

- Development Environment recommendation: **VSI option on IBM Cloud**<br>
  This option consumes no space and little compute on your laptop. You simply `ssh` into an image running on IBM Cloud to perform all actions on the kubernetes cluster <p>
- Cluster Environment recommendation: **Red Hat OpenShift Cluster on IBM Cloud**<br>
  IBM created its own Kubernetes Environment, called IKS, prior to purchasing Red Hat in 2019. Though IKS is totally acceptable and is still fully supported the general strategy at IBM has shifted towards Red Hat's OpenShift. For example, all their Cloud Pak offerings are on OpenShift. For this reason DevOps-Tools uses OpenShiuft as its Kubernetes environment on IBM Cloud. It is Referred to as ROKS (Red Hat OpenShift Services)

## IBM Cloud Resource Groups
In IBM Cloud there is a mechanism to keep project artifacts in one place. This allows for better control of securing access, billing, and general management of the artifacts. This concept is called _Resource Groups_.

We recommend creating a `DevOps-Tools` resource group for all your artifacts. 

Follow [these instructions](https://cloud.ibm.com/docs/resources?topic=resources-rgs#create_rgs) on IBM Cloud to create a resource group. Ensure you select this resource group for any artifact you create in IBM Cloud
