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

package io.github.Fisher2911.fishcore.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableBuilder {

    private static final String NAME = "%name%";
    private static final String COLUMNS = "%columns%";

    private String name;
    private final List<DatabaseType<?>> databaseTypes = new ArrayList<>();
    private StringBuilder statement = new StringBuilder("CREATE TABLE IF NOT EXISTS " + NAME + " (" + COLUMNS + ") ");

    private TableBuilder(final String name) {
        this.name = name;
    }

    public static TableBuilder builder(final String name) {
        return new TableBuilder(name);
    }

    public TableBuilder column(final DatabaseType<?>... databaseTypes) {
        this.databaseTypes.addAll(Arrays.asList(databaseTypes));
        return this;
    }

//    public TableBuilder key(final Key key, final DatabaseType<?>... databaseTypes) {
//
//    }

}
