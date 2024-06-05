# Feature toggle service
Service to store and process feature flags storing and managing with UI and rest API.

## Functionality
- Create new feature toggle
- Update existing feature toggle
- Archive feature toggle
- Release feature toggle(only for admin role)
- Search feature toggles by customer

## Tips
To perform any action as admin -> add Role=admin to localstorage(it's simulator of auth)
To activate feature user with admin role has to release it

## Technical description:
multi module project
Java 17 + Spring Boot 3 + gradle 8.7 + H2 file db - backend
Node 22 + React 16 - frontend

## How to start
There is 2 options how to start solution:
- Run .run.sh script from project root directory
- Do same actions like in script manually:
  - ./gradlew clean build 
  - java -Dspring.profiles.active=qa -jar feature-toggle-web/build/libs/feature-toggle-web-1.0.0-SNAPSHOT.jar
  - cd feature-toggle-front && npm start

It will open react ui on 3000 port and backend on 8080 on localhost


## To be done
FE:
1. Webpack + babel for frontend
2. oauth 2.0
2. Add redux to frontend to avoid extra calls
3. Test coverage for frontend
4. Batch updates on frontend
5. Add search by customer to frontend
6. Host server

BE: 
1. Create separate service from module "feature-toggle-service"
2. Improve test coverage
3. oauth 2.0
