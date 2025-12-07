# 🚕 Uber Demo Backend API  
**Spring Boot + MongoDB + JWT Authentication**

This project is a backend REST API for a mini Ride Sharing application built using **Spring Boot**, **MongoDB**, and **JWT Authentication**.

It supports:
- User & Driver authentication  
- Ride booking by User  
- Ride request viewing by Driver  
- Ride accepting by Driver  
- Ride completion by User or Driver  

---

## 🔐 Authentication Flow

1. User or Driver registers  
2. Login returns a **JWT Token**  
3. This token must be sent in every request using:

```http
Authorization: Bearer <your_token_here>
```

---

## ✅ Features Implemented

- ✅ JWT Login & Register  
- ✅ Password encryption using BCrypt  
- ✅ Create Ride (USER)  
- ✅ View Pending Rides (DRIVER)  
- ✅ Accept Ride (DRIVER)  
- ✅ Complete Ride (USER / DRIVER)  
- ✅ Role-based access control  
- ✅ MongoDB integration  
- ✅ Spring Security filters  
- ✅ Postman-tested APIs  

---

## 🧪 API Testing Steps (Postman)

---

### ✅ 1. Register USER

**POST**  
```http
http://localhost:8081/api/auth/register
```

```json
{
  "username": "Raj",
  "password": "1234",
  "role": "ROLE_USER"
}
```

---

### ✅ 2. Register DRIVER

**POST**  
```http
http://localhost:8081/api/auth/register
```

```json
{
  "username": "driver1",
  "password": "abcd",
  "role": "ROLE_DRIVER"
}
```

---

### ✅ 3. Login

**POST**  
```http
http://localhost:8081/api/auth/login
```

```json
{
  "username": "Raj",
  "password": "1234"
}
```

✅ Copy the returned **JWT Token**

---

### ✅ 4. Create Ride (USER)

**POST**  
```http
http://localhost:8081/api/v1/rides
```

**Headers**
```http
Authorization: Bearer <USER_TOKEN>
Content-Type: application/json
```

**Body**
```json
{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

✅ Ride created with status `REQUESTED`

<img width="455" height="285" alt="Screenshot 2025-12-07 at 9 43 52 PM" src="https://github.com/user-attachments/assets/b7be1cb9-f182-4a71-910f-ae1fbcff7449" />

---

### ✅ 5. View Pending Rides (DRIVER)

**GET**  
```http
http://localhost:8081/api/v1/driver/rides/requests
```

**Headers**
```http
Authorization: Bearer <DRIVER_TOKEN>
```

✅ Returns all `REQUESTED` rides

<img width="431" height="281" alt="Screenshot 2025-12-07 at 9 43 46 PM" src="https://github.com/user-attachments/assets/d1f5aa67-c3ca-4b74-91aa-1d96fadee14d" />

---

### ✅ 6. Accept Ride (DRIVER)

**POST**  
```http
http://localhost:8081/api/v1/driver/rides/{rideId}/accept
```

**Headers**
```http
Authorization: Bearer <DRIVER_TOKEN>
```

✅ Ride status becomes `ACCEPTED`

<img width="440" height="267" alt="Screenshot 2025-12-07 at 9 43 40 PM" src="https://github.com/user-attachments/assets/8c351799-51a5-4b5b-aefb-3ab08b869a2f" />

---

### ✅ 7. Complete Ride (USER / DRIVER)

**POST**  
```http
http://localhost:8081/api/v1/rides/{rideId}/complete
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```

✅ Ride status becomes `COMPLETED`

---<img width="508" height="292" alt="Screenshot 2025-12-07 at 10 00 09 PM" src="https://github.com/user-attachments/assets/e87374fc-04ac-45bc-8c19-afc8b6a24638" />


## 🛠 Tech Stack

- Java 22  
- Spring Boot 4  
- Spring Security  
- JWT Authentication  
- MongoDB  
- Maven  
- Postman  

---

## 👨‍💻 Author

Built as part of a backend learning mini-project by  
**Aditeey Singh Jadon**
