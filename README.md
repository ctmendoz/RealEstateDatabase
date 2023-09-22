<b>I. SUMMARY:</b>
- This was an assignment for my Data Models and Database Systems, class. It demonstrated knowledge of Java, MySQL, Excel, and JDBC.
- The program connects to a local MySQL database created with the "TableSetup.sql" file and gives the user the choice to either [1] insert the data from the CSV files included in this repository or [2] search through the database based on 6 different search criteria (name of real estate agent, price of the house, etc.).
- The dataInsert function uses PreparedStatements and CSV files in order to update the database. Whatever was currently in the database becomes deleted and the function goes through each line of the CSV files so that any new data can be submitted. The CSV files can be updated since they're in the same repository as the Java file. Be careful not remove any tuples by not including them in the respective CSV file.
  - Splitting the dataInsert function into a dataAdd and dataDelete function would admittedly be more ideal and potentially more secure for the database. This is a potential change that may be implemented in a future version of this program.
- The searchHome function makes use of Statement and ResultSet objects as well as MySQL queries to return results to the user. The results from the search function can be public information about a seller, agent, or house; private information such as social security numbers remain hidden from the user.

<b>II. HOW TO RUN:</b>
- In order to run this program, I had to use a Java IDE-- Eclipse specifically-- so that Java and MySQL can connect with each other via setting up a path to the "mysql-connector-java-8.0.28.jar" file. Running it without an IDE is likely possible, but using this README file as a guide would mean that you would have to use a Java IDE.
- Start a new project in the IDE using everything in this repository.
- Set up the path to the .jar file
- Start up a local MySQL database with your own choice of root username and password
- Edit lines 54 and 227 so that they include your root username and password
- Use the "TableSetup.sql" file to create the tables necessary for this database
- Change your CSV files to include whatever data entries you'd like. Make sure that they follow the appropriate formats per tables.
- Run the Java file
