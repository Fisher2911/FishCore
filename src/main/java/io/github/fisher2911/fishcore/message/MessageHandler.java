/*
 * MIT License
 *
 * Copyright (c) 2021 Fisher2911
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.fisher2911.fishcore.message;

import io.github.fisher2911.fishcore.FishCore;
import io.github.fisher2911.fishcore.logger.Logger;
import io.github.fisher2911.fishcore.util.FileUtil;
import io.github.fisher2911.fishcore.util.helper.Utils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.transformation.TransformationRegistry;
import net.kyori.adventure.text.minimessage.transformation.TransformationType;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final FishCore plugin;
    private final Logger logger;
    private final BukkitAudiences adventure;
    private static MessageHandler instance;
    private final Map<Message, String> messageMap = new HashMap<>();

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
     * @param sender receiver of message
     * @param key message key
     * @param placeholders placeholders
     */

    public void sendMessage(final CommandSender sender, final String key, final Map<String, String> placeholders) {
           final String message = this.applyPlaceholders(this.getMessage(key), placeholders);
           final Component component = this.parse(message);
           this.adventure.sender(sender).sendMessage(component);
    }

    /**
     *
     * @param sender receiver of message
     * @param key message key
     */

    public void sendMessage(final CommandSender sender, final String key) {
        this.sendMessage(sender, key, Collections.emptyMap());
    }

    /**
     *
     * @param player receiver of message
     * @param key message key
     * @param placeholders placeholders
     */

    public void sendActionBar(final Player player, final String key, final Map<String, String> placeholders) {
        final String message = this.applyPlaceholders(this.getMessage(key), placeholders);
        Component component = this.parse(message);
        this.adventure.player(player).sendActionBar(component);
    }

    /**
     *
     * @param player receiver of message
     * @param key message key
     */

    public void sendActionBar(final Player player, final String key) {
        this.sendActionBar(player, key);
    }

    /**
     *
     * @param player receiver of message
     * @param key message key
     * @param placeholders placeholders
     */

    public void sendTitle(final Player player, final String key, final Map<String, String> placeholders) {
        final String message = this.applyPlaceholders(this.getMessage(key), placeholders);
        Component component = this.parse(message);
        this.adventure.player(player).showTitle(Title.title(component, Component.empty()));
    }

    /**
     *
     * @param player receiver of message
     * @param key message key
     */

    public void sendTitle(final Player player, final String key) {
        this.sendTitle(player, key, Collections.emptyMap());
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
     *
     * @param message message being translated
     * @param placeHolders placeholders applied
     * @return message with placeholders applied
     */

    public String applyPlaceholders(String message, final Map<String, String> placeHolders) {
        for (final Map.Entry<String, String> entry : placeHolders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
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

            final Message.Type messageType = Utils.stringToEnum(
                    Utils.replaceIfNull(config.getString("type"), "")
                    , Message.Type.class, Message.Type.MESSAGE
            );

            this.messageMap.put(new Message(key, message, messageType), message);
        }
    }
}
