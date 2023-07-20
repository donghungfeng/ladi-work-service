package com.example.ladiworkservice.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {
    private static final SimpleDateFormat excelFormat1 = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat excelFormat2 = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
    private static final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Object getCellValue(Cell cell) {
        if (cell == null) return cell;

        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) return cell.getDateCellValue();
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }

    public static Long getLongCell(Row row, Integer index) {
        return getDoubleCell(row, index).longValue();
    }

    public static Double getDoubleCell(Row row, Integer index) {
        return Double.parseDouble(getStringCell(row, index));
    }

    public static String getStringCell(Row row, Integer index) {
        Object value = ExcelUtils.getCellValue(row.getCell(index));
        return value != null ? String.valueOf(value).trim() : "";
    }

    public static Long convertDate(String dateExcel) throws ParseException {
        Date date = convertExcelDate(dateExcel);
        return Long.valueOf(dbFormat.format(date));
    }

    private static Date convertExcelDate(String dateExcel) throws ParseException {
        try {
            return excelFormat1.parse(dateExcel);
        } catch (Exception e) {
            return excelFormat2.parse(dateExcel);
        }
    }

    public static Long getDateCell(Row row, Integer index) throws ParseException {
        String date = getStringCell(row, index);
        return StringUtils.isEmpty(date) ? null : convertDate(getStringCell(row, index));
    }
}
