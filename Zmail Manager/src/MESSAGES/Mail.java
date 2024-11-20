package MESSAGES;

import INTERFACE.IJsonUtiles;
import JSON.JSONGenericity;
import USERS.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Mail implements IJsonUtiles {

    private int ID;
    private static int auxID = 2411;
    private HashSet<UserMail> receptors;
    private UserMail sender;
    private String message;
    private Date date;
    private MailType mailType;
    private String matter;

    // Formateador de fecha estándar
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Mail(HashSet<UserMail> receptors, String message, UserMail sender, MailType mailType, String matter) {
        this.ID = auxID++;
        this.message = message;
        this.sender = sender;
        this.receptors = (receptors != null) ? receptors : new HashSet<>();
        this.date = new Date();
        this.mailType = mailType;
        this.matter = matter;
    }

    public Mail(Date date, HashSet<UserMail> receptors, String message, UserMail sender, MailType mailType, String matter) {
        this.ID = auxID++;
        this.message = message;
        this.sender = sender;
        this.receptors = (receptors != null) ? receptors : new HashSet<>();
        this.date = date;
        this.mailType = mailType;
        this.matter = matter;
    }

    public int getID() { return ID; }

    public HashSet<UserMail> getReceptors() {
        return (receptors != null) ? receptors : new HashSet<>();
    }

    public void addReceptors(UserMail user) {
        receptors.add(user);
    }

    public void addReceptors(Set<UserMail> users) {
        receptors.addAll(users);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserMail getSender() {
        return sender;
    }

    public void setSender(UserMail sender) {
        this.sender = sender;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public MailType getMailType() {
        return mailType;
    }

    public void setMailType(MailType mailType) {
        this.mailType = mailType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date stringToDate(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            JSONArray ja = new JSONArray();
            for (UserMail u : getReceptors()) {
                JSONGenericity j = new JSONGenericity();
                ja.put(j.transformToJSON(u, u.toHashMap()));
            }
            json.put("ID", getID());
            json.put("receptors_list", ja);
            json.put("message", getMessage());
            json.put("sender", getSender().toString());
            json.put("date", dateToString(getDate())); // Formato estandarizado
            json.put("mailType", getMailType().toString());
            json.put("matter", getMatter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Mail transformJSONToObject(JSONObject j) {
        try {
            String message = j.getString("message");
            UserMail sender = new UserMail(j.getString("sender"));
            MailType mailType = MailType.valueOf(j.getString("mailType"));
            String matter = j.getString("matter");
            JSONArray ja = j.getJSONArray("receptors_list");
            Date date = stringToDate(j.getString("date")); // Parseo de fecha
            HashSet<UserMail> rec = new HashSet<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject aux = ja.getJSONObject(i);
                rec.add(new UserMail(aux.getString("mail")));
            }
            return new Mail(date, rec, message, sender, mailType, matter);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String receptorsToString() {
        StringBuilder sb = new StringBuilder();
        HashSet<UserMail> aux = getReceptors();
        for (UserMail u : aux) {
            sb.append(u.toString()).append(", ");
        }
        return sb.toString();
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMapGenericity = new HashMap<>();
        hashMapGenericity.put("ID", getID());
        hashMapGenericity.put("receptors_list", getReceptors());
        hashMapGenericity.put("message", getMessage());
        hashMapGenericity.put("sender", getSender().toString());
        hashMapGenericity.put("date", dateToString(getDate())); // Formato estandarizado
        hashMapGenericity.put("mailType", getMailType().toString());
        hashMapGenericity.put("matter", getMatter());
        return hashMapGenericity;
    }

    public String sendsToString() {
        return "    Mails:" +
                "\n     Receptor/es:\n          " + receptorsToString()  +
                "\n     Asunto:\n          " + matter +
                "\n     Mensaje:\n          " + message +
                "\n     Fecha:\n          " + dateToString(date) +
                "\n     Tipo:\n          " + mailType + "\n\n";
    }

    public String recibedToString() {
        return "    Mails:" +
                "\n     Emisor:\n          " + sender+
                "\n     Asunto:\n          " + matter +
                "\n     Mensaje:\n          " + message +
                "\n     Fecha:\n          " + dateToString(date) +
                "\n     Tipo:\n          " + mailType + "\n\n";
    }
}