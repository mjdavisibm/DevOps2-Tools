* How to set up a series of devices on the Kubernetes Cluster


1. Create a Project call ``demo-ieam-devices``
1. Create an Ubuntu image to represent an edge device. The supplied Yaml installs 18.04
    
> Note

1. Login to the image
  ``kubectl exec -it ubuntu -- /bin/bash``
1. install Docker

Follow all the steps in this [url](https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-using-the-repository)

> If sudo is missing use ``apt-get update`` first then ``apt-get install sudo`` at the root prompt
> use ``lscpu`` if you do not know what hardware your ubuntu image is installed upon
> ``cat /etc/os-release`` to confirm O/S version


<!-- 1JTgU41IQZUnGAL20RERW4l1RrNff6emXv1BD7zrRQkv -->