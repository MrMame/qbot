# qBot
Random question Discord bot.
He will collect custom questions.



# How to Deploy

0. Rename "src\main\resources\DEFAULT_discord.properties" into 
"src\main\resources\discord.properties".
1. Do your necessary settings inside the "src\main\resources\discord.properties" file.
2. Run Mavens install-lifecycle
3. Inside target folder the runable jar file qbot-<versionNumber>.jar can be copied and used.


# Discord-Bot Interactions

There are thre different ways to communicatoe/react with the qbot by using Discord :

- qbot.interaction.events
- qbot.interaction.modals
- qbot.interaction.actionbuttons

All three of them needs bo re registeres in the JDA object before building the object.
This is done by using the JDAs builder addEventListener(EventListener) Method. The following
code registers those three interaction types. All interactions are stored in a list, 
collecting all of the same type. Each element of the list is added.
Those lists are created using the component classes in qbot.controllers.discord by Spring.

If you want to register some new interaction class, you have to ensure to add this new type to on of
the three "Factory" classes QBotButtons,QBotEvents,QBotModals,QBotSlashCommands.
The new class has to be returned within those lists. As soon as they are added, they will automatically 
been registered to the JDA object.



``` java
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
```

Events are triggered by discord. E.g. a user is creating a channel, writes a message and so on.


## Create new Event

1. Create a new Event class inside qbot.interactions.events, whill will extend ListenerAdapter. You have to annotate 
the class as a component, so Spring will create the class by itself.
``` java
    @Component
    public class QbotChannelCreateEvent extends ListenerAdapter{ //implements EventListener {
        ...
    }
```
2. Inside this class, overwrite the Method of the Event you want to react to by overwriting the ListenerAdaptes Method.
``` java
    
@Component
public class QbotChannelCreateEvent extends ListenerAdapter{ //implements EventListener {
    ...
    @Override
    public void onChannelCreate(ChannelCreateEvent event) {
        super.onChannelCreate(event);
        this.eventHandler.accept(event);
    }

}
```
3. Also put in a filed/getter/setter for stroing an external eventhandler for the event, that will get fired
when the event was triggerd. Keep in mind to use the right event-parameter type for the event you want.
``` java
   
@Component
public class QbotChannelCreateEvent extends ListenerAdapter{ //implements EventListener {

    Consumer<ChannelCreateEvent> eventHandler;

    public Consumer<ChannelCreateEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(Consumer<ChannelCreateEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }
    ...
}
```

4. Open the qbot.controllers.discord.QBotEvents class. Add an private field to store the new EventClass. Put in the New
classinto the constructor, so spring will autowire it. inside the Construcotr add the Event of the constructors 
parameter and add it to the list of events.
``` java
@Component
public class QBotEvents {

    private QbotChannelCreateEvent qbotChannelCreateEvent;
  
    private List<EventListener> events = new ArrayList<>();

    public QBotEvents(QbotChannelCreateEvent qbotChannelCreateEvent) {
        this.qbotChannelCreateEvent = qbotChannelCreateEvent;

        this.events.add(qbotChannelCreateEvent);
    }
    ...
}
```
5. At least, add a getter Method to access the new Event stored inside
the private field.
``` java
    @Component
    public class QBotEvents {
        
        ...
        
        public List<EventListener> getAsList(){
            return this.events;
        }
    
        public QbotChannelCreateEvent getQbotChannelCreateEvent() {
            return qbotChannelCreateEvent;
        }
        
        ...
    }
```



# Create new SlashCommand
1. Create new SlashCommands Class inside de.mme.qbot.controllers.discord.slashcommand, e.g. EchoSlashCommand extending
AbstractSlashCommand. Set the CommandData Object, that contains the definitions of the new SlashCommands. Those Informations
will be shown the user when he typen a slash command in a discord channel. 
Also define a SlashCommandEventHandler containing the behaviour of the SlashCommand. 
2. Go to de.mme.qbot.configs.discord and add a new private Method, that will instatioate the new SlashCommand object.
Use this Method to add the new SlashCommand to the List<SlashCommand> inside the public createSlashCommandsList() Methods.
3. Now th SlashCommand will b registered automatically by the DiscordController.