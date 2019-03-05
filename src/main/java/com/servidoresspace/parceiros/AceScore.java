package com.servidoresspace.parceiros;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class AceScore extends JavaPlugin {

    @Override
    public void onEnable() {
        new ScoreAPI().startTimer();
        Bukkit.getPluginManager().registerEvents(new ScoreAPI(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
