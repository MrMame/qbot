# qBot
Random question Discord bot.
He will collect custom questions.



# How to Deploy

0. Rename "src\main\resources\DEFAULT_discord.properties" into 
"src\main\resources\discord.properties".
1. Do your necessary settings inside the "src\main\resources\discord.properties" file.
2. Run Mavens install-lifecycle

# How to add new SlashCommands
1. Create new SlashCommands Class inside de.mme.qbot.controllers.discord.slashcommand, e.g. EchoSlashCommand extending
AbstractSlashCommand. Set the CommandData Object, that contains the definitions of the new SlashCommands. Those Informations
will be shown the user when he typen a slash command in a discord channel. 
Also define a SlashCommandEventHandler containing the behaviour of the SlashCommand. 
2. Go to de.mme.qbot.configs.discord and add a new private Method, that will instatioate the new SlashCommand object.
Use this Method to add the new SlashCommand to the List<SlashCommand> inside the public createSlashCommandsList() Methods.
3. Now th SlashCommand will b registered automatically by the DiscordController.