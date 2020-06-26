

Log onto IBM Cloud


##1. Configure Your Environment
This section describes the recommended way to set up your IBM CLoud account prior to creating devices, services etc. Some of these steps are documented in [IBM Cloud Best Practice web page](https://cloud.ibm.com/account/best-practices)

###A. Get an IBM ID for your account using your Corporate email address
Before you begin anything make sure you have an IBMid so you can login correctly to your IBM Cloud Account. Register for an [IBMid here](http://www.ibm.com/account/reg/us-en/signup?formid=urx-19776) using your **corporate** email.

Once you have created you IBMid and confirmed all necessary registration details login to IBM Cloud at (http://cloud.ibm.com)

It is possible to have your IBMid associated with 1 or more IBM Cloud accounts, so make sure you look in the top right where your __"account# - name"__ appears and if necessary select the right account. 

###B. Set up your Resource Groups
Simply put it is easier to organize your project for Dev, Test and Production environment using Resource Groups. Not only does this allow you to better manage access to the resources (services, VMs, Services ...) - you can even create multiple access control lists for each resource group, but it helps with itemized billing and other management issues you will encounter.

We recommend using a project name and the environment name hyphenated together, e.g.
1. Development Environment: **"<Project Name>-Dev"**, e.g HelloWorld-Dev
2. Test Environment: **<Project Name>-Test**, e.g. HelloWorld-Test
3. Production Environment: **<Project Name>-Prod**, e.g. HelloWorld.Prod

> Actions:

> Top Line --> Manage --> Account --> Account Resources --> Resource groups. Create as many resource groups as you need. Then return to the main dashboard (Click "IBM Cloud" in the top left)

###C. Set up you Access Control Lists
At this point you need to set up who else can access each resource group. This is donenvia Access Groups.

> Action:

> Manage --> Access (IAM) --> Users

##2. Create your Environment



###C. Creating a Virtual Service Instance (VSI)
First select the server you want to create
 
> Actions:

> Menu (Hamburger in top left) --> Classic Infrastructure --> Virtual Server --> Public Virtual Server --> Continue

Next 
#####i) Decide what public instance you need, e.g.

* Quantity: 1
* Billing: Monthly
* Hostname: HelloWorld
* Domain: mydomain.com

#####ii) Location
* NA South

#####iii) Select a profile
Notice that _popular profiles__ is selected by default. You can also select __all Profilies__

#####iv) SSH Keys
We recommend creating a certificate to log into the virtual machine rather that using a root/password combination. It's just more secure!

> Actions:

> To create a SSH                         key on a 


ssh-keygen

ssh-copy-id -i    

changing root password

visudu
man visudo
sudo group
                                     

(https://cloud.ibm.com/docs/vpc-on-classic-vsi?topic=vpc-on-classic-vsi-ssh-keys)
#####v) Image
* Image: Centos

#####vi) Add-ons
* Database Software: My SQL

