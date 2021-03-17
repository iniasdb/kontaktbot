package botje;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
	
	JDABuilder builder;

	public Bot() {
		builder = JDABuilder.createDefault("ODA2OTgyNzE0MDA2NzAwMDUz.YBxXOA.KW9yMeGSmH1WQ1XSjYomQz3Rldg");
		builder.setStatus(OnlineStatus.ONLINE);
		builder.addEventListeners(new Commands());
	}
	
	public void launch() {
		try {
			builder.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}	
	}
	
}
