package botje.commands;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import botje.Bot;
import botje.api.RedditApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Reddit extends ListenerAdapter {
	
	RedditApi api;
	
	public Reddit() throws Exception {
		api = new RedditApi();
	}
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (!event.getAuthor().isBot() && args[0].equals(Bot.prefix+"red")) {
			
			System.out.println(args.length);
			
			if (args.length < 2) {
			} else {
				if (args[1].equalsIgnoreCase("tetj")) {
					try {
						makeCall(event, "boobs");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (args[1].equalsIgnoreCase("milf")) {
					try {
						int rand = (int) Math.random();
						if (rand == 1) {
							makeCall(event, "gonewild30plus");
						} else {
							makeCall(event, "milf");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (args[1].equalsIgnoreCase("teen")) {
					try {
						int rand = (int) (Math.random()*6)+1;
						
						switch (rand) {
						case 1:
							makeCall(event, "LegalTeens");
							break;
						case 2:
							makeCall(event, "collegesluts");
							break;
						case 3:
							makeCall(event, "adorableporn");
							break;
						case 4:
							makeCall(event, "barelylegalteens");
							break;
						case 5:
							makeCall(event, "Just18");
							break;
						case 6:
							makeCall(event, "SchoolGirlSkirts");
						default:
							System.out.println("godverdekke da klopt hier ni");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (args[1].equalsIgnoreCase("ass")) {
					try {
						makeCall(event, "ass");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
    }

	
	private void makeCall(GuildMessageReceivedEvent event, String sr) throws Exception {
		if (event.getChannel().isNSFW() && checkPermissions(event)) {
			JsonObject jo = api.makeApiCall(sr);
			List<String> result = api.getImage(jo);
			
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.green);
			embed.setTitle(sr.toUpperCase());
			embed.setDescription(result.get(0));
			embed.setImage(result.get(1));
			event.getChannel().sendMessage(embed.build()).queue();
		} else {
			notNSFW(event);
		}

	}
	
	private void notNSFW(GuildMessageReceivedEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.red);
		embed.setTitle("Da mag hier ni eh manneken");
		embed.setDescription("Zo iets doede toch ni dejuu");
		event.getChannel().sendMessage(embed.build()).queue();
	}
	
	private boolean checkPermissions(GuildMessageReceivedEvent event) {
		List<Role> roles = event.getMember().getRoles();
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("Jongensleiding")) return true;
		}
		return false;
	}

}
