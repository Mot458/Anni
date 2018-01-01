package cz.mot.anni.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBrewer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import cz.mot.anni.Main;
import cz.mot.anni.object.GameTeam;
import cz.mot.anni.object.PlayerMeta;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.TileEntityBrewingStand;

public class EnderBrewingStandListener implements Listener {
	private HashMap<GameTeam, Location> locations;
	private HashMap<String, VirtualBrewingStand> brewingStands;

	public EnderBrewingStandListener(Main plugin) {
		locations = new HashMap<GameTeam, Location>();
		brewingStands = new HashMap<String, VirtualBrewingStand>();

		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			public void run() {
				for (VirtualBrewingStand b : brewingStands.values()) {
					try {
						b.c();
					} catch (Exception e) {

					}
				}
			}
		}, 0L, 1L);
	}

	public void setBrewingStandLocation(GameTeam team, Location loc) {
		locations.put(team, loc);
	}

	@EventHandler
	public void onBrewingStandOpen(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block b = e.getClickedBlock();
		if (b.getType() != Material.BREWING_STAND) {
			return;
		}

		Location loc = b.getLocation();
		Player player = e.getPlayer();
		GameTeam team = PlayerMeta.getMeta(player).getTeam();
		if (team == null || !locations.containsKey(team)) {
			return;
		}

		if (locations.get(team).equals(loc)) {
			EntityPlayer handle = ((CraftPlayer) player).getHandle();
			handle.openContainer(getBrewingStand(player));
			player.sendMessage(Main.getInstance().getConfig().getString("prefix").replace("&", "§") + Main.getInstance().getConfigManager().getConfig("messages.yml").getString("blocks.brewing").replace("&", "§"));
		} else if (locations.containsValue(loc)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBrewingStandBreak(BlockBreakEvent e) {
		if (locations.values().contains(e.getBlock().getLocation())) {
			e.setCancelled(true);
		}
	}

	private VirtualBrewingStand getBrewingStand(Player player) {
		if (!brewingStands.containsKey(player.getName())) {
			EntityPlayer handle = ((CraftPlayer) player).getHandle();
			brewingStands.put(player.getName(), new VirtualBrewingStand(handle));
		}
		return brewingStands.get(player.getName());
	}

	private class VirtualBrewingStand extends TileEntityBrewingStand {
		public VirtualBrewingStand(EntityHuman entity) {
			world = entity.world;
		}

		public InventoryHolder getOwner() {
			return new InventoryHolder() {
				public Inventory getInventory() {
					return new CraftInventoryBrewer(VirtualBrewingStand.this);
				}
			};
		}
	}
}
