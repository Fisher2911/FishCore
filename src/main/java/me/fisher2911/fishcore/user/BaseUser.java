package me.fisher2911.fishcore.user;

import java.util.UUID;

public abstract class BaseUser {

    protected final UUID uuid;
    protected String name;

    public BaseUser(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
