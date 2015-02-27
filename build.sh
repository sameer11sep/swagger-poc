sh -c 'mvn clean install'

cd target

rm -rf $CATALINA_HOME/webapps/swagger-poc*

mv swagger-poc-1.0-SNAPSHOT.war swagger-poc.war

cp swagger-poc.war $CATALINA_HOME/webapps/
