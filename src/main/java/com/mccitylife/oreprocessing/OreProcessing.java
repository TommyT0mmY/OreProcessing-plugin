package com.mccitylife.oreprocessing;

import com.mccitylife.oreprocessing.commands.Processhovel;
import com.mccitylife.oreprocessing.configfiles.Messages;
import com.mccitylife.oreprocessing.events.SignInteractions;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class OreProcessing extends JavaPlugin {

    private static OreProcessing instance;

    public Logger console;
    public Messages messages;
    public File datafolder;

    public void onEnable () {
        setInstance(this);
        datafolder = getDataFolder();
        console = getLogger();
        messages = new Messages();


        loadEvents();
        loadCommands();

        console.info("Loaded successfully");
    }

    public void onDisable() {
        console.info("Unloaded successfully");
    }

    public void Disable() {
        getServer().getPluginManager().disablePlugin(this);
    }

    public static OreProcessing getInstance() {
        return instance;
    }

    @SuppressWarnings("static-access")
    private void setInstance(OreProcessing instance) {
        this.instance = instance;
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new SignInteractions(), this);
    }

    private void loadCommands() {
        getCommand("processhovel").setExecutor(new Processhovel());
    }

}
