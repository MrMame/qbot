package de.mme.qbot.interaction.slashcommands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import org.springframework.stereotype.Component;

@Component

public class DareImportAllSlashCommand extends AbstractSlashCommand{
    public final static String COMMAND_NAME ="dare-import-all";
    public final static String COMMAND_DESCRIPTION ="Imports all dares from the provided textfile.";
    public final static String COMMAND_OPTION_IMPORTFILE_NAME ="importfile";
    public final static String COMMAND_OPTION_IMPORTFILE_DESCRIPTION ="File containing dares for import.";
    public final static String COMMAND_OPTION_APPENDDATA_NAME ="append-data";
    public final static String COMMAND_OPTION_APPENDDATA_DESCRIPTION ="True=all dares will be appended.False=Old dares will be overwritten.";



    public DareImportAllSlashCommand() {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION)
                .addOption(OptionType.ATTACHMENT,
                COMMAND_OPTION_IMPORTFILE_NAME,
                COMMAND_OPTION_IMPORTFILE_DESCRIPTION,
                        true)
                .addOption(OptionType.BOOLEAN,
                        COMMAND_OPTION_APPENDDATA_NAME,
                        COMMAND_OPTION_APPENDDATA_DESCRIPTION);

        this.setCommandData(commandData);

    }
}
