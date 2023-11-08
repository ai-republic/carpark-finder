### Singapore carpark finder application
This is a demo backend application presenting one API endpoint to query the nearest available carpark.
The application will search within a radius of 100km of the specified `latitude` and `longitude` coordinates.
The result is paged with specified paging parameters `page` and `per_page`.

The request will look like this:
[`http://localhost:8080/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10`](http://localhost:8080/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10).

You can view the API endpoint documentation using the Swagger link: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html).


## How to use
1. Clone this Git repository and import the project to your favorite IDE
2. Ensure Docker is running
3. Run `CarparkInformationImport` CSV import
4. IMPORTANT: Add missing carpark info dummy entries by executing `import_missing.sql` script in your SQL editor - since the datasources are not in sync
5. Run `CarparkAvailablityImport` API endpoint import
6. Start `CarparkFinderApplication` and click on the above example link and feel free to modify the parameters


## Relevant information
The application is running locally on port 8080.
MySQL is running as docker image on port 3306, locally on port 3307.