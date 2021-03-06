package org.kok202.dluid.ai.singleton.structure;

import lombok.Data;
import org.kok202.dluid.domain.stream.NumericRecordSet;

@Data
public class ManagedRecordSet {
    private String filePath;
    private boolean hasHeader;
    private int recordSize;
    private NumericRecordSet numericRecordSet;

    private CsvManagedData csvManagedData;
    private ExcelManagedData excelManagedData;

    public ManagedRecordSet() {
        csvManagedData = new CsvManagedData();
        excelManagedData = new ExcelManagedData();
    }
}
