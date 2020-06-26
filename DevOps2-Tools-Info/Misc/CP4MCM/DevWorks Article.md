# Deploy applications to managed Kubernetes clusters using channels and subscriptions

Use the application lifecycle capabilities of IBM Cloud Pak for Multicloud Management to manage deployed resources

- [link](https://developer.ibm.com/tutorials/deploy-applications-to-managed-kubernetes-clusters-using-channels-and-subscriptions/)
- Author: Joshua Packer
- Published: March 4, 2020

IBM Cloud Pak for Multicloud Management provides Kubernetes container management capabilities. With IBM Cloud Pak for Multicloud Management, you have control of your Kubernetes clusters and can ensure that your clusters are secure, operating efficiently, and delivering the service levels that your applications expect.

The channel and subscription model for application management in IBM Cloud Pak for Multicloud Management enables you to deliver applications to your managed clusters similar to how you receive OS package updates. It also provides greater flexibility when delivering your applications and updates, helping you maintain the continuous delivery of Helm charts and Kubernetes resources.

Channels are a kind of Kubernetes object that represents the usual places where you would expect to find Helm charts and Kubernetes resources, including templates within Kubernetes namespaces, YAML files within object stores, and Helm charts within Helm repositories. You can populate these channels manually or from your continuous integration systems. With IBM Cloud Pak for Multicloud Management version 3.2.1 or later, you can subscribe your managed clusters to one or more channels to create a diverse delivery ecosystem.

To help manage the ecosystem, you can propagate subscriptions to managed clusters. These subscriptions are another kind of Kubernetes object that you can use to specify which versions of specific charts and resources to deliver to clusters from your channel sources.

To demonstrate how to get started with channels and subscriptions, a sample package is available that you can download to quickly and easily create and begin using channels and subscriptions. This package is hosted on the [Open Cluster Management GitHub project](https://github.com/open-cluster-management/multicloud-subscriptions-developer-editions) and is available for download and use with any licensed edition of IBM Cloud Pak for Multicloud Management. With this package in your development environment, you can easily set up a channel that points to the [IBM Cloud Charts Helm Repository](https://github.com/ibm/charts), which is a repository of IBM and third-party developer edition Helm charts.

For example, this sample project subscribes managed clusters to the Helm chart for IBM MQ Advanced for Developers. This subscription deploys the latest version of the chart and continues to update the deployed chart whenever a new version is published to the repository that is represented by the channel. When you are creating your own subscriptions, you can use the same subscription to target multiple managed clusters so that you can update all clusters that need to include the same chart.
# Prerequisites

To use this sample package to create your own channels and subscriptions, ensure that your environment has the following prerequisites:

- [IBM Cloud Pak for Multicloud Management](https://www.ibm.com/support/knowledgecenter/SSFC4F_1.2.0/install/hardware_reqs.html#cloud_pak) hub cluster
- [IBM Cloud Pak for Multicloud Management](https://www.ibm.com/support/knowledgecenter/SSFC4F_1.2.0/mcm/manage_cluster/create_gui.html) managed cluster
- [IBM MQ Advanced for Developer edition Helm chart](https://github.com/IBM/charts/tree/master/stable/ibm-mqadvanced-server-dev)
- Kubernetes [secret](https://github.com/open-cluster-management/multicloud-subscriptions-developer-editions/blob/master/subscriptions/3-mqadvanced-subscription.yaml) for the IBM MQ administrator

In addition, you need to clone the sample files from the [Open Cluster Management GitHub](https://github.com/open-cluster-management/multicloud-subscriptions-developer-editions) project.

## Step 1. Create the Helm chart channels

You use YAML files for creating the channels and subscriptions for delivering and configuring resources found in the Open Cluster Management GitHub project. One of these YAML files creates a namespace type channel and a Helm repository type channel. The namespace type channel is used to hold secrets and configMaps. The HelmRepo channel is used to identify the IBM Developer Edition Helm Chart repository on GitHub.com. IBM MQ Advanced is one of the charts that IBM makes available in this repository for developers. Use the following commands to create the channels:

```
kubectl apply -f ./channels/1-secret-vault-channel.yaml.yaml
kubectl apply -f ./channels/2-ibmcom-helm-channel.yaml
```

## Step 2. Create the Helm repository subscription

With the Helm chart channels set up, you can create a subscription to the IBM Cloud Charts Helm Repository to install the latest version of the IBM MQ Advanced for Developers chart. Once again, you use a YAML file to create this subscription resource for the Helm chart, create an application resource, and create a subscription resource to deliver the IBM MQ Administrator secret. The application resource is used to tie one or more subscriptions together for dependencies. This relationship allows the IBM Cloud Pak for Multicloud Management console to display the different resources that are associated with the deployed application.

```
kubectl apply -f ./subscriptions/3-mqadvanced-subscription.yaml
```

This command propagates the vault/secret namespace subscription to all your managed clusters with the label environment: Dev. A second subscription is propagated to all your managed clusters. This second subscription subscribes the clusters to the IBM Developer Edition chart repository. The subscription finds a chart by name and then chooses a version of the chart based on the parameters in the subscription. Once a chart version is located, the subscription on the managed clusters deploys the associated Helm release by creating a HelmRelease resource that orchestrates the deployment.
## Step 3. Verify the subscription

To make sure that all resources are working, complete the following checks:

> Check the state of your hub cluster subscriptions with the following command:

```
kubectl -n developer-editions describe subscriptions | more
```

This command shows whether the subscriptions are successfully propagated to the appropriate managed clusters.

In the IBM Cloud Pak for Multicloud Management console, use the Applications dashboard to view the mq-advanced-server-dev application. The application should have two associated resources, the Kubernetes secret subscription and the Helm chart subscription.

On a managed cluster, you can look at the status of the chart by reviewing the subscription status with the following command:
```
kubectl -n developer-editions describe subscriptions
```
If there is a problem for deploying the chart or creating the secret, the issue is displayed in the command output. Alternately, you can see the results of the IBM MQ Advanced for Developers chart by running the following command:
```
kubectl -n developer-editions get all
```
Within the command output, you see a statefulSet that includes the health of the IBM MQ pods. The following command can also show you the details for the IBM MQ Advanced for Developers pod:
```
kubectl -n developer-editions get pods
```
## Conclusion

This example allowed us to deploy the IBM MQ Advanced Server Developer Edition chart to a managed cluster. These Kubernetes resource artifacts can be quickly duplicated to support other IBM Developer Edition charts as well as your own charts. For IBM Developer Edition charts, you can see the subscriptions in the GitHub repository. By changing the Channel URL, Subscription chart target name and version, and the Subscriptions override for custom values, you can subscribe a cluster to any Helm chart that you choose. This capability brings a new level of continuous delivery to your charts. As updates are published to the Helm Repository, these latest updates can be automatically applied or controlled through versions choices across one or more of your managed clusters.