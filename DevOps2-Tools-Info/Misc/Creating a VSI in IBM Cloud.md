# Recommended Approach to setting up you IBM account to create Virtual Servers

This document describes the best practice to create your first Virtual Server Instance (VSI) in IBM Cloud.

##1. Create and Configure your IBM Cloud Account
This section describes the recommended way to set up your IBM Cloud account prior to creating devices, services etc. Some of these steps are documented in [IBM Cloud Best Practice web page](https://cloud.ibm.com/account/best-practices)

###A. What happens once the PO is signed
Once a PO is signed an email is sent to the PO owner who should forward it to the project team lead or manager. This email will tell the team how to activate the IBM Cloud admin account.

> Our recommended approach is to use a general admin ID for the project and not an individual's ID. The ID is an email address at the corporation, e.g <project name>-admin@mycompany.com The email address must have an active email box as registration information will be sent to it

If for some reason an admin email address can not be created then an individuals can be used BUT if that person leaves/moves on from the project it is a little cumbersome to give admin rights to another individual.

###B. Get an IBM ID for your account using your Corporate email address
With either the admin email or individuals email address follow the instructions in the PO email to create an IBMid. Make sure a corporate email is used.

Anyone else who needs to be an administrator of the account should also register for an IBMid.

> Register for an IBMid [here](http://www.ibm.com/account/reg/us-en/signup?formid=urx-19776) using your **corporate** email.

Log in to IBM Cloud at [http://cloud.ibm.com](http://cloud.ibm.com)

It is possible to have your IBMid associated with 1 or more IBM Cloud accounts, so make sure you look in the top right of the dashboard where your _"account# - name"_ appears and if necessary select the right account. 

> Note: There is no need to give anyone else access to the IBM Cloud Dashboard. They only need access _IF_ they need to add, modify, remove (e.g. VSI) or generally manage the IBM Cloud Account.

###C. Set up an account owner's SSH keys to use for the servers
Our recommendation is to use SSH keys to manage access to resources on IBM cloud, and not use userid/password. This means
1. As the account owner you can use **one** key (certificate) to log in to **all** your virtual servers. However, each server can have its own root password and you do not have to share that password
2. Additional people who need access to the server can create their own public/private keys to log onto the server as any user (including root), and no one _shares_ passwords or certificates. To do this they need to send you their public keys that you add to the appropriate server account (see below)

#####Check if you already have an ssh key
If you are not sure if you have an ssh key and are using a unix based system (Mac, Linux) check your home directory for a folder called `.ssh` (i.e. `ls ~/.ssh`). If there are at least two files in there called **id_rsa** (your private key) and an **id_rsa.pub** (your public key) then you can use those. For more details see [here](https://cloud.ibm.com/docs/vpc-on-classic-vsi?topic=vpc-on-classic-vsi-ssh-keys) or [here](https://cloud.ibm.com/docs/vsi?topic=virtual-servers-ssh-keys)

If you have never created an ssh key perform the following.

	$ ssh-keygen -t rsa
	decide where to output you created files (using the default will mean less work for you later)
	press enter when asked to use a passphrase

We will be uploading the public version of the key to IBM Cloud and then attach that to each VSI you create. 

> Note: You can add additional public keys (i.e. other users who can access the VSI) once the VSIs are created (see below)

#####Adding your key to IBM Cloud

> Actions:

> Menu (Hamburger in top left) --> Classic Infrastructure --> Devices (on left menu) --> Manage --> SSH Keys --> Add (The easiest way to upload your public key is to use "Browse" and select your id_rsa.pub file (found in ~/.ssh). Label it with a meaningful name (and add a description if you want to)

##2. Create your first VSI

###A. Creating a Virtual Service Instance (VSI)
There are several ways to create your first VSI. Below is one way

> Actions:

> Menu (Hamburger in top left) --> Classic Infrastructure --> Devices (on left menu) --> Device List --> Order Devices --> Virtual Server --> (select) Public Virtual Server --> Continue. At this point the Virtual Server Instance order page will be visible


For each major section of the order page follow the steps below (we ignore certain sections)
#####i) Decide what public instance you need, e.g.

* **Quantity**: `1`
* **Billing**: `Monthly`
* **Hostname**: `HelloWorld`
* **Domain**: `mydomain.com`  <-- this is really an ID and can be any domain you want
* **Location**: `NA South`

#####ii) Select a profile
Notice that _popular profiles_ is selected by default. You can also select _all Profiles_. Select the compute size you want.

#####iii) SSH Keys
* Select your ssh key you uploaded above

#####iv) Image
* **Image**: `Centos`

#####v) Add-ons
* **Database Software**: `MySQL`

#####vi) Attached Storage disks
Either expand the bootdisk size or add additional storage to your requirement

#####vii) Network Interface
There are two visible networks on every machine in IBM Cloud (There is a third, known as the management network, but you can not see it). 
* _Private Network_. Think of this a an internal network that only devices inside IBM Cloud can communicate with. You can not turn it off, i.e. it will always exist. For example if you had a web server connected to a database, you would connect between the web server and DB using the private IP - You would also disallow any public access to the DB. The private network usually uses the 10. IP range.
* _Public Network_. This allows people from outside IBM Cloud (i.e. Internet traffic) to access your VSI. You can be turned off, i.e. no access is given.

> Note: It is the public network you will use to access your VSI instance from your corporation.

* **Uplink port speed**: ensure you choose one that includes public. 100 Mbs or 1GBs network

* **Private security group**: You can leave all the options switched off if no other VSI is going to access this VSI
* **Public security group**: There are three options (these can be changed after a VSI is created)
	
    * `allow_all` - Allows ssh and other protocol access to the image
    * `allow_http` - Allows standard http traffic into the VSI- 
    * `allow_https` - Allows standard https traffic into the VSI

#####viii) Create the VSI 

* Acknowledge the **I have read and agree to the Third-Party Service Agreements listed below:**
* Click **Create**

It will take a few minutes to create the VSI

#####ix) Login to your VSI instance
First get the **public** IP address from the VSI instance you just created - see device list dashboard. Then open a terminal on your laptop and type

	$ ssh root@<public ipaddress>
	The system will first challenge you with a "Do you know this machine". Say 'yes'. This will add the IP address and public key to your ~/.ssh/known_hosts file so subsequent logins by you will not be initially challenged with a "Do You Know?"
	[root@<server name> ~]#
You are now logged into your VSI !!

######x) Adding additional users to your VSI
If you have additional users who need to have root access to the VSI then the recommended approach is to use certificates - __not the root password__. Have each person first create an ssh key as described above and send you the public key i.e.<name>_pub then sitting in the directory where these public keys all sit on your laptop type for each certificate

	$ ssh-copy-id -i <name>_pub root@<public IP address>

This will add an entry into the VSI's root __authorized key__ file, i.e. `~/.ssh/authorized_keys`

#####xi) A Couple of Last Points to Note
*  Changing `root` password. **Note if you change the root password whilst logged into the VSI using `passwd` this will not be reflected in the IBM Cloud dashboard**. Instead use the IBM Cloud dashboard to change the root password. See the VSI **passwords** menu. 

> Note 
This screen will also allow you to add additional users. But note this DOES NOT create a new account in the VSI instance - its purely a place to help organize passwords

* If you prefer to just give `sudo` access to other people (rather than root) then create a new user, e.g. `admin`, and using tools like `visudo` ensure this login has `sudo` privileges and rights. Then add their certificates to this account, i.e. `ssh-copy-id -i <name>_pub admin@<public ip address>` (assuming you have already added your own certificate)



