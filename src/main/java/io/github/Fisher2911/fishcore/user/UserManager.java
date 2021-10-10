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

package io.github.Fisher2911.fishcore.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserManager<T extends BaseUser> {

    private final Map<UUID, T> userMap = new HashMap<>();


    /**
     *
     * @param baseUser BaseUser to be added
     */
    public void addUser(final T baseUser) {
        this.userMap.put(baseUser.getUuid(), baseUser);
    }

    /**
     *
     * @param uuid BaseUser's uuid
     * @return Optional BaseUser
     */

    public Optional<T> removeUser(final UUID uuid) {
        return Optional.ofNullable(this.userMap.remove(uuid));
    }

    /**
     *
     * @param uuid BaseUser's uuid
     * @return Optional BaseUser
     */

    public Optional<T> getUser(final UUID uuid) {
        return Optional.ofNullable(this.userMap.get(uuid));
    }

    /**
     *
     * @return all loaded BaseUsers
     */

    public Collection<T> getAllUsers() {
        return this.userMap.values();
    }


}
