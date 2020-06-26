# Application

This tutorial simply shows you how to install an application onto the MCM


Applicatiopn we will deploy:



# Cluster Master Hosts
See [here](https://www.ibm.com/support/knowledgecenter/SSBS6K_3.2.0/manage_cluster/cluster_endpoints.html#master)


## To get a cluster config.yaml file

### IKS
To get the Kube Config file set the environment variable ``KUBECONFIG`` to a **new** file name and then follow the instructions on the IBM Cloud Console for the cluster ``Access`` tabe to log into the cluster. The new file will be created with the correct info in it

>> export KUBCONFIG==$(pwd)/<Kube Config File Name

Login to cluster


The ``<Hub Cluster Master Host>:<Cluster Master API Port>`` will be the value after  

## OCP


