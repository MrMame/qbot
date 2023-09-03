package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.springframework.stereotype.Component;

@Component

public class DareRemoveByIdSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="dare-remove-byid";
    public final static String COMMAND_DESCRIPTION ="Removes the dare with the provided id";

    public final static String COMMAND_OPTION_ID_NAME ="id";
    public final static String COMMAND_OPTION_ID_DESCRIPTION ="ID of the dare to remove";




    public DareRemoveByIdSlashCommand() {


        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.INTEGER,
                        COMMAND_OPTION_ID_NAME,
                        COMMAND_OPTION_ID_DESCRIPTION,
                        true);

        this.setCommandData(commandData);

    }



}
