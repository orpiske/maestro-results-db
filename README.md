Maestro ResultsDB
============


Introduction
----

Maestro ResultsDB is a tool that records the test data reported by the Maestro peers and downloaded by the client into 
a MariaDB database. After the data is loaded, it can generate reports comparing different tests. It has a loose
integration with Prometheus (optional and disabled by default).

**Note**: this project is still a work in progress and documentation is scarse.

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