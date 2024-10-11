# Spring Native Demo

### Info
- https://codelabs.developers.google.com/spring-native-quickstart#0

### Build and run
```
mvn spring-boot:build-image
docker run --rm -p 8080:8080 demo:0.0.1-SNAPSHOT
curl localhost:8080
```
