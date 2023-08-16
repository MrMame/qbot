package de.mme.qbot.configs.discord;

import de.mme.qbot.controllers.discord.slashcommands.EchoSlashCommand;
import de.mme.qbot.controllers.discord.slashcommands.SlashCommand;
import de.mme.qbot.controllers.discord.slashcommands.TripleEchoSlashCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SlashCommandConfig {


    /**
     * Creates the list of SlashCommands used by the DiscordCntroller.
     * f you want to include new slash commands, you have to ensure to put the command
     * inside this configuration class. The DiscordController will load the new command automatically
     * @return ArrayList<AbstractSlashCommand> List of all SlashCommands, the Bot will provide.
     */
//    @Bean
//    public List<AbstractSlashCommand> mySlashCommandsList(){
//        List<AbstractSlashCommand> retList = new ArrayList<>();
//        retList.add(createEchoSlashCommand());
//        retList.add(createTripleEchoSlashCommand());
//        return retList;
//    }


    @Bean
    public List<SlashCommand> createSlashCommandsList(){
        List<SlashCommand> retList = new ArrayList<>();
        retList.add(createEchoSlashCommand());
        retList.add(createTripleEchoSlashCommand());
        return retList;
    }



    private EchoSlashCommand createEchoSlashCommand(){
        return new EchoSlashCommand();
    }


    private TripleEchoSlashCommand createTripleEchoSlashCommand(){
        return new TripleEchoSlashCommand();
    }


}
