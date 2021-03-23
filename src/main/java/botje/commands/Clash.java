package botje.commands;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import botje.Bot;
import botje.api.ClashApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clash extends ListenerAdapter {
	
	ClashApi api;
	
	public Clash() throws Exception {
		api = new ClashApi();
	}
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (!event.getAuthor().isBot() && args[0].equals(Bot.prefix+"coc")) {
			
			System.out.println(args.length);
			
			if (args.length < 2) {
				displayUsage(event);
			} else {
				if (args[1].equalsIgnoreCase("clan")) {
					try {
						getClanInfo(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (args[1].equalsIgnoreCase("ranking")) {
					if (args.length >= 3) {
						try {
							int num = Integer.parseInt(args[2]);
							getRanking(event, num);
						} catch (NumberFormatException e) {
							displayUsage(event);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							getRanking(event);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					displayUsage(event);
				}
			}
		}

    }
	
	private void getClanInfo(GuildMessageReceivedEvent event) throws Exception {
		JsonObject jo;
		jo = api.makeApiCall();
		List<String> result = api.getClanInformation(jo);
		
		EmbedBuilder clanInfo = new EmbedBuilder();
		clanInfo.setColor(Color.blue);
		clanInfo.setTitle(result.get(0));
		clanInfo.setDescription(result.get(1));
		clanInfo.setThumbnail(result.get(2));
		clanInfo.addField("level", result.get(3), false);
		clanInfo.addField("war league", result.get(7), false);
		clanInfo.addField("war victories", result.get(4), true);
		clanInfo.addField("war ties", result.get(5), true);
		clanInfo.addField("war losses", result.get(6), true);
		
		event.getChannel().sendMessage(clanInfo.build()).queue();
	}
	
	private void getRanking(GuildMessageReceivedEvent event) throws Exception {
		JsonObject jo;
		jo = api.makeApiCall();
		List<String[]> memberArray = api.getClanMemberInformation(jo);
		
		EmbedBuilder ranking = new EmbedBuilder();
		ranking.setColor(Color.green);
		ranking.setTitle("Ranking");
		
		for (String[] result : memberArray) {
			ranking.addField(result[5] + ":", result[1] + " " + result[4], false);
		}
		
		event.getChannel().sendMessage(ranking.build()).queue();
	}
	
	private void getRanking(GuildMessageReceivedEvent event, int num) throws Exception {
		JsonObject jo;
		jo = api.makeApiCall();
		List<String[]> memberArray = api.getClanMemberInformation(jo);
		
		EmbedBuilder ranking = new EmbedBuilder();
		ranking.setColor(Color.green);
		ranking.setTitle("Ranking");
		
		int i = 1;
		
		for (String[] result : memberArray) {
			if (i > num) {
				break;
			}
			ranking.addField(result[5] + ":", result[1] + " " + result[4], false);
			i++;
		}
		
		event.getChannel().sendMessage(ranking.build()).queue();
	}
	
	private void displayUsage(GuildMessageReceivedEvent event) {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setColor(Color.red);
		usage.setTitle("Clash of clans");
		usage.setDescription("Werking: ");
		usage.addField("coc clan", "geeft clan info", false);
		usage.addField("coc ranking", "toont ranking in clan", false);
		usage.addField("coc ranking {x}", "toont eerste x leden in ranking", false);
		event.getChannel().sendMessage(usage.build()).queue();
	}

}
