# Application Management

This Article describes how MCM helps you to deploy an application. Unlike the dreadfully written kc for MCM this article takes a very different approach to explaining how the MCM helps you manage applications. 

The concept is relatively simple. I have an application that is comprised of various parts - a web front end, a database, some business logic. These components comprise the _application_. These components need to be deployed to a single cluster or across multiple clusters. In addition, as we are talking about containers, I have to define how many of each componeent I want deployed, either for HA sakes or because of load considerations. Simply put I have something like this

## What is an Application?
Applications do something. Duh! 

Applications have a name, a version, and contain 1 or more components (Containers!)

Components have a name, a version, and when running 
1. Have a number of instances that are excuting - A Kube ReplicaSet
2. Are deployed to a cluster, with respect to all the other components, e.g. can be on the same cluster as other components or on a separate cluster
3. Are secured for access, and thus a way to communicate with them is needed - e.g. a userid an password

When you deploy an application in kubernetes you have to acurately describe what all the pieces are that make up an appliacation. All these "pieces" are called _resources_ in Kuberbetes and are coded as YAML files. [comment about my distaste oy YAML files] It is beyond the scope of this article to describe each and every resource in kubernetes but a few are mentioned here as they directly relate to the way MCM manages applications.

An Application in Kubernetes is comprised of Helm charts and Kubernetes resources.


1. Namespaces
2. Object Store Buckets: This is where you would store all the various YAML files (The Kube resources)
3. Helm Repositories: The actual Helm  charts. Yes these are also YAML files.
4. Git Repositories


All the above can be defined by the Devops person or the developer, or even both.

That is the information for one application.

Now let's turn our attention to one of the fundamental reasons for the existence of MCM. And that is that it manages many things. For example 
1) Environments: Development, test, staging, Pre-production, Production(s)
2) Kubernetes Clusters - it is in its name.
3) Applications

That last one is the subject here - though the others are indirectly important too. The MCM manages many applicaitons across many clusters, with many versions, and many environments. Keeping all that straight in someones head is untenable - hence its existence.

So how do we manage all these multple permutations of all these many _things_. Traditionally the general approach has been to have scripts with lots of If-Then statements - If Environemnt is "Development" then If Application installed < Version in respository &and , then ....>

But this is fraught with problems, not least of which is when you add a new "thing" or you need to add a new conditions for deployment you need to edit a script, and that could lead to breakingthings that still work.

So is there a different approach to managing large number of things that have large number of permutations. Well its not perfect but yes there is. It is called intent based  programming, where you define the end state you are looking for and let the system dynamically and in real-time write the workflow to get you there. Or to quote John-luke Picard from Star Trek "Make it so!".

This is the approach that MCM takes to manageng applications - and other areas too, but we will leave that for another time.

So lets go back to what this article is about. Deploying applications and add intent based deployment of those applications. Simply put we define the end state that we are looking for 
"I want Application X Version 7 with all these conponents to be deployed to the clusters responsible for running productions applciations on the east coast"

Ok So how do we do that? Well the first thing is to introduce a way that "interested parties" - clusters in this case and express an interest in the applications.
"Hi I am the production cluster for order management for the east coast. What would you like me to run and how do you want me to organize that for you"

The way to do that is the cluster defines its capabilities and publishes those to the Hub Cluster.

Simultaneosly the application developers have published what their application do, what they need to achieve this, and how they need to be organized.

It is the job of the bub to match the Applications with the clusters and ensure all the componenta at the correct version get deployed to the clusters

## Application Publishing
So how does the application developer publish their information to the hub. That is done via "Channels" and like the way you can buy groceries from many sources, from you local green grocer, from big brand store, a local farmer or farmers market, and even on-line there are many sources that the the channel can have. And unlike the grocery example when it comes to applications you have many "pieces" - those components - that make up the applications. For example,
1) The binary is stored ..
2) The secrets - those security tokens to access the component are stored here,
3)...



There are in fact 4 sources that an application's components and descriptors can be stored, and each "source" defines different things to deploy to the target/destination cluster



1) Namespaces
2) Object Store buckets
3) Helm Repositiories
3) GIT Repository

Channels are a kind of Kubernetes object that represents the usual places where you would expect to find Helm charts and Kubernetes resources, including templates within Kubernetes namespaces, YAML files within object stores, and Helm charts within Helm repositories. You can populate these channels manually or from your continuous integration systems. With IBM Cloud Pak for Multicloud Management version 3.2.1 or later, you can subscribe your managed clusters to one or more channels to create a diverse delivery ecosystem.


[Kubernetes Yaml editor](https://containership.github.io/konstellate-editor/)
### Pieces of the puzzle

Application
Deployables
Secrets
Channels
Subscriptions
Placement Rules






		