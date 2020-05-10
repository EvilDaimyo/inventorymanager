package cloud.akiba;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static cloud.akiba.InvManager.*;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length < 1) return false;
        Player player = (Player) sender;

        switch (args[0].toLowerCase()) {
            case "create":
            case "set":
            case "save": {
                if (args.length < 2) return false;
                save(args[1], player);
                sender.sendMessage("§aAdded!");
                break;
            }
            case "list": {
                List<String> data = new ArrayList<>();
                for (Inventory inventory : list) {
                    data.add(inventory.getName());
                }
                sender.sendMessage("§8(Inv) §7" + data);
                break;
            }
            case "del":
            case "remove":
            case "delete": {
                if (args.length < 2) return false;
                delete(args[1]);
                sender.sendMessage("§aDeleted!");
                break;
            }
            case "load": {
                if (args.length < 2) return false;
                load(player, args[1]);
                sender.sendMessage("§aLoaded!");
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}
