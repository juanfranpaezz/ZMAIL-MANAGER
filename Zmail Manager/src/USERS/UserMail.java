package USERS;

import INTERFACE.IJsonUtiles;

import java.util.HashMap;
import java.util.Objects;

public class UserMail implements IJsonUtiles {
    private String mail;

    public UserMail(String mail) {
        String trimmedMail = mail.replaceAll("\\s+", ""); // Eliminar espacios
        if (trimmedMail.endsWith("@zmail.com")) {
            this.mail = trimmedMail; // Si ya tiene el dominio, asignarlo directamente
        } else {
            this.mail = trimmedMail + "@zmail.com"; // Agregar el dominio si no está presente
        }
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> h = new HashMap<>();
        h.put("mail",getMail());
        return h;
    }


    @Override
    public String toString() {
        return mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMail userMail = (UserMail) o;
        return Objects.equals(mail, userMail.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mail);
    }
}
