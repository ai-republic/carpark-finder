package com.airepublic.wego.exercise.carparkinfo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityDTO;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * The API controller for providing the nearest carpark availability information.
 */
@RestController
public class CarparkController {
    private final static Logger LOG = LoggerFactory.getLogger(CarparkController.class);
    @Autowired
    private CarparkService carparkService;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Gets the nearest available carpark based on latitude and longitude.
     *
     * @return the nearest available carpark based on latitude and longitude
     */
    @GetMapping(path = "/carparks/nearest", params = { "latitude", "longitude", "page", "per_page" }, produces = "application/json")
    @Parameter(name = "latitude", description = "The latitude for the search")
    @Parameter(name = "longitude", description = "The longitude for the search")
    @Parameter(name = "page", description = "The page to be displayed")
    @Parameter(name = "per_page", description = "The number of results to be displayed on the page")
    @Operation(description = "Endpoint to query available carparks in Singapore")
    @ApiResponse(responseCode = "200", description = "If the request was successful", content = @Content(examples = { @ExampleObject("[\r\n"
            + "{\r\n"
            + "\"address\": \"BLK 401-413, 460-463 HOUGANG AVENUE 10\",\r\n"
            + "\"latitude\": 1.37429,\r\n"
            + "\"longitude\": 103.896,\r\n"
            + "\"total_lots\": 693,\r\n"
            + "\"available_lots\": 182\r\n"
            + "},\r\n"
            + "{\r\n"
            + "\"address\": \"BLK 351-357 HOUGANG AVENUE 7\",\r\n"
            + "\"latitude\": 1.37234,\r\n"
            + "\"longitude\": 103.899,\r\n"
            + "\"total_lots\": 249,\r\n"
            + "\"available_lots\": 143\r\n"
            + "},\r\n"
            + "{\r\n"
            + "\"address\": \"BLK 364 / 365 UPP SERANGOON RD\",\r\n"
            + "\"latitude\": 1.37011,\r\n"
            + "\"longitude\": 103.897,\r\n"
            + "\"total_lots\": 471,\r\n"
            + "\"available_lots\": 324\r\n"
            + "}\r\n"
            + "]") }))
    @ApiResponse(responseCode = "400", description = "If the request was not successful")
    public ResponseEntity<String> getNearestCarpark(@RequestParam("latitude") final String latitude, @RequestParam("longitude") final String longitude, @RequestParam("page") final String page, @RequestParam("per_page") final String length) {
        ResponseEntity<String> response = null;

        try {
            LOG.debug("Received nearest carpark request");

            // get available carparks from the service
            final List<CarparkAvailabilityDTO> carparks = carparkService.getAvailableCarparks(Double.parseDouble(latitude), Double.parseDouble(longitude), Integer.parseInt(page), Integer.parseInt(length));

            LOG.debug("Found nearest carpark result from service: {}", carparks);

            if (carparks != null && !carparks.isEmpty()) {
                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                response = new ResponseEntity<>(mapper.writeValueAsString(carparks), headers, HttpStatus.OK);
            }
        } catch (final Exception e) {
            LOG.error("Request failed: ", e);
        }

        if (response == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
