package com.airepublic.wego.exercise.carparkinfo.importer;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityEntity;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityRepository;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkInfoEntity;
import com.airepublic.wego.exercise.carparkinfo.util.CarparkDataJsonMapping;
import com.airepublic.wego.exercise.carparkinfo.util.CarparkInfoJsonMapping;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Imports the available carparks to the configured database {@link CarparkAvailabilityEntity}
 * entities.
 */
// @SpringBootApplication
// @EnableJpaRepositories("com.airepublic.wego.exercise")
// @EntityScan("com.airepublic.wego.exercise.*")
// @Order(2)
public class CarparkAvailabilityImport {
    private final static Logger LOG = LoggerFactory.getLogger(CarparkAvailabilityImport.class);
    @Value("${carpark-availability.endpoint}")
    private String apiURL;
    @Autowired
    private CarparkAvailabilityRepository repo;

    /**
     * Reads the JSON from the API URL and converts them to {@link CarparkAvailabilityEntity}
     * entities.
     *
     * @throws Exception if parsing failed
     */
    public void importCarparkAvailibility() throws Exception {
        // create the singaporean date time for the API parameter in the URL
        final DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_DATE_TIME).toFormatter();
        final LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.ofHours(8));
        final String uri = apiURL + "?date_time=" + dateTime.format(formatter);
        LOG.debug("API endpoint URL for carpar information: {}", uri);

        // create JSON parser to parse stream from the URL
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonParser parser = objectMapper.getFactory().createParser(new URL(uri));
        JsonToken value = parser.nextToken();
        final List<CarparkAvailabilityEntity> carparks = new ArrayList<>();

        // check for valid json start token
        if (value != JsonToken.START_OBJECT) {
            LOG.error("Error parsing json. The root should be an object token!");
            return;
        }

        LOG.debug("Parsing JSON for carpark availability...");

        // iterate until the node carpark_data is found and read capark_data objects from there
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            final String fieldName = parser.getCurrentName();
            // get value of field
            value = parser.nextToken();

            // check if car_park data node is found
            if (fieldName != null && fieldName.equals("carpark_data")) {
                LOG.debug("Found carpark_data field. Reading object tree now.");
                // parse the values as DTO objects
                final Iterator<CarparkDataJsonMapping> iter = parser.readValuesAs(CarparkDataJsonMapping.class);

                // convert them to carpark available entities
                while (iter.hasNext()) {
                    final CarparkDataJsonMapping data = iter.next();

                    for (final CarparkInfoJsonMapping info : data.carparkInfo) {
                        final CarparkAvailabilityEntity carpark = new CarparkAvailabilityEntity();
                        carpark.setTotalLots(Integer.parseInt(info.totalLots));
                        carpark.setAvailableLots(Integer.parseInt(info.availableLots));
                        carpark.setCarparkNumber(data.carparkNumber);
                        carpark.setCarparkInfo(new CarparkInfoEntity(data.carparkNumber));
                        carparks.add(carpark);
                    }
                }

                parser.skipChildren();
            }
        }

        LOG.info("Persisting carpark availability...");
        repo.saveAllAndFlush(carparks);
        LOG.info("Persisted carpark availability...successfully");
    }


    public void run(final String... args) throws Exception {
        LOG.info("Starting import of carpark availability...");
        importCarparkAvailibility();
    }


    /**
     * Main method - no args.
     *
     * @param args no arguments need to be specified
     */
    public static void main(final String[] args) {
        SpringApplication.run(CarparkAvailabilityImport.class, args);
    }
}
