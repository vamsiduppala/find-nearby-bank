# 🏦 Bank Service – Nearby Bank Finder

A Spring Boot microservice that returns a list of nearby banks based on a given ZIP code. It communicates with a separate `maps-service` to convert ZIP codes into coordinates and fetch bank data.

## 📁 Project Structure

```
com.nearbybank.bank/
├── config/                # Configuration classes
├── controller/            # REST API endpoints
├── service/               # Business logic for bank services
├── model/                 # POJOs for Bank data and related information
├── repository/            # Data access layer (e.g., database interactions)
├── BankServiceApplication.java  # Main class
└── resources/
    └── application.properties    # Configurations
```


## 📡 API Endpoint

### `GET /api/banks?zipcode={zipcode}`

This endpoint accepts a ZIP code and returns a list of nearby banks.

#### 🔁 Request Flow

1. A client (e.g., browser or frontend app) sends a request like:
   
   GET http://localhost:8080/api/banks?zipcode=10001

2. `BankController` receives the request and calls `BankService`.

3. `BankService` uses `GoogleMapsClient` to:
   - Call the `maps-service` endpoint `/api/maps/coordinates?zipcode=10001` to get coordinates.
   - Then call `/api/maps/banks?lat=...&lng=...` to get banks near those coordinates.

4. The list of banks is returned to the client in JSON format.

#### 📦 Sample Response

```json
[
  {
    "name": "Chase Bank",
    "address": "123 Main St, New York, NY",
    "latitude": "40.7128",
    "longitude": "-74.0060"
  },
  {
    "name": "Wells Fargo",
    "address": "456 5th Ave, New York, NY",
    "latitude": "40.7135",
    "longitude": "-74.0059"
  }
]
```

---

## ⚙️ How It Works

- This service does **not** connect directly to Google APIs.
- It depends on `maps-service` to handle external API calls.
- All logic is modular and cleanly separated:
  - Controller ➝ Service ➝ Integration client
  - Makes the app easy to maintain and extend

---

## ▶️ Running the Project in Eclipse

1. Import the project into Eclipse as a **Gradle Project**.
2. Make sure dependencies are resolved via **Gradle Tasks > build**.
3. Open `NearbybankApplication.java`.
4. Right-click > **Run As > Java Application**.
5. App will start on `http://localhost:8080`.

Make sure `maps-service` is also running before testing the endpoint.

---
