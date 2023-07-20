# Fortune Teller Service with Server-Sent Events (SSE)

This is a simple Fortune Teller Service built with Spring Boot that demonstrates how to use Server-Sent Events (SSE) to
notify clients about their future predictions asynchronously. The Fortune Teller Service allows clients to request their
future predictions by providing their names. The service processes the request asynchronously using the `@Async` method,
and once the prediction is ready, it notifies the client via SSE.

## Features

- Request future predictions by providing your name.
- Asynchronous processing of future predictions using `@Async` method.
- Server notifies the client via Server-Sent Events (SSE) once the prediction is ready.
- Real-time updates on the client-side without manual refreshing.

## Technologies Used

- Java 8+
- Spring Boot 2.x
- Maven (for Spring Boot project)

## Getting Started

### Prerequisites

Make sure you have the following installed on your system:

- Java Development Kit (JDK) 8 or higher
- Maven

### How to Run the Application

1. Clone the repository:

   ```bash
   git clone git@github.com:datmt/spring-boot-angular-sse.git
   cd spring-boot-angular-sse

To receive the prediction once it's ready, connect to the SSE endpoint:

```typescript
const eventSource = new EventSource('http://localhost:8080/notify');

eventSource.onmessage = event => {
const prediction = event.data;
console.log('Received Prediction:', prediction);
// Handle the prediction as needed on the client-side
};

eventSource.onerror = error => {
console.error('Error with SSE connection:', error);
eventSource.close();
};
```