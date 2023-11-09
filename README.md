# Singapore carpark finder application
This is a demo backend application presenting one API endpoint to query the nearest available carpark.
The application will search within a radius of 100km of the specified `latitude` and `longitude` coordinates.
The result is paged with specified paging parameters `page` and `per_page`.

The request will look like this:
[`http://localhost:8080/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10`](http://localhost:8080/carparks/nearest?latitude=1.274387933662743&longitude=103.3&page=1&per_page=10).

You can view the API endpoint documentation using the Swagger link: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html).

## Relevant information
The application is running locally on port 8080.
MySQL is running as docker image on port 3306, locally on port 3307.

### Handicaps
Due to carpark information CSV not containing all carparks that are retrieved via the availability endpoint I had to add 7 dummy entries (Northpole) to the CSV.

## How to use
1. Clone this Git repository and import the project to your favorite IDE
2. Ensure Docker is running
3. Start `CarparkFinderApplication` and click on the above example link and feel free to modify the parameters
