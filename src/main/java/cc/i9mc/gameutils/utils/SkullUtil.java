package cc.i9mc.gameutils.utils;

import cc.i9mc.gameutils.BukkitGameUtils;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class SkullUtil {

    private static final HashMap<String, String> playerTextures = new HashMap<>();
    @Getter
    private static final HashMap<String, ItemStack> skulls = new HashMap<>();

    /**
     * 取得一个自定义头颅
     *
     * @param texture 材质
     * @return 头颅物品
     */
    public static ItemStack getCustomSkull(String texture) {
        return skulls.computeIfAbsent(texture, x -> setTexture(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), texture));
    }

    /**
     * 设置一个头颅物品的材质
     *
     * @param skull   头颅
     * @param texture 目标材质
     * @return 自定义头颅
     */
    public static ItemStack setTexture(ItemStack skull, String texture) {
        Bukkit.getScheduler().runTaskAsynchronously(BukkitGameUtils.getInstance(), () -> {
            WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(UUID.randomUUID(), null);
            wrappedGameProfile.getProperties().put("textures", WrappedSignedProperty.fromValues("textures", texture, null));

            try {
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                Field field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(skullMeta, wrappedGameProfile.getHandle());
                skull.setItemMeta(skullMeta);
            } catch (ClassCastException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return skull;
    }

    public static ItemStack getPlayerSkull(String id) {
        String texture = getPlayerTexture(id);
        if (texture != null) {
            return skulls.computeIfAbsent(id, x -> setTexture(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), texture));
        } else {
            return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        }
    }

    public static String getPlayerTexture(String id) {
        if (playerTextures.containsKey(id)) {
            return playerTextures.get(id);
        } else {
            playerTextures.put(id, null);
            Bukkit.getScheduler().runTaskAsynchronously(BukkitGameUtils.getInstance(), () -> {
                try {
                    JsonObject userProfile = (JsonObject) new JsonParser().parse(URLUtil.readFromURL("https://api.mojang.com/users/profiles/minecraft/" + id));
                    JsonArray textures = ((JsonObject) new JsonParser().parse(URLUtil.readFromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + userProfile.get("id").getAsString()))).getAsJsonArray("properties");
                    for (JsonElement element : textures) {
                        if ("textures".equals(element.getAsJsonObject().get("name").getAsString())) {
                            playerTextures.put(id, element.getAsJsonObject().get("value").getAsString());
                        }
                    }
                } catch (Throwable ignored) {
                }
            });
        }
        return playerTextures.get(id);
    }
}
