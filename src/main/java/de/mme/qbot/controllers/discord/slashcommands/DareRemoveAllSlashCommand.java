package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component

public class DareRemoveAllSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="dare-remove-all";
    public final static String COMMAND_DESCRIPTION ="Removes all stored dares.";


    public DareRemoveAllSlashCommand() {


        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);


    }



}
