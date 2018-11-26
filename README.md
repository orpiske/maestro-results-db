Maestro ResultsDB
============


Introduction
----

Maestro ResultsDB is a tool that records the test data reported by the Maestro peers and downloaded by the client into 
a MariaDB database. After the data is loaded, it can generate reports comparing different tests. It has a loose
integration with Prometheus (optional and disabled by default).

**Note**: this project is still a work in progress and documentation is scarce.

Building
----
Build Status (devel): [![Build Status](https://travis-ci.org/maestro-performance/maestro-results-db.svg?branch=devel)](https://travis-ci.org/maestro-performance/maestro-results-db)

Build Status (master): [![Build Status](https://travis-ci.org/maestro-performance/maestro-results-db.svg?branch=master)](https://travis-ci.org/maestro-performance/maestro-results-db)

Codacy Report: [![Codacy Badge](https://api.codacy.com/project/badge/Grade/93bf8e148d114781b2e6e02a2c880e76)](https://www.codacy.com/app/orpiske/maestro-results-db?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=maestro-performance/maestro-results-db&amp;utm_campaign=Badge_Grade)


Local build:
```
mvn clean install
```

Packaging for release:

```
mvn -PPackage clean package
```



Setup
----

If a MariaDB database is available. First, create a user: 


```
CREATE USER 'maestro'@'localhost' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'localhost' WITH GRANT OPTION;
CREATE USER 'maestro'@'%' IDENTIFIED BY 'maestro-dev';
GRANT ALL PRIVILEGES ON maestro.* TO 'maestro'@'%' WITH GRANT OPTION;
```

Then create the tables: 
``````
cd doc/mariadb
mysql -u maestro messaging-ci-dev.usersys.redhat.com -p maestro < ./schema.sql
``````

Then create the views:
``````
cd doc/mariadb
mysql -u maestro messaging-ci-dev.usersys.redhat.com -p maestro < ./views.sql
``````

After the database is setup, proceed to adjust the configuration. The suggested way is to copy the 
`maestro-results.properties` file in the configuration directory to `$HOME/.maestro/maestro-results.properties`.
Adjust the parameters as needed. 