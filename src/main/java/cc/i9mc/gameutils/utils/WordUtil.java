package cc.i9mc.gameutils.utils;

import cc.i9mc.gameutils.enums.GameType;

import java.util.regex.Pattern;

public class WordUtil {
    public static int getIntFromString(String input) {
        try {
            return Integer.parseInt(Pattern.compile("[^0-9]").matcher(input).replaceAll("").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static GameType getGameType(String input) {
        if (input.contains("Lobby")) {
            return GameType.LOBBY;
        }

        return GameType.GAME;
    }
}
