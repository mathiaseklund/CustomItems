package customitems;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemCommand implements CommandExecutor {

	CustomItems plugin = CustomItems.getMain();
	Messages msg = Messages.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("customitems.admin") || player.isOp()) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("info")) {
						msg.msg(player, "&7Usage: &b/item info <id>");
					} else {
						String id = args[0];
						if (plugin.exists(id)) {
							player.getInventory().addItem(plugin.getItem(id));
						} else {
							msg.msg(player, "No item with that id");
						}
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("info")) {
						String id = args[1];
						if (plugin.exists(id)) {
							ArrayList<String> info = plugin.getInfo(id);
							for (String s : info) {
								msg.msg(player, s);
							}
						} else {
							msg.msg(player, "No item with that id");
						}
					}
				}
			}
		}
		return false;
	}

}
