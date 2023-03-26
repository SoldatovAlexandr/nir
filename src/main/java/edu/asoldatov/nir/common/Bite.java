package edu.asoldatov.nir.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Bite {

    ORTHOGNATHIC("Ортогнатический", 76, 31),
    DEEP("Глубокий", 76, 39),
    DISTAL("Дистальный", 76, 49),
    MESIAL("Мезиальный", 76, 59),
    CROSS("Перекрестный", 78, 31),
    STRAIGHT("Прямой", 78, 39),
    OPEN("Открытый", 78, 49),
    TORTOANOMALY("Тортоаномалия", 78, 59),
    CROWDING("Скученность", 80, 31),
    DIASTIMA("Диастема/Тремы", 80, 43),
    NONE("-", 0, 0);

    private final String label;
    private final int row;
    private final int column;

    public static Bite getBite(final Sheet sheet) {
        return Arrays.stream(values())
                .filter(v -> v != NONE)
                .filter(v -> isNotEmpty(sheet.getRow(v.row).getCell(v.column)))
                .findFirst()
                .orElse(NONE);
    }

    private static boolean isNotEmpty(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue() != 0.0;
        } else if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.FORMULA) {
            return !cell.getStringCellValue().isBlank() && !cell.getStringCellValue().equals("0");
        }
        return false;
    }
}
