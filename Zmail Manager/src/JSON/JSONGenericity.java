package JSON;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONGenericity<T>{

    public JSONGenericity() {
    }
    public JSONObject transformToJSON(T t, HashMap<String,Object> atributos){
        JSONObject j = new JSONObject();
        try{
            Iterator<Map.Entry<String, Object>> iterador = atributos.entrySet().iterator();
            while (iterador.hasNext()) {
                Map.Entry<String, Object> entrada = iterador.next();
                String key = entrada.getKey();
                Object value = entrada.getValue();
                j.put(key, value);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return j;
    }
}
