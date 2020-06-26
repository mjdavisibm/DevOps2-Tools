ChartMuseum-UI Kubernetes Install Instructions using Yaml files
This can only be used in ChartMuseum is already installed

We use the `chartmuseum` namespace for installing the ChartMuseum-UI. There is also no storage needed so no PVC is created

##1. Installing the ChartMuseum-UI container from GitHub
Using the Kubernetes Dashboard click the `+Create` button in the top right. Paste the `2_Install.yaml` file into the `CREATE FROM TEXT INPUT` tab and press upload.

> Note that there is a line in the YAML that allows connectivity from the ChartMuseum-UI deployment to the ChartMuseum deployment in the IKS cluster. It does this by using the `chartmuseum` name on port 8080. Pods in Kubernetes can automatically access all other pods by using the apps name and the port it is running on.

##2. Allowing access to the ChartMuseum-UI instance so you can Login.
Kubernetes requires  a `Service` object to be created in order for users to access the applications. 
Using the Kubernetes Dashboard click the `+Create` button in the top right. Paste the `3_Allow_Access.yaml` file into the `CREATE FROM TEXT INPUT` tab and press upload.