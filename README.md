##################################################
# To build
##################################################
mvn clean install -P artifactory-aws


##################################################
# To run the app
##################################################
java -jar target/configuration-service-9.35.0-SNAPSHOT.jar


##################################################
# To test
##################################################
- start the Spring Cloud Config server

- verify it sees the props for the NotifyGW:
    curl http://localhost:8888/notifygatewaysvc/default -v -X GET
    200 Long json reflecting the content of /home/centos/springcloudtest/notifygatewaysvc.yml

- verify that a change to /home/centos/springcloudtest/notifygatewaysvc.yml is reflected on the fly
    - change a property's value (make sure you do git add and commit)
    - curl http://localhost:8888/notifygatewaysvc/default -v -X GET
    - verify the new value is displayed

- build and start the NotifyGw

- verify all the config for NotifyGw is done correctly: use its curlTests.txt
    - TODO change the pwd and verify it is reflected on the fly


################################################################
# Work done to set up the local git directory
################################################################
cd /home/centos/springcloudtest
git init
create file notifygatewaysvc.yml (notifygatewaysvc must match the spring.application.name in the NotifyGW)
git commit


################################################################
# Important notes
################################################################
- see @RefreshScope on NotifyConfiguration in the Notify GW:
    - by default, the configuration values are read on the client’s startup, and not again. You can force a bean to refresh its configuration - to pull
    updated values from the Config Server - by annotating the bean with the Spring Cloud Config @RefreshScope and then by triggering a refresh
    event.


################################################################
# TODOs
################################################################
TODO spring.cloud.config.server.git.uri points to a local git directory. This will have to be replaced with a github project.
TODO Trigger a refresh event to verify props are updated on the fly.
