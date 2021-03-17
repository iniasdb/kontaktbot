package botje.commands;

import java.awt.Color;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
	
	private final static String prefix = "!";   
	private static final Logger logger = LogManager.getLogger(Info.class);
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (!event.getAuthor().isBot() && args[0].equals(prefix+"help")) {
			logger.info("Display help embed");
			display(event);
		}
    }
	
	private void display(GuildMessageReceivedEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Help");
		embed.setDescription("Beschikbare commands");
		embed.addField("!telling {channelnaam}", "Telt het aantal personen in een voicechannel per afdeling", false);
		embed.setColor(Color.green);
		event.getChannel().sendMessage(embed.build()).queue();
		logger.info("Help embed build and shown");
	}

}
