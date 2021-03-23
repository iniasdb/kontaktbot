package botje.api;

import java.util.ArrayList;
import java.util.Arrays;
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

public class ClashApi {
	
	public ClashApi() throws Exception {
		
//		JsonObject jo = makeApiCall();
//		List<String> clanInfo = getClanInformation(jo);
//		List<String[]> memberArray = getClanMemberInformation(jo);
//		
//		for (String info : clanInfo) {
//			System.out.println(info);
//		}
//		
//		for (String[] member : memberArray) {
//			for (String info : member) {
//				System.out.println(info);
//			}
//		}
	}
	
	public JsonObject makeApiCall() throws Exception {
	    String host = "https://api.clashofclans.com/v1/clans";

	    String key = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImEwNmUzZjFkLWIxNTEtNDM0OC1iZmI0LTEzZTg2YzgxNzM0NCIsImlhdCI6MTYxNjQ5MDI1OSwic3ViIjoiZGV2ZWxvcGVyL2ZiNmJlZTFjLTU4NTctNDI4Mi02YzcxLTRhODMwYjhlZjYzYyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjgxLjgyLjIwNi43IiwiODQuMTk2LjEzMC4xNSJdLCJ0eXBlIjoiY2xpZW50In1dfQ.ptA0IEmujC6ecE_7qUOQS9QYaNb-Uly79-JTqUsgropHb42Dr1HtY5ISukDSxWvzCnVQJ65aTr0c_Tys4ZvYUg";

	    String query = "/%23QROCY8JJ";
	    	      
	    HttpResponse <JsonNode> response = Unirest.get(host + query)
	  	      .header("Authorization", key)
	  	      .asJson();
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    JsonParser jp = new JsonParser();
	    JsonElement je = jp.parse(response.getBody().toString());
	    
	    JsonObject jo = je.getAsJsonObject();
	    
	    return jo;
	}
	
	public List<String> getClanInformation(JsonObject jo) {
		
	    String clanName = jo.get("name").getAsString();
	    String description = jo.get("description").getAsString();
	    String badgeUrl = jo.get("badgeUrls").getAsJsonObject().get("medium").getAsString();
	    String clanLevel = jo.get("clanLevel").getAsString();
	    String warWins = jo.get("warWins").getAsString();
	    String warTies = jo.get("warTies").getAsString();
	    String warLosses = jo.get("warLosses").getAsString();
	    String warLeague = jo.get("warLeague").getAsJsonObject().get("name").getAsString();
	    
	    //System.out.println(clanName + " " + description + " level: " + clanLevel + " league: " + warLeague);
	    //System.out.println("wins "+ warWins + " ties " + warTies + " losses " + warLosses);
	    
	    
		List<String> result = new ArrayList<String>(Arrays.asList(clanName, description, badgeUrl, clanLevel, warWins, warTies, warLosses, warLeague));
	    
	    return result;
	}
	
	public List<String[]> getClanMemberInformation(JsonObject jo) {
		JsonArray membersArray =  jo.get("memberList").getAsJsonArray();
		
		List<String[]> result = new ArrayList<String[]>();
		
	    for (int i = 0; i < membersArray.size(); i++) {
	    	  String tag = membersArray.get(i).getAsJsonObject().get("tag").getAsString();
	    	  String name = membersArray.get(i).getAsJsonObject().get("name").getAsString();
	    	  String level = membersArray.get(i).getAsJsonObject().get("expLevel").getAsString();
	    	  String league = membersArray.get(i).getAsJsonObject().get("league").getAsJsonObject().get("name").getAsString();
	    	  String trophies = membersArray.get(i).getAsJsonObject().get("trophies").getAsString();
	    	  String rank = membersArray.get(i).getAsJsonObject().get("clanRank").getAsString();
	    	  String donations = membersArray.get(i).getAsJsonObject().get("donations").getAsString();
	    	  String donationsReceived = membersArray.get(i).getAsJsonObject().get("donationsReceived").getAsString();
	    	  
//	    	  System.out.println(name + " " + tag + " " + level + " " + trophies);
//	    	  System.out.println(league + " " + rank);
//	    	  System.out.println(donations + " " + donationsReceived);
//	    	  System.out.println();
	    	  
	    	  String[] memberInfo = {tag, name, level, league, trophies, rank, donations, donationsReceived};
	    	  result.add(memberInfo);
	    }
	    
	    return result;

    }
}
