##################################################
# To build
##################################################
mvn clean install -P artifactory-aws


##################################################
# Prerequisites
##################################################
- define a git directory to store props:
    cd /home/centos/
    mkdir springcloudtest
    git init
    create file notifygatewaysvc.yml (notifygatewaysvc must match the spring.application.name in the NotifyGW) --> this is the default profile
    create file notifygatewaysvc-prod.yml   --> for props you want to override in PROD
    git commit

- TODO spring.cloud.config.server.git.uri points to a local git directory. This will have to be replaced with a github project.


##################################################
# To run the app
##################################################
java -jar target/configuration-service-9.35.0-SNAPSHOT.jar


##################################################
# To test
##################################################
- start the Spring Cloud Config server

- verify it sees the default props for the NotifyGW:
    curl http://localhost:8888/notifygatewaysvc/default -v -X GET
    200 Long json reflecting the content of /home/centos/springcloudtest/notifygatewaysvc.yml

- verify it sees the PROD props for the NotifyGW:
    curl http://localhost:8888/notifygatewaysvc/prod -v -X GET
    200 Long json reflecting the content of /home/centos/springcloudtest/notifygatewaysvc.yml and notifygatewaysvc-prod.yml

- verify that a change to /home/centos/springcloudtest/notifygatewaysvc.yml is reflected on the fly
    - change a property's value (make sure you do git add and commit)
    - curl http://localhost:8888/notifygatewaysvc/default -v -X GET
    - verify the new value is displayed

- build and start the NotifyGw:
    - verify all the config for NotifyGw is done correctly: use its curlTests.txt
    - change the value for notify.templateId and verify it is reflected on the fly:
        - in the Config Server: curl http://localhost:8888/notifygatewaysvc/default -v -X GET
        - in NotifyGW:
            - curl http://localhost:8181/manual/true -v -X GET -u notifygw:ctp
                - the logs will show you the old value of templateId.
            - curl -u notifygw:ctp http://localhost:8281/mgmt/refresh -v -X POST -d "" (IMPORTANT step to force the client to refresh itself and draw the new value: note @RefreshScope used in NotifyGW)
                - 200 is received with ["config.client.version","notify.templateId"]
            - curl http://localhost:8181/manual/true -v -X GET -u notifygw:ctp
                - the logs will show you the new value.


################################################################
# TODOs
################################################################
TODO props for different profiles
TODO Will we need to use: https://cloud.spring.io/spring-cloud-consul/
