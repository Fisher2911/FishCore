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

package io.github.fisher2911.fishcore.upgrade;

import io.github.fisher2911.fishcore.economy.Cost;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class Upgrade<T> {

    protected final String id;
    protected final String displayName;
    protected final Map<Integer, T> levelDataMap;
    protected final Map<Integer, Cost> levelCostMap;

    public Upgrade(final String id,
                   final String displayName,
                   final Map<Integer, T> levelDataMap,
                   final Map<Integer, Cost> levelCostMap) {
        this.id = id;
        this.displayName = displayName;
        this.levelDataMap = levelDataMap;
        this.levelCostMap = levelCostMap;
    }

    /**
     *
     * @return id of upgrade
     */

    public String getId() {
        return this.id;
    }

    /**
     *
     * @return displayName of upgrade
     */

    public String getDisplayName() {
        return this.displayName;
    }

    /**
     *
     * @return levelDataMap of upgrade
     */

    public Map<Integer, T> getLevelDataMap() {
        return this.levelDataMap;
    }

    /**
     *
     * @return levelCostMap of upgrade
     */

    public Map<Integer, Cost> getLevelCostMap() {
        return this.levelCostMap;
    }

    /**
     *
     * @param level level that cost is at
     * @return cost of level, or null if it does not exist
     */

    public @Nullable Cost getCostAtLevel(final int level) {
        return this.getCostAtLevel(level, null);
    }

    /**
     *
     * @param level level that cost is at
     * @param defaultCost default cost returned if cost is null
     * @return cost of level, or default if cost is null
     */

    public Cost getCostAtLevel(final int level, final Cost defaultCost) {
        return this.levelCostMap.getOrDefault(level, defaultCost);
    }

    /**
     *
     * @param level level that data is at
     * @return data at level, or null if it does not exist
     */

    public @Nullable T getDataAtLevel(final int level) {
        return this.getDataAtLevel(level, null);
    }

    /**
     *
     * @param level level that data is at
     * @param defaultData default data returned if level is null
     * @return data at level, or default if leve is null
     */

    public T getDataAtLevel(final int level, final  T defaultData) {
        return this.levelDataMap.getOrDefault(level, defaultData);
    }
}
