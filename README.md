# URL Shortener Challenge

This repository contains a solution for a URL Shortener service, designed to handle up to **1M RPM** and provide **99.98% uptime**. It features the following components:

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
- **Kubernetes**: Used for orchestration and scaling, with each service deployed in its own pod and managed through a **LoadBalancer**.

## Features

- URLs can be created, modified, enabled/disabled, and monitored.
- Supports real-time access statistics.
- Asynchronous communication between services ensures high throughput and minimal latency.
- High availability ensured through Redis HA, Kafka clustering, and scalable microservices on Kubernetes.

## Deployment Architecture

- **Kubernetes** manages service replication and scaling.
- **LoadBalancer** routes traffic to the Gateway Service.
- External services:
  - **Prometheus** is secured through a VPN for metrics gathering.
  - **MongoDB** operates externally, allowing elastic scaling.
  
For more detailed information, including technology choices and justifications, refer to the attached PDF document.

## Setup Instructions

1. Clone the repository
2. Configure environment variables for MongoDB, Redis, Kafka, and Prometheus.
3. Deploy using Kubernetes following the instructions in `deployment/k8s/`.

---

For more details, please refer to the full documentation included in the PDF.

