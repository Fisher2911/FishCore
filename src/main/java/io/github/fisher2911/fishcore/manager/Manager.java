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

package io.github.fisher2911.fishcore.manager;

import io.github.fisher2911.fishcore.util.helper.IdHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Manager<K, V extends IdHolder<K>> {

    private final Map<K, V> userMap = new HashMap<>();

    public Manager() {}

    /**
     *
     * @param value value being inserted
     */

    public void add(final V value) {
        this.userMap.put(value.getId(), value);
    }

    /**
     *
     * @param key key of value being removed
     * @return Optional of value if it exists
     */

    public Optional<V> remove(final K key) {
        return Optional.ofNullable(this.userMap.remove(key));
    }

    /**
     *
     * @param key key of value being retrieved
     * @return Optional of value if it exists
     */

    public Optional<V> get(final K key) {
        return Optional.ofNullable(this.userMap.get(key));
    }

    /**
     *
     * @return Collection of all values
     */

    public Collection<V> getAll() {
        return this.userMap.values();
    }
    
}
