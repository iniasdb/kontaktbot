package botje;

import java.util.Scanner;

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
		try {
			Scanner scanner = new Scanner(getClass().getResourceAsStream("token.txt"));
			builder = JDABuilder.createDefault(scanner.next());
			scanner.close();
			builder.setStatus(OnlineStatus.ONLINE);
			builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
			builder.addEventListeners(new Info());
			builder.addEventListeners(new Telling());
			builder.addEventListeners(new Join());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void launch() {
		try {
			builder.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}	
	}
	
}
