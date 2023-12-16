package edu.asoldatov.nir.importer.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Component
public class KdataImporterImpl implements KdataImporter {
/*
    private final KdataRepository repository;

    private final static String DELIMITER = ",";*/

    @Transactional
    @Override
    public void importInternal(String fileName) {
/*
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = split(line);
                Kdata kdata = Kdata.builder()
                        .build();

                repository.save(kdata);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Not found file [%s].", fileName));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Сan not import file [%s].", fileName), e);
        }*/

    }

   /* private String[] split(String line) {
        return Arrays.stream(line.split(DELIMITER))
                .map(String::trim)
                .toArray(String[]::new);
    }*/

}
