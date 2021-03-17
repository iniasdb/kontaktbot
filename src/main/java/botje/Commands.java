package botje;

import java.awt.Color;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	
	private final static String prefix = "!";   
	private static final Logger logger = LogManager.getLogger(Commands.class);

		
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();

		if (!event.getAuthor().isBot() && message.substring(0, 1).equals(prefix)) {
			logger.info("message received: ", message);
    		commandSwitch(message, event);
		}
    }
	
	private void commandSwitch(String message, GuildMessageReceivedEvent event) {
		String command = message.substring(1).toLowerCase();
		
		switch (command) {
			case "info":
				displayInfo(event);
				break;
			case "telling":
				telling(event);
			default:
				break;
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
		logger.info("Info embed build");
	}
	
	private void telling(GuildMessageReceivedEvent event) {
		
		String channelName = event.getMessage().getContentRaw().substring(7);
		
		System.out.println(channelName);
		
		List<VoiceChannel> channel = event.getGuild().getVoiceChannelsByName(channelName, true);
		System.out.println(channel.get(0).getMembers().size());
		
//		List<GuildChannel> channels = event.getGuild().getChannels();
//		for (GuildChannel channel : channels) {
//			if (channel.getName().equalsIgnoreCase("lkj")) {
//				System.out.println(channel.getMembers().size());
//			}
//		}
	}
    
}
