package com.clm.nace.clmnacedataanalytics.utility;

import com.clm.nace.clmnacedataanalytics.model.NaceData;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Data
public class FileUtility {

    public static List<NaceData> processNaceCSV(InputStream inputStream) {
        try (Reader inputReader = new InputStreamReader(inputStream, "UTF-8")) {
            CSVReader reader = new CSVReader(inputReader);
            reader.readNext();
            CsvToBean csvToBean = new CsvToBean();
            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(NaceData.class);
            String[] columns = new String[]{"order", "level", "code", "parent", "description", "itemIncludeA", "itemIncludeB", "rulings", "itemExludes", "reference"};
            mappingStrategy.setColumnMapping(columns);
            return csvToBean.parse(mappingStrategy, reader);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
