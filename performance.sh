#!/bin/bash

# Set the image name and container name
IMAGE_NAME="docker.io/library/demo-native:latest"  # Replace with your Docker image name
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

