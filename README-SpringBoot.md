mvn archetype:generate -DgroupId=com.bolkimen.microservice -DartifactId=spring_cloud

https://spring.io/guides/gs/spring-boot-docker
Build docker:
mvnw spring-boot:build-image

Spring auth:
https://github.com/spring-projects/spring-security-samples/blob/main/reactive/webflux/java/authentication/one-time-token/magic-link/src/main/java/org/example/magiclink/MagicLinkOneTimeTokenGenerationSuccessHandler.java

