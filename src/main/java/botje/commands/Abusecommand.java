package botje.commands;

import javax.annotation.Nonnull;

import botje.Bot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Abusecommand extends ListenerAdapter {
		
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (!event.getAuthor().isBot() && args[0].equals(Bot.prefix+"heeftIniasEenLeven")) {
			event.getChannel().sendMessage("nee wtf").queue();
		}
    }

}
