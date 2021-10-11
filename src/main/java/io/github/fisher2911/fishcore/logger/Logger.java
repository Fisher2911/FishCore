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

package io.github.fisher2911.fishcore.logger;

import io.github.fisher2911.fishcore.FishCore;

import java.util.Map;
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

    public void info(final String info, final Map<String, String> placeholders) {
        this.info(this.applyPlaceholders(info, placeholders));
    }

    public void warn(final String warning) {
        this.logger.warning(warning);
    }

    public void warn(final String warning, final Map<String, String> placeholders) {
        this.warn(this.applyPlaceholders(warning, placeholders));
    }

    public void error(final String error) {
        this.logger.severe(error);
    }

    public void error(final String error, final Map<String, String> placeholders) {
        this.error(this.applyPlaceholders(error, placeholders));
    }

    private String applyPlaceholders(String message, final Map<String, String> placeholders) {
        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
    }

    public void configWarning(final String warning) {
        this.logger.log(Level.CONFIG, warning);
    }

}
