![wScreenshot 2023-07-27 165944](https://github.com/ganeshbagav7/ccpl/assets/72212389/e6c19fa9-da53-4cc7-817e-c209884dca91)

This repository contains a Zomato Clone application built using React for the frontend and Spring Boot for the backend. The application aims to replicate the popular food delivery and restaurant discovery platform, Zomato.

## Demonstration Video

[![Demonstration Video](https://github.com/ganeshbagav7/ccpl/assets/72212389/240d2737-5b7e-4bc4-a31b-1a4668c36350)](https://youtu.be/mBqUNr6FVvA)

## Technologies Used

- Java
- Spring Boot
- MySQL
- React

## Features

The Zomato Clone application comes with the following features:

- User Registration and Authentication: Users can register and log in to the platform to access personalized features.
- Restaurant Listing: The application allows users to browse through a list of restaurants available in the database.
- Search and Filters: Users can search for specific restaurants or use filters to find restaurants based on location, cuisine, ratings, etc.
- Restaurant Details: Users can view detailed information about a specific restaurant, including menu, reviews, and ratings.
- Ordering Food: Users can place orders from the restaurant of their choice and add items to their cart.
- Cart Management: Users can manage the items in their cart, add or remove items, and proceed to checkout.
- Reviews and Ratings: Users can leave reviews and ratings for restaurants they have visited.
- User Profile: Users have access to their profile, where they can view and edit their personal information.
- Admin Panel: An admin panel is available for managing restaurants, users, and orders.

## Setup Instructions

To run the Zomato Clone application on your local machine, we going to set up the environment for spring boot for backend and react for the frontend:

### Prerequisites

Before proceeding, ensure you have the following installed on your machine:

- Node.js and npm (for running React frontend)
- Java Development Kit (JDK)
- MySQL Server

### Backend Setup

1. Open MySQL Workbench or MySQL shell, create a schema with the name 'zomato'
   
2. Open Project in any IDE (Preferred: Visual Studio Code )

3. Install [Spring Boot Extensions Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)

4. Open zomato-clone\Backend\src\main\resources\application.properties
5. Configure the MySQL connection properties:
   Replace the following placeholders with your MySQL database connection details:

```properties
spring.datasource.url=jdbc:mysql://your-mysql-host:your-mysql-port/zomato
spring.datasource.username=your-mysql-username
spring.datasource.password=your-mysql-password
```
Make sure to replace your-mysql-host, your-mysql-port, your-mysql-username, and your-mysql-password with the appropriate values for your MySQL setup.

   Save the application.properties file after making the changes.

5. Run the Spring Boot application.

### Frontend Setup

1.  Open Zomato ProjectName's folder with Code Editor (Preferred: Visual Studio Code).

2.  Open the terminal and in the terminal navigate to the 'zomato-clone\Frontend' folder
  
3.  Install the required dependencies using npm:

   ```
   npm install
   ```

5.  Start the React development server:

   ```
   npm start
   ```

6. The application will be accessible at `http://localhost:3000` in your browser.

## Contribution

Contributions to the Zomato Clone project are welcome! If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request.


   **Note**: Be cautious when using the `update` option in production, as it may lead to data loss or schema changes.

## Contributing

We welcome contributions to improve the Zomato Clone application. Feel free to submit a pull request if you find any issues or want to add new features.

Happy coding! 🚀