package de.mme.qbot.discordapi;


import de.mme.qbot.QbotApplication;
import de.mme.qbot.discordapi.eventlisteners.ChannelListener;
import de.mme.qbot.discordapi.eventlisteners.ReadyListener;
import de.mme.qbot.discordapi.slashcommands.EchoSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class DiscordService {

    @Autowired
    private Environment environment;

    static Logger logger = LoggerFactory.getLogger(QbotApplication.class);

    private static JDA jda;

    public DiscordService(Environment env) {

        this.environment = env;
        this.jda = CreateExampleJda();
    }

    private JDA CreateExampleJda()  {

        String discordToken = this.environment.getProperty("settings.discord.token");

        this.jda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("ExampleJDA running"));

        // Add Event Listeners ==============================================
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new ChannelListener());
        builder.addEventListeners(EchoSlashCommand.getInstance());
        // ==================================================================

        // Build the JDA Object
        this.jda =  builder.build();
        // Wait for the JDA Object to be ready for use
        try {
            this.jda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }

        // Now Add slash commands ===========================================
        // Those need to be send to the discord server.
        jda.updateCommands().addCommands(
                EchoSlashCommand.getInstance().getCommandData()
        ).queue();
        // ==================================================================

        return this.jda;
    }








}
