h1. OpenStrategies-org
http://github.com/sfrancolla/openstrategies-org

Copyright (C) 2010, OpenStrategies, Steven Francolla, and individual contributors as indicated
by the @author tags. See the copyright.txt in the distribution for a full listing of individual 
contributors.

Released under the LGPLv3.0 license: 
http://www.gnu.org/licenses/lgpl-3.0.txt

All levels of collaboration strongly encouraged.  Simply fork and go on github.com.


h3. DESCRIPTION

This project started as a quick proof-of-concept of a real-time strategy for the open-souce 
Marketcetera trading platform.  The intention now is to mature the existing and introduce new, 
progressively more complex proof-of-concept units.  


h3. INSTALLATION

h5. Prerequisites
- Maven
- Marketcetera
- Eclipse (optional)

h5. Set-up
- Extract the project
- cd <project-root>
- mvn install:install-file -Dfile=lib\metctools-1.1.5.jar -DgroupId=org.kohera -DartifactId=metctools -Dversion=1.1.5 -Dpackaging=jar
- mvn install package eclipse:eclipse
- Eclipse | File -> Import -> Existing projects into workspace...


h3. RUN A STRATEGY

h5. For the example RealTimeMovingAverageSuggestions:
- Open RealTimeMovingAverageSuggestions.txt 
- Update createModule command to reflect the absolute path to RealTimeMovingAverageSuggestions.java 

Then, be sure to include <project-root>/target/openstrategies-org-1.0-SNAPSHOT.jar on the classpath
when invoking StrategyAgent.


h3. CONTACT:

sfrancolla@gmail.com