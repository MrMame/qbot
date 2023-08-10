package de.mme.qbot.discordapi;

import de.mme.qbot.discordapi.eventlisteners.ChannelListener;
import de.mme.qbot.discordapi.eventlisteners.ReadyListener;
import de.mme.qbot.discordapi.slashcommands.EchoSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDAFactory {

    static Logger logger = LoggerFactory.getLogger(JDAFactory.class);

    public static JDA CreateExampleJda(String discordToken)  {

        JDA returnJda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("ExampleJDA running"));
        // Add Event Listeners
        addEventListeners(builder);
        // Build the JDA Object
        returnJda =  builder.build();
        // Wait for the JDA Object to be ready for use
        try {
            returnJda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }
        // Now Add slash commands. Those will be send to the discord server.
        addSlashCommands(returnJda);

        return returnJda;
    }


    private static void addEventListeners(JDABuilder builder){
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new ChannelListener());
    }

    private static void addSlashCommands(JDA jda){
        jda.updateCommands().addCommands(
                new EchoSlashCommand().getCommandData()
        ).queue();
    }



}
