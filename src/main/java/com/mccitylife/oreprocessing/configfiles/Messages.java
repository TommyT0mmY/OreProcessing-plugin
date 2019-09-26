package com.mccitylife.oreprocessing.configfiles;

import com.mccitylife.oreprocessing.OreProcessing;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Messages {

    private OreProcessing mainClass = OreProcessing.getInstance();
    private FileConfiguration messagesConfig;

    public Messages () {
        loadMessages(true);
    }

    private Map <String, String> messagesMap = new HashMap<>();
    File messagesConfigFile = new File(mainClass.datafolder, "messages.yml");

    private List<String> everyKey = new ArrayList<>(Arrays.asList (
            "only-players-command",
            "invalid-permissions",
            "received_shovel"
    ));

    private void loadMessages(boolean readMessages) { //loading messages.yml

        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            mainClass.saveResource("messages.yml", false);
            mainClass.console.info("Created file messages.yml");
            mainClass.console.info("To modify ingame messages edit messages.yml and reload the server");
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (Exception e) {
            mainClass.console.severe("Couldn't load messages.yml file properly!");
            mainClass.Disable(); // Disabling
        }

        if (readMessages) {
            readMessages();
        }
    }

    private void readMessages() { //reads messages from messages.yml and puts them in messagesMap
        boolean shouldReload = false; //when not every message is present in messages.yml this will switch to true

        //reading from messages.yml and putting every message in a local HashMap
        for (String currKey : everyKey) {
            String currentMessage = messagesConfig.getString(currKey);
            if (currentMessage != null) {
                messagesMap.put(currKey, currentMessage);
                continue;
            }
            shouldReload = true;
        }

        if (shouldReload) { //when in messages.yml there aren't all the messages needed
            //resetting file with default messages
            messagesConfigFile.delete();
            messagesConfigFile.getParentFile().mkdirs();
            mainClass.saveResource("messages.yml", false);
            messagesConfig = new YamlConfiguration();
            try {
                messagesConfig.load(messagesConfigFile);
            } catch (Exception e) {
                mainClass.console.severe("Couldn't load messages.yml file properly!");
                mainClass.Disable(); // Disabling
            }

            //restoring custom messages (excluding default messages)
            for (String currKey : everyKey) {
                String readedMessage = messagesMap.get(currKey);
                if (readedMessage != null) {
                    messagesConfig.set(currKey, readedMessage);
                }
            }

            //reloading file
            try {
                messagesConfig.save(messagesConfigFile);
                messagesConfig.load(messagesConfigFile);
            } catch (IOException | InvalidConfigurationException Exc) { mainClass.console.severe("Couldn't load messages.yml file properly!"); mainClass.Disable(); }

            //finally reading every loaded message correctly
            for (String toReadKey : everyKey) {
                String currentMessage = messagesConfig.getString(toReadKey);
                messagesMap.put(toReadKey, currentMessage);
            }
        }
    }

    public String getMessage (String key) { return messagesMap.get(key); }

    public String formattedMessage (String prefix, String messageKey) { return String.format("%s[OreProcessing] %s", prefix, getMessage(messageKey)); }

    public String formattedText (String prefix, String message) { return String.format("%s[OreProcessing] %s", prefix, message); }

    public String usageMessage (String commandName) { return formattedText("§c", mainClass.getCommand(commandName).getUsage()); } //used by command classes to send the correct usage message
}
