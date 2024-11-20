package USERS;

import INTERFACE.IJsonUtiles;

import java.util.*;

public abstract class User implements IJsonUtiles {
    protected UserMail userMail;
    protected String password;
    protected Date creationDate;
    protected String ID;


    public User(UserMail mail, String password){
        this.userMail = mail;
        this.password = password;
        this.creationDate = new Date();
        this.ID=UUID.randomUUID().toString();  ;

    }
    public User(){}

    public String getID() {
        return ID;
    }

    public UserMail getUserMail() {
        return userMail;
    }

    public void setUserMail(UserMail userMail) {
        this.userMail = userMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userMail, user.userMail);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userMail);
    }

    @Override
    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMapGenericity = new HashMap<>();
        hashMapGenericity.put("mail", getUserMail());
        hashMapGenericity.put("password", getPassword());
        hashMapGenericity.put("creationDate", getCreationDate());
        hashMapGenericity.put("idUser", getID());
        return hashMapGenericity;
    }


    @Override
    public String toString() {
        return "User{" +
                "userMail=" + userMail +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                ", idUser=" + ID +
                '}';
    }
}
