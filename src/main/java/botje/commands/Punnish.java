package botje.commands;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import botje.Bot;
import botje.api.gifAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Punnish extends ListenerAdapter {
	
	private static final Logger logger = LogManager.getLogger(Info.class);
	Guild guild;
	gifAPI gif = new gifAPI();
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		guild = event.getChannel().getGuild();
		Role role = guild.getRolesByName("gestrafte loser", true).get(0);

		if (!event.getAuthor().isBot() && args[0].equals(Bot.prefix+"straf")) {
			List<Member> admins = guild.getMembersWithRoles(guild.getRolesByName("admin", true).get(0));
			
			if (admins.contains(guild.getMemberById(event.getAuthor().getId()))) {
				if (args.length > 1) {
					Member member;
					boolean add = false;
					boolean delete = false;
					boolean keep = false;
					
					for (int i = 1; i < args.length; i++) {
													
						switch (args[i].toLowerCase()) {
							case "-a":
								add = true;
								break;
							case "-u":
								delete = true;
								break;
							case "-k":
								keep = true;
								break;
							case "-h":
								displayUsage(event);
								break;
							default:
								int mentionLength = args[i].length();
								member = guild.getMemberById(args[i].substring(3, mentionLength-1));
								
								if (add) {
									if (!keep) {
										deleteRole(event, null, role);
									}
									giveRole(event, member, role);
								} else if (delete) {
									deleteRole(event, member, role);
								}
						}
					}
				}
			} else {
				giveRole(event, guild.getMemberById(event.getAuthor().getId()), role);
			}
		}
	}
	
	private void deleteRole(GuildMessageReceivedEvent event, Member member, Role role) {
		if (member == null) {
			for (Member m : guild.getMembersWithRoles(role)) {
				guild.removeRoleFromMember(m, role).queue();
			}
		} else {
			guild.removeRoleFromMember(member, role).queue();
		}
		event.getChannel().sendMessage("https://giphy.com/gifs/uphe-slap-good-job-boy-628oSvw2Tvc3JXBHjU").queue();
	}
	
	private void giveRole(GuildMessageReceivedEvent event, Member member, Role role) {
		guild.addRoleToMember(member, role).queue();
		String gifUrl = gif.search("loser");
		event.getChannel().sendMessage(gifUrl).queue();
	}
	
	private void displayUsage(GuildMessageReceivedEvent event) {
		EmbedBuilder usage = new EmbedBuilder();
		usage.setColor(Color.red);
		usage.setTitle("Straf");
		usage.setDescription("Werking: ");
		usage.addField("straf -a @mention @mention2 ...", "Iemand straffen, andere gestraften weg", false);
		usage.addField("straf -a -k @mention @mention2 ...", "Iemand straffen, andere gestraften blijven", false);
		usage.addField("straf -u @mention @mention2 ...", "Iemand 'ontstraffen", false);
		event.getChannel().sendMessage(usage.build()).queue();
	}

}
