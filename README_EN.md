
# 🎭 Java RMI - Theatre Reservation System

📌 **Distributed Systems Lab Assignment**  
📅 **Due Date: 1/6/2025**  
👨‍💻 **Implemented in Java using RMI**

---

## 📚 Description

This application is a remote theatre seat reservation system built with Java RMI (Remote Method Invocation). Multiple clients can simultaneously:

- View available seats
- Make reservations
- Cancel seats
- View their bookings
- Register for cancellation notifications

---

## 🧩 Architecture

The system consists of the following Java files:

| File | Role |
|------|------|
| `THInterface.java` | Remote interface - defines methods |
| `THImpl.java` | Implements the interface - contains business logic |
| `THServer.java` | Server that registers the remote object |
| `THClient.java` | Command-line client to interact with server |

---

## 📦 Seat Types & Prices

| Type | Description | Code | Price |
|------|-------------|------|-------|
| PA | Front Orchestra - Zone A | ΠΑ | €50 |
| PB | Orchestra - Zone B | ΠΒ | €40 |
| PG | Rear Orchestra - Zone C | ΠΓ | €30 |
| KE | Central Balcony | ΚΕ | €35 |
| PT | Side Balconies | ΠΘ | €25 |

---

## 🚀 How to Run

### ✅ 1. Compile

```bash
javac *.java
```

### ✅ 2. Start Server (without rmiregistry)

```bash
java THServer
```

### ✅ 3. Start Client (in a new terminal)

```bash
java THClient <command> <parameters>
```

---

## 🛠️ Client Commands

```bash
java THClient                         # Show help instructions
java THClient list localhost          # List available seats
java THClient book localhost ΠΑ 2 Giannis_Papadopoulos    # Book 2 seats of type ΠΑ
java THClient guests localhost        # Show all customer bookings
java THClient cancel localhost ΠΑ 1 Giannis_Papadopoulos  # Cancel 1 seat
```

📌 If not enough seats are available:
- The user can accept fewer seats
- Or register for availability notification

---

## 🔔 Notification Support (Advanced Feature)

- Users can register to be notified when cancelled seats become available.
- Server sends out simple text notifications to registered clients.

---

## 🔮 Future Extensions (Section C of Assignment)

- 📅 Support for multiple plays and performance dates.
- 🌐 Web interface (e.g., Spring Boot or JSP/Servlet).
- 🗃️ Integration with RDBMS (e.g., MySQL/PostgreSQL) via JDBC.

---

## 📁 Project Structure

```
TheatreRMI/
├── THInterface.java
├── THImpl.java
├── THServer.java
├── THClient.java
└── README.md
```

---

## 🧪 Example Executions

```bash
java THServer
# → Server started.

java THClient list localhost
# → 100 seats of type ΠΑ - price: €50
# → ...

java THClient book localhost ΠΑ 2 Maria
# → Booking successful. Total cost: €100

java THClient guests localhost
# → Maria has: 2 seats of type ΠΑ (€100)
```

---

## 👨‍💻 Author

- **Name:** [Enter your name here]
- **Student ID:** [Enter your student ID]
- **Course:** Distributed Systems – Lab

---
