mvn archetype:generate -DgroupId=com.bolkimen.microservice -DartifactId=spring_cloud

https://spring.io/guides/gs/spring-boot-docker
Build docker:
mvnw spring-boot:build-image

Spring auth:
https://github.com/spring-projects/spring-security-samples/blob/main/reactive/webflux/java/authentication/one-time-token/magic-link/src/main/java/org/example/magiclink/MagicLinkOneTimeTokenGenerationSuccessHandler.java
https://github.com/spring-projects/spring-security-samples/tree/main/servlet/spring-boot/java/oauth2/authorization-server#running-the-app

Client requests:
curl -X POST messaging-client:secret@localhost:9000/oauth2/token -d "grant_type=client_credentials" -d "scope=message:read"
export TOKEN=
curl -H "Authorization: Bearer $TOKEN" localhost:8080

curl -X POST user:password@localhost:8081/token -d "grant_type=client_credentials" -d "scope=message:read"
curl -X POST user:password@localhost:8081/token -H "Authorization: Bearer $JWT_TOKEN" -d "grant_type=client_credentials" -d "scope=message:read"
export JWT_TOKEN=...

