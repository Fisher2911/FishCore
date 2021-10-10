package me.fisher2911.fishcore.database;

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
