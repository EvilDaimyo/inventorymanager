package cloud.akiba;

import org.bukkit.inventory.ItemStack;

public class Inventory {
    public Inventory(String name) {
        this.name = name;
    }

    public ItemStack[] getInventory() {
        return this.inventory;
    }

    public ItemStack[] getArmorInventory() {
        return this.armorInventory;
    }

    public String getName() {
        return name;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void setArmorInventory(ItemStack[] armorInventory) {
        this.armorInventory = armorInventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private ItemStack[] inventory, armorInventory;
}