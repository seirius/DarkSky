package darksky.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReturn {
	
	public final static String ERROR_CODE       = "ERROR_CODE";
	public final static String ERROR_MSG        = "ERROR_MSG";
	public final static String DATA             = "CONTENIDO";
	public final static int    DEFAULT_ERR_CODE = -1;
	
	private JsonObject ret;
	private JsonElement data;
	
	public JsonReturn() {
		ret = new JsonObject();
		ret.addProperty(ERROR_CODE, 0);
		ret.addProperty(ERROR_MSG, "");
	}
	
	public void setError(int errorCode, String errorMsg) {
		ret.addProperty(ERROR_CODE, errorCode);
		ret.addProperty(ERROR_MSG, errorMsg);
	}
	
	/**
	 * Pone el mensaje de error con el ERROR_CODE por defecto
	 * 
	 * @param errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		ret.addProperty(ERROR_CODE, DEFAULT_ERR_CODE);
		ret.addProperty(ERROR_MSG, errorMsg);
	}
	
	public void setData(JsonElement data) {
		this.data = data;
		ret.add(DATA, data);
	}
	
	public JsonObject getRet() {
		return ret;
	}
	
	/**
	 * Permite retornar la informacion poniendo DATA directamente
	 * 
	 * @param data
	 * @return
	 */
	public JsonObject getRet(JsonObject data) {
		this.data = data;
		ret.add(DATA, data);
		return ret;
	}
	
	public String getRetAsString() {
		return ret.getAsString();
	}
	
	/**
	 * Permite retornar la informacion poniendo DATA directamente
	 * 
	 * @param data
	 * @return
	 */
	public String getRetAsString(JsonElement data) {
		this.data = data;
		ret.add(DATA, data);
		
		return ret.getAsString();
	}
	
	public boolean isDataNull() {
		if (data == null) {
			return true;
		}
		
		return false;
	}
	
	public static List<String> fromStringToJsonArray(String json) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonArray jsonArray = element.getAsJsonArray();
		List<String> jsonList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonList.add(jsonArray.get(i).getAsString());
		}
		return jsonList;
	}
	
	
}
