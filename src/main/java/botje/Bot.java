package botje;

import javax.security.auth.login.LoginException;

import botje.commands.Info;
import botje.commands.Telling;
import botje.events.Join;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {
	
	JDABuilder builder;

	public Bot() {
		builder = JDABuilder.createDefault("token");
		builder.setStatus(OnlineStatus.ONLINE);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.addEventListeners(new Info());
		builder.addEventListeners(new Telling());
		builder.addEventListeners(new Join());
	}
	
	public void launch() {
		try {
			builder.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}	
	}
	
}
