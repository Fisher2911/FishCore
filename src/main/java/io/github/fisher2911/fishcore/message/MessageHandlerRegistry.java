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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MessageHandlerRegistry {

    public static final MessageHandlerRegistry REGISTRY = new MessageHandlerRegistry();

    private final Map<Class<? extends FishCore>, MessageHandler> registry = new HashMap<>();

    private MessageHandlerRegistry() {}

    public void register(final Class<? extends FishCore> plugin) {
        final MessageHandler messageHandler = new MessageHandler(JavaPlugin.getPlugin(plugin));
        messageHandler.load();
        this.registry.put(plugin, messageHandler);
    }

    public MessageHandler get(final Class<? extends FishCore> plugin) {
        MessageHandler messageHandler = this.registry.get(plugin);
        if (messageHandler == null) {
            messageHandler = new MessageHandler(JavaPlugin.getPlugin(plugin));
            this.registry.put(plugin, messageHandler);
        }
        return messageHandler;
    }

}
