This project started because I wanted to write some java code and use an open source tool known as Drools. As I progressed through the idea this grew initial idea kept growing. I should also say IO am an Architesct that develops, but I am not a full time developer. I Tinker and work on strategy for cloud solutions. I no longer write a lot of code.

NEED MORE

As I went through the motions of defining what I needed for my application I decided on some initial "Base Principals". These were
1. I wanted to keep this cheap so my first decision was to look at Open Source for most of my tooling.
1. I wanted to do this using development best practices. I am an expereinced architect that knows the correct pieces to use to develop solution, but I am not a full blown developer.
1. I wanted to eventually deploy the solution to the cloud so people could use it
1. I knew I was going to need help, so I needed to ensure team members could easily be onboarded.
1. As a corollary to the above point I did not want to write GUIs. I did this when I was younger but I have not kept pace with all the latest advances, e.g. the latest HTML, Swift, .... solutions. I also did not want to learn all the ins and out of developing for mobile solutions, which was something that I knew I would need to consider.


More needed


I first decided that I would develop everything in Eclipse. I have used this Integrated Development Environment (IDE) for many years from my days developing Smalltalk, then Java, and more recently Python. However, I have not been developing apps for a number of years - I might even say a decade! So i wanted to refresh my goog development practices and update my usage level of modern day tools.

Thus I also decided I would refresh my knowledge (to be honest learn) JUnit for testing and Maven for build management. I quickly began to understand the benefits. I followed a Test Driven approach to developing my solution, i.e. I was writing my Junit tests from day one to test my ideas out - In my nomenclature I refer to these a Unit Tests. 

As I progressed I kept having to learn/add new libraries. The Drools system I was experimenting with needed a Java Logging implementation. Drools uses Simple Logging for Java (SLF4j), which is a facade to logging implementations like, xxx and yyy. I decided to use Log4J as I had used this in the past. I also found out I needed a JSON parsing package, I decided on XXXXXXXX. Drools also utilizes a Business Process Management (BPM) component - what I knew as JBOSS, or now jBPM. All of the above libraries showed me the benefits of adding Maven's library loading features. I became a pom.xml addict.

I was happily developing a solution now all on my laptop but the solution was eventually going to have to run on a server in the Cloud. I am an IBM employee so it was a foregone conclusion I would use IBM Cloud, in fact I wanted to really understand the solution we were brining to market.

So my first thought was I needed to create a server that I could push my code to and perform system tests. In other words a test environment that would be close to production that had its onw tests (System Integration Test)


create a Tomcat server that I could put he KiieServer on - 




this lead me to using a build tool


as I wanted a way to experiment with DevOps tools as I was 