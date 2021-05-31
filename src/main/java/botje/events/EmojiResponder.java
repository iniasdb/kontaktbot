package botje.events;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmojiResponder extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		System.out.println(event.getAuthor().getAsTag());
		if (event.getAuthor().getAsTag().equalsIgnoreCase("JonasC#1250")) {
		//if (event.getAuthor().getAsTag().equalsIgnoreCase("inias#9588")) {	//test
			System.out.println("ja");
			event.getMessage().addReaction("zwijn:742503462943588454").queue();
		}
		
	}

	
}
