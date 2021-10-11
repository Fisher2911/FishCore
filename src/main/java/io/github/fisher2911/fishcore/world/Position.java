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

package io.github.fisher2911.fishcore.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class Position {

    private final WeakReference<World> worldReference;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    /**
     * @param world {@link org.bukkit.World}
     * @param x     x coordinate
     * @param y     y coordinate
     * @param z     z coordinate
     * @param yaw   yaw
     * @param pitch pitch
     */

    public Position(final World world,
                    final double x,
                    final double y,
                    final double z,
                    final float yaw,
                    final float pitch) {
        this.worldReference = new WeakReference<>(world);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * @param world {@link org.bukkit.World}
     * @param x     x coordinate
     * @param y     y coordinate
     * @param z     z coordinate
     */

    public Position(final World world,
                    final double x,
                    final double y,
                    final double z) {
        this(world, x, y, z, 0, 0);
    }

    /**
     * @param worldReference WeakReference of {@link org.bukkit.World}
     * @param x     x coordinate
     * @param y     y coordinate
     * @param z     z coordinate
     */

    public Position(final @NotNull WeakReference<World> worldReference,
                    final double x,
                    final double y,
                    final double z) {
        this(worldReference, x, y, z, 0, 0);
    }

    /**
     * @param worldReference WeakReference of {@link org.bukkit.World}
     * @param x     x coordinate
     * @param y     y coordinate
     * @param z     z coordinate
     * @param yaw   yaw
     * @param pitch pitch
     */

    public Position(final @NotNull WeakReference<World> worldReference,
                    final double x,
                    final double y,
                    final double z,
                    final float yaw,
                    final float pitch) {
        this.worldReference = worldReference;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * @return {@link java.util.Optional} of {@link org.bukkit.World}.
     * The World is stored in a {@link java.lang.ref.WeakReference}, so may be null
     */

    public Optional<World> getWorld() {
        return Optional.ofNullable(this.worldReference.get());
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getBlockX() {
        return Location.locToBlock(this.x);
    }

    public int getBlockY() {
        return Location.locToBlock(this.y);
    }

    public int getBlockZ() {
        return Location.locToBlock(this.z);
    }


    /**
     * @return converts this {@link io.github.fisher2911.fishcore.world.Position} to
     * {@link org.bukkit.Location}
     */

    public Location toBukkitLocation() {
        final Optional<World> optionalWorld = this.getWorld();
        return new Location(optionalWorld.isEmpty() ? null : optionalWorld.get(),
                this.x, this.y, this.z, this.yaw, this.pitch);
    }

    /**
     *
     * @param x x
     * @param y y
     * @param z z
     * @param yaw yaw
     * @param pitch pitch
     * @return new Position
     */

    public Position add(final double x, final double y, final double z, final float yaw, final float pitch) {
        return new Position(worldReference, this.x + x,
                this.y + y,
                this.z + z,
                this.yaw + yaw,
                this.pitch + pitch);
    }

    /**
     *
     * @param x x
     * @param y y
     * @param z z
     * @return new Position
     */

    public Position add(final double x, final double y, final double z) {
        return this.add(x, y, z, 0, 0);
    }

    /**
     *
     * @param x x
     * @param y y
     * @param z z
     * @param yaw yaw
     * @param pitch pitch
     * @return new Position
     */

    public Position subtract(final double x, final double y, final double z, final float yaw, final float pitch) {
        return new Position(worldReference, this.x - x,
                this.y - y,
                this.z - z,
                this.yaw - yaw,
                this.pitch - pitch);
    }

    /**
     *
     * @param x x
     * @param y y
     * @param z z
     * @return new Position
     */

    public Position subtract(final double x, final double y, final double z) {
        return this.subtract(x, y, z, 0, 0);
    }

    /**
     *
     * @param material material to be set
     */

    public void setBlockType(final Material material) {
        this.toBukkitLocation().getBlock().setType(material);
    }

    /**
     *
     * @return block at position
     */

    public Block getBlock() {
        return this.toBukkitLocation().getBlock();
    }

    /**
     *
     * @param location {@link org.bukkit.Location}
     * @return {@link io.github.fisher2911.fishcore.world.Position}
     */

    public static Position fromBukkitLocation(final Location location) {
        final World world = location.getWorld();
        return new Position(new WeakReference<>(world),
                location.getX(),
                location.getZ(),
                location.getY(),
                location.getYaw(),
                location.getPitch());
    }

    /**
     *
     * @return the position's chunk long key
     */

    public long getChunkKey() {
        long x = (this.getBlockX() >> 4);
        long z = (this.getBlockZ() >> 4);
        return x & 0xffffffffL | (z & 0xffffffffL) << 32;
    }

}
