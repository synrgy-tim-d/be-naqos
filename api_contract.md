#Users
* User object
```
{
  id: string
  name: string
  username: string
  email: string
  gender: enum
  phone: string
  is_active: boolean
  image_id: int (FK)
  user_otp: int (FK)
  created_at: datetime(iso 8601)
  updated_at: datetime(iso 8601)
}
```
**GET /users**
----
  Returns all users in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  
```
{
  users: [
           {<user_object>},
           {<user_object>},
           {<user_object>}
         ]
}
```

**GET /users/:id**
----
  Returns the specified user.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
* **Code:** 200  
  **Content:**  `{ <user_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "User doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**GET /users/:id/orders**
----
  Returns all Orders associated with the specified user.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  
```
{
  orders: [
           {<order_object>},
           {<order_object>},
           {<order_object>}
         ]
}
```
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "User doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /users**
----
  Creates a new User and returns the new object.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Data Params**  
```
  {
	  name: string
	  username: string
	  email: string
	  password: string
	  gender: enum
	  phone: string
  }
```
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <user_object> }` 

**PATCH /users/:id**
----
  Updates fields on the specified user and returns the updated object.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
```
  {
	  name: string
	  username: string
	  email: string
	  password: string
	  gender: enum
	  phone: string
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
* **Code:** 200  
  **Content:**  `{ <user_object> }`  
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "User doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /users/:id**
----
  Deletes the specified user.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
  * **Code:** 204 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "User doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

---------------------------------------------------------------------------------------------------------------------

#Room
* Room object
```
{
  id: string
  room_type: enum
  rules: string
  price_per_daily: decimal
  price_per_weekly: decimal
  price_per_monthly: decimal
  is_available: boolean
  facility_id: array[] (FK)
  image_id: array[] (FK)
  created_at: datetime(iso 8601)
  updated_at: datetime(iso 8601)
}
```
**GET /room**
----
  Returns all room in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:** 
* **Code:** 200  
  **Content:**  
```
{
  room: [
           {<room_object>},
           {<room_object>},
           {<room_object>}
         ]
}
``` 

**GET /room/:id**
----
  Returns the specified kos.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <room_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Room doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**GET /room/:id/orders**
----
  Returns all Orders associated with the specified room.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
* **Code:** 200  
  **Content:**  
```
{
  orders: [
           {<room_object>},
           {<room_object>},
           {<room_object>}
         ]
}
``` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Room doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /room**
----
  Creates a new Room and returns the new object.
* **URL Params**  
  None
* **Data Params**  
```
  {
	  room_type: enum
	  rules: string
	  price_per_daily: decimal
	  price_per_weekly: decimal
	  price_per_monthly: decimal
	  is_available: boolean
	  facility_id: array[]
	  image_id: array[]
  }
```
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <room_object> }` 

**PATCH /room/:id**
----
  Updates fields on the specified Room and returns the updated object.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
```
  {
	  room_type: enum
	  rules: string
	  price_per_daily: decimal
	  price_per_weekly: decimal
	  price_per_monthly: decimal
	  is_available: boolean
	  facility_id: array[]
	  image_id: array[]
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
* **Code:** 200  
  **Content:**  `{ <room_object> }`  
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Room doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /room/:id**
----
  Deletes the specified product.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
  * **Code:** 204
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Room doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

---------------------------------------------------------------------------------------------------------------------

#Kos
* Kos object
```
{
  id: string
  name: string
  description: string
  kos_type: enum
  is_available: boolean
  owner_id: int (FK)
  location_id: int (FK)
  room_id: array[] (FK)
  created_at: datetime(iso 8601)
  updated_at: datetime(iso 8601)
}
```
**GET /kos**
----
  Returns all kos in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:** 
* **Code:** 200  
  **Content:**  
```
{
  kos: [
           {<kos_object>},
           {<kos_object>},
           {<kos_object>}
         ]
}
``` 

**GET /kos/:id**
----
  Returns the specified kos.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <kos_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Kos doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /kos**
----
  Creates a new Kos and returns the new object.
* **URL Params**  
  None
* **Data Params**  
```
  {
	  name: string
	  description: string
	  kos_type: enum
	  is_available: enum
	  owner_id: int
	  location_id: int 
	  room_id: array[]
  }
```
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <kos_object> }` 

**PATCH /kos/:id**
----
  Updates fields on the specified Kos and returns the updated object.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
