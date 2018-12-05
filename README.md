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


Deploying Maestro Results DB
----

[Deployment Documentation](extra/doc/Deployment.md)


Development
---- 

Development details for Maestro Results DB are available [here](extra/doc/Development.md).