


Articles

- [Example exercises to differentiate OpenShift and Kubernetes](https://developer.ibm.com/tutorials/examples-differentiate-openshift-kubernetes/)


- Video: [What’s the difference between Kubernetes and OpenShift?](https://developer.ibm.com/videos/kubernetes-plus-openshift-video/)
- [10 most important differences between OpenShift and Kubernetes](https://cloudowski.com/articles/10-differences-between-openshift-and-kubernetes/)


Openshift == OKD + K8 + Container System (Docker) + Istio + ...

- Deploying: Projects organize a team faster (opinionated approach)
- Managing: Operations team get EFK stack automatically (opinionated approach). Kube you have to install e.g. ELK, Grafina etc
- Adding additinal Nodes much easier in OpenShift (e.g. ansible)
- Security 


---------

## Security - secure by default
1. OpenShift forbids containers to run as root - though you can override this. Implication: a lot of dockerhub containers will not run immediately on OpenShift
2. RBAC is mandatory - Option in Kube.
3. Authentication and authorization model is different

## Deployment
1. OpenShift uses Operators Kube uses Helm (which had a problem with the unrestructed Tiller server pod until version 3 of helm)

## Accessing Pods
1. Raoute are in Openshift - Use HAProxy - very mature. They are akin to Ingress in Kube - Ingress allows more options but is less mature

## Deployments
1. OpenShift uses DeploymentConfig vs Kube's Deployment. DeploymentConfig has more options

## Container Images
- Image Streams

## Integrated Jenkins for CI/CD


## Projects are more than Namespaces

## OPenShift is easier for beginners