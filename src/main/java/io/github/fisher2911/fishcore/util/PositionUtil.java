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

package io.github.fisher2911.fishcore.util;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class PositionUtil {

    /**
     *
     * @param chunkX chunk x coordinate
     * @param chunkZ chunk z coordinate
     * @return long key of chunk
     */

    public static long getChunkKey(final int chunkX, int chunkZ) {
        return chunkX & 0xffffffffL | (chunkZ & 0xffffffffL) << 32;
    }

    /**
     *
     * @param chunk chunk whose key is being calculated
     * @return long key of chunk
     */

    public static long getChunkKey(final Chunk chunk) {
        return getChunkKey(chunk.getX(), chunk.getZ());
    }

    /**
     *
     * @param location location whose chunk is being calculated
     * @return calculated long key of chunk
     */

    public static long getChunkKey(final Location location) {
        int x = (location.getBlockX() >> 4);
        int z = (location.getBlockZ() >> 4);
        return getChunkKey(x, z);
    }

}
