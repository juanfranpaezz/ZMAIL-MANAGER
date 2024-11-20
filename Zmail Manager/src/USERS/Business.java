package USERS;

import EXCEPTIONS.PasswordException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Business extends User {
    private String companyName;
    private CompanyType companyType;


    public Business(UserMail mail, String password, String companyName, CompanyType companyType) {
        super(mail, password);
        this.companyName = companyName;
        this.companyType = companyType;
    }

    public Business(){}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    @Override
    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMapGenericity = new HashMap<>(super.toHashMap());
        hashMapGenericity.put("companyName", getCompanyName());
        hashMapGenericity.put("companyType", getCompanyType());
        hashMapGenericity.put("user_type", "Business");
        return hashMapGenericity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName);
    }

    public static Business transformJSONToObject(JSONObject jsonObject) {
        try{
            UserMail mail = new UserMail(jsonObject.getString("mail"));
            String password = jsonObject.getString("password");
            Date creationDate = new Date(jsonObject.getLong("creationDate"));
            String companyName = jsonObject.getString("companyName");
            CompanyType companyType = CompanyType.valueOf(jsonObject.getString("companyType"));
            int idUser = jsonObject.getInt("idUser");
            JSONArray contactArrayList = jsonObject.getJSONArray("contactList");
            Business business = new Business(mail, password, companyName, companyType);
            return business;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
