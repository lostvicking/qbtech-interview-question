# Benford's Law Testing API

A Kotlin-based REST API that performs statistical analysis to determine whether account balance data follows Benford's Law using chi-squared testing. This tool is commonly used in fraud detection, auditing, and data quality analysis.

## What is Benford's Law?

Benford's Law states that in many naturally occurring datasets, the first digit follows a specific logarithmic distribution where smaller digits appear more frequently than larger ones. Specifically:
- Digit 1 appears ~30.1% of the time
- Digit 2 appears ~17.6% of the time
- Digit 9 appears ~4.6% of the time

When data significantly deviates from this pattern, it may indicate manipulation, fraud, or artificial generation.

## Features

- **Statistical Analysis**: Uses chi-squared test to determine compliance with Benford's Law
- **Flexible Input**: Accepts account balances in formatted string format
- **Configurable Confidence**: Supports custom confidence levels for hypothesis testing
- **JSON REST API**: Clean RESTful interface with JSON request/response
- **Comprehensive Results**: Returns both boolean result and detailed distribution data
- **Error Handling**: Robust error handling for malformed requests

## API Documentation

### Endpoint

```
POST /benford-test-of-fit
```

### Request Format

```json
{
  "confidenceLevel": "0.05",
  "accountBalances": "account1: 20.68, account2: 2.81, account3: 14971.28, ..."
}
```

**Request Fields:**
- `confidenceLevel` (string): Statistical significance level (e.g., "0.05" for 95% confidence)
- `accountBalances` (string): Comma-separated account balances in format "accountN: amount"

### Response Format

```json
{
  "followsBenfordsLaw": true,
  "confidenceLevel": 0.05,
  "expectedDistribution": [30.1, 17.6, 12.5, 9.7, 7.9, 6.7, 5.8, 5.1, 4.6],
  "actualDistribution": [28, 19, 14, 8, 9, 7, 6, 5, 4]
}
```

**Response Fields:**
- `followsBenfordsLaw` (boolean): Whether the data follows Benford's Law at the specified confidence level
- `confidenceLevel` (double): The confidence level used for the test
- `expectedDistribution` (array): Expected counts for digits 1-9 based on Benford's Law
- `actualDistribution` (array): Actual counts of digits 1-9 in the provided data

## Quick Start

### Prerequisites

- Java 11 or higher
- Gradle (included via wrapper)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd qbtech-interview-question
   ```

2. **Build and run**
   ```bash
   ./gradlew run
   ```

3. **Test the API**
   ```bash
   curl -X POST http://localhost:8080/benford-test-of-fit \
     -H "Content-Type: application/json" \
     -d '{
       "confidenceLevel": "0.05",
       "accountBalances": "account1: 123.45, account2: 234.56, account3: 345.67"
     }'
   ```

The server will start on `http://localhost:8080` by default.

## Technology Stack

- **Kotlin**: Primary programming language
- **Ktor**: Web framework for building the REST API
- **Apache Commons Math3**: Statistical functions for chi-squared testing
- **Kotlinx Serialization**: JSON serialization/deserialization
- **Gradle**: Build system and dependency management
- **JUnit**: Testing framework

## Project Structure

```
src/
├── main/kotlin/com/victor/
│   ├── Application.kt          # Main application entry point
│   ├── Routing.kt              # API route definitions
│   ├── dto/
│   │   ├── BenfordRequest.kt   # Request data class
│   │   └── BenfordResponse.kt  # Response data class
│   ├── math/
│   │   └── BenfordChecker.kt   # Core Benford's Law analysis logic
│   └── parser/
│       └── AccountBalanceParser.kt # Parses account balance strings
└── test/kotlin/com/victor/     # Unit tests
```

## Development

### Available Gradle Tasks

| Task | Description |
|------|-------------|
| `./gradlew test` | Run all tests |
| `./gradlew build` | Build the project |
| `./gradlew run` | Run the development server |
| `./gradlew buildFatJar` | Build executable JAR with dependencies |
| `./gradlew buildImage` | Build Docker image |

### Running Tests

```bash
./gradlew test
```

### Using the Postman Collection

A Postman collection is included in `postman-collection/qbtech.postman_collection.json` with example requests:

1. Import the collection into Postman
2. Ensure the server is running on `localhost:8080`
3. Execute the "post" request to see a working example
4. Try the "post garbage" request to test error handling

## Understanding Results

### Interpreting the Response

- **`followsBenfordsLaw: true`**: The data distribution is consistent with Benford's Law at the specified confidence level
- **`followsBenfordsLaw: false`**: The data significantly deviates from Benford's Law, which may indicate:
  - Data manipulation or fraud
  - Artificial data generation
  - Data from a domain where Benford's Law doesn't naturally apply

### Statistical Methodology

The API uses a chi-squared goodness-of-fit test to compare the observed first-digit distribution against the expected Benford distribution. The null hypothesis is that the data follows Benford's Law. If the p-value is less than the significance level (confidence level), the null hypothesis is rejected.