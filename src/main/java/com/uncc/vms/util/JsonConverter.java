package com.uncc.vms.util;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sachin on 9/26/2015.
 */
public class JsonConverter {

    private static final String dateFormat = "yyyy-MM-dd";

    private JSONObject createJSONStructure(final File file) throws JSONException, IOException {
        final String xlsFiles[] = {"xls", "xlsx"};
        final String csvFile = "csv";

        if (FilenameUtils.isExtension(file.getAbsolutePath(), xlsFiles)) {
            return xlsToJSONConvertor(file);
        } else if (FilenameUtils.isExtension(file.getAbsolutePath(), csvFile)) {
            return csvToJSONConvertor(file);
        }
        throw new UnsupportedOperationException("Only xls, xlsx and csv files are supported");
    }

    private JSONObject csvToJSONConvertor(final File file) throws JSONException, IOException {

        CSVReader csvReader = new CSVReader(new FileReader(file));

        String keys[] = csvReader.readNext();
        String values[];

        JSONArray result = new JSONArray();
        while ((values = csvReader.readNext()) != null) {
            //System.out.println(Arrays.toString(tokens));
            //System.out.println(values.length);
            JSONObject obj = new JSONObject();

            for (int i = 0; i < keys.length; i++) {
//                obj.put(keys[i], values[i]);

                String key = keys[i];
                String val = values[i];
                if (key.contains("format")) {
                    String format = key.substring(key.indexOf(":") + 1, key.lastIndexOf(")")).trim();
                    String keyUpdated = key.substring(0, key.indexOf(" ")).trim();
                    System.out.println("keyUpdated = " + keyUpdated);
                    System.out.println("format = " + format);
                    try {
                        if (dateFormat.equalsIgnoreCase(format))
                            val = formatDate(val, dateFormat);
                    } catch (ParseException pe) {
                    }
                    obj.put(keyUpdated, val);
                } else if (val.contains(",")) {
                    String subValues[] = val.split(",");
                    for (int l = 0; l < subValues.length; l++) {
                        subValues[l] = subValues[l].trim();
                    }
                    JSONArray arr = new JSONArray(subValues);
                    obj.put(key, arr);
                } else {
                    obj.put(key, val);
                }
            }
            result.put(obj);
        }
        System.out.println(result.toString());
        return null;
    }

    private static JSONObject csvToJSONConvertor1(final File file) throws JSONException, IOException {
        BufferedReader csvFileReader = new BufferedReader(new FileReader(file));
        final String CSV_DELIMITER = ",";
        String keys[] = csvFileReader.readLine().split(CSV_DELIMITER);
        printArray(keys);

        JSONArray result = new JSONArray();
        String line = "";
        while ((line = csvFileReader.readLine()) != null) {
            String values[] = line.split(CSV_DELIMITER);
            JSONObject obj = new JSONObject();
            printArray(values);
            System.out.println("key size=" + keys.length);
            System.out.println("val size=" + values.length);
            for (int i = 0; i < keys.length; i++) {
                obj.put(keys[i], values[i]);
            }

            result.put(obj);
        }
        System.out.println(result.toJSONObject(result).toString());
        return null;
    }

    private static void printArray(String[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i] + "\t");
        }
        System.out.println();
    }

    private static JSONObject xlsToJSONConvertor(final File file) throws JSONException, IOException {

        FileInputStream fin = new FileInputStream(file);
        Workbook workbook = null;
        try {
            if (FilenameUtils.isExtension(file.getAbsolutePath(), "xls")) {
                System.out.println("creating workbook for xls file");
                workbook = new HSSFWorkbook(fin);
            } else if (FilenameUtils.isExtension(file.getAbsolutePath(), "xlsx")) {
                System.out.println("creating workbook for xlsx file");
                workbook = new XSSFWorkbook(fin);
            }

            int sheetCount = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator iterator = sheet.rowIterator();

                int topRowNo = sheet.getTopRow();
                int lastRowNo = sheet.getLastRowNum();
                Iterator<Cell> keyIterator = sheet.getRow(topRowNo).iterator();
                int no = 0;

                ArrayList<String> keys = new ArrayList<String>();
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next().toString();
                    System.out.print(key + ",");
                    keys.add(key);
                }

                JSONArray result = new JSONArray();

                for (int j = 1; j <= lastRowNo; j++) {
                    JSONObject obj = new JSONObject();
                    Row row = sheet.getRow(j);
                    int k = 0;
                    int keyCount = keys.size();
                    int cellNum = row.getFirstCellNum();
                    int lastCellNo = row.getLastCellNum();
                    //System.out.println("cellNum = " + cellNum);
                    //System.out.println("lastCellNo = " + lastCellNo);
                    while (cellNum < lastCellNo) {
                        Cell cell = row.getCell(cellNum);
                        String val = "";
                        if (cell != null)
                            val = cell.toString();
                        //System.out.print(val + ",");
                        if (k < keyCount) {
                            String key = keys.get(k);
                            if (key.contains("format")) {
                                String format = key.substring(key.indexOf(":") + 1, key.lastIndexOf(")")).trim();
                                String keyUpdated = key.substring(0, key.indexOf(" ")).trim();
                                System.out.println("keyUpdated = " + keyUpdated);
                                System.out.println("format = " + format);
                                try {
                                    if (dateFormat.equalsIgnoreCase(format))
                                        val = formatDate(val, dateFormat);
                                } catch (ParseException pe) {
                                }
                                obj.put(keyUpdated, val);
                            } else if (val.contains(",")) {
                                String values[] = val.split(",");
                                for (int l = 0; l < values.length; l++) {
                                    values[l] = values[l].trim();
                                }
                                JSONArray arr = new JSONArray(values);
                                obj.put(key, arr);
                            } else {
                                obj.put(key, val);
                            }
                        }
                        k++;

                        cellNum++;
                    }
                    //System.out.println();
                    System.out.println(obj.toString());
                    result.put(obj);
                }
                //System.out.println("toprow " + topRowNo);
                //System.out.println("getLastRowNum " + lastRowNo);

                System.out.println(result.toString());
            }
        } finally {
            if (fin != null)
                fin.close();
        }
        return null;
    }

    private static String formatDate(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));
    }
}