```
  {
	  name: string
	  description: string
	  kos_type: enum
	  is_available: enum
	  owner_id: int
	  location_id: int 
	  room_id: array[]
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
* **Code:** 200  
  **Content:**  `{ <kos_object> }`  
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Kos doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /kos/:id**
----
  Deletes the specified product.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
  * **Code:** 204
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Kos doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

---------------------------------------------------------------------------------------------------------------------

#Orders
* Order object
```
{
  id: string
  total: decimal
  is_available: boolean
  is_paid: boolean
  order_detail: [
              {<order_detail_object>},
              {<order_detail_object>},
              {<order_detail_object>},
            ] (FK)
  created_at: datetime(iso 8601)
  updated_at: datetime(iso 8601)
}
```
**GET /orders**
----
  Returns all orders in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:** 
* **Code:** 200  
  **Content:**  
```
{
  orders: [
           {<order_object>},
           {<order_object>},
           {<order_object>}
         ]
}
``` 

**GET /orders/:id**
----
  Returns the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**GET /orders/:id/rooms**
----
  Returns all room associated with the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  
```
{
  orders: [
           {<room_object>},
           {<room_object>},
           {<room_object>}
         ]
}
```
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**GET /orders/:id/user**
----
  Returns all Users associated with the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** `{ <user_object> }`  
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /orders**
----
  Creates a new Order and returns the new object.
* **URL Params**  
  None
* **Data Params**  
```
  {
	total: decimal
	is_available: boolean
	is_paid: boolean
	order_detail: [
              {<order_detail_object>},
              {<order_detail_object>},
              {<order_detail_object>},
            ] (FK)
  }
```
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order_object> }` 

**PATCH /orders/:id**
----
  Updates fields on the specified order and returns the updated object.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
```
  {
	total: decimal
	is_available: boolean
	is_paid: boolean
	order_detail: [
              {<order_detail_object>},
              {<order_detail_object>},
              {<order_detail_object>},
            ] (FK)
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /orders/:id**
----
  Deletes the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
  * **Code:** 204 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`
  
---------------------------------------------------------------------------------------------------------------------

#Order Details
* Order Detail object
```
{
  id: integer
  note: string
  booking_option: enum
  amount: array[]
  start_date: date
  end_date: date
  owner_id: string (FK)
  occupant_id: string (FK)
  kos_id: string (FK)
  room_id: [
              {<room_object>},
              {<room_object>},
              {<room_object>},
            ] (FK)
}
```
**GET /orders/detail**
----
  Returns all order details in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Success Response:** 
* **Code:** 200  
  **Content:**  
```
{
  order_detail: [
           {<order_detail_object>},
           {<order_detail_object>},
           {<order_detail_object>}
         ]
}
``` 

**GET /orders/detail/:id**
----
  Returns the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order detail doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**GET /orders/detail/:id/rooms**
----
  Returns all room associated with the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  
```
{
  order_detail: [
           {<room_object>},
           {<room_object>},
           {<room_object>}
         ]
}
```
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order detail doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /orders/detail**
----
  Creates a new Order Detail and returns the new object.
* **URL Params**  
  None
* **Data Params**  
```
  {
	  note: string
	  booking_option: enum
	  amount: array[]
	  start_date: date
	  end_date: date
	  owner_id: string (FK)
	  occupant_id: string (FK)
	  kos_id: string (FK)
	  room_id: [
				  {<room_object>},
				  {<room_object>},
				  {<room_object>},
				] (FK)
  }
```
* **Headers**  
  Content-Type: application/json  
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order_detail_object> }` 

**PATCH /orders/detail/:id**
----
  Updates fields on the specified order and returns the updated object.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
```
  {
	  note: string
	  booking_option: enum
	  amount: array[]
	  start_date: date
	  end_date: date
	  owner_id: string (FK)
	  occupant_id: string (FK)
	  kos_id: string (FK)
	  room_id: [
				  {<room_object>},
				  {<room_object>},
				  {<room_object>},
				] (FK)
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**  
* **Code:** 200  
  **Content:**  `{ <order__detail_object> }` 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order detail doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /orders/detail/:id**
----
  Deletes the specified order.
* **URL Params**  
  *Required:* `id=[string]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:** 
  * **Code:** 204 
* **Error Response:**  
  * **Code:** 404  
  **Content:** `{ error : "Order detail doesn't exist" }`  
  OR  
  * **Code:** 401  
  **Content:** `{ error : error : "You are unauthorized to make this request." }`