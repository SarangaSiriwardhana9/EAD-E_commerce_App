
# E-commerce Mobile App

This Android mobile application is part of an end-to-end E-commerce system, developed as a group project for the Enterprise Application Development course. The app serves as the customer-facing interface, allowing users to browse products, make purchases, and manage their accounts.

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
   git clone https://github.com/your-username/ecommerce-mobile-app.git
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
