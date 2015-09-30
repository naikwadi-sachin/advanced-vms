package com.uncc.vms.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by sachin on 9/26/2015.
 */
public class JsonConverter {

    private static final String dateFormat = "yyyy-MM-dd";

    private static JSONArray csvToJSONConvertor(final File file) throws JSONException, IOException {
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
        return result;
    }

    private static void printArray(String[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i] + "\t");
        }
        System.out.println();
    }


    private static String formatDate(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));
    }
}
