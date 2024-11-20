package USERS;

import MESSAGES.Mail;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MailManager {
    public MailManager() {
    }

    public static boolean  cargarAJSON(Mail mailcito) {
        try {
            JSONArray jjj = new JSONArray();
            if (Files.exists(Paths.get("Mails.json"))) {
                String contenido = new String(Files.readAllBytes(Paths.get("Mails.json")));
                jjj = new JSONArray(contenido);
            }else{
                FileWriter f = new FileWriter("Mails.json");
            }

            jjj.put(mailcito.toJson());
            FileWriter f = new FileWriter("Mails.json");
            f.write(jjj.toString(4));
            f.close();
            return true;
        } catch (IOException | JSONException e) {
            e.getMessage();
        }
        return false;
    }
}
