package cc.i9mc.gameutils.utils;


import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilderUtil {
    private ItemStack itemStack;

    public ItemBuilderUtil() {
        itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilderUtil setType(Material material) {
        itemStack.setType(material);

        return this;
    }

    public ItemBuilderUtil setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        return this;
    }

    public ItemBuilderUtil setOwner(String owner) {
        itemStack.setType(Material.SKULL_ITEM);
        itemStack.setDurability((short) 3);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        itemStack.setItemMeta(skullMeta);

        return this;
    }

    public ItemBuilderUtil setUnbreakable(boolean unbreakable, boolean hide) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.spigot().setUnbreakable(unbreakable);
        if (unbreakable && hide) {
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil setPotionData(PotionEffect potionEffect) {
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.addCustomEffect(potionEffect, true);
        itemStack.setItemMeta(potionMeta);

        return this;
    }


    public ItemBuilderUtil setColor(Color paramColor) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(paramColor);
        itemStack.setItemMeta(leatherArmorMeta);

        return this;
    }

    public ItemBuilderUtil setWoolColor(DyeColor dyeColor) {
        Wool wool = new Wool(dyeColor);
        itemStack = wool.toItemStack(1);

        return this;
    }

    public ItemBuilderUtil setAmount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public ItemBuilderUtil setDurability(int durability) {
        if ((this.itemStack.getType() == Material.SKULL_ITEM) && (this.itemStack.getDurability() == 3)) {
            return this;
        }

        this.itemStack.setDurability((short) durability);

        return this;
    }

    public ItemBuilderUtil setDisplayName(String displayName) {
        ItemMeta localItemMeta = this.itemStack.getItemMeta();
        localItemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(localItemMeta);

        return this;
    }

    public ItemBuilderUtil setLores(List<String> list) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil setLores(String... strings) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(strings));
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil addLore(String string) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(string);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil addLores(String... strings) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.addAll(Arrays.asList(strings));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return this;
    }


    public ItemBuilderUtil addItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil addItemFlag(ItemFlag... itemFlags) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil removeItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilderUtil addEnchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public ItemStack getItem() {
        return this.itemStack;
    }
}