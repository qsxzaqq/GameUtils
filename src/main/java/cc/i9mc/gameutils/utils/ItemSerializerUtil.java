package cc.i9mc.gameutils.utils;

import cc.i9mc.gameutils.utils.nms.NMSUtils;
import cc.i9mc.gameutils.utils.nms.nbt.NBTUtils;
import cc.i9mc.gameutils.utils.reflect.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * 此类用于将物品转换为mojangson数据或将mojangson数据转换为物品
 * <p>
 * This class is convert an item to mojangson data or unconvert mojangson data to an item
 *
 * @author Zoyn
 * @since 2018/8/29
 */
public class ItemSerializerUtil {

    private static Method MOJANGSON_TO_NBT;

    static {
        try {
            MOJANGSON_TO_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("MojangsonParser"), "parse", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prevent accidental construction
    private ItemSerializerUtil() {
    }

    /**
     * Create the item mojangson data
     *
     * @param itemStack the item
     * @return {@link String}
     */
    public static String getItemStackJson(ItemStack itemStack) {
        Object savedTag = NBTUtils.saveItemNBT(itemStack);
        if (savedTag != null) {
            return savedTag.toString();
        } else {
            return "null";
        }
    }

    /**
     * Load a item by using mojangson data
     *
     * @param mojangson the mojangson data
     * @return {@link ItemStack}
     */
    public static ItemStack loadItemStackJson(String mojangson) {
        ItemStack itemStack = null;
        try {
            Object nbtTag = ReflectionUtils.invokeMethod(MOJANGSON_TO_NBT, null, mojangson);
            Object nmsItem = NBTUtils.newNMSItemStack(nbtTag);
            itemStack = (ItemStack) NMSUtils.getBukkitItem(nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemStack;
    }

}
