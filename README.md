# URL short-linker

This repository contains a solution for a URL Shortener service, designed to handle requests in an asynchronicus way, and using top tecnologies. It features the following components:

## Overview

- **Java 17** with **Spring WebFlux**: Chosen for its non-blocking, reactive capabilities, allowing the application to scale efficiently under heavy load.
- **Microservices Architecture**:
  - **Gateway Service**: Manages authentication and routing.
  - **UrlManager Service**: Handles URL creation, storage, and management using a MongoDB cluster for scalable storage.
  - **StatisticsService**: Collects and stores URL usage metrics asynchronously, leveraging Kafka and Redis.
  
- **MongoDB**: Chosen for its scalability and ability to handle large amounts of data.
- **Kafka**: Used for asynchronous message passing between services.
- **Redis**: Implemented for caching and fast retrieval of URL statistics, ensuring high performance.
- **Prometheus**: Integrated for monitoring and metrics collection, allowing nearly real-time analysis of service performance.
- **Kubernetes**: Inside the pdf document, it is explained how to make it posible to scale the arquitecture.

## Features

- URLs can be created, modified, enabled/disabled, and monitored.
- Supports real-time access statistics.
- Asynchronous communication between services ensures high throughput and minimal latency.
- Designed to use a High availability ensured through Redis HA, Kafka clustering, and scalable microservices on Kubernetes.

## Deployment Architecture
  - **Java** with reactive and non blocking capabilities
  - **Prometheus** can be put inside of k8s or outside protected with VPN
  - **MongoDB** operates externally, allowing elastic scaling.
  
For more detailed information, including technology choices and justifications, refer to the attached PDF document.

## Setup Instructions

1. Clone the repository
2. Deploy docker-compose elements of kafka, prometheus, and redis.
3. Configure environment variables the microservices to run and connect to the kafka, redis and prometheus instances.

---

For more details, please refer to the full documentation included in the PDF.

