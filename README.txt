OpenStrategies-org
http://github.com/sfrancolla/openstrategies-org

Copyright (C) 2010, OpenStrategies, Steven Francolla, and individual contributors as indicated
by the @author tags. See the copyright.txt in the distribution for a full listing of individual 
contributors.

Released under the LGPLv3.0 license: 
http://www.gnu.org/licenses/lgpl-3.0.txt

All levels of collaboration strongly encouraged.  Simply fork and go on github.com.


DESCRIPTION

This project started as a quick proof-of-concept of a real-time strategy for the open-souce 
Marketcetera trading platform.  The intention now is to mature the existing and introduce new, 
progressively more complex proof-of-concept units.  


INSTALLATION

Prerequisites:
- Maven
- Marketcetera
- Eclipse (optional)

Set-up:
- Extract the project
- Edit the pom.xml.  Uncomment the <repositories> tag in the project's pom.xml.
- Do:
    cd <project-root>
    mvn install:install-file -Dfile=lib\jbrukh-metctools-1.1.5.jar -DgroupId=org.kohera -DartifactId=metctools -Dversion=1.1.5 -Dpackaging=jar
    mvn install package 

Finally, for Eclipse:
- Do:
    mvn eclipse:eclipse
- Open Eclipse
- File -> Import -> Existing projects into workspace...


RUN A STRATEGY

For the example RealTimeMovingAverageSuggestions:
- Open RealTimeMovingAverageSuggestions.txt 
- Update createModule command to reflect the absolute path to RealTimeMovingAverageSuggestions.java 

Then, be sure to include <project-root>/target/openstrategies-org-1.0-SNAPSHOT.jar on the classpath
when invoking StrategyAgent.


CONTACT:

sfrancolla@gmail.com