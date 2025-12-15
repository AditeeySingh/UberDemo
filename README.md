# 🚕 Uber Demo Backend API

Spring Boot + MongoDB + JWT Authentication

This project is a backend REST API for a mini Ride Sharing application built using Spring Boot, MongoDB, and JWT Authentication.

It supports:
	•	User & Driver authentication
	•	Ride booking by User
	•	Ride request viewing by Driver
	•	Ride accepting by Driver
	•	Ride completion by User or Driver
	•	Advanced ride search & filtering
	•	Analytics using MongoDB Aggregation



🔐 Authentication Flow
	1.	User or Driver registers
	2.	Login returns a JWT Token
	3.	This token must be sent in every request using:
```http
Authorization: Bearer <your_token_here>
```



✅ Features Implemented

🔑 Authentication & Security
	-	✅ JWT Login & Register
	-	✅ Password encryption using BCrypt
	-	✅ Role-based access control (USER / DRIVER)
	-	✅ Spring Security filters

🚗 Ride Management
	-	✅ Create Ride (USER)
	-	✅ View Pending Rides (DRIVER)
	-	✅ Accept Ride (DRIVER)
	-	✅ Complete Ride (USER / DRIVER)

🔍 Advanced Search & Filters
	-	✅ Search rides by pickup/drop keyword
	-	✅ Filter rides by distance range
	-	✅ Filter rides by date range
	-	✅ Filter rides by status
	-	✅ Combined filters (status + keyword)
	-	✅ Sorting by fare (asc / desc)
	-	✅ Pagination support

📊 Analytics (MongoDB Aggregation)
	-	✅ Rides per day
	-	✅ Driver summary (total rides, total fare)
	-	✅ Status summary



## 🧪 API Testing Steps (Postman)



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

👉 Copy the returned **JWT Token**




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

👉 Ride created with status `REQUESTED`

<img width="455" height="285" alt="Screenshot 2025-12-07 at 9 43 52 PM" src="https://github.com/user-attachments/assets/b7be1cb9-f182-4a71-910f-ae1fbcff7449" />




### ✅ 5. View Pending Rides (DRIVER)

**GET**  
```http
http://localhost:8081/api/v1/driver/rides/requests
```

**Headers**
```http
Authorization: Bearer <DRIVER_TOKEN>
```

👉 Returns all `REQUESTED` rides

<img width="431" height="281" alt="Screenshot 2025-12-07 at 9 43 46 PM" src="https://github.com/user-attachments/assets/d1f5aa67-c3ca-4b74-91aa-1d96fadee14d" />



### ✅ 6. Accept Ride (DRIVER)

**POST**  
```http
http://localhost:8081/api/v1/driver/rides/{rideId}/accept
```

**Headers**
```http
Authorization: Bearer <DRIVER_TOKEN>
```

👉 Ride status becomes `ACCEPTED`

<img width="440" height="267" alt="Screenshot 2025-12-07 at 9 43 40 PM" src="https://github.com/user-attachments/assets/8c351799-51a5-4b5b-aefb-3ab08b869a2f" />




### ✅ 7. Complete Ride (USER / DRIVER)

**POST**  
```http
http://localhost:8081/api/v1/rides/{rideId}/complete
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```

👉 Ride status becomes `COMPLETED`

<img width="508" height="292" alt="Screenshot 2025-12-07 at 10 00 09 PM" src="https://github.com/user-attachments/assets/e87374fc-04ac-45bc-8c19-afc8b6a24638" />



## 🔎 Advanced Ride Queries



### ✅ 8. Get All Rides for a User

**GET**  
```http
http://localhost:8081/api/v1/rides/user/{userId}
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```

👉 Response: 200 OK (Working)



### ✅ 9. Get User Rides by Status

**GET**  
```http
http://localhost:8081/api/v1/rides/user/{userId}/status/{status}
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
👉 Response: 200 OK (Working)



### ✅ 10. Driver Active Rides

**GET**  
```http
http://localhost:8081/api/v1/driver/{driverId}/active-rides
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
👉 Response: 200 OK (Working)



### ✅ 11. Filter by Status + Keyword

**GET**  
```http
http://localhost:8081/api/v1/rides/filter-status?status=COMPLETED&search=kor
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```

<img width="520" height="366" alt="12" src="https://github.com/user-attachments/assets/82d6b20f-0494-4633-9f93-aa3b5c264e57" />




### ✅ 12. Search Rides by Keyword

**GET**  
```http
http://localhost:8081/api/v1/rides/search?text=kor
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```

<img width="520" height="366" alt="12" src="https://github.com/user-attachments/assets/82d6b20f-0494-4633-9f93-aa3b5c264e57" />




### ✅ 13. Sort Rides by Fare

**GET**  
```http
http://localhost:8081/api/v1/rides/sort?order=desc
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
👉 Response: 200 OK (Working)




### ✅ 14. Filter Rides by Distance

**GET**  
```http
http://localhost:8081/api/v1/rides/filter-distance?min=2&max=10
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="644" height="325" alt="14" src="https://github.com/user-attachments/assets/c0b8fd1b-9082-4412-a4fd-ae686897f991" />




### ✅ 15. Filter Rides by Date Range

**GET**  
```http
http://localhost:8081/api/v1/rides/filter-date-range?start=2025-12-01&end=2025-12-31
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="472" height="297" alt="15" src="https://github.com/user-attachments/assets/0750ed92-090f-4069-92bd-661dc4e86dd4" />




### ✅ 16. Advanced Search (Pagination + Sort)

**GET**  
```http
http://localhost:8081/api/v1/rides/advanced-search
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="605" height="575" alt="16" src="https://github.com/user-attachments/assets/2c90ac09-73e3-45e0-b6ea-96cc90ab119a" />



## 📊 Analytics APIs (DRIVER JWT ONLY)



### ✅ 17. Rides Per Day

**GET**  
```http
http://localhost:8081/api/v1/analytics/rides-per-day
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="470" height="306" alt="17" src="https://github.com/user-attachments/assets/d278e8c7-71d8-44d6-83a6-3b73b33ce52d" />




### ✅ 17. Rides Per Day

**GET**  
```http
http://localhost:8081/api/v1/analytics/rides-per-day
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="470" height="306" alt="17" src="https://github.com/user-attachments/assets/d278e8c7-71d8-44d6-83a6-3b73b33ce52d" />



### ✅ 17.1 Driver Summary

**GET**  
```http
http://localhost:8081/api/v1/analytics/driver/{driverId}/summary
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="455" height="302" alt="17 1" src="https://github.com/user-attachments/assets/b23be65f-5870-4e41-8b9d-3b9ad7f29869" />




### ✅ 17.2 Status Summary

**GET**  
```http
http://localhost:8081/api/v1/analytics/status-summary
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
<img width="440" height="236" alt="17 2" src="https://github.com/user-attachments/assets/403608d2-05c5-4d0b-93f8-2427c8a974b1" />





### ✅ 18. Get Rides on a Specific Date

**GET**  
```http
http://localhost:8081/api/v1/rides/date/{date}
```

**Headers**
```http
Authorization: Bearer <ANY_VALID_TOKEN>
```
👉 Response: 200 OK (Working)


## 🛠 Tech Stack

- Java 22  
- Spring Boot 4  
- Spring Security  
- JWT Authentication  
- MongoDB  
- Maven  
- Postman  


## 👨‍💻 Author

Built as part of a backend learning mini-project by  
**Aditeey Singh Jadon**
