package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import jxl.read.biff.BiffException;
import model.Line;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by Jianhua Sun on 2018/9/4.
 */
public class ExcelReader {
    private String fullFileName;
    private String sheetName;

    public ExcelReader(String fileName, String sheetName) {
        this.fullFileName = fileName;
        this.sheetName = sheetName;
    }

    public Object[][] readTestData() {
        List<Line> lines = readData();
        int size = lines.size();
        Object[][] testData = new Object[size][2];
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            testData[i][0] = line.getLineNo();
            testData[i][1] = line.getData();
        }
        return testData;
    }

    public List<Line> readData() {
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        Sheet sheet;
        List<Line> lines = new ArrayList<Line>();
        try {
            fileInputStream = new FileInputStream(fullFileName);
            workbook = WorkbookFactory.create(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();
            boolean skipColumnExists = headerRow.getCell(0).getStringCellValue().equalsIgnoreCase("skip");
            for (int i = 1; i <= rowCount; i++) {
                Row currentRow = sheet.getRow(i);
                Cell skip = skipColumnExists ? currentRow.getCell(0) : null;
                if (skip == null || !skip.getBooleanCellValue()) {
                    Map<String, String> data = new HashMap<String, String>();
                    for (int j = 1; j < colCount; j++) {
                        String key = headerRow.getCell(j).getStringCellValue().trim();
                        Cell cell = currentRow.getCell(j);
                        if (cell == null || cell.getStringCellValue().isEmpty())
                            continue;
                        String value = cell.getStringCellValue().trim();
                        data.put(key, value);
                    }
                    lines.add(new Line(i + 1, Collections.unmodifiableMap(data)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }
}



















