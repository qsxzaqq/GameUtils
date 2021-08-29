package cc.i9mc.gameutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationUtil {
    public static Location locationConversion(String string) {
        return new Location(Bukkit.getWorld(string.split(",")[0]), Double.valueOf(string.split(",")[1]), Double.valueOf(string.split(",")[2]), Double.valueOf(string.split(",")[3]), Float.valueOf(string.split(",")[4]), Float.valueOf(string.split(",")[5]));
    }

    public static Location locationConversion(String world, String string) {
        return new Location(Bukkit.getWorld(world), Double.valueOf(string.split(",")[0]), Double.valueOf(string.split(",")[1]), Double.valueOf(string.split(",")[2]), Float.valueOf(string.split(",")[3]), Float.valueOf(string.split(",")[4]));
    }

    public static Vector getPosition(Location location1, Location location2, double Y) {
        double X = location1.getX() - location2.getX();
        double Z = location1.getZ() - location2.getZ();
        return new Vector(X, Y, Z);
    }
}
