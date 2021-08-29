package cc.i9mc.gameutils.yaml;

import java.util.Map;

public interface Configuration extends ConfigurationSection {
    void addDefault(String path, Object value);

    void addDefaults(Map<String, Object> defaults);

    void addDefaults(Configuration defaults);

    Configuration getDefaults();

    void setDefaults(Configuration defaults);

    ConfigurationOptions options();
}