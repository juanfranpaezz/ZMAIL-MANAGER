package USERS;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import JSON.JSONGenericity;
import EXCEPTIONS.PasswordException;
import EXCEPTIONS.*;
import JSON.JSONUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsersManager<T extends User> {
    private static int auxID = 319672;
    private LinkedList<T> usersList;
    private JSONGenericity<T> jgn;

    public UsersManager() {
        this.usersList = new LinkedList<T>();
        this.jgn =  new JSONGenericity<T>();


    }

    public LinkedList<T> getUsersList() {
        return usersList;
    }

    public boolean registerUser(T user) {
        synchronized (this) {
            try {
                if (!Files.exists(Paths.get("Users.json"))) {
                    try (FileWriter f = new FileWriter("Users.json")) {
                        f.write("[]"); // Inicializa como JSONArray vacío
                    }
                }

                if (!validateUserExists(user.getUserMail())) {
                    String contenido = new String(Files.readAllBytes(Paths.get("Users.json")));
                    if (contenido == null || contenido.trim().isEmpty()) {
                        contenido = "[]"; // JSONArray vacío si el archivo está vacío
                    }

                    JSONArray jjj = new JSONArray(contenido);

                    usersList.add(user);
                    jjj.put(jgn.transformToJSON(user, user.toHashMap()));

                    return JSONUtiles.uploadToFile(jjj, "Users.json");
                }

            } catch (IOException | JSONException e) {
                System.err.println("Error en registerUser: " + e.getMessage());
                e.printStackTrace();
            }
            return false;
        }
    }
    public boolean loginUser(UserMail userMail, String password) {
        try {
            if (!Files.exists(Paths.get("Users.json"))) {
                System.out.println("El archivo no existe.");
                return false;
            }
            // Leer el contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get("Users.json")));
            // Obtener la lista de usuarios
            JSONArray usersList = new JSONArray(content);
            // Obtener el email a verificar desde el objeto UserMail
            String emailToCheck = userMail.toString();
            // Buscar si el email existe en la lista
            for (int i = 0; i < usersList.length(); i++) {
                JSONObject existingUser = usersList.getJSONObject(i);
                String existingEmail = existingUser.getString("mail");
                if (existingEmail.equals(emailToCheck)) {
                    String existingPassword = existingUser.getString("password");
                    if (password.equals(existingPassword)){
                        return true;
                    }
                }
            }
        } catch (JSONException | IOException e) {
            e.getMessage();
        }
        return false; // Usuario no encontrado
    }
    public static boolean isUserPro(UserMail userMail){
        try {
            String file = JSONUtiles.downloadFile("Users.json");

            JSONArray aa=new JSONArray(file);
            for(int i=0;i<aa.length();i++){
                JSONObject aux=aa.getJSONObject(i);
                if(aux.getString("user_type").matches("Pro")){
                    if(aux.getString("mail").matches(userMail.toString().trim())){
                        return true;
                    }
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean validateUserExists(UserMail email){

        try {

            String contenido = new String(Files.readAllBytes(Paths.get("Users.json")));
            JSONArray jA = new JSONArray(contenido);
            for (int i = 0; i < jA.length(); i++) {
                JSONObject jk = jA.getJSONObject(i);
                String existingEmail = jk.getString("mail");

                if ((email.toString().trim()).equals(existingEmail.trim())) { //NO CAMBIAR NUNCA EN LA VIDA
                    return true;
                }
            }
        } catch (IOException | JSONException e) {
            e.getMessage();
        }
        return false;
    }

    public boolean verifyThePassword(String newPassword){
        if (newPassword.length() < 8 || newPassword.length() > 25 || newPassword.chars().noneMatch(Character::isDigit) || newPassword.chars().noneMatch(Character::isUpperCase) || newPassword.chars().noneMatch(Character::isLowerCase)) return false;
        return true;
    }
    public boolean verifyPassword(String newPassword)throws PasswordException{
        if (newPassword.length() < 8) { //VERIFICA QUE LA LONGITUD DE LA CONTRASEÑA NO SEA MENOR A 8 CARACTERES
            throw new PasswordException.PasswordTooShort();
        } else if (newPassword.length() > 25) {//VERIFICA QUE LA LONGITUD DE LA CONTRASEÑA NO SEA MAYOR A 25 CARACTERES
            throw new PasswordException.PasswordTooLong();
        } else if (newPassword.chars().noneMatch(Character::isDigit)) { //VERIFICA QUE HAYA POR LO MENOS 1 DIGITO EN LA CONTRASEÑA
            throw new PasswordException.PasswordNeedsNumber();
        } else if (newPassword.chars().noneMatch(Character::isUpperCase)) { //VERIFICA QUE HAYA POR LO MENOS 1 MAYUSCULA EN LA CONTRASEÑA
            throw new PasswordException.PasswordNeedsUpperCase();
        } else if (newPassword.chars().noneMatch(Character::isLowerCase)) { //VERIFICA QUE HAYA POR LO MENOS 1 MINUSCULA EN LA CONTRASEÑA
            throw new PasswordException.PasswordNeedsLowerCase();
        }else return true;
    }
    public boolean comparePasswords(String password1,String password2)throws PasswordException.PasswordDoesntMatch {
        if(!password1.equals(password2)){
            throw new PasswordException.PasswordDoesntMatch();
        }else return true;
    }

    public static int getNextID() {
        return auxID++;
    }
}