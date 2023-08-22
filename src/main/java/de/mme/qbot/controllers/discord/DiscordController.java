package de.mme.qbot.controllers.discord;


import de.mme.qbot.controllers.discord.slashcommands.*;
import de.mme.qbot.helper.discord.ImportExportFiles;
import de.mme.qbot.model.domain.Question;
import de.mme.qbot.services.IQuestionRepoService;
import de.mme.qbot.services.MaximumQuestionsStoredException;
import de.mme.qbot.services.QuestionRepoService;
import de.mme.qbot.helper.discord.embeds.QuestionEmbedFactory;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Controller
@PropertySource("classpath:discord.properties")
public class DiscordController implements EventListener{

    JDA jda;
    IQuestionRepoService questionService;
    List<ISlashCommand> slashCommandsList;

    Map<Object, Consumer<GenericEvent>> listenerHandlersMap = new HashMap<>();

    static Logger logger = LoggerFactory.getLogger(DiscordController.class);

    @Autowired
    public DiscordController(Environment env, IQuestionRepoService questionService) {
        // First create the Discord API Object
        this.jda = createDiscordApiObject(env);
        // Service for Question persitence
        this.questionService = questionService;

        // Register all used SlashCommands and its EventHandlers, used by the DiscordController
        this.slashCommandsList = new ArrayList<>();
        this.slashCommandsList.add(new QuestionAddSlashCommand(this::onQuestionAddSlashCommand));
        this.slashCommandsList.add(new QuestionGetAllSlashCommand(this::onQuestionGetAllSlashCommand));
        this.slashCommandsList.add(new QuestionGetUniqueRandomSlashCommand(this::onQuestionGetUniqueRandomSlashCommand));
        this.slashCommandsList.add(new QuestionRemoveAllSlashCommand(this::onQuestionRemoveAllSlashCommand));
        this.slashCommandsList.add(new QuestionRemoveByIdSlashCommand(this::onQuestionRemoveByIdSlashCommand));
        this.slashCommandsList.add(new QuestionExportAllSlashCommand(this::onQuestionExportAllSlashCommand));
        this.slashCommandsList.add(new QuestionImportAllSlashCommand(this::onQuestionImportAllSlashCommand));


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
            final MessageEmbed qemb = QuestionEmbedFactory.createNormalEmbed(uniqueQuestion);

            event.replyEmbeds(qemb).queue((msg)->{
                    msg.retrieveOriginal().queue((rMsg)->{
                        if(uniqueQuestion.getAnswerA() != null && !uniqueQuestion.getAnswerA().isEmpty()){
                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E6")).queue();}  // U+1F1E6 --> A
                        if(uniqueQuestion.getAnswerB() != null && !uniqueQuestion.getAnswerB().isEmpty()){
                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E7")).queue();}  // U+1F1E7 --> B
                        if(uniqueQuestion.getAnswerC() != null && !uniqueQuestion.getAnswerC().isEmpty()){
                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E8")).queue();}  // U+1F1E8 --> C
                        if(uniqueQuestion.getAnswerD() != null && !uniqueQuestion.getAnswerD().isEmpty()){
                            rMsg.addReaction(Emoji.fromUnicode("U+1F1E9")).queue();}  // U+1F1E9 --> D
                        if(uniqueQuestion.getAnswerE() != null && !uniqueQuestion.getAnswerE().isEmpty()){
                            rMsg.addReaction(Emoji.fromUnicode("U+1F1EA")).queue();}  // U+1F1E6 --> E
                    });

            });

        }catch(NoSuchElementException ex){
            event.replyEmbeds(QuestionEmbedFactory.createErrorEmbed("No question available. Please add some questions first.")).queue();
        }

    }
    private void onQuestionAddSlashCommand(SlashCommandFiredEvent event){
        QuestionAddSlashCommand questionAddSlashCommand =  ((QuestionAddSlashCommand) (event.getFiredSlashCommand()));

        MessageEmbed emb = QuestionEmbedFactory.createErrorEmbed("Error while adding question");

        try{
            Question newQuestion
                    = new Question(event.getOption(questionAddSlashCommand.COMMAND_OPTION_QUESTION_NAME, OptionMapping::getAsString),
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
            logger.warn("The maximum number of questions is already stored in repository");
            emb = QuestionEmbedFactory.createErrorEmbed("The maximum number of questions is already stored.\r\n"
                                                        + "You have to delete a question before adding a new one.\r\n"
                                                        + "Maximum number of allowed questions to store is " + QuestionRepoService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED);
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

        // Create the Exportfile containing all questions from repo
        String exportFileContent = ImportExportFiles.createExportFileContent(questionService.getAllQuestions());

        // Create the message for delivering the export
        MessageCreateBuilder messageCreateBuilder = new MessageCreateBuilder();
        messageCreateBuilder.addContent("qBot-Questions exportfile");

        // Add the exported data to the delivering message
        InputStream targetStream = new ByteArrayInputStream(exportFileContent.toString().getBytes());
        messageCreateBuilder.addFiles(FileUpload.fromData(targetStream,"qbot-export.txt"));

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
        }catch(NoImportFileFoundException e){
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("Importfile was not found.");
        }catch(MaximumQuestionsStoredException e){
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("The maximum number of questions is already stored.\r\n"
                    + "You have to delete a question before adding a new one.\r\n"
                    + "The number of allowed questions to store is " + QuestionRepoService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED);
        }catch(ErrorReadingImportFileException e){
            logger.error(e.toString());
            retMessageEmb = QuestionEmbedFactory.createErrorEmbed("Error while reading importfile");
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
        slashCommandsList.stream()
                .filter(ISlashCommand -> {return ISlashCommand.getCommandData().getName().equals(event.getName());})
                .forEach(ISlashCommand -> {
                    ISlashCommand.getCommandHandler().accept(new SlashCommandFiredEvent(ISlashCommand,event));});
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
        builder.setActivity(Activity.watching("ExampleJDA running"));

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


    private static List<Question> readQuestionsFromCommandImportfileOption(SlashCommandFiredEvent event) throws NoImportFileFoundException, ErrorReadingImportFileException {

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

        }catch(NullPointerException ex){
            throw new NoImportFileFoundException("Something is wrong with slashcommands option "
                    + QuestionImportAllSlashCommand.COMMAND_OPTION_IMPORTFILE_NAME,
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
    private void addQuestionsToRepository(List<Question> qList)throws MaximumQuestionsStoredException  {
        for (Question question : qList) {
            questionService.saveQuestion(question);
        }
    }


}
