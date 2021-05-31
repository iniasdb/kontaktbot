package botje.commands;

import java.awt.Color;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import botje.Bot;
import botje.database.MySQLConnector;
import botje.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Poef extends ListenerAdapter {
	
	private static final Logger logger = LogManager.getLogger(Info.class);
	private MySQLConnector dbConnector;
	ObservableList<Person> members = FXCollections.observableArrayList();
	
	@Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (!event.getAuthor().isBot() && args[0].equals(Bot.prefix+"poef")) {
			if (args.length == 2) {
				if (args[1].equals("link")) {
					displayLink(event);
				} else if (args[1].equalsIgnoreCase("ranking")) {
					dbConnector = new MySQLConnector();
					dbConnector.Connect();
					members = dbConnector.getPoef(1);
					displayRanking(1, members, event);
				}
			} else {

			}
		}
    }
	
	public void displayLink(GuildMessageReceivedEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Poef link");
		embed.setDescription("http://84.196.130.15/poefkesApp/login/");
		embed.setColor(Color.green);
		event.getChannel().sendMessage(embed.build()).queue();
		logger.info("link embed build and shown");
	}
	
	private void displayRanking(int product, ObservableList<Person> members, GuildMessageReceivedEvent event) {
		String name;
		switch (product) {
		case 1:
			name = "Bier";
			break;
		case 2:
			name = "Cola";
			break;
		case 3:
			name = "Ice-Tea";
			break;
		case 4:
			name = "Fles citron";
			break;
		case 5:
			name = "Chips";
			break;
		default:
			name = null;
		}
		
		if (name != null) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(name + " ranking");
			for (Person p : members) {
				embed.addField(p.getMemberName(), Integer.toString(p.getAmount()), false);
			}
			embed.setColor(Color.green);
			event.getChannel().sendMessage(embed.build()).queue();
			logger.info(name + " ranking embed build and shown");
		}
	}
	
}
