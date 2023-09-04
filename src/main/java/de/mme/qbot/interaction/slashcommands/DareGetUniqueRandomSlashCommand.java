package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.springframework.stereotype.Component;

@Component

public class DareGetUniqueRandomSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="dare-get-unique-random";
    public final static String COMMAND_DESCRIPTION ="Returns a random dare. No duplicates will be returned until all dares were returned";



    public DareGetUniqueRandomSlashCommand() {
        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);

    }
}
