package com.airepublic.wego.exercise.carparkinfo.importer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.airepublic.wego.exercise.carparkinfo.service.CarparkInfoEntity;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkInfoRepository;
import com.airepublic.wego.exercise.carparkinfo.util.LatLonCoordinate;
import com.airepublic.wego.exercise.carparkinfo.util.SVY21;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import com.opencsv.exceptions.CsvValidationException;

//@SpringBootApplication
//@EnableJpaRepositories("com.airepublic.wego.exercise")
//@EntityScan("com.airepublic.wego.exercise.*")
//@Order(1)
@Service
public class CarparkInformationImport {
    private final static Logger LOG = LoggerFactory.getLogger(CarparkInformationImport.class);
    @Autowired
    private CarparkInfoRepository repository;

    /**
     * Import the CSV for the specified filename containing the carpark information.
     *
     * @param filename the CSV filename
     * @throws FileNotFoundException if the file could not be found
     */
    public void importCSV(final String filename) throws CsvValidationException, IOException {
        LOG.debug("CSV import file for carpark information: {}", filename);
        final List<CarparkInfoEntity> carparkInfos = new ArrayList<>();
        final CSVReader c = new CSVReaderHeaderAwareBuilder(new FileReader(filename))
                .withCSVParser(new CSVParserBuilder()
                        .withQuoteChar('\"')
                        .withSeparator(',')
                        .build())
                .build();

        String[] fields = c.readNext();
        LOG.debug("Parsing CSV file for carpark information...");

        while (fields != null) {
            // map first 4 colums to the carpark info entity
            final CarparkInfoEntity carparkInfo = new CarparkInfoEntity();
            carparkInfo.setCarparkNumber(fields[0]);
            carparkInfo.setAddress(fields[1]);
            final LatLonCoordinate latLon = SVY21.computeLatLon(Double.parseDouble(fields[2]), Double.parseDouble(fields[3]));
            carparkInfo.setLatitude(latLon.getLatitude());
            carparkInfo.setLongitude(latLon.getLongitude());
            carparkInfos.add(carparkInfo);

            fields = c.readNext();
        }

        LOG.info("Persisting carpark information...");
        repository.saveAllAndFlush(carparkInfos);
        LOG.info("Persisted carpark information...successfully");
    }


    public void run(final String... args) throws Exception {
        LOG.info("Starting CSV import of carpark information...");
        importCSV("HDBCarparkInformation.csv");
    }


    /**
     * Main method - no args.
     *
     * @param args no arguments need to be specified
     */
    public static void main(final String[] args) {
        SpringApplication.run(CarparkInformationImport.class, args);
    }
}
