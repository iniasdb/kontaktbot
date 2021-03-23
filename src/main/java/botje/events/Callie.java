package botje.events;

import java.util.Random;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Callie extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getMessage().getContentRaw().contains("haha")) {
			
			Random random = new Random();
			int randNum = random.nextInt(100);
			
			if (randNum < 20) {
				String id = event.getAuthor().getId();
				event.getChannel().sendMessage("<@"+id+"> lach de lach der dwazen").queue();
			}
			
		}
	}

}
