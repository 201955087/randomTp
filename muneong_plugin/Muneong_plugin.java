package me.air_bottle.muneong_plugin;

import me.air_bottle.muneong_plugin.command.run_command;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Muneong_plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().warning("플러그인 활성화");
        Bukkit.getCommandMap().register("command_plugin", new run_command("random_tp"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().warning("플러그인 비활성화");
    }
}
