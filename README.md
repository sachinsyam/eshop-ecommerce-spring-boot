# eshop-ecommerce-spring-boot

This is an eCommerce app built on Spring Boot 2.7.11, Thymeleaf, and MySQL. It provides various features for an online store.
You can find the screenshots of the app [here](https://postimg.cc/gallery/W0wRwsJ).

## Features

- Authentication is handled using Spring Security with username and password.
- Role-based authentication with method-level access control.
- User registration with phone number OTP verification and password reset using email.
- Product listing with pagination and search functionality.
- Support for product variants.
- Offer price for products.
- Coupons (category and product-wise).
- Banners with links to products.
- Wishlist functionality.
- Shopping cart functionality.
- Orders and order details.
- Wallet and wallet transaction details.
- Rating and reviews for products.
- Checkout with Cash on Delivery (COD) or Online Payment (Razorpay integrated).
- Ability to download invoices as PDF.

## Admin Dashboard

The app also includes an admin dashboard with the following features:

- View statistics of orders, including total revenue, profit, and order trends through graphs.
- Category-wise products sold.
- Block user functionality.
- Unlist products.
- Crop images and upload them for products.
- Add variants for products.
- Credit/Debit from user wallet.
- Coupon management.
- Banner management.
- Order management.
- Audit view to see details of users who visited the website. Location and ISP details are obtained using a plugin.
- Generate various reports in PDF and CSV formats.
- Scheduled tasks for maintenance.

## Integrations

The app integrates with the following services:

- Twilio for SMS OTP.
- Razorpay for the payment gateway.
- GeoIP to fetch IP details.
- html2pdf
- itextpdf

## Templates

The app uses templates for both the store (coza store) and the admin dashboard (sneat).

## Hosting

The app was hosted on a GCP VM.

## Setting up the Spring Boot App

1. Clone the repository:

   ```
   git clone https://github.com/sachinsyam/eshop-ecommerce-spring-boot.git
   ```

2. Open the project in your preferred IDE.

3. Create a database for the application. You can use any relational database management system (RDBMS) that is supported by Spring Boot, such as MySQL, PostgreSQL, or H2. Currently, I have used MySQL. If you wish to use another database, add the respective dependency in pom.xml

5. Provide the SQL credentials in the application configuration. 

   - Open the `src/main/resources/application-prod.properties` file.
   - Update the following properties with your database credentials:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/<database_name>
     spring.datasource.username=<username>
     spring.datasource.password=<password>
     ```

     Make sure to replace `<database_name>`, `<username>`, and `<password>` with your actual database details.

6. Build and run the application using Maven:

   ```
   mvn clean install spring-boot:run -Dspring.profiles.active=prod

   ```

   This will build the application, resolve dependencies, and start the Spring Boot app locally.

7. Access the application by navigating to `http://localhost:8080` in your web browser.

8. Follow the provided instructions or refer to the [API documentation](https://documenter.getpostman.com/view/1164234/2s93ecvVYj) to explore and interact with the eCommerce app.
