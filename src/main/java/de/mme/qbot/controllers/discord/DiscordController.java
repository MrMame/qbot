package de.mme.qbot.controllers.discord;



import de.mme.qbot.exceptions.DareImportException;
import de.mme.qbot.exceptions.ErrorReadingImportFileException;
import de.mme.qbot.exceptions.NoImportFileFoundException;
import de.mme.qbot.exceptions.QuestionImportException;
import de.mme.qbot.interaction.actionbuttons.IActionButton;
import de.mme.qbot.helper.discord.*;
import de.mme.qbot.interaction.modals.AnonymAnswerModal;
import de.mme.qbot.interaction.modals.IQbotModal;
import de.mme.qbot.interaction.slashcommands.*;
import de.mme.qbot.model.domain.Dare;

import de.mme.qbot.model.domain.Question;
import de.mme.qbot.services.*;
import de.mme.qbot.services.DareService;
import de.mme.qbot.services.QuestionService;

import de.mme.qbot.exceptions.MaximumDaresStoredException;
import de.mme.qbot.exceptions.MaximumQuestionsStoredException;
import de.mme.qbot.exceptions.TextIsTooLongException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import de.mme.qbot.helper.discord.MessageCreator.SystemMessageTypes;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Controller
@PropertySource("classpath:discord.properties")
public class DiscordController {



    private final IQuestionService questionService;
    private final IDareService dareService;
    private final MessageCreator msgCreator;
    private final QbotSlashCommands slashCommands;
    private final QbotButtons actionButtons;
    private final QBotModals qbotModals;
    private final QBotEvents qbotEvents;
    private final Environment env;

    private JDA jda;
//    private Map<String, Consumer<ActionButtonFiredEvent>> actionButtonsEventHandlersMap;
//    private Map<Object, Consumer<GenericEvent>> listenerHandlersMap = new HashMap<>();

    static Logger logger = LoggerFactory.getLogger(DiscordController.class);

    @Autowired

    public DiscordController(IQuestionService questionService, IDareService dareService, MessageCreator msgCreator, QbotSlashCommands slashCommands, QbotButtons actionButtons, Environment env, QBotModals qbModals, QBotEvents qbotEvents) {
        this.questionService = questionService;
        this.dareService = dareService;
        this.msgCreator = msgCreator;
        this.slashCommands = slashCommands;
        this.actionButtons = actionButtons;
        this.qbotModals = qbModals;
        this.qbotEvents = qbotEvents;
        this.env = env;
        // First create the Discord API Object
        this.jda = createDiscordApiObject(env);


        // Register EventHandlers for Slash commands
        this.slashCommands.getQuestionAddSlashCommand().setCommandHandler(this::onQuestionAddSlashCommand);
        this.slashCommands.getDareAddSlashCommand().setCommandHandler(this::onDareAddSlashCommand);
        this.slashCommands.getQuestionGetAllSlashCommand().setCommandHandler(this::onQuestionGetAllSlashCommand);
        this.slashCommands.getDareGetAllSlashCommand().setCommandHandler(this::onDareGetAllSlashCommand);
        this.slashCommands.getQuestionGetUniqueRandomSlashCommand().setCommandHandler(this::onQuestionGetUniqueRandomSlashCommand);
        this.slashCommands.getDareGetUniqueRandomSlashCommand().setCommandHandler(this::onDareGetUniqueRandomSlashCommand);
        this.slashCommands.getQuestionRemoveAllSlashCommand().setCommandHandler(this::onQuestionRemoveAllSlashCommand);
        this.slashCommands.getDareRemoveAllSlashCommand().setCommandHandler(this::onDareRemoveAllSlashCommand);
        this.slashCommands.getQuestionRemoveByIdSlashCommand().setCommandHandler(this::onQuestionRemoveByIdSlashCommand);
        this.slashCommands.getDareRemoveByIdSlashCommand().setCommandHandler(this::onDareRemoveByIdSlashCommand);
        this.slashCommands.getQuestionExportAllSlashCommand().setCommandHandler(this::onQuestionExportAllSlashCommand);
        this.slashCommands.getDareExportAllSlashCommand().setCommandHandler(this::onDareExportAllSlashCommand);
        this.slashCommands.getQuestionImportAllSlashCommand().setCommandHandler(this::onQuestionImportAllSlashCommand);
        this.slashCommands.getDareImportAllSlashCommand().setCommandHandler(this::onDareImportAllSlashCommand);

        this.actionButtons.getAnonymAnswerButton().setEventHandler(this::onDoAnonymAnswerButtonPressed);
        this.actionButtons.getGetQuestionButton().setEventHandler(this::onGetQuestionButtonPressed);
        this.actionButtons.getGetDareButton().setEventHandler(this::onGetDareButtonPressed);
        this.actionButtons.getVoteAnswerAButton().setEventHandler(this::onVoteAnswerAButtonPressed);
        this.actionButtons.getVoteAnswerBButton().setEventHandler(this::onVoteAnswerBButtonPressed);
        this.actionButtons.getVoteAnswerCButton().setEventHandler(this::onVoteAnswerCButtonPressed);
        this.actionButtons.getVoteAnswerDButton().setEventHandler(this::onVoteAnswerDButtonPressed);
        this.actionButtons.getVoteAnswerEButton().setEventHandler(this::onVoteAnswerEButtonPressed);

        this.qbotModals.getAnonymAnswerModal().setEventHandler(this::onReceiveAnonymAnswerModal);

        this.qbotEvents.getQbotChannelCreateEvent().setEventHandler(this::onChannelCreateEvent);
        this.qbotEvents.getQbotChannelDeleteEvent().setEventHandler(this::onChannelDeleteEvent);


        // Send all finished SlashCommands to Discord, so they will be showing up the users
        sendSlashCommandsToDiscord(this.jda ,slashCommands);
    }



