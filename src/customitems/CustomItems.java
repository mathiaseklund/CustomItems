package customitems;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomItems extends JavaPlugin
{

	private static CustomItems main;

	File configurationConfig;
	public FileConfiguration config;
	File itemData;
	public FileConfiguration idata;

	String prefix = "";

	Messages msg;
	Utils util;

	public static CustomItems getMain()
	{
		return main;
	}

	public void onEnable()
	{
		main = this;
		configurationConfig = new File(getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configurationConfig);
		itemData = new File(getDataFolder(), "itemData.yml");
		idata = YamlConfiguration.loadConfiguration(itemData);
		loadConfig();
		getCommand("reloaditems").setExecutor(new ReloadCommand());
		getCommand("customitem").setExecutor(new ItemCommand());
		msg = Messages.getInstance();
		util = Utils.getInstance();
	}

	public void savec()
	{
		try
		{
			config.save(configurationConfig);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void saveid()
	{
		try
		{
			idata.save(itemData);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadConfig()
	{

		// idata.addDefault("1.id", 276);
		// idata.addDefault("1.meta", 0);
		// idata.addDefault("1.displayname", "Display Name");
		// ArrayList<String> example_lore = new ArrayList<String>();
		// example_lore.add("Example lore");
		// idata.addDefault("1.lore", example_lore);
		// ArrayList<String> enchants = new ArrayList<String>();
		// enchants.add("DAMAGE_ALL,1");
		// idata.addDefault("1.enchants", enchants);
		config.addDefault("message.noperm", "&4Error: You don't have permission to use this function.");
		config.addDefault("message.onlyplayer", "&4Error: Only players may use this function.");
		config.addDefault("prefix", "");
		config.options().copyDefaults(true);
		// idata.options().copyDefaults(true);
		saveid();
		// savec();
		prefix = config.getString("prefix");

	}

	public static String convertToInvisibleString(String s)
	{
		String hidden = "";
		for (char c : s.toCharArray())
			hidden += ChatColor.COLOR_CHAR + "" + c;
		return hidden;
	}

	public ItemStack getItem(String id)
	{
		itemData = new File(getDataFolder(), "itemData.yml");
		idata = YamlConfiguration.loadConfiguration(itemData);
		ItemStack is = new ItemStack(idata.getInt(id + ".id"), 1, (short) idata.getInt(id + ".meta"));
		ItemMeta im = is.getItemMeta();
		if (idata.getString(id + ".displayname") != null)
		{
			String name = util.colorString(idata.getString(id + ".displayname"));
			im.setDisplayName(name);
		}
		ArrayList<String> lore = new ArrayList<String>();
		if (idata.getStringList(id + ".lore") != null)
		{
			ArrayList<String> l = new ArrayList<String>();
			l.addAll(idata.getStringList(id + ".lore"));
			for (String s : l)
			{
				lore.add(s);
			}
		}
		if (idata.getString(id + ".damage") != null)
		{
			String string = idata.getString(id + ".damage");
			double minval = Double.parseDouble(string.split("-")[0]);
			double maxval = Double.parseDouble(string.split("-")[0]);
			// TODO DAMAGE SYSTEm
		}
		if (idata.getString(id + ".damage") != null)
			lore.add(convertToInvisibleString("id:" + id));
		im.setLore(lore);
		if (idata.getStringList(id + ".enchants") != null)
		{
			ArrayList<String> enchants = new ArrayList<String>();
			enchants.addAll(idata.getStringList(id + ".enchants"));
			for (String s : enchants)
			{
				String name = s.split(",")[0];
				int lvl = Integer.parseInt(s.split(",")[1]);
				im.addEnchant(Enchantment.getByName(name), lvl, true);
			}
		}
		is.setItemMeta(im);
		return is;
	}

	public ItemStack getItem(String id, int amount)
	{
		itemData = new File(getDataFolder(), "itemData.yml");
		idata = YamlConfiguration.loadConfiguration(itemData);
		ItemStack is = new ItemStack(idata.getInt(id + ".id"), amount, (short) idata.getInt(id + ".meta"));
		is.setAmount(amount);
		ItemMeta im = is.getItemMeta();
		if (idata.getString(id + ".displayname") != null)
		{
			String name = util.colorString(idata.getString(id + ".displayname"));
			im.setDisplayName(name);
		}
		ArrayList<String> lore = new ArrayList<String>();
		if (idata.getStringList(id + ".lore") != null)
		{
			ArrayList<String> l = new ArrayList<String>();
			l.addAll(idata.getStringList(id + ".lore"));
			for (String s : l)
			{
				lore.add(s);
			}
		}
		lore.add(convertToInvisibleString("id:" + id));
		im.setLore(lore);
		if (idata.getStringList(id + ".enchants") != null)
		{
			ArrayList<String> enchants = new ArrayList<String>();
			enchants.addAll(idata.getStringList(id + ".enchants"));
			for (String s : enchants)
			{
				String name = s.split(",")[0];
				int lvl = Integer.parseInt(s.split(",")[1]);
				im.addEnchant(Enchantment.getByName(name), lvl, true);
			}
		}
		is.setItemMeta(im);
		return is;
	}

	public String getId(ItemStack is)
	{
		String idstring = "noid";
		if (is != null)
		{
			if (is.hasItemMeta())
			{
				ItemMeta im = is.getItemMeta();
				if (im.hasLore())
				{
					ArrayList<String> lore = new ArrayList<String>();
					lore.addAll(im.getLore());
					for (String s : lore)
					{
						s = s.replaceAll("§", "");
						if (s.contains("id:"))
						{
							idstring = s.split(":")[1];
						}
					}
				}

			}
		}
		return idstring;
	}

	public boolean exists(String id)
	{
		itemData = new File(getDataFolder(), "itemData.yml");
		idata = YamlConfiguration.loadConfiguration(itemData);
		if (idata.getInt(id + ".id") > 0)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public ArrayList<String> getInfo(String id)
	{
		itemData = new File(getDataFolder(), "itemData.yml");
		idata = YamlConfiguration.loadConfiguration(itemData);
		ArrayList<String> info = new ArrayList<String>();
		info.add("Item Info:");
		info.add("Minecraft ID: " + idata.getInt(id + ".id"));
		if (idata.getString(id + ".displayname") != null)
		{
			info.add("Display Name: " + idata.getString(id + ".displayname"));
		}
		if (idata.getStringList(id + ".lore") != null)
		{
			info.add("Lore:");
			ArrayList<String> l = new ArrayList<String>();
			l.addAll(idata.getStringList(id + ".lore"));
			for (String s : l)
			{
				info.add("   " + util.colorString(s));
			}
		}
		if (idata.getStringList(id + ".enchants") != null)
		{
			info.add("Enchantments:");
			ArrayList<String> enchants = new ArrayList<String>();
			enchants.addAll(idata.getStringList(id + ".enchants"));
			for (String s : enchants)
			{
				String name = s.split(",")[0];
				String level = s.split(",")[1];
				info.add("   " + "Name: " + name + " , Level: " + level);
			}
		}
		return info;
	}
}
