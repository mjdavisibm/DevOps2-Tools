This folder describes how to use Helm to install the DevOps-Tools

Helm allows you to package multiple scripts and setup instructions into a helm chart and execute them all in one command. This differs from the other folder in this repository 'Using Yaml' that uses individual yaml files and instructions to install the DevOps-Tools into kubernetes

IKS has its on catalog of helm scripts that you can use and can be found [here](https://cloud.ibm.com/kubernetes/solutions/helm-charts)

To use Helm you need to install the HELM CLI onto your desktop and install `tiller` into your cluster. In our case we recommend you the `Web Console (beta)` to perform the latter). The information to setup helm is [here](https://cloud.ibm.com/docs/containers?topic=containers-helm)

For Tiller use the section named _If Tiller is not installed with a service account:_



Helm [documentation](https://helm.sh/docs/)

Useful things to know

* To add a helm repo, `https://kubernetes-charts.storage.googleapis.com` to Helm use

	helm repo add https://kubernetes-charts.storage.googleapis.com
* To install a helm chart into IKS instance first login and then install the help chart

	ibmcloud login -a http://cloud.ibm.com
	helm install [-f values.yaml] stable/chartmuseum
		The [-f values.yaml] option allows you to override some of the charts variables.
* To find the correct values to configure the install use

	helm inspect values stable/chartmuseum > chartmuseum.yaml
 