Maestro Results UI
============


Reports Server Pages
----

Make sure [bower](https://bower.io/) and [lessc](http://lesscss.org/) are installed. [NPM](https://www.npmjs.com/get-npm) is required for installing it.: 

```
npm install -g bower
```

The web resources are located in `maestro-results-server/src/main/resources/site/resources`. Web 
dependencies are defined in the bower.json file. 

```
cd maestro-results-server/src/main/resources/site-extra/resources
bower install
```

Use the compile target on the Makefile to update the css style from the less file.

```
make compile
```

References: 
* [Patternfly Setup](https://www.patternfly.org/get-started/setup/)
* [How to use Patternfly](http://andresgalante.com/howto/2016/05/06/how-to-use-patternfly.html)