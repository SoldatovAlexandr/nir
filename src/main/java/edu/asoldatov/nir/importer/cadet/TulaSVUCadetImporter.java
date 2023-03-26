package edu.asoldatov.nir.importer.cadet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class TulaSVUCadetImporter extends DefaultCadetImporter {

    @Override
    protected Integer getLevelOfHygiene(Sheet sheet) {
        Cell cell = sheet.getRow(59).getCell(55);
        if (cell == null) {
            cell = sheet.getRow(58).getCell(59);
        }
        return Double.valueOf(cell.getNumericCellValue()).intValue();
    }

    @Override
    protected int getCount(Sheet sheet, int row, int cell) {
        if (sheet.getRow(row).getCell(cell) == null) {
            return super.getCount(sheet, row + 2, cell);
        }
        return super.getCount(sheet, row, cell);
    }
}
