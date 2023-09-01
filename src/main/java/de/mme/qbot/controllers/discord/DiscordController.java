package de.mme.qbot.controllers.discord;


import de.mme.qbot.controllers.discord.slashcommands.*;
import de.mme.qbot.helper.discord.DareEmbedFactory;
import de.mme.qbot.helper.discord.ImportExportFiles;
import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.helper.discord.QuestionMessageFactory;
import de.mme.qbot.model.domain.Question;
import de.mme.qbot.services.*;
import de.mme.qbot.services.DareService;
import de.mme.qbot.services.QuestionService;
import de.mme.qbot.helper.discord.QuestionEmbedFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Controller
@PropertySource("classpath:discord.properties")
public class DiscordController implements EventListener{

    JDA jda;
    IQuestionService questionService;
    IDareService dareService;
    List<ISlashCommand> slashCommandsList;
    Environment env;

    Map<Object, Consumer<GenericEvent>> listenerHandlersMap = new HashMap<>();

    static Logger logger = LoggerFactory.getLogger(DiscordController.class);

    @Autowired
    public DiscordController(Environment env, IQuestionService questionService, IDareService dareService) {
        this.env = env;
        // First create the Discord API Object
        this.jda = createDiscordApiObject(env);
        // Service for Question persitence
        this.questionService = questionService;
        this.dareService = dareService;

        // Register all used SlashCommands and its EventHandlers, used by the DiscordController
        this.slashCommandsList = new ArrayList<>();
        this.slashCommandsList.add(new QuestionAddSlashCommand(this::onQuestionAddSlashCommand));
        this.slashCommandsList.add(new DareAddSlashCommand(this::onDareAddSlashCommand));
        //this.slashCommandsList.add(new QuestionGetAllSlashCommand(this::onQuestionGetAllSlashCommand));
        //this.slashCommandsList.add(new DareGetAllSlashCommand(this::onDareGetAllSlashCommand));
        this.slashCommandsList.add(new QuestionGetUniqueRandomSlashCommand(this::onQuestionGetUniqueRandomSlashCommand));
        this.slashCommandsList.add(new DareGetUniqueRandomSlashCommand(this::onDareGetUniqueRandomSlashCommand));
        this.slashCommandsList.add(new QuestionRemoveAllSlashCommand(this::onQuestionRemoveAllSlashCommand));
        this.slashCommandsList.add(new DareRemoveAllSlashCommand(this::onDareRemoveAllSlashCommand));
        this.slashCommandsList.add(new QuestionRemoveByIdSlashCommand(this::onQuestionRemoveByIdSlashCommand));
        this.slashCommandsList.add(new DareRemoveByIdSlashCommand(this::onDareRemoveByIdSlashCommand));
        this.slashCommandsList.add(new QuestionExportAllSlashCommand(this::onQuestionExportAllSlashCommand));
        this.slashCommandsList.add(new DareExportAllSlashCommand(this::onDareExportAllSlashCommand));
        this.slashCommandsList.add(new QuestionImportAllSlashCommand(this::onQuestionImportAllSlashCommand));
        this.slashCommandsList.add(new DareImportAllSlashCommand(this::onDareImportAllSlashCommand));


        // Register all JDA Events and its EventHandlers, used by the DiscordController
        this.listenerHandlersMap.put(ChannelDeleteEvent.class,this::onChannelDeleteEvent);
        this.listenerHandlersMap.put(ChannelCreateEvent.class,this::onChannelCreateEvent);
        this.listenerHandlersMap.put(ReadyEvent.class,this::onReadyEvent);
        this.listenerHandlersMap.put(SlashCommandInteractionEvent.class,this::onSlashCommandReceivedEvent);

        // Send all finished SlashCommands to Discord, so they will be showing up the users
        sendSlashCommandsToDiscord(this.jda ,this.slashCommandsList);
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

    // --------------------------- SlashCommands Events (Add if necessary) ------------------------------------------

    private void onQuestionGetAllSlashCommand(SlashCommandFiredEvent event){
        QuestionGetAllSlashCommand qSc = ((QuestionGetAllSlashCommand) event.getFiredSlashCommand());

        StringBuilder allQuestions = new StringBuilder();
        this.questionService.getAllQuestions().forEach((question) -> {
            allQuestions.append(question.toString() + "\n\n");
        });

        if(allQuestions.isEmpty())allQuestions.append("No questions available.");

        MessageEmbed returnMessage = QuestionEmbedFactory.createSystemEmbed(allQuestions.toString());

        event.replyEmbeds(returnMessage)
                .setEphemeral(true)
                .queue();

    }
    private void onQuestionGetUniqueRandomSlashCommand(SlashCommandFiredEvent event){


        ;
        boolean hasAnswerA=false;
        try{
            final Question uniqueQuestion =  this.questionService.getUniqueRandomQuestion().get();
//            final MessageEmbed qemb = QuestionEmbedFactory.createNormalEmbed(uniqueQuestion);
            final MessageCreateData qmsg = QuestionMessageFactory.createQuestionMessage(uniqueQuestion);

            event.reply(qmsg).queue();

//            event.replyEmbeds(qemb).queue((msg)->{
//                    msg.retrieveOriginal().queue((rMsg)->{
//                        if(uniqueQuestion.getAnswerA() != null && !uniqueQuestion.getAnswerA().isEmpty()){
//                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E6")).queue();}  // U+1F1E6 --> A
//                        if(uniqueQuestion.getAnswerB() != null && !uniqueQuestion.getAnswerB().isEmpty()){
//                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E7")).queue();}  // U+1F1E7 --> B
//                        if(uniqueQuestion.getAnswerC() != null && !uniqueQuestion.getAnswerC().isEmpty()){
//                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E8")).queue();}  // U+1F1E8 --> C
//                        if(uniqueQuestion.getAnswerD() != null && !uniqueQuestion.getAnswerD().isEmpty()){
//                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E9")).queue();}  // U+1F1E9 --> D
//                        if(uniqueQuestion.getAnswerE() != null && !uniqueQuestion.getAnswerE().isEmpty()){
//                            rMsg.addReaction(Emoji.fromUnicode("U+1F1EA")).queue();}  // U+1F1E6 --> E
//                    });
//
//            });

        }catch(NoSuchElementException ex){
            event.replyEmbeds(QuestionEmbedFactory.createErrorEmbed("No question available. Please add some questions first.")).queue();
        }

    }
    private void onQuestionAddSlashCommand(SlashCommandFiredEvent event){
        QuestionAddSlashCommand questionAddSlashCommand =  ((QuestionAddSlashCommand) (event.getFiredSlashCommand()));

        MessageEmbed emb = QuestionEmbedFactory.createErrorEmbed("Error while adding question");

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

            emb = QuestionEmbedFactory.createSystemEmbed(questionAddReturnMessage);

        }catch(MaximumQuestionsStoredException ex){
            logger.error("The maximum number of questions is already stored in repository");
            emb = QuestionEmbedFactory.createErrorEmbed("The maximum number of questions is already stored.\r\n"
                                                        + "You have to delete a question before adding a new one.\r\n"
                                                        + "Maximum number of allowed questions to store is " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED);
        }catch(TextIsTooLongException ex){
            logger.error("User was trying to store a question with a field (question/answer) containing more characters than allowed.");
            emb = QuestionEmbedFactory.createErrorEmbed("Shorten your text first before trying to add the question again.\r\n"
                    + ex.getMessage());
        }

        event.replyEmbeds(emb)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionRemoveAllSlashCommand(SlashCommandFiredEvent event){

        questionService.removeAll();

        MessageEmbed emb = QuestionEmbedFactory.createSystemEmbed("All questions are removed.");

        event.replyEmbeds(emb)
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

        MessageEmbed emb = QuestionEmbedFactory.createSystemEmbed(retMessage.toString());

        event.replyEmbeds(emb)
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionExportAllSlashCommand(SlashCommandFiredEvent event){


        // Default Error message for initialization
        MessageEmbed messageEmb= QuestionEmbedFactory.createErrorEmbed("Error while trying to export questions.");

        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        try{

            // Create the Exportfile containing all questions from repo
            String exportFileContent = ImportExportFiles.createQuestionExportFileContent(questionService.getAllQuestions());

            // Add the exported data to the delivering message
            InputStream targetStream = new ByteArrayInputStream(exportFileContent.toString().getBytes());

            // Finish wo errors, so create the sytsem message
            messageEmb = QuestionEmbedFactory.createSystemEmbed("All questions exported.");

            messageCreateBuilder.addFiles(FileUpload.fromData(targetStream, ImportExportFiles.FILENAME_PREFIX_EXPORT_QUESTIONS + ".txt"));
            messageCreateBuilder.addEmbeds(messageEmb);

        }catch(Exception ex){
            logger.error("Error during question export." + ex.toString());
            messageEmb= QuestionEmbedFactory.createErrorEmbed("Error while trying to export questions.");;
            messageCreateBuilder.addEmbeds(messageEmb);
        }

        // Deliver message to discord
        event.reply(messageCreateBuilder.build())
                .setEphemeral(true)
                .queue();
    }
    private void onQuestionImportAllSlashCommand(SlashCommandFiredEvent event){

        // Default Error message for initialization
        MessageEmbed retMessageEmb= QuestionEmbedFactory.createErrorEmbed("Error while trying to import questions.");;

        // Read the file content into Question List
        try {
            List<Question> qList = readQuestionsFromCommandImportfileOption(event);
            RemoveAllQuestionsFromRepositioryIfNotAppendingOption(event);
            addQuestionsToRepository(qList);
            retMessageEmb = QuestionEmbedFactory.createSystemEmbed("Question import finished ok.");
        }catch(QuestionImportException e){
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("Some Questions could not be imported.\r\n" +
                                                                    "Please check the maximum length of question/answer text",
                                                                    e.getErrorQuestions());
        }
        catch(NoImportFileFoundException e){
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("Importfile was not found.");
        }catch(MaximumQuestionsStoredException e) {
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("The maximum number of questions to store is reached.\r\n"
                    + "Only the first " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED + " Questions are imported.\r\n"
                    + "The number of allowed questions to store is " + QuestionService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED,
                    e.getErrQuestion());
        }catch(ErrorReadingImportFileException e){
            logger.error(e.toString());
            retMessageEmb = DareEmbedFactory.createErrorEmbed("Error while reading importfile: " + e.toString());
        }finally {
            // Deliver message to discord-user
            event.replyEmbeds(retMessageEmb)
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

        MessageEmbed returnMessage = DareEmbedFactory.createSystemEmbed(allDares.toString());

        event.replyEmbeds(returnMessage)
                .setEphemeral(true)
                .queue();

    }
    private void onDareGetUniqueRandomSlashCommand(SlashCommandFiredEvent event){


        ;
        boolean hasAnswerA=false;
        try{
            final Dare uniqueDare =  this.dareService.getUniqueRandomDare().get();
            final MessageEmbed qemb = DareEmbedFactory.createNormalEmbed(uniqueDare);

            event.replyEmbeds(qemb).queue();


        }catch(NoSuchElementException ex){
            event.replyEmbeds(DareEmbedFactory.createErrorEmbed("No dare available. Please add some dares first.")).queue();
        }

    }
    private void onDareAddSlashCommand(SlashCommandFiredEvent event){
        DareAddSlashCommand dareAddSlashCommand =  ((DareAddSlashCommand) (event.getFiredSlashCommand()));

        MessageEmbed emb = DareEmbedFactory.createErrorEmbed("Error while adding dare");

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

            emb = DareEmbedFactory.createSystemEmbed(dareAddReturnMessage);

        }catch(MaximumDaresStoredException ex){
            logger.error("The maximum number of dares is already stored in repository");
            emb = DareEmbedFactory.createErrorEmbed("The maximum number of dares is already stored.\r\n"
                    + "You have to delete a dare before adding a new one.\r\n"
                    + "Maximum number of allowed dares to store is " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED);
        }catch(TextIsTooLongException ex){
            logger.error("User was trying to store a dare with a field (dare/answer) containing more characters than allowed.");
            emb = DareEmbedFactory.createErrorEmbed("Shorten your text first before trying to add the dare again.\r\n"
                    + ex.getMessage());
        }

        event.replyEmbeds(emb)
                .setEphemeral(true)
                .queue();
    }
    private void onDareRemoveAllSlashCommand(SlashCommandFiredEvent event){

        dareService.removeAll();

        MessageEmbed emb = DareEmbedFactory.createSystemEmbed("All dares are removed.");

        event.replyEmbeds(emb)
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

        MessageEmbed emb = DareEmbedFactory.createSystemEmbed(retMessage.toString());

        event.replyEmbeds(emb)
                .setEphemeral(true)
                .queue();
    }
    private void onDareExportAllSlashCommand(SlashCommandFiredEvent event){


        // Default Error message for initialization
        MessageEmbed messageEmb= DareEmbedFactory.createErrorEmbed("Error while trying to export dares.");

        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        try{

            // Create the Exportfile containing all dares from repo
            String exportFileContent = ImportExportFiles.createDareExportFileContent(dareService.getAllDares());

            // Add the exported data to the delivering message
            InputStream targetStream = new ByteArrayInputStream(exportFileContent.toString().getBytes());

            // Finish wo errors, so create the sytsem message
            messageEmb = DareEmbedFactory.createSystemEmbed("All dares exported.");

            messageCreateBuilder.addFiles(FileUpload.fromData(targetStream,ImportExportFiles.FILENAME_PREFIX_EXPORT_DARES + ".txt"));
            messageCreateBuilder.addEmbeds(messageEmb);

        }catch(Exception ex){
            logger.error("Error during dare export." + ex.toString());
            messageEmb= DareEmbedFactory.createErrorEmbed("Error while trying to export dares.");;
            messageCreateBuilder.addEmbeds(messageEmb);
        }

        // Deliver message to discord
        event.reply(messageCreateBuilder.build())
                .setEphemeral(true)
                .queue();
    }
    private void onDareImportAllSlashCommand(SlashCommandFiredEvent event){

        // Default Error message for initialization
        MessageEmbed retMessageEmb= DareEmbedFactory.createErrorEmbed("Error while trying to import dares.");;

        // Read the file content into Dare List
        try {
            List<Dare> qList = readDaresFromCommandImportfileOption(event);
            RemoveAllDaresFromRepositioryIfNotAppendingOption(event);
            addDaresToRepository(qList);
            retMessageEmb = DareEmbedFactory.createSystemEmbed("Dare import finished ok.");
        }catch(DareImportException e){
            logger.error(e.toString());
            retMessageEmb = DareEmbedFactory.createErrorEmbed("Some Dares could not be imported.\r\n" +
                            "Please check the maximum length of dare/answer text",
                    e.getErrorDares());
        }
        catch(NoImportFileFoundException e){
            logger.error(e.toString());
            retMessageEmb = DareEmbedFactory.createErrorEmbed("Importfile was not found.");
        }catch(MaximumDaresStoredException e) {
            logger.error(e.toString());
            retMessageEmb = DareEmbedFactory.createErrorEmbed("The maximum number of dares to store is reached.\r\n"
                            + "Only the first " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED + " Dares are imported.\r\n"
                            + "The number of allowed dares to store is " + DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED,
                    e.getErrDares());
        }catch(ErrorReadingImportFileException e){
            logger.error(e.toString());
            retMessageEmb = DareEmbedFactory.createErrorEmbed("Error while reading importfile: " + e.toString());
        }finally {
            // Deliver message to discord-user
            event.replyEmbeds(retMessageEmb)
                    .setEphemeral(true)
                    .queue();
        }
    }
    

    // =========================== Internal Privates ===============================================================
    private void sendSlashCommandsToDiscord(JDA jda,List<ISlashCommand> slashCommandsList){
        // Now Add slash commands ===========================================
        ArrayList<CommandData> commandDatas = new ArrayList();
        slashCommandsList.forEach((ISlashCommand)->{
            commandDatas.add(ISlashCommand.getCommandData());
        });
        jda.updateCommands().addCommands(commandDatas).queue();
    }
    private void onSlashCommandReceivedEvent(GenericEvent genericEvent){
        SlashCommandInteractionEvent event = (SlashCommandInteractionEvent) genericEvent;

        if(isGuildAllowed(event)==false){
            MessageEmbed errEmb = QuestionEmbedFactory.createErrorEmbed("Sorry. Your Server is not allowed to use this Bot.");
            // Deliver message to discord-user
            event.replyEmbeds(errEmb)
                    .setEphemeral(true)
                    .queue();
        }else {

            slashCommandsList.stream()
                    .filter(ISlashCommand -> {
                        return ISlashCommand.getCommandData().getName().equals(event.getName());
                    })
                    .forEach(ISlashCommand -> {
                        ISlashCommand.getCommandHandler().accept(new SlashCommandFiredEvent(ISlashCommand, event));
                    });
        }

    }
    @Override
    public void onEvent(GenericEvent genericEvent) {
        Consumer<GenericEvent> registeredEventHandler =this.listenerHandlersMap.get(genericEvent.getClass());
        if(registeredEventHandler!=null)registeredEventHandler.accept(genericEvent);
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
        builder.setActivity(Activity.playing("qBot at your service"));

        // Add Event Listeners ==============================================
        builder.addEventListeners(this);
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


}
