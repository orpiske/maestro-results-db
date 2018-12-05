Maestro Results DB: Kubernetes Deployment
============

Preparation
----

Deploying this component on Kubernetes or OpenShift is not significantly different than deploying any other Maestro 
component on the same environment. Nonetheless, a few things must be observed: 

1. Do not run the Reports Tool and the Results DB at the same time. If you have such deployment on your environment, 
please make sure that you remove it. 

2. This tool should be used along with a MariaDB instance. None is provided along with this and you should make sure 
to have one.

3. Ensure that you adjust the following environment variables on the deployment template so that they match what has 
been defined on your MariaDB install: MAESTRO_REPORTS_DATASOURCE_URL (to the URL of the DB), 
MAESTRO_REPORTS_DATASOURCE_USERNAME (to the DB username) and MAESTRO_REPORTS_DATASOURCE_PASSWORD (to the DB password). 


Deploy the Reports Tool
--- 

You have completed the preparation steps, you can deploy the tool using the following commands:

```
kubectl apply -f results-pvc.yaml
kubectl apply -f results-service.yaml -f results-deployment.yaml
kubectl expose service results-external --hostname=<the hostname you want to use to access it>
```


