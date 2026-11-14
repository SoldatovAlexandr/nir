package edu.asoldatov.nir.importer.cadet;

import edu.asoldatov.nir.common.*;
import edu.asoldatov.nir.model.cadet.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultCadetImporter implements CadetImporter {

    private final static List<Integer> WRONG_TEETH_NUMBERS = List.of(87, 86, 76, 77, 67, 56, 66, 67);
    private final DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;

    @Override
    public Cadet importInternal(Sheet sheet) {
        return Cadet.builder()
                .fullName(extractString(sheet, 6, 1))
                .gender(getGender(sheet.getRow(6)))
                .birthday(getBirthday(sheet.getRow(6)))
                .age(getAge(sheet.getRow(6)))
                .ethnicGroup(EthnicGroup.getValue(extractString(sheet, 8, 1).toLowerCase().trim()))
                .placeOfBirth(extractString(sheet, 10, 1))
                .regionOfResidence(extractString(sheet, 12, 1))
                .preventiveActions(extractString(sheet, 15, 1))
                .preventiveActionsDetails(extractString(sheet, 8, 33))
                .healthGroup(getHealthGroup(sheet, 10, 33))
                .biteType(extractString(sheet, 12, 33))
                .vvk(extractString(sheet, 15, 33))
                .dentalAnomalies(extractString(sheet, 17, 33))
                .dentalCondition(getDentalCondition(sheet))
                .periodontalTissue(getPeriodontalTissue(sheet))
                .levelOfHygiene(getLevelOfHygiene(sheet))
                .enamelSpotting(getEnamelSpotting(sheet))
                .attachmentLosses(getAttachmentLoss(sheet))
                .hygieneIndices(getHygieneIndices(sheet))
                .emergencyHelp(getIntOrElseZero(sheet.getRow(97).getCell(57)))
                .temporomandibularJoint(getTemporomandibularJoint(sheet))
                .damageOralMucosa(getDamageOralMusoca(sheet))
                .bite(Bite.getBite(sheet))
                .toothErosion(getIntegerOrElseZero(sheet, 84, 3))
                .toothErosionCount(getCount(sheet, 100, 3))
                .dentalTrauma(getIntegerOrElseZero(sheet, 84, 13))
                .dentalTraumaCount(getCount(sheet, 100, 13))
                .build();
    }

    private List<HygieneIndex> getHygieneIndices(Sheet sheet) {
        return List.of(
                extractHygieneIndex(sheet.getRow(89).getCell(45), sheet.getRow(90).getCell(45)),
                extractHygieneIndex(sheet.getRow(89).getCell(47), sheet.getRow(90).getCell(47)),
                extractHygieneIndex(sheet.getRow(89).getCell(49), sheet.getRow(90).getCell(49)),
                extractHygieneIndex(sheet.getRow(93).getCell(49), sheet.getRow(92).getCell(45)),
                extractHygieneIndex(sheet.getRow(93).getCell(49), sheet.getRow(92).getCell(47)),
                extractHygieneIndex(sheet.getRow(93).getCell(49), sheet.getRow(92).getCell(49))
        );
    }

    private HygieneIndex extractHygieneIndex(Cell cellValue, Cell cellNumber) {
        int index;
        if (cellValue.getCellType() == CellType.STRING) {
            try {
                index = Double.valueOf(cellValue.getStringCellValue().replace(',', '.')).intValue();
            } catch (Exception e) {
                index = 0;
            }
        } else {
            index = Double.valueOf(cellValue.getNumericCellValue()).intValue();
        }
        return HygieneIndex.builder()
                .index(index)
                .number(Double.valueOf(cellNumber.getNumericCellValue()).intValue())
                .build();
    }

    private DamageOralMucosa getDamageOralMusoca(Sheet sheet) {
        return DamageOralMucosa.builder()
                .firstState(getValueOrElseMinusOne(sheet.getRow(85).getCell(23)))
                .firstLocalization(getValueOrElseMinusOne(sheet.getRow(85).getCell(33)))
                .secondState(getValueOrElseMinusOne(sheet.getRow(87).getCell(23)))
                .secondLocalization(getValueOrElseMinusOne(sheet.getRow(87).getCell(33)))
                .thirdState(getValueOrElseMinusOne(sheet.getRow(89).getCell(23)))
                .thirdLocalization(getValueOrElseMinusOne(sheet.getRow(89).getCell(33)))
                .build();
    }

    private int getValueOrElseMinusOne(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
        return -1;
    }

    private List<AttachmentLoss> getAttachmentLoss(Sheet sheet) {
        return List.of(
                extractAttachmentLoss(sheet.getRow(69).getCell(55), 17),
                extractAttachmentLoss(sheet.getRow(69).getCell(57), 11),
                extractAttachmentLoss(sheet.getRow(69).getCell(59), 27),
                extractAttachmentLoss(sheet.getRow(70).getCell(55), 47),
                extractAttachmentLoss(sheet.getRow(70).getCell(57), 31),
                extractAttachmentLoss(sheet.getRow(70).getCell(59), 37)
        );
    }

    private AttachmentLoss extractAttachmentLoss(Cell cell, int number) {
        return AttachmentLoss.builder()
                .value(getValueOrElseMinusOne(cell))
                .number(number)
                .build();
    }

    private int getHealthGroup(Sheet sheet, int r, int c) {
        Cell cell = sheet.getRow(r).getCell(c);
        if (cell.getCellType() == CellType.NUMERIC) {
            return extractInteger(sheet, r, c);
        }
        return HealthGroup.getValueByLabel(extractString(sheet, r, c));
    }

    private int getIntegerOrElseZero(Sheet sheet, int row, int cell) {
        try {
            return extractInteger(sheet, row, cell);
        } catch (IllegalStateException e) {
            System.out.println(extractString(sheet, row, cell));
            return 0;
        }
    }

    protected int getCount(Sheet sheet, int row, int cell) {
        String value = extractStringWithIntValue(sheet.getRow(row).getCell(cell)) + extractStringWithIntValue(sheet.getRow(row).getCell(cell + 2));
        if (value.isBlank()) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    private String extractStringWithIntValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
        }
        return "";
    }

    private TemporomandibularJoint getTemporomandibularJoint(Sheet sheet) {
        return TemporomandibularJoint.builder()
                .symptoms(getIntOrElseZero(sheet.getRow(76).getCell(7)))
                .clicking(getIntOrElseZero(sheet.getRow(79).getCell(5)))
                .soreness(getIntOrElseZero(sheet.getRow(79).getCell(19)))
                .limitationOfJawMovement(getIntOrElseZero(sheet.getRow(80).getCell(19)))
                .build();
    }

    private int getIntOrElseZero(Cell cell) { //прошу 0 потому что кто-то ставит галочки тут
        if (cell.getCellType() == CellType.NUMERIC) {
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
        return 0;
    }

    private List<EnamelSpotting> getEnamelSpotting(Sheet sheet) {
        List<EnamelSpotting> result = new ArrayList<>();
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(23), 34));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(25), 13));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(27), 12));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(29), 11));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(31), 21));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(33), 22));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(35), 23));
        result.add(extractEnamelSpotting(sheet.getRow(67).getCell(37), 24));
        result.add(extractEnamelSpotting(sheet.getRow(69).getCell(37), 36));
        result.add(extractEnamelSpotting(sheet.getRow(69).getCell(23), 46));
        return result;
    }

    private EnamelSpotting extractEnamelSpotting(Cell cell, int number) {
        return EnamelSpotting.builder()
                .value(Double.valueOf(cell.getNumericCellValue()).intValue())
                .number(number)
                .build();
    }

    protected Integer getLevelOfHygiene(Sheet sheet) {
        Cell cell = sheet.getRow(59).getCell(55);
        return Double.valueOf(cell.getNumericCellValue()).intValue();
    }

    private List<PeriodontalTissue> getPeriodontalTissue(Sheet sheet) {
        List<PeriodontalTissue> result = new ArrayList<>();

        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(3), 17)); //x=-1
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(5), 16));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(7), 15));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(9), 14));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(11), 13));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(13), 12));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(15), 11));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(17), 21));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(19), 22));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(21), 23));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(23), 24));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(25), 25));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(27), 26));
        result.add(extractPeriodontalTissue(sheet.getRow(59).getCell(29), 27));

        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(3), 47)); //x=-1
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(5), 46));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(7), 45));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(9), 44));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(11), 43));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(13), 42));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(15), 41));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(17), 31));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(19), 32));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(21), 33));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(23), 34));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(25), 35));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(27), 36));
        result.add(extractPeriodontalTissue(sheet.getRow(61).getCell(29), 37));

        return result;
    }

    private PeriodontalTissue extractPeriodontalTissue(Cell cell, int number) {
        int value;
        if (cell.getCellType() == CellType.NUMERIC) {
            value = Double.valueOf(cell.getNumericCellValue()).intValue();
        } else {
            value = -1;
        }
        return PeriodontalTissue.builder()
                .number(number)
                .value(value)
                .build();
    }

    private LocalDate getBirthday(Row row) {
        String value = "20" + getStringFromValue(row.getCell(37 + 8)) + getStringFromValue(row.getCell(37 + 10))
                + getStringFromValue(row.getCell(37 + 4)) + getStringFromValue(row.getCell(37 + 6))
                + getStringFromValue(row.getCell(37)) + getStringFromValue(row.getCell(37 + 2));
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            return LocalDate.parse("20000101", formatter);
        }
    }

    private Integer getAge(Row row) {
        return Integer.valueOf(getStringFromValue(row.getCell(55)) + getStringFromValue(row.getCell(57)));
    }

    private String getStringFromValue(Cell cell) {
        return String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
    }

    private Gender getGender(Row row) {
        if (row.getCell(29).getNumericCellValue() == 1.0) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }

    private String extractString(Sheet sheet, int row, int cell) {
        Cell c = sheet.getRow(row).getCell(cell);
        if (c.getCellType() == CellType.NUMERIC) {
            return String.valueOf(Double.valueOf(c.getNumericCellValue()).intValue());
        }
        return sheet.getRow(row).getCell(cell).getStringCellValue();
    }

    private Integer extractInteger(Sheet sheet, int row, int cell) {
        return Double.valueOf(sheet.getRow(row).getCell(cell).getNumericCellValue()).intValue();
    }


    private DentalCondition getDentalCondition(Sheet sheet) {
        List<YearDentalCondition> years = new ArrayList<>();
        years.add(getYearDentalCondition(sheet, 24, 52, "2017-2018"));
        years.add(getYearDentalCondition(sheet, 26, 50, "2016-2017"));
        years.add(getYearDentalCondition(sheet, 28, 48, "2015-2016"));
        years.add(getYearDentalCondition(sheet, 30, 46, "2014-2015"));
        years.add(getYearDentalCondition(sheet, 32, 44, "2013-2014"));
        years.add(getYearDentalCondition(sheet, 34, 42, "2012-2013"));
        years.add(getYearDentalCondition(sheet, 36, 40, "2011-2012"));
        return DentalCondition.builder()
                .years(years)
                .enamelFluorosis(getEnamelFluorosis(sheet))
                .build();
    }

    private int getEnamelFluorosis(Sheet sheet) {
        return Double.valueOf(sheet.getRow(46).getCell(49).getNumericCellValue()).intValue();
    }

    private YearDentalCondition getYearDentalCondition(Sheet sheet, int first, int second, String year) {
        List<Tooth> teeth = getFirstRowTeeth(sheet, first);
        teeth.addAll(getSecondRowTeeth(sheet, second));
        return YearDentalCondition.builder()
                .year(year)
                .teeth(teeth)
                .build();
    }

    private List<Tooth> getFirstRowTeeth(Sheet sheet, int rowIndex) {
        List<Tooth> teeth = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        teeth.add(getTooth(row.getCell(9), 17));
        teeth.add(getTooth(row.getCell(11), 16));
        teeth.add(getTooth(row.getCell(13), 15));
        teeth.add(getTooth(row.getCell(15), 14));
        teeth.add(getTooth(row.getCell(17), 13));
        teeth.add(getTooth(row.getCell(19), 12));
        teeth.add(getTooth(row.getCell(21), 11));
        teeth.add(getTooth(row.getCell(23), 21));
        teeth.add(getTooth(row.getCell(25), 22));
        teeth.add(getTooth(row.getCell(27), 23));
        teeth.add(getTooth(row.getCell(29), 24));
        teeth.add(getTooth(row.getCell(31), 25));
        teeth.add(getTooth(row.getCell(33), 26));
        teeth.add(getTooth(row.getCell(35), 27));
        return filterTeeth(teeth);
    }

    private List<Tooth> filterTeeth(List<Tooth> teeth) {
        if (teeth.stream().allMatch(v -> v.getValue() == 9)) {
            return new ArrayList<>();
        }
        return teeth.stream()
                .filter(v -> !WRONG_TEETH_NUMBERS.contains(v.getNumber()))
                .collect(Collectors.toList());
    }

    private List<Tooth> getSecondRowTeeth(Sheet sheet, int rowIndex) {
        List<Tooth> teeth = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        teeth.add(getTooth(row.getCell(9), 47));
        teeth.add(getTooth(row.getCell(11), 46));
        teeth.add(getTooth(row.getCell(13), 45));
        teeth.add(getTooth(row.getCell(15), 44));
        teeth.add(getTooth(row.getCell(17), 43));
        teeth.add(getTooth(row.getCell(19), 42));
        teeth.add(getTooth(row.getCell(21), 41));
        teeth.add(getTooth(row.getCell(23), 31));
        teeth.add(getTooth(row.getCell(25), 32));
        teeth.add(getTooth(row.getCell(27), 33));
        teeth.add(getTooth(row.getCell(29), 34));
        teeth.add(getTooth(row.getCell(31), 35));
        teeth.add(getTooth(row.getCell(33), 36));
        teeth.add(getTooth(row.getCell(35), 37));
        return filterTeeth(teeth);
    }

    private Tooth getTooth(Cell cell, int number) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return Tooth.builder()
                    .value(Double.valueOf(cell.getNumericCellValue()).intValue())
                    .number(number)
                    .build();
        }
        return Tooth.builder()
                .value(ToothValueType.getValueByLabel(cell.getStringCellValue()))
                .number(number + 40)
                .build();
    }
}
