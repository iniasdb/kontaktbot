package botje.commands;

import java.awt.Color;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter {
	
	private final static String prefix = "!";   
	private static final Logger logger = LogManager.getLogger(Info.class);
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (!event.getAuthor().isBot() && args[0].equals(prefix+"info")) {
			logger.info("Display info command");
			displayInfo(event);
		}
    }
	
	private void displayInfo(GuildMessageReceivedEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Info");
		embed.setDescription("Info over dezen bot eh makker");
		embed.addField("creator", "elle ma", false);
		embed.setFooter("Created by techtoolbox", event.getMember().getUser().getAvatarUrl());
		embed.setColor(Color.green);
		event.getChannel().sendMessage(embed.build()).queue();
		logger.info("Info embed build and shown");
	}

}
