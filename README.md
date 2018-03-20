# SalesDiary2

             Algorithms  for verifying the username

Aim: To ensure that a username is signed up once.

                       Algorithms 

1.	Obtain the user name specified by user at sign up
2.	Compare this user name u1 with with a query list of available user names ut 
a.	If u1 matches any of the names from ut
Prompt user to specify another distinct name.
b.	Else :
Sign up the user name u1.
Step-wise refinement of the Algorithm above
1.	Get the specified username and store in String variable u1
2.	Create an empty ArrayList called ut
3.	Use the database cursor to select all available user names
4.	Within a loop populate all username into ut
5.	Within another loop compare u1 with each item of ut
6.	If u1 matches any item in ut: prompt the user to specify another unique username
7.	Else: sign up the user (store the user name in the database.

                  Algorithms for New Menu Item 
Overview:
The “New” menu Item serves as an interface connecting the “productCatalogView” to “createProductCatalog”. When this menu Item is clicked, the “createProductCatalog” page is loaded. And from there the user inserts or stores a new product and its details to the database of interest.
The relationship between the “New” menu Item and the “createProductCatalog” is established through a costumed dialog box which provides User Interface for inputting product information into the database. 

                   Steps
1. The user clicks the “New” menu Item
2. On clicking the “New” menu Item, establish a dialog to facilitate the input of new products and its details.
3. The user provides the necessary details required to add a new product and its details to the database. 

                   Step-Wise Refinement
1.	Create a void method called “addProductDetail” with String parameter: “tableName” serving as the database table name of a          particular user.
2.	Create an xml file serving as a layout for the dialog box.
3.	Create the dialog box within this method.
4.	Set a dialog box title and associate the dialog box with the xml file.
5.	Find all views of the xml file by their ids.
6.	Set event listeners for “capture”, “Add” and “close” buttons. The capture button performs two major functions:
a.	Capturing the product’s image and obtaining the product’s image path immediately. 
b.	While the close button is used to dismiss the dialog. Although, the dialog could be dismissed by touching the portions outside its focus.
7.	Ensure that the dialog box is visible.
8.	Ensure that the “addProductDetail” method is called within the menu choice method.

                  Algorithms  for Edit Menu Item

Overview:
The Edit Menu Item serves as a dialog through which the User updates the product details within the product Catalog database.
When the user long clicks an item within the List of products in the product catalog, a dialog box pops out. The dialog consists of label, input fields and buttons. When this dialog box is loaded, every related details to the product of interest is specified automatically on its corresponding fields.

The user edits the field of interest and clicks on the “edit” button, and real-time updates are made to the database.

                    STEPS
1.	Pop out a menu when the user long clicks an item on the list of available products
2.	Show an edit dialog, when the user clicks the “edit” Item.
3.	Load all details concerning the selected product in the edit dialog.
4.	Update the product catalog database as soon as the user clicks the edit button.

                  STEP-WISE REFINEMENT
1.	Create the Custom dialog box
2.	Set a title for this dialog box
3.	Use the dialog box to obtain product details to be edited from the database. Note: the product id will be used to obtain the product details of interest.
4.	Set an event listener for the dialog box “edit” button
5.	Within the “edit” button’s event listener inner class, update the database with changes made by the user.

               In other words:
1.	Create a layout (xml) file for the edit dialog box
2.	Instantiate a dialog object within the “editProductDetail” method. Note: this method uses two parameter:   
a.	position (integer) 
b.	tableName (String).
3.	Set title and associate the layout xml file with the dialog object.
4.	Find all views (text views, edit text and buttons) through their ids.
5.	Create an sqlite database helper object
6.	Query the database for all products and their details.
7.	Use the sqlite database helper object to execute the query, and return result set to the database cursor.
8.	Move the cursor the position of the product of choice,  and fetch its details on the dialog box
9.	Set these details on corresponding input fields of the dialog
10.	Get the edited details 
11.	Update   these new details to the database at real-time. Note: if the user clicks edit without changing the fetched details no physical change will be made, as the database will be re-updated with the same details fetched previously.

                Algorithms for Delete Item 
