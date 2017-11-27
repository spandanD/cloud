# Cloud

Event driven communication framework built on top of Sevlet 3.0 async API that lets users subscribe and publish to channels in realtime without reliance on traditional short polling techniques. This provides the following advantages.

	1. Low latency
	2. Unthrottled network bandwidth
	3. Recycled thread pool to handle other incoming requests
	
## Get started
cloud-core is the central framework that supports asycnchronous communications. Download the project and add it as a dependency to your application.
The project has been built with Maven.

cloud-sample provides an example of how this framework can be used in your applicaiton. The example is a simple chat application that demonstrates the framework usage. Other applications of this framework can be found in multi-user stock trading platform, news broadcasting services, online gaming etc.
cloud-js is the necessary file that you will have to import into your project, all other files are application specific.
