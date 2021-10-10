package me.fisher2911.fishcore.database;

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
