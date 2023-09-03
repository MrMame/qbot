package de.mme.qbot.controllers.discord;

import de.mme.qbot.controllers.discord.slashcommands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QbotSlashCommands {

    private DareAddSlashCommand dareAddSlashCommand;
    private DareExportAllSlashCommand dareExportAllSlashCommand;
    private DareGetAllSlashCommand dareGetAllSlashCommand;
    private DareGetUniqueRandomSlashCommand dareGetUniqueRandomSlashCommand;
    private DareImportAllSlashCommand dareImportAllSlashCommand;
    private DareRemoveAllSlashCommand dareRemoveAllSlashCommand;
    private DareRemoveByIdSlashCommand dareRemoveByIdSlashCommand;

    private QuestionAddSlashCommand questionAddSlashCommand;
    private QuestionExportAllSlashCommand questionExportAllSlashCommand;
    private QuestionGetAllSlashCommand questionGetAllSlashCommand;
    private QuestionGetUniqueRandomSlashCommand questionGetUniqueRandomSlashCommand;
    private QuestionImportAllSlashCommand questionImportAllSlashCommand;
    private QuestionRemoveAllSlashCommand questionRemoveAllSlashCommand;
    private QuestionRemoveByIdSlashCommand questionRemoveByIdSlashCommand;

    private List<ISlashCommand> slashCommands = new ArrayList<>();

    @Autowired
    public QbotSlashCommands(DareAddSlashCommand dareAddSlashCommand, DareExportAllSlashCommand dareExportAllSlashCommand, DareGetAllSlashCommand dareGetAllSlashCommand, DareGetUniqueRandomSlashCommand dareGetUniqueRandomSlashCommand, DareImportAllSlashCommand dareImportAllSlashCommand, DareRemoveAllSlashCommand dareRemoveAllSlashCommand, DareRemoveByIdSlashCommand dareRemoveByIdSlashCommand, QuestionAddSlashCommand questionAddSlashCommand, QuestionExportAllSlashCommand questionExportAllSlashCommand, QuestionGetAllSlashCommand questionGetAllSlashCommand, QuestionGetUniqueRandomSlashCommand questionGetUniqueRandomSlashCommand, QuestionImportAllSlashCommand questionImportAllSlashCommand, QuestionRemoveAllSlashCommand questionRemoveAllSlashCommand, QuestionRemoveByIdSlashCommand questionRemoveByIdSlashCommand) {
        this.dareAddSlashCommand = dareAddSlashCommand;
        this.dareExportAllSlashCommand = dareExportAllSlashCommand;
        this.dareGetAllSlashCommand = dareGetAllSlashCommand;
        this.dareGetUniqueRandomSlashCommand = dareGetUniqueRandomSlashCommand;
        this.dareImportAllSlashCommand = dareImportAllSlashCommand;
        this.dareRemoveAllSlashCommand = dareRemoveAllSlashCommand;
        this.dareRemoveByIdSlashCommand = dareRemoveByIdSlashCommand;
        this.questionAddSlashCommand = questionAddSlashCommand;
        this.questionExportAllSlashCommand = questionExportAllSlashCommand;
        this.questionGetAllSlashCommand = questionGetAllSlashCommand;
        this.questionGetUniqueRandomSlashCommand = questionGetUniqueRandomSlashCommand;
        this.questionImportAllSlashCommand = questionImportAllSlashCommand;
        this.questionRemoveAllSlashCommand = questionRemoveAllSlashCommand;
        this.questionRemoveByIdSlashCommand = questionRemoveByIdSlashCommand;

        slashCommands.add(dareAddSlashCommand);
        slashCommands.add(dareExportAllSlashCommand);
        slashCommands.add(dareGetAllSlashCommand);
        slashCommands.add(dareGetUniqueRandomSlashCommand);
        slashCommands.add(dareImportAllSlashCommand);
        slashCommands.add(dareRemoveAllSlashCommand);
        slashCommands.add(dareRemoveByIdSlashCommand);
        slashCommands.add(questionAddSlashCommand);
        slashCommands.add(questionExportAllSlashCommand);
        slashCommands.add(questionGetAllSlashCommand);
        slashCommands.add(questionGetUniqueRandomSlashCommand);
        slashCommands.add(questionImportAllSlashCommand);
        slashCommands.add(questionRemoveAllSlashCommand);
        slashCommands.add(questionRemoveByIdSlashCommand);

    }

    public DareAddSlashCommand getDareAddSlashCommand() {
        return dareAddSlashCommand;
    }

    public DareExportAllSlashCommand getDareExportAllSlashCommand() {
        return dareExportAllSlashCommand;
    }

    public DareGetAllSlashCommand getDareGetAllSlashCommand() {
        return dareGetAllSlashCommand;
    }

    public DareGetUniqueRandomSlashCommand getDareGetUniqueRandomSlashCommand() {
        return dareGetUniqueRandomSlashCommand;
    }

    public DareImportAllSlashCommand getDareImportAllSlashCommand() {
        return dareImportAllSlashCommand;
    }

    public DareRemoveAllSlashCommand getDareRemoveAllSlashCommand() {
        return dareRemoveAllSlashCommand;
    }

    public DareRemoveByIdSlashCommand getDareRemoveByIdSlashCommand() {
        return dareRemoveByIdSlashCommand;
    }

    public QuestionAddSlashCommand getQuestionAddSlashCommand() {
        return questionAddSlashCommand;
    }

    public QuestionExportAllSlashCommand getQuestionExportAllSlashCommand() {
        return questionExportAllSlashCommand;
    }

    public QuestionGetAllSlashCommand getQuestionGetAllSlashCommand() {
        return questionGetAllSlashCommand;
    }

    public QuestionGetUniqueRandomSlashCommand getQuestionGetUniqueRandomSlashCommand() {
        return questionGetUniqueRandomSlashCommand;
    }

    public QuestionImportAllSlashCommand getQuestionImportAllSlashCommand() {
        return questionImportAllSlashCommand;
    }

    public QuestionRemoveAllSlashCommand getQuestionRemoveAllSlashCommand() {
        return questionRemoveAllSlashCommand;
    }

    public QuestionRemoveByIdSlashCommand getQuestionRemoveByIdSlashCommand() {
        return questionRemoveByIdSlashCommand;
    }

    public List<ISlashCommand> getAsList() {
        return slashCommands;
    }

}
