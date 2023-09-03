package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.springframework.stereotype.Component;

@Component

public class DareAddSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="dare-add";
    public final static String COMMAND_DESCRIPTION ="Add a dare to the Bot";

    public final static String COMMAND_OPTION_DARE_NAME ="text";
    public final static String COMMAND_OPTION_DARE_DESCRIPTION ="Dare Text. Max 250 Characters";


    public DareAddSlashCommand() {


        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING,
                        COMMAND_OPTION_DARE_NAME,
                        COMMAND_OPTION_DARE_DESCRIPTION,
                        true)
                ;


        this.setCommandData(commandData);


    }



}
