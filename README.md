# Unicorn

Unicorn is W3C's unified validator, which helps people improve the quality of their Web pages by performing a variety of checks. Unicorn gathers the results of the popular HTML and CSS validators, as well as other useful services.

This site addresses these audiences:

* Users, those who want to check their Web pages and understand how to fix them based on Unicorn results.
* Developers, those who wish to add new services, work on existing services, or help develop the underlying Unicorn framework.
* Server Managers, those who wish to run their own Unicorn service locally. 

## How to compile and deploy Unicorn:

###### 1. The first thing you have to do in order to make Unicorn work is to add a `unicorn.home` parameter to your JVM parameters, pointing to the unicorn root directory.

example : 

`Dunicorn.home=/var/lib/tomcat6/webapps/unicorn/` or `Dunicorn.home=/C:/Program%20Files/Tomcat/webapps/unicorn/` (read your servlet engine documentation to know how to add this parameter)


###### 2. You will find all the configuration files that Unicorn uses in `WEB-INF/conf`.

* The main one is `unicorn properties`, which contains some properties that you may want to change:
	* `UNICORN_URL` is the URL of your installation of Unicorn.
	* `DEFAULT_LANGUAGE`, the language Unicorn will use if language negotiation fails.
	* You can nest properties in this file, meaning that Unicorn will replace any ${<Property_key>} by its value.
	* There is one property that is not specify in the file but added at runtime, `UNICORN_HOME`, which is equal to the JVM parameter `unicorn.home`.
* `velocity.properties` contains properties for the template engine that Unicorn uses: Apache Velocity
	* By default template caching is set to false. In a production environment you should set this property to true.
	* `parser.pool.size` is set to the default velocity value (20). If you have a lot of requests you may have to increase this value. In any case check the logs to see if you need to change it (Velocity will log warnings).
* `log4j.properties` is the properties file for Apache Log4j. The property `UNICORN_HOME` is also available in this file. By default logs will be written in `WEB-INF/logs` and sorted by package and level.
	* If you are developing Unicorn locally you should add the appender GUI to the root logger. This will pop up a useful LogFactor5 console.
	* You can find documentation about log4j configuration here: http://logging.apache.org/log4j/1.2/manual.html
	* Note that log4j is not mandatory for Unicorn to work properly. If log4j.properties does not exist the default `java.util.Logger` will be used.
* observers.list is the list of the observers contract links


###### 3. Under `WEB-INF/resources/tasklist` you will find the task related files which are xml files describing tasks and rdf files containing metadata about tasks.


###### 4. Use ant to compile the project. You can use the 'war' task to make a war file of the 'jar' task to package Unicorn in a jar. The files will be written in the dist directory. See `build.xml` for more info.
ex: `ant war`


###### 5. As root, copy the file resources/tomcat_policy in the policy directory of tomcat (/etc/tomcat5/policy.d for Debian) and eventually edit it to fit your needs.
Note that this file is very important because it will give permissions to read and write files under Unicorn servlet dir, but also to connect to distant hosts (observers).


## How to initialize Unicorn:

Once you have compiled and deploy Unicorn on your engine you must initialize it. There are a few mandatory steps that Unicorn has to do before being usable, like parsing the contract files, language files, taklists, etc...

If your engine uses the `web.xml` description file (which should be the case with almost any servlet engine) initialization is automated at startup.

If you want to manually initialize Unicorn you can simply execute the InitAction by connecting to http://localhost:8080/unicorn/init. This task will launch all initialization tasks which are:
* initialize Unicorn core
* load Unicorn observers (you can execute this task only by connecting to /init?task=observers)
* load Unicorn tasklists (/init?task=tasklist)
* load language files (/init?task=language)

In a production environment InitAction servlet should be protected to be accessible only from localhost (set PROTECT_INIT_ACTION to true in unicorn.properties)

## How does the log work:

Under Tomcat logs files are on `"webapps/unicorn/WEB-INF/logs/"`.

There are split in two directory :
* "level" where log are split in one file by log level (trace, debug, info, warning and error).
* "package" where log are split by package where they come from. There are also one file called "all.log" which contains all logs informations.