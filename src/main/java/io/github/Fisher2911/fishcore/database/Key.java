package io.github.Fisher2911.fishcore.database;

public enum Key {

    UNIQUE("UNIQUE"),

    PRIMARY("PRIMARY KEY"),

    FOREIGN("FOREIGN KEY");

    private final String value;

    Key(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
