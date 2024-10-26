
![emate](https://github.com/user-attachments/assets/1094201c-189c-4c3e-8acd-7bec0a68ee6b)


# E-MATE  - E-commerce Mobile App

This project is part of an end-to-end e-commerce system created for educational purposes at Sri Lanka Institute of Information Technology, as an assignment for the Enterprise Application Development module. The system consists of a web application for back-office work and a mobile application for customer interactions, both connected to a centralized web service.

This repository contains the Kotlin-based Android application, enabling customers to browse products, place orders, track order status, and rate vendors. The application communicates with a .NET backend hosted on IIS, following a client-server architecture designed to support real-time customer engagement and streamlined order management.

## Features

- **User Management**
  - Account creation using email as the primary key
  - Account modification and deactivation
  - Account activation by CSR or Administrator required for login

- **Product Browsing and Purchasing**
  - Browse products by category
  - Search for specific items
  - View detailed product information
  - Add products to cart
  - Place orders and simulate payments

- **Order Tracking**
  - Track order status
  - View order history

- **Vendor Ranking and Commenting**
  - Rank vendors based on purchase experience
  - Leave comments on vendors

## Technologies Used

- Kotlin
- Android SDK
- Retrofit for API calls
- Gson for JSON parsing
- Glide for image loading
- Android Jetpack components (ViewModel, LiveData, Room)

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/SarangaSiriwardhana9/EAD-E_commerce_App.git
   ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Configure the base URL for the web service in the `app/src/main/res/values/strings.xml` file:
   ```xml
   <string name="base_url">https://your-web-service-url.com/</string>
   ```

5. Build and run the app on an emulator or physical device.

## Usage

1. Launch the app and create a new account using your email address.
2. Wait for account activation by a CSR or Administrator.
3. Log in to the app once your account is activated.
4. Browse products, add items to your cart, and place orders.
5. Track your order status and view your order history.
6. Rate and comment on vendors based on your purchase experience.

## Contributing

This project is part of a group assignment and is not open for external contributions at this time.

## License

This project is for educational purposes only and is not licensed for commercial use.
| Screenshot 1 | Screenshot 2 | Screenshot 3 |
|--------------|--------------|--------------|
| ![Screenshot_20241008-220547](https://github.com/user-attachments/assets/5284ddcb-20db-4c8d-8942-3da95ca9bd79) | ![Screenshot_20241008-220540](https://github.com/user-attachments/assets/434fac11-c0ce-4cf1-a387-1a61dd879ec6) | ![Screenshot_20241008-220442](https://github.com/user-attachments/assets/b94e463b-5cce-4203-a072-bf092b75ad89) |
| Screenshot 4 | Screenshot 5 | Screenshot 6 |
| ![Screenshot_20241008-221031](https://github.com/user-attachments/assets/a2fe8c46-e4b4-4cbf-b98b-0579a9015810) | ![Screenshot_20241008-220622](https://github.com/user-attachments/assets/9f0cea17-50ab-4fdc-ab9e-3dab51207cf6) | ![Screenshot_20241008-220631](https://github.com/user-attachments/assets/db07ebe6-0fad-462c-a59b-83b7f710f8a1) |
| Screenshot 7 | Screenshot 8 | Screenshot 9 |
| ![Screenshot_20241008-220608](https://github.com/user-attachments/assets/a47009c6-7799-44c1-a85b-c0f1ee6d3081) | ![Screenshot_20241008-220638](https://github.com/user-attachments/assets/b7d60edc-ece0-4408-ab54-8f6fd9a2e949) | ![Screenshot_20241008-220644](https://github.com/user-attachments/assets/e70a3d33-10ac-4718-8662-20e2c2de538b) |
| Screenshot 10 | Screenshot 11 | Screenshot 12 |
| ![Screenshot_20241008-220659](https://github.com/user-attachments/assets/5195d420-987a-4004-8353-a7073138c520) | ![Screenshot_20241008-220711](https://github.com/user-attachments/assets/7a268bce-ff34-447b-b6f7-d01a701fb17a) | ![Screenshot_20241008-221025](https://github.com/user-attachments/assets/139c77d8-4040-403a-9fe2-f62ad67750de) |
| Screenshot 13 | Screenshot 14 | Screenshot 15 |
| ![Screenshot_20241008-221013](https://github.com/user-attachments/assets/d42e5083-331e-463a-8d2c-1abbc895a6a7) | ![Screenshot_20241008-221002](https://github.com/user-attachments/assets/3954d02a-bc04-4212-b251-5d54ac995c57) | ![Screenshot_20241008-220723](https://github.com/user-attachments/assets/0c0e82ce-fbf3-4dd4-a3c9-5f6f7ba574d2) |














