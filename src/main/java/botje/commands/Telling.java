package botje.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Telling extends ListenerAdapter {
	
	private final static String prefix = "!";   
	
	private List<Member> channelMembers = new ArrayList<Member>();
	private HashMap<String, Integer> attendenceMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> groupsMap = new HashMap<String, Integer>();
	private boolean j;
	private boolean m;
	
	public Telling() {
		initHashMap(groupsMap);
	}
	
	private void initHashMap(HashMap<String, Integer> map) {
		map.put("kadees", 0);
		map.put("kanjers", 0);
		map.put("rakkers", 0);
		map.put("toppers", 0);
		map.put("kerels", 0);
		map.put("aspijongens", 0);
		map.put("vbjongens", 0);
		map.put("snollen", 0);
		map.put("kiekeboes", 0);
		map.put("kwiks", 0);
		map.put("tippers", 0);
		map.put("tiptiens", 0);
		map.put("aspimeisjes", 0);
		map.put("vbmeisjes", 0);
	}
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (!event.getAuthor().isBot() && args[0].equals(prefix+"telling")) {
			
			if (args.length < 2) {
				// display usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(Color.red);
				usage.setTitle("Telling");
				usage.setDescription("Werking: "+ prefix + "telling {voicechannel}");
				event.getChannel().sendMessage(usage.build()).queue();
			} else {
				String channelName = args[1];
				telling(channelName, event);
			}
		}
    }
	
	private void telling(String channelName, GuildMessageReceivedEvent event) {
		List<VoiceChannel> channels = event.getGuild().getVoiceChannelsByName(channelName, true);
		
		if (channels.size() == 0) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(Color.red);
			error.setTitle("Telling");
			error.setDescription("Voicechannel " + channelName + " bestaat niet");
			event.getChannel().sendMessage(error.build()).queue();
		} else if (channels.size() > 1) {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(Color.red);
				error.setTitle("Telling");
				error.setDescription("Er zijn meerdere voicechannels met deze naam: " + channelName);
				event.getChannel().sendMessage(error.build()).queue();
		} else {
			initHashMap(attendenceMap);
			initHashMap(groupsMap);
			
			getAllGroupNumbers(event);
			
			channelMembers = channels.get(0).getMembers();
			for (Member member : channelMembers) {
				countGroups(member, attendenceMap);
			}
			
			System.out.println(channelName);
			
			m = true;
			j = true;
			if (channelName.equalsIgnoreCase("lkj")) {
				m = false;
			} else if (channelName.equalsIgnoreCase("lkm")) {
				j = false;
			}
			
			buildEmbed(event, j, m);
		}
		
	}
	
	private void buildEmbed(GuildMessageReceivedEvent event, boolean j, boolean m) {
		EmbedBuilder tellingEmbed = new EmbedBuilder();
		tellingEmbed.setTitle("telling");
		tellingEmbed.setColor(Color.green);
		
		System.out.println(j + " " + m);
		
		if (j) {
			tellingEmbed.addField("kadees", attendenceMap.get("kadees").toString() + "/" + groupsMap.get("kadees"), true);
			tellingEmbed.addField("kanjers", attendenceMap.get("kanjers").toString() + "/" + groupsMap.get("kanjers"), true);
			tellingEmbed.addField("rakkers", attendenceMap.get("rakkers").toString() + "/" + groupsMap.get("rakkers"), true);
			tellingEmbed.addField("toppers", attendenceMap.get("toppers").toString() + "/" + groupsMap.get("toppers"), true);
			tellingEmbed.addField("kerels", attendenceMap.get("kerels").toString() + "/" + groupsMap.get("kerels"), true);
			tellingEmbed.addField("aspijongens", attendenceMap.get("aspijongens").toString() + "/" + groupsMap.get("aspijongens"), true);
	        tellingEmbed.addField("vbjongens", attendenceMap.get("vbjongens").toString() + "/" + groupsMap.get("vbjongens"), true);
		}
		if (m) {
			tellingEmbed.addField("snollen", attendenceMap.get("snollen").toString() + "/" + groupsMap.get("snollen"), true);
			tellingEmbed.addField("kiekeboes", attendenceMap.get("kiekeboes").toString() + "/" + groupsMap.get("kiekeboes"), true);
			tellingEmbed.addField("kwiks", attendenceMap.get("kwiks").toString() + "/" + groupsMap.get("kwiks"), true);
			tellingEmbed.addField("tippers", attendenceMap.get("tippers").toString() + "/" + groupsMap.get("tippers"), true);
			tellingEmbed.addField("tiptiens", attendenceMap.get("tiptiens").toString() + "/" + groupsMap.get("tiptiens"), true);
			tellingEmbed.addField("aspimeisjes", attendenceMap.get("aspimeisjes").toString() + "/" + groupsMap.get("aspimeisjes"), true);
			tellingEmbed.addField("vbmeisjes", attendenceMap.get("vbmeisjes").toString() + "/" + groupsMap.get("vbmeisjes"), true);
		}
		
		
		event.getChannel().sendMessage(tellingEmbed.build()).queue();
	}

	private void countGroups(Member member, HashMap<String, Integer> map) {
	
		List<Role> memberRoles = member.getRoles();
		
		for (Role role : memberRoles) {
			String roleName = role.getName().toLowerCase();
			switch (roleName) {
				case "kadees":
					map.put("kadees", map.get("kadees") + 1);
					break;
				case "snollen":
					map.put("snollen", map.get("snollen") + 1);
					break;
				case "kanjers":
					map.put("kanjers", map.get("kanjers") + 1);
					break;
				case "kiekeboes":
					map.put("kiekeboes", map.get("kiekeboes") + 1);
					break;
				case "rakkers":
					map.put("rakkers", map.get("rakkers") + 1);
					break;
				case "kwiks":
					map.put("kwiks", map.get("kwiks") + 1);
					break;
				case "toppers":
					map.put("toppers", map.get("toppers") + 1);
					break;
				case "tippers":
					map.put("tippers", map.get("tippers") + 1);
					break;
				case "aspijongens":
					map.put("aspijongens", map.get("aspijongens") + 1);
					break;
				case "aspimeisjes":
					map.put("aspimeisjes", map.get("aspimeisjes") + 1);
					break;
				case "vbjongens":
					map.put("vbjongens", map.get("vbjongens") + 1);
					break;
				case "vbmeisjes":
					map.put("vbmeisjes", map.get("vbmeisjes") + 1);
					break;
			}
		}
		
	}
	
	private void getAllGroupNumbers(GuildMessageReceivedEvent event) {
		List<Member> allMembers = event.getGuild().getMembers();
		
		for (Member member : allMembers) {
			countGroups(member, groupsMap);
		}
	}

}
