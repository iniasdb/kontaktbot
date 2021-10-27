package botje.api;

import org.brunocvcunha.jiphy.Jiphy;
import org.brunocvcunha.jiphy.JiphyConstants;
import org.brunocvcunha.jiphy.model.JiphyGif;
import org.brunocvcunha.jiphy.model.JiphySearchResponse;
import org.brunocvcunha.jiphy.requests.JiphySearchRequest;

public class gifAPI {
	
	Jiphy jiphy;
	
	public gifAPI() {
		 jiphy = Jiphy.builder()
			    .apiKey(JiphyConstants.API_KEY_BETA)
			    .build();
	}
	
	public String search(String query) {
		try {
			JiphySearchResponse result = jiphy.sendRequest(new JiphySearchRequest(query));
			
			for (JiphyGif gif : result.getData()) {
				if (Integer.parseInt(gif.getTrending_datetime().substring(0,4)) > 0) {
					return gif.getUrl();
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
