package customitems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

	private static CustomItems plugin = CustomItems.getMain();
	private static Messages instance = new Messages();

	public static Messages getInstance() {
		return instance;
	}

	public void onlyplayer(CommandSender sender) {
		cmsg(sender, plugin.config.getString("messages.onlyplayer"));
	}

	public void noperm(Player player) {
		msg(player, plugin.config.getString("messages.noperm"));
	}

	public void noperm(CommandSender sender) {
		cmsg(sender, plugin.config.getString("messages.noperm"));
	}

	public void msg(Player player, String msg) {
		player.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", player.getName())));
	}

	public void cmsg(CommandSender sender, String msg) {
		sender.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", sender.getName())));
	}

	public void brdcst(String msg) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
}