    // --------------------------- Normal Events (Add if necessary) --------------------------------------------------
    private void onChannelDeleteEvent(GenericEvent genericEvent){
        ChannelDeleteEvent event = (ChannelDeleteEvent) genericEvent;
        logger.info("FIRED ChannelDeleteEvent - Channel '"
                + event.getChannel().getName()
                + "' was deleted");
    }
    private void onChannelCreateEvent(GenericEvent genericEvent){
        ChannelCreateEvent event = (ChannelCreateEvent) genericEvent;
        logger.info("FIRED ChannelCreateEvent - Channel '"
                + event.getChannel().getName()
                + "' was created");
    }
    private void onReadyEvent(GenericEvent genericEvent) {
        ReadyEvent event = (ReadyEvent) genericEvent;
        this.logger.info("FIRED ReadyEvent - API is ready");
    }


    // --------------------------- Action Buttons Handler (Add if necessary) ----------------------------------------


    private void onGetQuestionButtonPressed(ButtonInteractionEvent event){
        final Optional<Question> uniqueQuestion =  this.questionService.getUniqueRandomQuestion();
        final MessageCreateData qmsg = msgCreator.createQuestionMessage(uniqueQuestion);
        event.reply(qmsg).queue();
    }
    private void onGetDareButtonPressed(ButtonInteractionEvent event){
        final Optional<Dare> uniqueDare =  this.dareService.getUniqueRandomDare();
        final MessageCreateData dMsg = msgCreator.createDareMessage(uniqueDare);
        event.reply(dMsg).queue();
    }
    private void onDoAnonymAnswerButtonPressed(ButtonInteractionEvent event){
        Modal modal = this.qbotModals.getAnonymAnswerModal().getModal();
        event.replyModal(modal).queue();
    }

