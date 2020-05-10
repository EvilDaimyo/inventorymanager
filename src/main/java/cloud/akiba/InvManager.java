package cloud.akiba;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvManager extends JavaPlugin {
    @Override
    public void onEnable() {
        config = getConfig();
        saveDefaultConfig();
        init();
        plugin = this;

        Bukkit.getPluginCommand("inventories").setExecutor(new Command());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void init() {
        FileConfiguration cfg = config;
        for (String name : cfg.getStringList("data")) {
            Inventory inv = new Inventory(name);
            String key = "list." + inv.getName() + ".";

            if (cfg.getList(key + "inventory") != null) {
                List<?> list = cfg.getList(key + "inventory");
                ItemStack[] contents = new ItemStack[list.size()];
                for (int i = 0; i < list.size(); i++) contents[i] = list.get(i) == null ? new ItemStack(Material.AIR) : (ItemStack) list.get(i);
                inv.setInventory(contents);
            }
            if (cfg.getList(key + "armorInventory") != null) {
                List<?> list = cfg.getList(key + "armorInventory");
                ItemStack[] contents = new ItemStack[list.size()];
                for (int i = 0; i < list.size(); i++) contents[i] = list.get(i) == null ? new ItemStack(Material.AIR) : (ItemStack) list.get(i);
                inv.setArmorInventory(contents);
            }
            list.add(inv);
        }
    }

    public static void delete(String name) {
        if (getInventory(name) == null) return;
        FileConfiguration cfg = config;
        List<String> data = cfg.getStringList("data");
        Inventory inv = getInventory(name);

        list.remove(inv);
        data.remove(inv.getName());
        cfg.set("list." + inv.getName(), null);
        cfg.set("data", data);
    }

    public static void save(String name, Player player) {
        FileConfiguration cfg = config;
        Inventory inv = getInventory(name) == null ? new Inventory(name) : getInventory(name);
        String key = "list." + inv.getName() + ".";

        inv.setInventory(player.getInventory().getContents());
        inv.setArmorInventory(player.getInventory().getArmorContents());

        cfg.set(key + "inventory", Arrays.asList(inv.getInventory()));
        cfg.set(key + "armorInventory", inv.getArmorInventory());

        if (getInventory(name) == null) {
            list.add(inv);

            List<String> data = new ArrayList<>();
            for (Inventory inventory : list) {
                data.add(inventory.getName());
            }
            cfg.set("data", data);
        }
    }

    public static void load(List<Player> players, String name) {
        for (Player player : players) {
            load(player, name);
        }
    }

    public static void load(Player player, String name) {
        Inventory inv = getInventory(name);
        player.getInventory().clear();
        player.getInventory().setContents(inv.getInventory());
        player.getInventory().setArmorContents(inv.getArmorInventory());
        player.updateInventory();
    }

    public static Inventory getInventory(String name) {
        for (Inventory inv : list) if (inv.getName().equalsIgnoreCase(name)) return inv;
        return null;
    }

    private static Plugin plugin;
    public static List<Inventory> list = new ArrayList<>();
    public static FileConfiguration config;
}