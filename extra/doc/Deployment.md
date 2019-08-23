Maestro Results DB Deployment
============

Introduction
----

If you have deployed any other Maestro component, the ResultsDB should be no challenge at all. In fact, it works and 
behaves just like any other Maestro component. 

Get Maestro
----

You can find official Maestro packages on the [Docker Hub](https://hub.docker.com/r/maestroperf/). Maestro tarballs 
can also be found [here](http://www.orpiske.net/files/maestro-results-db/), although the tarballs are not guaranteed to be the
latest.

**Note**: It is very easy to build Maestro. Check the [Development Guide](Development.md) for details about how to build 
Maestro and have the latest tarballs if that is what you need.


Maestro ResultsDB Deployment: Preparing the Database
----

The ResultsDB requires a MariaDB instance to run. The scripts for creating and/or upgrading a DB are provided along 
with the tarballs delivered in the project. 

To prepare the MariaDB, first, create a user for Maestro: 


```
CREATE USER 'maestro'@'localhost' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'localhost' WITH GRANT OPTION;
CREATE USER 'maestro'@'%' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'%' WITH GRANT OPTION;
```

Then create the tables: 

``````
cd doc/mariadb
mysql -u maestro -h my.db.hostname.com -p maestro < ./schema.sql
``````

Maestro ResultsDB Deployment: Quick Start Using Docker 
----

You can use the `maestroperf/maestro-results-db:stable` (or `maestroperf/maestro-results-db:edge` to get the development 
version) as the image for running the results DB. 

Having created the DB user, the tables and views on the earlier step, you can run a container image accessing that DB 
using the following command line:

```
docker run -e MAESTRO_REPORTS_DRIVER=org.mariadb.jdbc.Driver -e MAESTRO_REPORTS_DATASOURCE_URL=jdbc:mariadb://my.db.hostname.com:3306/maestro -e MAESTRO_REPORTS_DATASOURCE_USERNAME=maestro -e MAESTRO_REPORTS_DATASOURCE_PASSWORD=maestro-dev -e MAESTRO_BROKER=mqtt://my.broker.hostname.com:31883 maestroperf/maestro-results-server:devel
``` 

If running with the docker compose method explained on the Maestro documentation, you might need to adjust the network
used by the container. 

Maestro Results DB Deployment: Using Kubernetes
----

This deployment method is documented in greater detail [here](../kubernetes).


Maestro Results DB Deployment: Other
----

It is possible to have the DB parameters set on a property file as well. Both the `maestro-results.properties` file in 
the configuration directory as well as `$HOME/.maestro/maestro-results.properties` can be adjusted.
