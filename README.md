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
3. Run `CarparkInfoImport` CSV import
4. Add missing carpark info dummy entries by executing `import_missing.sql` script
5. Run `CarparkAvailablityImport` API endpoint import
6. Start `CarparkFinderApplication` and click on the above example link