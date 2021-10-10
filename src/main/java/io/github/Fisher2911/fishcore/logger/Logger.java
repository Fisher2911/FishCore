package io.github.Fisher2911.fishcore.logger;

import io.github.Fisher2911.fishcore.FishCore;

import java.util.logging.Level;

public class Logger {

    private final FishCore plugin;
    private final java.util.logging.Logger logger;

    public Logger(final FishCore plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.getLogger();
    }

    public void info(final String info) {
        this.logger.info(info);
    }

    public void warn(final String warning) {
        this.logger.warning(warning);
    }

    public void error(final String error) {
        this.logger.severe(error);
    }

    public void configWarning(final String warning) {
        this.logger.log(Level.CONFIG, warning);
    }

}
