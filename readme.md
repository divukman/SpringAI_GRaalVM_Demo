# Spring Native Demo

### Info
- https://codelabs.developers.google.com/spring-native-quickstart#0

### Build and run

```
mvn -Pnative spring-boot:build-image -Dmodule.image.name=demo-native
mvn spring-boot:build-image -Dmodule.image.name=demo-jvm

Successfully built image 'docker.io/library/demo-jvm:latest'
Successfully built image 'docker.io/library/demo-native:latest'

$ docker run --rm -p 8080:8080 docker.io/library/demo-jvm:latest
$ docker run --rm -p 8080:8080 docker.io/library/demo-native:latest
```

Results in java app startup:
```
Java:
Started DemoApplication in 1.362 seconds (process running for 1.717)
Started DemoApplication in 1.218 seconds (process running for 1.487)
Started DemoApplication in 1.266 seconds (process running for 1.517)
Started DemoApplication in 1.267 seconds (process running for 1.521)
Started DemoApplication in 1.219 seconds (process running for 1.47)

Native:
Started DemoApplication in 0.05 seconds (process running for 0.054)
Started DemoApplication in 0.056 seconds (process running for 0.061)
Started DemoApplication in 0.055 seconds (process running for 0.06)
Started DemoApplication in 0.05 seconds (process running for 0.055)
Started DemoApplication in 0.051 seconds (process running for 0.055)
```

~ 30 times faster

Script to measure performance when running with docker:
```
#!/bin/bash

# Set the image name and container name
IMAGE_NAME="docker.io/library/demo-jvm:latest"  # Replace with your Docker image name
CONTAINER_NAME="performance-testing-java"


# Function to measure the time until the endpoint is available
measure_time() {
    start_time=$(date +%s.%N)

    # Wait for the endpoint to become available
    while ! curl -s -o /dev/null "http://localhost:8080/"; do
        sleep 0.1  # Wait before retrying
    done

    end_time=$(date +%s.%N)
    elapsed_time=$(echo "$end_time - $start_time" | bc)
    echo "$elapsed_time"
}

# Perform the curl request 10 times and collect response times
total_time=0
for i in {1..10}; do
    # Start the Docker container
    docker run -d -p 8080:8080 --name "$CONTAINER_NAME" "$IMAGE_NAME"
    # Wait for the container to start
    echo "Waiting for the container to initialize..."

    response_time=$(measure_time)
    echo "Response time $i: $response_time seconds"
    total_time=$(echo "$total_time + $response_time" | bc)

    # Clean up: stop and remove the container
    docker stop "$CONTAINER_NAME"
    docker rm "$CONTAINER_NAME"
done

# Calculate the average response time
average_time=$(echo "$total_time / 10" | bc -l)
echo "Average response time: $average_time seconds"
```


Java image:
```
~/temp » ./performance.sh                                                                                                                                dimitar@precision-7730
b6d6dcafa3220b8cd49bde70355fa83bd4aa22c0704b6a3cac4a5a55f47f2f2c
Waiting for the container to initialize...
Response time 1: 1.674053343 seconds
performance-testing-java
performance-testing-java
1ca61dc402bd26ec7e0c44ffb1682d185d81df284297fb5be0eaae0e00f0cc6e
Waiting for the container to initialize...
Response time 2: 1.679520762 seconds
performance-testing-java
performance-testing-java
bca19d7818fd84698ec03186b3ba7856a1ca47c81f3132bef48953e7777ede53
Waiting for the container to initialize...
Response time 3: 1.567384406 seconds
performance-testing-java
performance-testing-java
4720b698c518c0c24714301695518d90c9207cf1ad55fed3c06aff466700e803
Waiting for the container to initialize...
Response time 4: 1.675686046 seconds
performance-testing-java
performance-testing-java
ffa6d5c8648588c3664cd2bee38550838ea072dc89a9be8d765d8a1ccba660d6
Waiting for the container to initialize...
Response time 5: 1.574411013 seconds
performance-testing-java
performance-testing-java
1175cceb3cb59196e419453754f9be7c573a0593f4397829a6cba60fc1229b43
Waiting for the container to initialize...
Response time 6: 1.680449878 seconds
performance-testing-java
performance-testing-java
78883b74d4212ca25929bb40c98d27a965aca0925f3f38ba7bad728e457a61de
Waiting for the container to initialize...
Response time 7: 1.586676521 seconds
performance-testing-java
performance-testing-java
23963c318780b3c6bdb53b585f8a662aa9c8854111a3f75a8dfc4e03cd0c4a58
Waiting for the container to initialize...
Response time 8: 1.717228702 seconds
performance-testing-java
performance-testing-java
5dfa8f1b93af4e72c5e4740d3e342b3d3e8ffc33884c6c9cc18e62969c5628c4
Waiting for the container to initialize...
Response time 9: 1.677510478 seconds
performance-testing-java
performance-testing-java
f41b48a67e0a055096c3e07cd803bd6502610b224cc88ef6d2b5874679c370de
Waiting for the container to initialize...
Response time 10: 2.015047407 seconds
performance-testing-java
performance-testing-java
Average response time: 1.68479685560000000000 seconds

```

Native image:
```
~/temp » ./performance.sh                                                                                                                                dimitar@precision-7730
0b3940a76e83db04d1142c16c8343ce6fce1827bf9557c75222f22288dd078c9
Waiting for the container to initialize...
Response time 1: .120352718 seconds
performance-testing-java
performance-testing-java
419513a89f77c7894c506147814b15a2893f22f2b5479840a513fe83707f2aa8
Waiting for the container to initialize...
Response time 2: .122276927 seconds
performance-testing-java
performance-testing-java
9613d8b8c9edc0279d697e8180d70e3a6153cc4e230694140e48087256e003ef
Waiting for the container to initialize...
Response time 3: .139895795 seconds
performance-testing-java
performance-testing-java
59310aba23b4ca17a3152f67f33130040db3a4615c68ec9dd51178e8cf9fae75
Waiting for the container to initialize...
Response time 4: .120231069 seconds
performance-testing-java
performance-testing-java
5fef02ebec7d2d113ea835281b258d9f9d37e5aa1b4561d69afa44c4190542f1
Waiting for the container to initialize...
Response time 5: .143759633 seconds
performance-testing-java
performance-testing-java
cc21decc31183fb453bb9a2281a55c7434f871ba09177ca18ea2e8138dbe8449
Waiting for the container to initialize...
Response time 6: .121200133 seconds
performance-testing-java
performance-testing-java
5f6831d656bda0cd8c5329b5b906c70436490a86c098837a29d97f500f489faa
Waiting for the container to initialize...
Response time 7: .119149933 seconds
performance-testing-java
performance-testing-java
e9aa096910241a0715830cb5ff01732d3510b46b2a13fe09a8127a8981812ab8
Waiting for the container to initialize...
Response time 8: .141421881 seconds
performance-testing-java
performance-testing-java
097293f584150d9cb01d475376400d7430dd0b54a6235bc47dd1bbb3410650bf
Waiting for the container to initialize...
Response time 9: .118247989 seconds
performance-testing-java
performance-testing-java
7a1194d6e36dbbbdfcf677fcccd281a6b704c029a6dea9c413ee473545610a58
Waiting for the container to initialize...
Response time 10: .121047839 seconds
performance-testing-java
performance-testing-java
Average response time: .12675839170000000000 seconds

```
~ 14 times faster
