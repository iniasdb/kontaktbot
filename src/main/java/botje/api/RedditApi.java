package botje.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class RedditApi {
	
	public JsonObject makeApiCall(String sr) throws Exception {
	    String host = "https://www.reddit.com/r/"+sr+"/hot.json?limit=100";
	    	      
	    HttpResponse <JsonNode> response = Unirest.get(host).header("User-agent", "kontaktbot").asJson();
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    JsonParser jp = new JsonParser();
	    JsonElement je = jp.parse(response.getBody().toString());
	    
	    JsonObject jo = je.getAsJsonObject();
	    
	    return jo;
	}
	
	public List<String> getImage(JsonObject jo) {
			    
	    JsonArray children = jo.get("data").getAsJsonObject()
	    		.get("children").getAsJsonArray();
	    
	    int amount = jo.get("data").getAsJsonObject().get("dist").getAsInt();
	    
	    int random = (int) (Math.random()*amount)+1;
	    System.out.println(random);
	    
	    JsonObject item = children
				.get(random).getAsJsonObject()
				.get("data").getAsJsonObject();
	    
		String title = item
				.get("title").getAsString();
		
		String image = item.get("url").getAsString();
	    
	    List<String> result = new ArrayList<String>();
	    result.add(title);
	    result.add(image);
	    
	    return result;
	}
	
}
