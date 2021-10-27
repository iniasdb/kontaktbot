package botje;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import botje.commands.Help;
import botje.commands.Punnish;
import botje.commands.Reddit;
import botje.commands.Abusecommand;
import botje.commands.Clash;
import botje.commands.Telling;
import botje.events.Callie;
import botje.events.EmojiResponder;
import botje.events.Join;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
	
	JDABuilder builder;
	public final static String prefix = "!";   
	
	public Bot() {
		try {
			Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("token.txt"));
			String token  = scanner.next();
			builder = JDABuilder.createDefault(token);
			scanner.close();
			builder.setStatus(OnlineStatus.ONLINE);
			builder.setActivity(Activity.playing("met het kutje van uw moeder"));
			builder.setChunkingFilter(ChunkingFilter.ALL);
			builder.setMemberCachePolicy(MemberCachePolicy.ALL);
			builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
			
			//builder.addEventListeners(new Info());
			builder.addEventListeners(new Help());
			builder.addEventListeners(new Telling());
			builder.addEventListeners(new Abusecommand());
			builder.addEventListeners(new Clash());
			builder.addEventListeners(new Join());
			builder.addEventListeners(new Callie());
			builder.addEventListeners(new EmojiResponder());
			//builder.addEventListeners(new Poef());
			builder.addEventListeners(new Reddit());
			builder.addEventListeners(new Punnish());

		} catch (Exception e) {
			System.out.println(getClass().getCanonicalName());
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
