package botje;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import botje.commands.Help;
import botje.commands.Info;
import botje.commands.Abusecommand;
import botje.commands.Telling;
import botje.events.Join;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
	
	JDABuilder builder;
	
	public Bot() {
		try {
			Scanner scanner = new Scanner(getClass().getResourceAsStream("token.txt"));
			builder = JDABuilder.createDefault(scanner.next());
			scanner.close();
			builder.setStatus(OnlineStatus.ONLINE);
			builder.setChunkingFilter(ChunkingFilter.ALL);
			builder.setMemberCachePolicy(MemberCachePolicy.ALL);
			builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
			//builder.addEventListeners(new Info());
			builder.addEventListeners(new Help());
			builder.addEventListeners(new Telling());
			builder.addEventListeners(new Join());
			builder.addEventListeners(new Abusecommand());
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
