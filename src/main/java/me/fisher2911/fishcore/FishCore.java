package me.fisher2911.fishcore;

import me.fisher2911.fishcore.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class FishCore extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {
        this.initClasses();
    }

    @Override
    public void onDisable() {

    }

    private void initClasses() {
        this.logger = new Logger(this);
    }

    public Logger logger() {
        return this.logger;
    }
}
