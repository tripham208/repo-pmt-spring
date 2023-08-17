package vn.id.pmt.spring.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;


public class CSVUtil {
    public static String TYPE = "text/csv";

    /**
     * Return {@code true} if file upload is csv
     *
     * @param file The file upload
     * @return {@code true} if file upload is csv
     */
    public static boolean isCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }


    /**
     * Csv to list.
     *
     * @param <T>         the type parameter
     * @param is          the InputStream
     * @param targetClass the target class
     * @return the list of target class
     */
    public static <T> List<T> csvToList(InputStream is, Class<T> targetClass) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().build())) {
            final ObjectMapper mapper = new ObjectMapper();

            Optional<CSVRecord> recordHeader = csvParser.stream().findFirst();
            List<CSVRecord> records = csvParser.getRecords();
            List<T> result;

            result = recordHeader.map(strings -> records.stream().map(
                    record -> convertCSVRecordToObj(record, strings, mapper, targetClass)
            ).collect(Collectors.toList())).orElseGet(ArrayList::new);

            return result;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * Convert csv record to target class
     *
     * @param <T>         the type parameter
     * @param record      the record
     * @param header      the header
     * @param mapper      the mapper
     * @param targetClass the target class
     * @return object of target class
     */
    public static <T> T convertCSVRecordToObj(CSVRecord record, CSVRecord header, ObjectMapper mapper, Class<T> targetClass) {
        int index = 0;
        System.out.println(record.toString());
        Map<String, String> map = new HashMap<>();
        for (String field : header.values()) {
            map.put(field, record.get(index));
            index++;
        }
        return mapper.convertValue(map, targetClass);
    }


    /**
     * Export csv byte array input stream.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the byte array input stream
     */
    public static <T> ByteArrayInputStream exportCSV(List<T> data) {
        final CSVFormat format = CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL).build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {


            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat())
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                    ;
            //Ref: https://www.baeldung.com/jackson-serialize-dates
            Map<String, Object> map = objectMapper.convertValue(data.get(0), new TypeReference<>() {});
            csvPrinter.printRecord(map.keySet());

            for (T item : data) {
                map = objectMapper.convertValue(item, new TypeReference<>() {});
                System.out.println(map);
                csvPrinter.printRecord(map.values());
            }
            csvPrinter.flush();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}