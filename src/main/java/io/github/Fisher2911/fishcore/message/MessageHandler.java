package io.github.Fisher2911.fishcore.message;

import io.github.Fisher2911.fishcore.FishCore;
import io.github.Fisher2911.fishcore.logger.Logger;
import io.github.Fisher2911.fishcore.util.FileUtil;
import io.github.Fisher2911.fishcore.util.helper.Utils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.transformation.TransformationRegistry;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final FishCore plugin;
    private final Logger logger;
    private final BukkitAudiences adventure;
    private static MessageHandler instance;
    private final Map<String, String> messageMap = new HashMap<>();

    private MessageHandler(final FishCore plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.logger();
        this.adventure = BukkitAudiences.create(this.plugin);
    }

    /**
     *
     * @return instance of MessageHandler
     */

    public static MessageHandler getInstance() {
        if (instance == null) {
            instance = new MessageHandler(FishCore.getPlugin(FishCore.class));
            instance.load();
        }
        return instance;
    }

    /**
     * Closes adventure
     */

    public void close() {
        this.adventure.close();
    }

    private final MiniMessage miniMessage = MiniMessage.builder()
            .transformations(TransformationRegistry.
                    builder().
                    add(TransformationType.CLICK_EVENT,
                            TransformationType.DECORATION,
                            TransformationType.COLOR
                    ).build())
            .build();

    /**
     *
     * @param sender audience message is sent to
     * @param key message key
     */

    public void sendMessage(final CommandSender sender, final String key) {
           final String message = this.getMessage(key);
           final Component component = this.parse(message);
           this.adventure.sender(sender).sendMessage(component);
    }

    /**
     *
     * @param parsed message to be parsed
     * @return MiniMessage parsed string
     */

    public Component parse(final String parsed) {
        return this.miniMessage.parse(parsed);
    }

    /**
     *
     * @param key message key
     * @return message, or empty string if message not found
     */

    public String getMessage(final String key) {
        return this.messageMap.getOrDefault(key, "");
    }

    /**
     * Loads all messages from messages.yml
     */
    private void load() {
        final String fileName = "messages.yml";
        final File file = FileUtil.getFile(fileName);
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (final String key : config.getKeys(false)) {
            final String message = Utils.replaceIfNull(config.getString(key), "", value -> {
                if (value == null) {
                    this.logger.configWarning(String.format(ErrorMessages.ITEM_NOT_FOUND, "message", fileName));
                }
            });

            this.messageMap.put(key, message);
        }
    }
}
