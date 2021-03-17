package botje.events;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		try {
			event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("vrienden van kontakt", true).get(0)).complete();
		} catch (Exception HierarchyException) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(Color.red);
			error.setTitle("Join error");
			error.setDescription("Bot moet een hogere rol hebben dan 'vrienden van kontakt'");
			event.getGuild().getTextChannelsByName("admin-test-channel", true).get(0).sendMessage(error.build()).queue();
		}
	}

}
