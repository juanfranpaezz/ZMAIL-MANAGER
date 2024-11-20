package JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONUtiles {

    public static boolean uploadToFile(JSONArray jsonArray, String fileName){
        try{
            FileWriter file = new FileWriter(fileName);
            file.write(jsonArray.toString(4));
            file.close();
            return true;
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String downloadFile(String fileName){
        String content = "";
        try {

            content = new String(Files.readAllBytes(Paths.get(fileName)));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
