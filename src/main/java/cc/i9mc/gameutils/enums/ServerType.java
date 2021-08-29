package cc.i9mc.gameutils.enums;

import lombok.Getter;
import lombok.Setter;

public enum ServerType {
    BUNGEECORD, BUKKIT;

    @Getter
    @Setter
    private static ServerType serverType;
}