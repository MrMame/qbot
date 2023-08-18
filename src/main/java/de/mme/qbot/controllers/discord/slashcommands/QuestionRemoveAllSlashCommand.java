package de.mme.qbot.controllers.discord.slashcommands;

import de.mme.qbot.controllers.discord.SlashCommandFiredEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.function.Consumer;

public class QuestionRemoveAllSlashCommand extends AbstractSlashCommand{

    public final static String COMMAND_NAME ="question-remove-all";
    public final static String COMMAND_DESCRIPTION ="Removes all stored questions.";

    public QuestionRemoveAllSlashCommand(Consumer<SlashCommandFiredEvent> eventHandler) {

        CommandData commandData = Commands.slash(COMMAND_NAME,COMMAND_DESCRIPTION);

        this.setCommandData(commandData);
        this.setCommandHandler(eventHandler);

    }



}
