package customitems;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

	CustomItems plugin = CustomItems.getMain();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender.hasPermission("plugin.reload")) {
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			plugin.getServer().getPluginManager().enablePlugin(plugin);
			sender.sendMessage(ChatColor.GREEN + "Item Database Reloaded");
		}
		return false;
	}

}
