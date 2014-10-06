# Unicorn

Unicorn is W3C's unified validator, which helps people improve the quality of their Web pages by performing a variety of checks. Unicorn gathers the results of the popular HTML and CSS validators, as well as other useful services.

This site addresses these audiences:

* Users, those who want to check their Web pages and understand how to fix them based on Unicorn results.
* Developers, those who wish to add new services, work on existing services, or help develop the underlying Unicorn framework.
* Server Managers, those who wish to run their own Unicorn service locally. 

##How to compile and deploy Unicorn:

1. The first thing you have to do in order to make Unicorn work is to add a `unicorn.home` parameter to your JVM parameters, pointing to the unicorn rot directory.

example : 

`Dunicorn.home=/var/lib/tomcat6/webapps/unicorn/`

or

`Dunicorn.home=/C:/Program%20Files/Tomcat/webapps/unicorn/` (read your servlet engine documentation to know how to add this parameter)


2. You will find all the configuration files that Unicorn uses in `WEB-INF/conf`. 

* The main one is `unicorn properties`, which contains some properties that you may want to change:
	* UNICORN_URL is the url of your installation of Unicorn.
	* DEFAULT_LANGUAGE, the language Unicorn will use if language negotiation fails.
	* You can nest properties in this file, meaning that Unicorn will replace any ${<Property_key>} by its value.
	* There is one property that is not specify in the file but added at runtime, UNICORN_HOME, which is equal to the JVM parameter unicorn.home.
* `velocity.properties` contains properties for the template engine that Unicorn uses: Apache Velocity