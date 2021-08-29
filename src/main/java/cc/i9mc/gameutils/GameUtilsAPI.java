package cc.i9mc.gameutils;

import cc.i9mc.gameutils.enums.GameType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GameUtilsAPI {
    @Getter
    @Setter
    private static String lobbyChatGroup = null;
    @Getter
    @Setter
    private static String lobbyChatConfig = null;
    @Getter
    @Setter
    private static String lobbyChatHover = null;
    @Getter
    @Setter
    private static GameType gameType = null;
    @Getter
    @Setter
    private static String serverName = null;
    @Getter
    @Setter
    private static int serverId;
}
