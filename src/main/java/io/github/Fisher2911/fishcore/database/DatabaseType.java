/*
 * MIT License
 *
 * Copyright (c) $year Fisher2911
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

package io.github.Fisher2911.fishcore.database;

import java.sql.Date;
import java.sql.Timestamp;

public class DatabaseType<T> {

    public static final DatabaseType<Boolean> BOOLEAN = new DatabaseType<>("BOOLEAN");
    public static final DatabaseType<Byte> TINYINT = new DatabaseType<>("TINYINT");
    public static final DatabaseType<Short> SMALLINT = new DatabaseType<>("SMALLINT");
    public static final DatabaseType<Integer> INTEGER = new DatabaseType<>("INTEGER");
    public static final DatabaseType<Long> BIGINT = new DatabaseType<>("BIGINT");
    public static final DatabaseType<Float> DECIMAL = new DatabaseType<>("DECIMAL");
    public static final DatabaseType<Date> DATE = new DatabaseType<>("DATE");
    public static final DatabaseType<Timestamp> DATETIME = new DatabaseType<>("DATETIME");
    public static final DatabaseType<Character> CHAR = new DatabaseType<>("CHAR");
    public static final DatabaseType<String> VARCHAR = new DatabaseType<>("VARCHAR");
    public static final DatabaseType<String> TEXT = new DatabaseType<>("TEXT");

    private final String type;
    private T data;

    public DatabaseType(final String type) {
        this.type = type;
    }

    public DatabaseType(final String type, final T data) {
        this.type = type;
        this.data = data;
    }

    public DatabaseType<T> withData(final T data) {
        return new DatabaseType<>(this.type, data);
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }
}
