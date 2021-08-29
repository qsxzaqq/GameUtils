package cc.i9mc.gameutils.yaml;

import java.util.Map;

public interface ConfigurationSerializable {
    Map<String, Object> serialize();
}