    private void onVoteAnswerAButtonPressed(ButtonInteractionEvent event){
        toggleUsernameInAnswer(event,MessageCreator.EMBED_ANSWER_A_TITLE);
    }
    private void onVoteAnswerBButtonPressed(ButtonInteractionEvent event){
        toggleUsernameInAnswer(event,MessageCreator.EMBED_ANSWER_B_TITLE);
    }
    private void onVoteAnswerCButtonPressed(ButtonInteractionEvent event){
        toggleUsernameInAnswer(event,MessageCreator.EMBED_ANSWER_C_TITLE);
    }
    private void onVoteAnswerDButtonPressed(ButtonInteractionEvent event){
        toggleUsernameInAnswer(event,MessageCreator.EMBED_ANSWER_D_TITLE);
    }
    private void onVoteAnswerEButtonPressed(ButtonInteractionEvent event){
        toggleUsernameInAnswer(event,MessageCreator.EMBED_ANSWER_E_TITLE);
    }

    // --------------------------- SlashCommands Events (Add if necessary) ------------------------------------------

    private void onQuestionGetAllSlashCommand(SlashCommandFiredEvent event){
        QuestionGetAllSlashCommand qSc = ((QuestionGetAllSlashCommand) event.getFiredSlashCommand());

        StringBuilder allQuestionsText = new StringBuilder();
        this.questionService.getAllQuestions().forEach((question) -> {
            allQuestionsText.append(question.toString() + "\n\n");
        });
        // If we had no questions at all, we append only the Info Text
        if(allQuestionsText.isEmpty()) allQuestionsText.append("No questions available.");

        final MessageCreateData dMsg = msgCreator.createSystemMessage(SystemMessageTypes.Info,allQuestionsText.toString());

        event.reply(dMsg)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionGetUniqueRandomSlashCommand(SlashCommandFiredEvent event){
            final Optional<Question> uniqueQuestion =  this.questionService.getUniqueRandomQuestion();
            final MessageCreateData qmsg = msgCreator.createQuestionMessage(uniqueQuestion);
            event.reply(qmsg).queue();
    }
    private void onQuestionAddSlashCommand(SlashCommandFiredEvent event){
        QuestionAddSlashCommand questionAddSlashCommand =  ((QuestionAddSlashCommand) (event.getFiredSlashCommand()));

        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Error while adding question");

        try{
            Question newQuestion
                    = new Question(0L,
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_QUESTION_NAME, OptionMapping::getAsString),
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_ANSWER_A_NAME, OptionMapping::getAsString),
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_ANSWER_B_NAME, OptionMapping::getAsString),
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_ANSWER_C_NAME, OptionMapping::getAsString),
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_ANSWER_D_NAME, OptionMapping::getAsString),
                    event.getOption(questionAddSlashCommand.COMMAND_OPTION_ANSWER_E_NAME, OptionMapping::getAsString)
            );

            Question savedQuestion = this.questionService.saveQuestion(newQuestion);

            String questionAddReturnMessage;
            questionAddReturnMessage = (savedQuestion==null)?
                    "Error - Couldn't add question!"
                    :"OK - Added Question \n" + savedQuestion.toString();

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,questionAddReturnMessage);

        }catch(MaximumQuestionsStoredException ex){
            logger.error("The maximum number of questions is already stored in repository");
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"The maximum number of questions is already stored.\r\n"
                                                        + "You have to delete a question before adding a new one.\r\n"
                                                        + "Maximum number of allowed questions to store is " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED);
        }catch(TextIsTooLongException ex){
            logger.error("User was trying to store a question with a field (question/answer) containing more characters than allowed.");
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Shorten your text first before trying to add the question again.\r\n"
                    + ex.getMessage());
        }

        event.reply(msg)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionRemoveAllSlashCommand(SlashCommandFiredEvent event){

        questionService.removeAll();

        final MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"All questions are removed.");

        event.reply(msg)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionRemoveByIdSlashCommand(SlashCommandFiredEvent event){

        long questionId = event.getOption(QuestionRemoveByIdSlashCommand.COMMAND_OPTION_ID_NAME, OptionMapping::getAsLong);

        Optional<Question> targetQuestion = questionService.getQuestionById(questionId);

        StringBuilder retMessage = new StringBuilder();

        if(targetQuestion.isEmpty()){
            retMessage.append("There is no question found with id " + questionId
                    + "\n No question was deleted.");
        }else{
            questionService.removeQuestionById(questionId);
            retMessage.append("Question with id " + questionId
                    + "\n was deleted."
                    + "\n\n " + targetQuestion.get().toString() );
        }

        final MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,retMessage.toString());

        event.reply(msg)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionExportAllSlashCommand(SlashCommandFiredEvent event){

        MessageCreateData msg;
        try{
            // Create the Exportfile containing all questions from repo
            String exportFileContent = ImportExportFiles.createQuestionExportFileContent(questionService.getAllQuestions());

            // Add the exported data to the delivering message
            InputStream targetStream = new ByteArrayInputStream(exportFileContent.toString().getBytes());
            // Message
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,
                    "All questions exported.",
                    FileUpload.fromData(targetStream, ImportExportFiles.FILENAME_PREFIX_EXPORT_QUESTIONS + ".txt"));

        }catch(Exception ex){
            logger.error("Error during question export." + ex.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Error while trying to export questions.");
        }
        // Deliver message to discord
        event.reply(msg)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionImportAllSlashCommand(SlashCommandFiredEvent event){

        // Default Error message for initialization
        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"InitMessage");

        // Read the file content into Question List
        try {
            List<Question> qList = readQuestionsFromCommandImportfileOption(event);
            RemoveAllQuestionsFromRepositioryIfNotAppendingOption(event);
            addQuestionsToRepository(qList);
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"Question import finished ok.");
        }catch(QuestionImportException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Some Questions could not be imported.\r\n" +
                                                                    "Please check the maximum length of question/answer text",
                                                                    e.getErrorEntites());
        }
        catch(NoImportFileFoundException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Importfile was not found.");

        }catch(MaximumQuestionsStoredException e) {
            logger.error(e.toString());

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"The maximum number of questions to store is reached.\r\n"
                    + "Only the first " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED + " Questions are imported.\r\n"
                    + "The number of allowed questions to store is " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED,
                    e.getErrEntities());
        }catch(ErrorReadingImportFileException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Error while reading importfile: " + e.toString());

        }finally {
            // Deliver message to discord-user
            event.reply(msg)
                    .setEphemeral(true)
                    .queue();
        }
    }



    private void onDareGetAllSlashCommand(SlashCommandFiredEvent event){
        DareGetAllSlashCommand qSc = ((DareGetAllSlashCommand) event.getFiredSlashCommand());

        StringBuilder allDares = new StringBuilder();
        this.dareService.getAllDares().forEach((dare) -> {
            allDares.append(dare.toString() + "\n\n");
        });

        if(allDares.isEmpty())allDares.append("No dares available.");


        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,allDares.toString());

        event.reply(msg)

                .setEphemeral(true)
                .queue();

    }
    private void onDareGetUniqueRandomSlashCommand(SlashCommandFiredEvent event){

        Optional<Dare> uniqueDare =  this.dareService.getUniqueRandomDare();
        MessageCreateData msg = msgCreator.createDareMessage(uniqueDare);
        event.reply(msg).queue();

    }
    private void onDareAddSlashCommand(SlashCommandFiredEvent event){
        DareAddSlashCommand dareAddSlashCommand =  ((DareAddSlashCommand) (event.getFiredSlashCommand()));


        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"INIT Text");

        try{
            Dare newDare
                    = new Dare(0L,
                    event.getOption(DareAddSlashCommand.COMMAND_OPTION_DARE_NAME, OptionMapping::getAsString)
            );

            Dare savedDare = this.dareService.saveDare(newDare);

            String dareAddReturnMessage;
            dareAddReturnMessage = (savedDare==null)?
                    "Error - Couldn't add dare!"
                    :"OK - Added Dare \n" + savedDare.toString();

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,dareAddReturnMessage);

        }catch(MaximumDaresStoredException ex){
            logger.error("The maximum number of dares is already stored in repository");
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"The maximum number of dares is already stored.\r\n"

                    + "You have to delete a dare before adding a new one.\r\n"
                    + "Maximum number of allowed dares to store is " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED);
        }catch(TextIsTooLongException ex){
            logger.error("User was trying to store a dare with a field (dare/answer) containing more characters than allowed.");

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Shorten your text first before trying to add the dare again.\r\n"
                    + ex.getMessage());
        }

        event.reply(msg)

                .setEphemeral(true)
                .queue();
    }
    private void onDareRemoveAllSlashCommand(SlashCommandFiredEvent event){

        dareService.removeAll();
        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"All dares are removed");
        event.reply(msg)

                .setEphemeral(true)
                .queue();
    }
    private void onDareRemoveByIdSlashCommand(SlashCommandFiredEvent event){

        long dareId = event.getOption(DareRemoveByIdSlashCommand.COMMAND_OPTION_ID_NAME, OptionMapping::getAsLong);

        Optional<Dare> targetDare = dareService.getDareById(dareId);

        StringBuilder retMessage = new StringBuilder();

        if(targetDare.isEmpty()){
            retMessage.append("There is no dare found with id " + dareId
                    + "\n No dare was deleted.");
        }else{
            dareService.removeDareById(dareId);
            retMessage.append("Dare with id " + dareId
                    + "\n was deleted."
                    + "\n\n " + targetDare.get().toString() );
        }


        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info, retMessage.toString());

        event.reply(msg)

                .setEphemeral(true)
                .queue();
    }
    private void onDareExportAllSlashCommand(SlashCommandFiredEvent event){


        // Default Error message for initialization

        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"INIT text");


        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        try{

            // Create the Exportfile containing all dares from repo
            String exportFileContent = ImportExportFiles.createDareExportFileContent(dareService.getAllDares());

            // Add the exported data to the delivering message
            InputStream targetStream = new ByteArrayInputStream(exportFileContent.toString().getBytes());

            // Finish wo errors, so create the sytsem message

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,
                    "All dares exported",
                    FileUpload.fromData(targetStream,ImportExportFiles.FILENAME_PREFIX_EXPORT_DARES + ".txt"));

        }catch(Exception ex){
            logger.error("Error during dare export." + ex.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Error while trying to export dares.");
        }

        // Deliver message to discord
        event.reply(msg)

                .setEphemeral(true)
                .queue();
    }
    private void onDareImportAllSlashCommand(SlashCommandFiredEvent event){

        // Default Error message for initialization

        MessageCreateData msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"INIT Text");


        // Read the file content into Dare List
        try {
            List<Dare> qList = readDaresFromCommandImportfileOption(event);
            RemoveAllDaresFromRepositioryIfNotAppendingOption(event);
            addDaresToRepository(qList);

            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"Dare import finished ok.");
        }catch(DareImportException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Info,"Some Dares could not be imported.\r\n" +
                            "Please check the maximum length of dare/answer text",
                    e.getErrorEntites());
        }
        catch(NoImportFileFoundException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Importfile was not found");
        }catch(MaximumDaresStoredException e) {
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"The maximum number of dares to store is reached.\r\n"
                            + "Only the first " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED + " Dares are imported.\r\n"
                            + "The number of allowed dares to store is " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED,
                    e.getErrEntities());
        }catch(ErrorReadingImportFileException e){
            logger.error(e.toString());
            msg = msgCreator.createSystemMessage(SystemMessageTypes.Error,"Error while reading importfile: " + e.toString());
        }finally {
            // Deliver message to discord-user
            event.reply(msg)

                    .setEphemeral(true)
                    .queue();
        }
    }

    // =========================== MODALs Events ===============================================================

    private void onReceiveAnonymAnswerModal(ModalInteractionEvent event){
        event.reply("Jemand hat geantwortet: \r\n\r\n"
                    + event.getValue(AnonymAnswerModal.MODAL_ANONYM_ASNWER_INPUTFIELD_ID).getAsString()
        ).queue();
    }


    // =========================== Internal Privates ===============================================================
    private void sendSlashCommandsToDiscord(JDA jda,QbotSlashCommands slashCommands){
        // Now Add slash commands ===========================================
        ArrayList<CommandData> commandDatas = new ArrayList();
        slashCommands.getAsList().forEach((ISlashCommand)->{
            commandDatas.add(ISlashCommand.getCommandData());
        });
        jda.updateCommands().addCommands(commandDatas).queue();
    }


    private JDA createDiscordApiObject(Environment env){


        String discordToken = env.getProperty("settings.discord.token");

        JDA retJda = null;

        JDABuilder builder = JDABuilder.createDefault(discordToken);


        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.playing("at your service"));

        // Add Event Listeners ==============================================
        for(ISlashCommand slash:this.slashCommands.getAsList()){
            builder.addEventListeners(slash);
        }
        for(IActionButton button:this.actionButtons.getAsList()){
            builder.addEventListeners(button);
        }
        for(IQbotModal modal:this.qbotModals.getAsList()){
            builder.addEventListeners(modal);
        }
        for(EventListener event:this.qbotEvents.getAsList()){
            builder.addEventListeners(event);
        }
        // ==================================================================

        // Build the JDA Object
        retJda =  builder.build();
        // Wait for the JDA Object to be ready for use
        try {
            retJda.awaitReady();
            logger.info("...JDA Object was created and is ready to use.");
        } catch (InterruptedException e) {
            logger.warn("Abort waiting for JDA Object to get ready. There is no JDA Object available.");
            throw new RuntimeException(e);
        }

        return retJda;
    }


    private static List<Question> readQuestionsFromCommandImportfileOption(SlashCommandFiredEvent event)
            throws NoImportFileFoundException, ErrorReadingImportFileException {

        List<Question> retList = new ArrayList<>();

        try{
            // Download the attached file and create a buffered reader from its content
            Message.Attachment theAttachment = event.getOptions()
                    .stream()
                    .filter((optMap)->optMap.getName().equals(QuestionImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME))
                    .findFirst()
                    .get()
                    .getAsAttachment();
            BufferedReader br = new BufferedReader(new InputStreamReader(theAttachment.getProxy().download().get()));

            // Create List of Questions from the Importfile content
            retList = ImportExportFiles.ReadQuestionsFromImportfile(br);

        }catch(NoSuchElementException ex){
            throw new NoImportFileFoundException("No File was found with given importfile name. "
                    + QuestionImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME,
                    ex);
        }catch(NullPointerException ex){
            throw new NoImportFileFoundException("Something is wrong with slashcommands option "
                    + QuestionImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME,
                    ex);
        }catch(InterruptedException | ExecutionException ex) {
            throw new ErrorReadingImportFileException("Importfile reading thread was interrupted somehow.", ex);
        }

        return retList;

    }
    private static List<Dare> readDaresFromCommandImportfileOption(SlashCommandFiredEvent event)
            throws NoImportFileFoundException, ErrorReadingImportFileException {

        List<Dare> retList = new ArrayList<>();

        try{
            // Download the attached file and create a buffered reader from its content
            Message.Attachment theAttachment = event.getOptions()
                    .stream()
                    .filter((optMap)->optMap.getName().equals(DareImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME))
                    .findFirst()
                    .get()
                    .getAsAttachment();
            BufferedReader br = new BufferedReader(new InputStreamReader(theAttachment.getProxy().download().get()));

            // Create List of Dares from the Importfile content
            retList = ImportExportFiles.ReadDaresFromImportfile(br);

        }catch(NoSuchElementException ex){
            throw new NoImportFileFoundException("No File was found with given importfile name. "
                    + DareImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME,
                    ex);
        }catch(NullPointerException ex){
            throw new NoImportFileFoundException("Something is wrong with slashcommands option "
                    + DareImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME,
                    ex);
        }catch(InterruptedException | ExecutionException ex) {
            throw new ErrorReadingImportFileException("Importfile reading thread was interrupted somehow.", ex);
        }

        return retList;

    }
    private void RemoveAllQuestionsFromRepositioryIfNotAppendingOption(SlashCommandFiredEvent event) {
        // If user doesn't want to append, clear the database
        Boolean appendData = event.getOption(QuestionImportAllSlashCommand.COMMAND_OPTION_APPENDDATA_NAME, OptionMapping::getAsBoolean);
        Boolean clearBeforeImport = (appendData!=null && appendData==false);
        if(clearBeforeImport){questionService.removeAll();}
    }
    private void RemoveAllDaresFromRepositioryIfNotAppendingOption(SlashCommandFiredEvent event) {
        // If user doesn't want to append, clear the database
        Boolean appendData = event.getOption(QuestionImportAllSlashCommand.COMMAND_OPTION_APPENDDATA_NAME, OptionMapping::getAsBoolean);
        Boolean clearBeforeImport = (appendData!=null && appendData==false);
        if(clearBeforeImport){dareService.removeAll();}
    }
    private void addQuestionsToRepository(List<Question> qList)
            throws MaximumQuestionsStoredException, QuestionImportException  {
        List<Question> errQuestion = new ArrayList<>();
        // Try to save questions to repository
        for (Question question : qList) {
            try {
                questionService.saveQuestion(question);
            } catch (TextIsTooLongException ex) {
                errQuestion.add(question);
            } catch (MaximumQuestionsStoredException ex){
                // If we try to store more then the maximum, we rethrow but with Question error list this time,
                // because there also could be some import troubles that would be interesting for the user to know.
                throw new MaximumQuestionsStoredException(ex,errQuestion);
            }
        }
        // If we had trouble with importing some questions, we throw an exception
        if(!errQuestion.isEmpty()){
            throw new QuestionImportException(errQuestion.size() + " Questions could not be imported."
                    , errQuestion);}

    }

    private void addDaresToRepository(List<Dare> dList)
            throws MaximumDaresStoredException, DareImportException  {
        List<Dare> errDare = new ArrayList<>();
        // Try to save questions to repository
        for (Dare dare : dList) {
            try {
                dareService.saveDare(dare);
            } catch (TextIsTooLongException ex) {
                errDare.add(dare);
            } catch (MaximumDaresStoredException ex){
                // If we try to store more then the maximum, we rethrow but with Question error list this time,
                // because there also could be some import troubles that would be interesting for the user to know.
                throw new MaximumDaresStoredException(ex,errDare);
            }
        }
        // If we had trouble with importing some questions, we throw an exception
        if(!errDare.isEmpty()){
            throw new DareImportException(errDare.size() + " Dares could not be imported."
                    , errDare);}

    }

    private boolean isGuildAllowed(SlashCommandInteractionEvent event){
        Boolean retBool = false;
        if(event.isFromGuild()){
            String[] allowedIds = env.getProperty("settings.discord.allowedguild").split("#");
            retBool = Arrays.stream(allowedIds).anyMatch(id->id.equals(event.getGuild().getId()));
        }
        return retBool;
    }
    private boolean isGuildAllowed(ButtonInteractionEvent event){
        Boolean retBool = false;
        if(event.isFromGuild()){
            String[] allowedIds = env.getProperty("settings.discord.allowedguild").split("#");
            retBool = Arrays.stream(allowedIds).anyMatch(id->id.equals(event.getGuild().getId()));
        }
        return retBool;
    }

private void toggleUsernameInAnswer(ButtonInteractionEvent event,String targetAnswerEmbedTitle){
    List<MessageEmbed> oldEmbeds = event.getMessage().getEmbeds();
    List<MessageEmbed> newEmbeds = EmbedsCreator.toggleUsernameInDescription(targetAnswerEmbedTitle,oldEmbeds,event.getUser());
    event.editMessageEmbeds(newEmbeds).queue();

}

}
