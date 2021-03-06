package me.palapon2545.warpplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.palapon2545.warpplate.pluginMain;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class pluginMain extends JavaPlugin implements Listener {

	private final Material Material = null;
	public final Logger logger = Logger.getLogger("Minecraft");
	public pluginMain plugin;

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disable! ");
		saveConfig();
		Bukkit.broadcastMessage(ChatColor.BLUE + "Server> " + ChatColor.GRAY + "SMDWarpPlate System: " + ChatColor.RED
				+ ChatColor.BOLD + "Disable");
		for (Player player1 : Bukkit.getOnlinePlayers()) {
			player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 0);

		}
	}

	public void onEnable() {
		Bukkit.broadcastMessage(ChatColor.BLUE + "Server> " + ChatColor.GRAY + "SMDWarpPlate System: " + ChatColor.GREEN
				+ ChatColor.BOLD + "Enable");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		for (Player player1 : Bukkit.getOnlinePlayers()) {
			player1.playSound(player1.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		Player player = (Player) sender;
		String message = "";
		String m[] = message.split("\\s+");
		if (CommandLabel.equalsIgnoreCase("smdwarpplate")
				|| CommandLabel.equalsIgnoreCase("smdwarpplate:smdwarpplate")) {
			player.sendMessage("=========§e§l[§f§lHow to use§e§l]§r=========");
			player.sendMessage("You need to §e§lplace §rthe §d§lsign.");
			player.sendMessage("If start first line with §b§l[tp] §rLine §d§l2 , 3 , 4 §ris §a§lX , Y , Z §rin order.");
			player.sendMessage(
					"If start first line with §b§l[cmd] §rLine §d§l2 , 3 ,4 §ris §a§lcommand. §r[it will stick line together, space have effect]");
			player.sendMessage("You need to place §c§lone block §ron them.");
			player.sendMessage(
					"Then, place §6§lGOLDEN_PLATE §e§o(Weighted Pressure Plate (Light)) §ron block on them.");
			player.sendMessage("Use §e%p §rwill replace to §eplayer that using name");

		}
		return true;
	}

	@EventHandler
	public void chackbeforewarp(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		loc.setY(loc.getY());
		Block block = loc.getBlock();
		if (block.getType() == Material.GOLD_PLATE) {
			Location loc2 = player.getLocation();
			loc2.setY(loc.getY() - 2);
			Block block2 = loc2.getBlock();
			if ((block2.getType() == Material.SIGN_POST) || (block2.getType() == Material.WALL_SIGN)) {
				Sign sign = (Sign) block2.getState();
				if (sign.getLine(0).equalsIgnoreCase("[tp]") || sign.getLine(0).equalsIgnoreCase("[cmd]")
						|| sign.getLine(0).equalsIgnoreCase("[server]") || sign.getLine(0).equalsIgnoreCase("[world]")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 10));
					ActionBar action = new ActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD + "Hold " + ChatColor.GREEN
							+ ChatColor.BOLD + ChatColor.UNDERLINE + "Shift" + ChatColor.AQUA + " to use.");
					action.sendToPlayer(player);
				}
			}
		}
	}

	@EventHandler
	public void warping(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		Location loc = player.getLocation();
		loc.setY(loc.getY());
		Block block = loc.getBlock();
		if (event.isSneaking() == true) {
			if (block.getType() == Material.GOLD_PLATE) {
				Location loc2 = player.getLocation();
				loc2.setY(loc.getY() - 2);
				Block block2 = loc2.getBlock();
				if ((block2.getType() == Material.SIGN_POST) || (block2.getType() == Material.WALL_SIGN)) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equalsIgnoreCase("[tp]")) {

						double xh = Integer.parseInt(sign.getLine(1));
						double yh = Integer.parseInt(sign.getLine(2));
						double zh = Integer.parseInt(sign.getLine(3));

						Location loca = player.getLocation();
						loca.setX(xh + 0.5);
						loca.setY(yh);
						loca.setZ(zh + 0.5);

						player.teleport(loca);
					}
				}
				if ((block2.getType() == Material.SIGN_POST) || (block2.getType() == Material.WALL_SIGN)) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equalsIgnoreCase("[cmd]")) {
						String cmd = sign.getLine(1) + sign.getLine(2) + sign.getLine(3);
						String cmdreplaceplayer = cmd.replaceAll("%p", playerName);
						player.performCommand(cmdreplaceplayer);
					}
				}
				if ((block2.getType() == Material.SIGN_POST) || (block2.getType() == Material.WALL_SIGN)) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equalsIgnoreCase("[server]")) {
						String cmd = sign.getLine(1) + sign.getLine(2) + sign.getLine(3);
						String cmdreplaceplayer = cmd.replaceAll("%p", playerName);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdreplaceplayer);
					}
				}
				if ((block2.getType() == Material.SIGN_POST) || (block2.getType() == Material.WALL_SIGN)) {
					Sign sign = (Sign) block2.getState();
					if (sign.getLine(0).equalsIgnoreCase("[world]")) {
						World world = Bukkit.getWorld(sign.getLine(1) + sign.getLine(2) + sign.getLine(3));
						if (world != null) {
						double x = player.getLocation().getX();
						double y = player.getLocation().getY();
						double z = player.getLocation().getZ();
						double pitch = player.getLocation().getPitch();
						double yaw = player.getLocation().getYaw();
						Location loc1 = new Location(world, x, y, z);
						loc1.setPitch((float) pitch);
						loc1.setYaw((float) yaw);
						player.teleport(loc1);
						} else {
							ActionBar action = new ActionBar(ChatColor.RED + "World not found, Please contact admin.");
							action.sendToPlayer(player);
						}
					}
				}
			}
		} else {
			return;
		}
	}

	public void playCircularEffect(Location location, Effect effect, boolean v) {
		for (int i = 0; i <= 8; i += ((!v && (i == 3)) ? 2 : 1))
			location.getWorld().playEffect(location, effect, i);
	}
}
