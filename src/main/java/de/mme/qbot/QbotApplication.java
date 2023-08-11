package de.mme.qbot;

import de.mme.qbot.discordapi.JDAFactory;
import de.mme.qbot.utils.ExternalConfigReader;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class QbotApplication {

	private static JDA jda;

	public static void main(String[] args) {
		SpringApplication.run(QbotApplication.class, args);



		// DEBUG Config Reader
		try {
			ExternalConfigReader cfg = new ExternalConfigReader("discordSettings.properties");
			//System.out.println("Discord Token is=" + cfg.readProperty("settings.discord.token"));
			jda = JDAFactory.CreateExampleJda( cfg.readProperty("settings.discord.token"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

}
