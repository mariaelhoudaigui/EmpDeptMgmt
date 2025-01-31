# Description

This project demonstrates the integration of JavaFX with Scene Builder for building a GUI, Hibernate for ORM (Object-Relational Mapping), and is developed using NetBeans IDE. The application is designed for managing employee and department data, with capabilities to interact with a database.

## **Technologies Used**
- **JavaFX:** A framework for building the graphical user interface (GUI).
- **Scene Builder:** A visual layout tool for designing JavaFX GUIs.
- **Hibernate ORM:** Used for object-relational mapping to interact with the database.
- **NetBeans:** The Integrated Development Environment (IDE) used to develop the project.
- **MySQL Database:** The database used for storing employee and department data.

## **Installation Instructions**

### **1. Prerequisites**
- **Java Development Kit (JDK):** Ensure that JDK 8 or later is installed on your machine.
- **NetBeans IDE:** Download and install the [NetBeans IDE](https://netbeans.apache.org/download/index.html). Make sure it includes support for Java, JavaFX, and Hibernate.
- **Scene Builder:** Download and install [Scene Builder](https://gluonhq.com/products/scene-builder/), a tool used for building JavaFX user interfaces visually.
- **MySQL Database:** Install MySQL or use an existing database for this project. Set up a database to store employee and department information.

### **2. Configure Database**
- Set up a MySQL database and create a schema to store employee and department data.
- Update the Hibernate configuration *(hibernate.cfg.xml)* with your database connection details (URL, username, password).

### **3. Set up Scene Builder**
- Open FxmlDocuments.fxml in Scene Builder to visually design the GUI.
- You can modify the layout, add UI components like buttons, text fields, and tables, and link them to the controller.

### **4. NetBeans Setup**
- Open the project in NetBeans IDE.
- Make sure to add the Hibernate libraries to the project

### **5. Using the Application**
- Employee Management: You can add, search and delete employee records from the GUI.
- Department Management: You can add, search and delete department records from the GUI.
- Real-Time Updates: The data will be updated in the database and reflected in the interface.

## **File Explanation**

### **A. Hibernate Files**
- **hibernate.cfg.xml**: Contains database connection details.
- **hibernate.reveng.xml**: Helps Hibernate auto-generate mappings from the database.
- **employe.hbn.xml** and **departement.hbn.xml**: Define how Employee and Department classes map to database tables.
- **hibernate-utile.java**: Provides helper functions for managing database sessions.
### **B. Java Classes**
- **Employe.java**: Represents an employee in the application.
- **Departement.java**: Represents a department in the application.
- **main.java**: Main class to run the application.
- **FxemlDocumentsController.java**: Controls the interaction between the user interface and the backend.
### **C. JavaFX Files**
- **FxmlDocuments.fxml**: FXML file defining the UI layout (buttons, text fields, tables) of the JavaFX application.

#

- *Note: You can use either **hibernate.reveng.xml** or **.hbn.xml** files for mapping; both serve the same purpose.*



