./gradlew clean build &&
java -Dspring.profiles.active=qa -jar feature-toggle-web/build/libs/feature-toggle-web-1.0.0-SNAPSHOT.jar & cd feature-toggle-front && npm start
