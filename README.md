# Warehouse Management System
A Spring Boot application for managing articles, products, and orders in a warehouse setting. This project demonstrates core functionalities such as importing articles and products from JSON files, listing available products, and processing orders based on stock availability.

Assumptions:

Data Source: The application relies on the data provided in articles.json and products.json for managing articles and products, respectively.
Product Availability: A product can be added to an order only if the required quantities of all its constituent articles are available in stock.
Order Processing: An order must contain all the required items with available stock. If any item in the order is out of stock, the order cannot be completed.
Data Integrity: The JSON files are assumed to be well-formed and follow the specified structure as provided in the example.

Funtionalities Included:

Import Articles: Import articles from articles.json.
Import Products: Import products from products.json.
Add or Update Article: Add a new article or update the stock of existing article
Add or Update Product: Add a new product if required articles are present or update the stock of product.
List Products: Retrieve a list of all products and their available quantities based on the current stock of products.
Process Order: Validate if a transaction is possible given the current stock state and update the stock accordingly.

Future Improvements:

Database Integration: While the current implementation uses in-memory storage, integrating a database would provide persistent storage and allow for scalability.
Error Handling: Implement robust error handling to manage exceptions and edge cases gracefully.
Validation: Add input validation to ensure data integrity and prevent invalid or malicious input.
Logging: Implement logging to track application events and facilitate debugging and monitoring.
API Documentation: Document the REST API endpoints using tools like Swagger for better understanding and testing.
UI: Develop a simple frontend interface to interact with the warehouse management system.
Concurrency Handling: Implement concurrency control mechanisms to handle simultaneous access and updates to the stock.
