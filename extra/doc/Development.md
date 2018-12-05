Maestro ResultsDB: Development
============

Building
----

Use the following command to build Maestro:
 
```./mvnw -PPackage clean package```.
 
This should generate the following tarball files:

```
./maestro-results-server/target/maestro-results-server-1.5.0-bin.tar.gz
./maestro-results-cli/target/maestro-results-cli-1.5.0-bin.tar.gz
```

Run Configurations for IntelliJ
----

Some tips and tricks for developing and debugging Maestro are available [here](development/runConfigurations). To use
those, you can copy all the XML files to your ```${project.dir}/.idea/runConfigurations``` directory


Remote Debugging 
----

To enable remote debugging, export the variable MAESTRO_DEBUG and set it to "y". The test for the variable is case 
sensitive. The debug port is set to 8000 for all components.

Version bump
----

Run the following to bump the versions:

```
mvn versions:set -DnewVersion=new_version
```

And then the following to accept the changes:

```
mvn versions:commit
```

Reports Server Pages
----

Make sure [bower](https://bower.io/) and [lessc](http://lesscss.org/) are installed. [NPM](https://www.npmjs.com/get-npm) is required for installing it.: 

```
npm install -g bower
```

The web resources are located in `maestro-reports/maestro-reports-server/src/main/resources/site/resources`. Web 
dependencies are defined in the bower.json file. 

```
cd maestro-reports/maestro-reports-server/src/main/resources/site/resources
bower install
```

Use the compile target on the Makefile to update the css style from the less file.

```
make compile
```

References: 
* [Patternfly Setup](https://www.patternfly.org/get-started/setup/)
* [How to use Patternfly](http://andresgalante.com/howto/2016/05/06/how-to-use-patternfly.html)

Maestro Libraries: Deploying in Self-Maintained Maven Repository
----

If you maintain your own Maven repository, you can deploy this library using:

```
mvn deploy -DaltDeploymentRepository=libs-snapshot::default::http://hostname:8081/path/to/libs-snapshot-local
```

Releasing
----

To release a new version and publish the jars to the public repositories:

```
mvn -DautoVersionSubmodules=true -PDelivery release:prepare && echo "Prepare complete" && mvn -PDelivery release:perform
```