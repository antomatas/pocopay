## Home assignment for Pocopay

#### To run application in container:
* make sure datasource in application.properties is `spring.datasource.url=jdbc:postgresql://db:5432/pocopay`
* if you do not want test-data to be added then uncomment in application properties `spring.liquibase.contexts=dev`
* build application jar: `./gradlew bootJar`
* build docker images and run them: `docker-compose up`


#### To run application locally:
* change datasource in application.properties to `spring.datasource.url=jdbc:postgresql://localhost:5432/pocopay`
* build and run postgre container using `build.sh` and `run.sh` located in etc/docker/postgres
* run as spring boot application (main class is ee.pocopay.homeassignment.HomeAssignmentApplication)
