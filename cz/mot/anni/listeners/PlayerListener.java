package cz.mot.anni.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cz.mot.anni.Main;
import cz.mot.anni.Util;
import cz.mot.anni.api.TitleAPI;
import cz.mot.anni.chat.ChatUtil;
import cz.mot.anni.events.NexusDamageEvent;
import cz.mot.anni.events.NexusDestroyEvent;
import cz.mot.anni.manager.PhaseManager;
import cz.mot.anni.object.GameTeam;
import cz.mot.anni.object.Kit;
import cz.mot.anni.object.PlayerMeta;
import cz.mot.anni.stats.StatType;
import cz.mot.anni.stats.StatsGUI;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class PlayerListener implements Listener {
    private final Main plugin;

    private final HashMap<String, Kit> kitsToGive = new HashMap<>();

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("static-access")
	@EventHandler
    public void onMOTDPing(ServerListPingEvent e) {
    	
                if (plugin.getPhase() == 0) {
                    String motd = plugin.getConfig().getString("motdLobby").replace("&", "§");
                    motd = motd.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                    motd = motd.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                    motd = motd.replace("%ARROW%", "»");
                    
                	e.setMotd(motd);
                	
                } else if (plugin.getPhase() == 1) {
                	
               String motd1 = plugin.getConfig().getString("motdPhase1").replace("&", "§");
                motd1 = motd1.replaceAll("%TIME%", plugin.getPhaseManager().timeString(plugin.getPhaseManager().getTime()));
                motd1 = motd1.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd1 = motd1.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                motd1 = motd1.replaceAll("%GREENNEXUS%", String.valueOf(getNexus(GameTeam.GREEN)));
                motd1 = motd1.replaceAll("%GREENCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd1 = motd1.replaceAll("%REDNEXUS%", String.valueOf(getNexus(GameTeam.RED)));
                motd1 = motd1.replaceAll("%REDCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd1 = motd1.replaceAll("%BLUENEXUS%", String.valueOf(getNexus(GameTeam.BLUE)));
                motd1 = motd1.replaceAll("%BLUECOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd1 = motd1.replaceAll("%YELLOWNEXUS%", String.valueOf(getNexus(GameTeam.YELLOW)));
                motd1 = motd1.replaceAll("%YELLOWCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd1 = motd1.replace("%ARROW%", "»");
                
                e.setMotd(motd1);
                
                } else if (plugin.getPhase() == 2) {
                	
               String motd2 = plugin.getConfig().getString("motdPhase2").replace("&", "§");
                motd2 = motd2.replaceAll("%TIME%", plugin.getPhaseManager().timeString(plugin.getPhaseManager().getTime()));
                motd2 = motd2.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd2 = motd2.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                motd2 = motd2.replaceAll("%GREENNEXUS%", String.valueOf(getNexus(GameTeam.GREEN)));
                motd2 = motd2.replaceAll("%GREENCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd2 = motd2.replaceAll("%REDNEXUS%", String.valueOf(getNexus(GameTeam.RED)));
                motd2 = motd2.replaceAll("%REDCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd2 = motd2.replaceAll("%BLUENEXUS%", String.valueOf(getNexus(GameTeam.BLUE)));
                motd2 = motd2.replaceAll("%BLUECOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd2 = motd2.replaceAll("%YELLOWNEXUS%", String.valueOf(getNexus(GameTeam.YELLOW)));
                motd2 = motd2.replaceAll("%YELLOWCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd2 = motd2.replace("%ARROW%", "»");
                
                e.setMotd(motd2);
                
                } else if (plugin.getPhase() == 3) {
                	
               String motd3 = plugin.getConfig().getString("motdPhase3").replace("&", "§");
                motd3 = motd3.replaceAll("%TIME%", plugin.getPhaseManager().timeString(plugin.getPhaseManager().getTime()));
                motd3 = motd3.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd3 = motd3.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                motd3 = motd3.replaceAll("%GREENNEXUS%", String.valueOf(getNexus(GameTeam.GREEN)));
                motd3 = motd3.replaceAll("%GREENCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd3 = motd3.replaceAll("%REDNEXUS%", String.valueOf(getNexus(GameTeam.RED)));
                motd3 = motd3.replaceAll("%REDCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd3 = motd3.replaceAll("%BLUENEXUS%", String.valueOf(getNexus(GameTeam.BLUE)));
                motd3 = motd3.replaceAll("%BLUECOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd3 = motd3.replaceAll("%YELLOWNEXUS%", String.valueOf(getNexus(GameTeam.YELLOW)));
                motd3 = motd3.replaceAll("%YELLOWCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd3 = motd3.replace("%ARROW%", "»");
                
                e.setMotd(motd3);
                
                } else if (plugin.getPhase() == 4) {
                	
               String motd4 = plugin.getConfig().getString("motdPhase4").replace("&", "§");
                motd4 = motd4.replaceAll("%TIME%", plugin.getPhaseManager().timeString(plugin.getPhaseManager().getTime()));
                motd4 = motd4.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd4 = motd4.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                motd4 = motd4.replaceAll("%GREENNEXUS%", String.valueOf(getNexus(GameTeam.GREEN)));
                motd4 = motd4.replaceAll("%GREENCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd4 = motd4.replaceAll("%REDNEXUS%", String.valueOf(getNexus(GameTeam.RED)));
                motd4 = motd4.replaceAll("%REDCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd4 = motd4.replaceAll("%BLUENEXUS%", String.valueOf(getNexus(GameTeam.BLUE)));
                motd4 = motd4.replaceAll("%BLUECOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd4 = motd4.replaceAll("%YELLOWNEXUS%", String.valueOf(getNexus(GameTeam.YELLOW)));
                motd4 = motd4.replaceAll("%YELLOWCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd4 = motd4.replace("%ARROW%", "»");
                
                e.setMotd(motd4);
                
                } else if (plugin.getPhase() == 5) {
                	
               String motd5 = plugin.getConfig().getString("motdPhase5").replace("&", "§");
                motd5 = motd5.replaceAll("%TIME%", plugin.getPhaseManager().timeString(plugin.getPhaseManager().getTime()));
                motd5 = motd5.replaceAll("%PLAYERCOUNT%", String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd5 = motd5.replaceAll("%MAXPLAYERS%", String.valueOf(Bukkit.getMaxPlayers()));
                motd5 = motd5.replaceAll("%GREENNEXUS%", String.valueOf(getNexus(GameTeam.GREEN)));
                motd5 = motd5.replaceAll("%GREENCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd5 = motd5.replaceAll("%REDNEXUS%", String.valueOf(getNexus(GameTeam.RED)));
                motd5 = motd5.replaceAll("%REDCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd5 = motd5.replaceAll("%BLUENEXUS%", String.valueOf(getNexus(GameTeam.BLUE)));
                motd5 = motd5.replaceAll("%BLUECOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd5 = motd5.replaceAll("%YELLOWNEXUS%", String.valueOf(getNexus(GameTeam.YELLOW)));
                motd5 = motd5.replaceAll("%YELLOWCOUNT%", String.valueOf(getPlayers(GameTeam.GREEN)));
                motd5 = motd5.replace("%ARROW%", "»");
                
                e.setMotd(motd5);
        }
      }
    
    private int getNexus(GameTeam t) {
        int health = 0;

        if (t.getNexus() != null)
            health = t.getNexus().getHealth();

        return health;
    }

    private int getPlayers(GameTeam t) {
        int size = 0;

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerMeta meta = PlayerMeta.getMeta(p);
            if (meta.getTeam() == t)
                size++;
        }

        return size;
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerMeta pmeta = PlayerMeta.getMeta(player);
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getItemInHand();
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("ItemClassID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("JoinItemClassName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName()
                                .contains(item)) {
                        	String namei = plugin.getConfig().getString("TitleClassMenu").replace("&", "§");
                            Util.showClassSelector(e.getPlayer(),
                                    namei);
                            return;
                        }
                    }
                }
                if (handItem.getType() == Material.COMPASS) {
                    boolean setCompass = false;
                    boolean setToNext = false;
                    while (!setCompass) {
                        for (GameTeam team : GameTeam.teams()) {
                            if (setToNext) {
                                ItemMeta meta = handItem.getItemMeta();
                                String kompas = plugin.getConfig().getString("CompassItem").replace("&", "§").replace("%TEAM%", String.valueOf(team.toString())).replaceAll("%COLOR%", String.valueOf(team.color()));
                                meta.setDisplayName(kompas);
                     
                                handItem.setItemMeta(meta);
                                player.setCompassTarget(team.getNexus()
                                        .getLocation());
                                setCompass = true;
                                break;
                            }
                            if (handItem.getItemMeta().getDisplayName()
                                    .contains(team.toString()))
                                setToNext = true;
                        }
                    }
                }
            }
        }

        if (e.getClickedBlock() != null) {
            Material clickedType = e.getClickedBlock().getType();
            if (clickedType == Material.SIGN_POST
                    || clickedType == Material.WALL_SIGN) {
                Sign s = (Sign) e.getClickedBlock().getState();
                if (s.getLine(0).contains(ChatColor.DARK_PURPLE + "[Team]")) {
                    String teamName = ChatColor.stripColor(s.getLine(1));
                    GameTeam team = GameTeam.valueOf(teamName.toUpperCase());
                    if (team != null) {
                        if (pmeta.getTeam() == GameTeam.NONE)
                            plugin.joinTeam(e.getPlayer(), teamName);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
    	Player p = e.getPlayer();
    	if (p.getWorld().getName().contains("lobby")) {
    		e.setCancelled(true);
    	}
    }

	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInteractTeam(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getItemInHand();
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("ItemTeamID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("JoinItemTeamName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName().contains(item));
                        	plugin.Team(player);
                        }
                    }
                }
        }
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInteractStart(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getItemInHand();
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("StartItemID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("StartItemName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName().contains(item));
                        
                        plugin.startTimer();
                        }
                    }
                }
        }
    }
	
	@EventHandler
    public void onInteractVote(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = p.getItemInHand();
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("VoteItemID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("VoteItemName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName().contains(item));
                        
                        plugin.Vote(p);
                        }
                    }
                }
        }
    }
	
	@EventHandler
    public void onInteractLeave(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack handItem = p.getItemInHand();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("LeaveItemID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("LeaveItemName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName().contains(item));
                        
                    	String message = plugin.getConfig().getString("LeaveMessage").replace("&", "§").replace("%ARROW%", "»");
                        
                        p.sendMessage(message);
                        
                        Random random = new Random();
                        List<String> randomArgs = Arrays.asList("hub1", "hub2");
                        int ints = random.nextInt(randomArgs.size());
                        String rndm = randomArgs.get(ints);
                        
                        out.writeUTF("Connect");
                        out.writeUTF("hub1");
                        p.sendPluginMessage((Plugin) this, "BungeeCord", out.toByteArray());
                        }
                    }
                }
        }
    }
	
	@EventHandler
    public void onInteractStats(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack handItem = p.getItemInHand();
        
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            if (handItem != null) {
            	int id = plugin.getConfig().getInt("StatsItemID");
                if (handItem.getType() == Material.getMaterial(id)) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                    	String item = plugin.getConfig().getString("StatsItemName").replace("&", "§");
                        if (handItem.getItemMeta().getDisplayName().contains(item));
                        
                        StatsGUI.openStatsGUI(p);
                        }
                    }
                }
        }
    }
	
	//:         player.teleport(meta.getTeam().getRandomSpawn());
	
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
        Player p = e.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        
    	p.setGameMode(GameMode.SURVIVAL);
        
        if (meta.isAlive()) {
            if (kitsToGive.containsKey(e.getPlayer().getName())) {
                meta.setKit(kitsToGive.get(e.getPlayer().getName()));
                kitsToGive.remove(e.getPlayer().getName());
            }
      e.setRespawnLocation(meta.getTeam().getRandomSpawn());
      p.setGameMode(GameMode.SPECTATOR);
      p.setFlySpeed(0.1F);
      p.sendMessage(prefix + " §7You are invisible to §b3 §7seconds!");
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
         public void run() {
          PlayerMeta meta = PlayerMeta.getMeta(p);
          
          p.teleport(meta.getTeam().getRandomSpawn());
          meta.getKit().give(p, meta.getTeam());
          p.setGameMode(GameMode.SURVIVAL);
          p.sendMessage(prefix + " §7Now you are uninvisible!");
        }
      }, 60L);
        } else {
            e.setRespawnLocation(plugin.getMapManager().getLobbySpawnPoint());
            p.setGameMode(GameMode.SPECTATOR);
        	p.setPlayerListName("§7§l[SPEC] §f" + p.getDisplayName());
        	for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
      	      pl.hidePlayer(p);
    }
  }
}
    

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
    	String msg = plugin.getConfig().getString("JoinTitle").replace("&", "§");
    	String msg1 = plugin.getConfig().getString("JoinSubTitle").replace("&", "§");
        final Player player = e.getPlayer();
        plugin.getPhaseManager();
        
        player.setGameMode(GameMode.ADVENTURE);
		String msg2 = plugin.getConfig().getString("Footer").replace("&", "§").replaceAll(plugin.getPhase() == 0 ? plugin.getConfig().getString("motdLobby") : "%PHASE%", ChatUtil.translateRoman(plugin.getPhase())).replaceAll("%TIME%", PhaseManager.timeString(plugin.getPhaseManager().getTime()));
        e.setJoinMessage("");
        PlayerMeta meta = PlayerMeta.getMeta(player);
        if (Main.getInstance().getConfig().getBoolean("EnableTab") == true) {
        	TitleAPI.sendTabTitle(player, String.valueOf(plugin
                    .getMapManager().getCurrentMap() == null ? plugin.getConfig().getString("Voting") : plugin.getConfig()
    				.getString("Header").replaceAll("%MAP%", WordUtils.capitalize(plugin.voting.getWinner()))), String.valueOf(plugin.getPhase() == 0 ? plugin.getConfig().getString("motdLobby") : msg2));
        }
        player.sendMessage(prefix + plugin.getConfigManager().getConfig("config.yml").getString("ActionWelcome").replace("&", "§"));
        if (plugin.getPhase() > plugin.lastJoinPhase && !player.hasPermission("anni.bypass.phaselimiter")) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(plugin.getMapManager().getLobbySpawnPoint());
                	player.setGameMode(GameMode.SPECTATOR);
                	player.setPlayerListName("§7§l[SPEC] §f" + player.getDisplayName());
                	for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                	      pl.hidePlayer(player);
                	}
                }
            }, 1l);
            e.setJoinMessage(null);
            return;
        }
        if (Main.getInstance().getConfig().getBoolean("EnableJoinTitle") == true) {
        	TitleAPI.sendTitle(player, 20, 50, 20, msg, msg1);
        }
        

        if (meta.isAlive())
            player.teleport(meta.getTeam().getRandomSpawn());
        else {
        	player.setGameMode(GameMode.ADVENTURE);
            player.teleport(plugin.getMapManager().getLobbySpawnPoint());
            PlayerInventory inv = player.getInventory();
            inv.setHelmet(null);
            inv.setChestplate(null);
            inv.setLeggings(null);
            inv.setBoots(null);
            player.setHealth(20);
            player.setFoodLevel(20);

            player.getInventory().clear();

            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());

            player.setLevel(0);
            player.setExp(0);
            player.setSaturation(20F);
            int id = plugin.getConfig().getInt("ItemClassID");
            ItemStack selector = new ItemStack(Material.getMaterial(id));
            ItemMeta itemMeta = selector.getItemMeta();
            String item = plugin.getConfig().getString("JoinItemClassName").replace("&", "§");
            itemMeta.setDisplayName(item);
            Util.giveTeamSelector(player);
            selector.setItemMeta(itemMeta);
        	int slot = plugin.getConfig().getInt("JoinItemClassSlot");
            player.getInventory().setItem(slot-1, selector);
            
            int id4 = plugin.getConfig().getInt("VoteItemID");
        	int slot4 = plugin.getConfig().getInt("VoteItemSlot");
            String item4 = plugin.getConfig().getString("VoteItemName").replace("&", "§");
			ItemStack vote = new ItemStack(Material.getMaterial(id4));
            ItemMeta itemMeta4 = vote.getItemMeta();
            itemMeta4.setDisplayName(item4);
            vote.setItemMeta(itemMeta4);
            player.getInventory().setItem(slot4-1, vote);
            
            int id5 = plugin.getConfig().getInt("LeaveItemID");
        	int slot5 = plugin.getConfig().getInt("LeaveItemSlot");
            String item5 = plugin.getConfig().getString("LeaveItemName").replace("&", "§");
			ItemStack leave = new ItemStack(Material.getMaterial(id5));
            ItemMeta itemMeta5 = leave.getItemMeta();
            itemMeta5.setDisplayName(item5);
            leave.setItemMeta(itemMeta5);
            player.getInventory().setItem(slot5-1, leave);
            
            int id6 = plugin.getConfig().getInt("StatsItemID");
        	int slot6 = plugin.getConfig().getInt("StatsItemSlot");
            String item6 = plugin.getConfig().getString("StatsItemName").replace("&", "§");
			ItemStack stats = new ItemStack(Material.getMaterial(id6));
            ItemMeta itemMeta6 = stats.getItemMeta();
            itemMeta6.setDisplayName(item6);
            stats.setItemMeta(itemMeta6);
            player.getInventory().setItem(slot6-1, stats);
            
            if (player.hasPermission("anni.startitem")) {
            	
                int id3 = plugin.getConfig().getInt("StartItemID");
            	int slot3 = plugin.getConfig().getInt("StartItemSlot");
                String item3 = plugin.getConfig().getString("StartItemName").replace("&", "§");
    			ItemStack start = new ItemStack(Material.getMaterial(id3));
                ItemMeta itemMeta3 = start.getItemMeta();
                itemMeta3.setDisplayName(item3);
                start.setItemMeta(itemMeta3);
                player.getInventory().setItem(slot3-1, start);
            }
            
            player.updateInventory();
        }

        if (plugin.useMysql)
            plugin.getDatabaseHandler()
                    .query("INSERT IGNORE INTO `annihilation` (`username`, `kills`, "
                            + "`deaths`, `wins`, `losses`, `nexus_damage`) VALUES "
                            + "('"
                            + player.getName()
                            + "', '0', '0', '0', '0', '0');");

        if (plugin.getPhase() == 0 && plugin.getVotingManager().isRunning()) {
            plugin.checkStarting();
        }

        plugin.getSignHandler().updateSigns(meta.getTeam());
        plugin.getScoreboardHandler().update();
    }
    
    /**
    *
    * @param event - Resets GameTeam When Player Leaves Lobby
    */
    @EventHandler
    public void leaveRemoveGameTeam(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        if (event.getPlayer().getWorld().getName().equals("lobby")) {
            if (meta.isAlive())
            meta.setAlive(false);
                meta.setTeam(GameTeam.NONE);
        plugin.getSignHandler().updateSigns(meta.getTeam());
        
        event.setQuitMessage(null);
    }
}

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();

        if (plugin.getPhase() > 0) {
            PlayerMeta meta = PlayerMeta.getMeta(p);
            if (!meta.getTeam().getNexus().isAlive()) {
                meta.setAlive(false);
                for (Player pp : Bukkit.getOnlinePlayers())
                    pp.hidePlayer(p);
            }
        }

        plugin.getStatsManager().setValue(StatType.DEATHS, p, plugin.getStatsManager().getStat(StatType.DEATHS, p) + 1);

        if (p.getKiller() != null && !p.getKiller().equals(p)) {
            Player killer = p.getKiller();
            plugin.getStatsManager().incrementStat(StatType.KILLS, killer);
            e.setDeathMessage(ChatUtil.formatDeathMessage(p, p.getKiller(), e.getDeathMessage()));

        } else
            e.setDeathMessage(ChatUtil.formatDeathMessage(p, e.getDeathMessage()));
        if (p.getKiller() instanceof Player) {
        	p.getKiller().giveExpLevels(plugin.getConfig().getInt("KillXP"));	
        }

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                EntityPlayer cPlayer = ((CraftPlayer) p).getHandle();
                cPlayer.playerConnection.a(in);
            }
        }, 1l);
    }
    
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        final Player player = e.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        final String namei = plugin.getConfig().getString("TitleClassMenu").replace("&", "§");
        player.teleport(meta.getTeam().getRandomSpawn());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
            	Util.showClassSelector(player, namei);
            }
        }, 10L);
        if (!plugin.getPortalPlayers().containsKey(player)) {
            plugin.getPortalPlayers().put(player, player.getName());
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getEntity().getWorld().getName().equals("lobby")) {
                e.setCancelled(true);

                if (e.getCause() == DamageCause.VOID)
                    e.getEntity().teleport(
                            plugin.getMapManager().getLobbySpawnPoint());
            }
        }
    }


    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            if (damager.getWorld().getName().equals("lobby")) {
                e.setCancelled(true);
                return;
            }
            if (plugin.getPhase() < 1) {
                e.setCancelled(true);
                return;
            }

            Player attacker = (Player) damager;
            if (PlayerMeta.getMeta(attacker).getKit() == Kit.WARRIOR) {
                ItemStack hand = attacker.getItemInHand();
                if (hand != null) {
                    String lowercaseName = hand.getType().toString()
                            .toLowerCase();
                    if (lowercaseName.contains("sword")
                            || lowercaseName.contains("axe"))
                        e.setDamage(e.getDamage() + 1.0);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
        if (plugin.getPhase() > 0) {
            if (Util.isEmptyColumn(e.getBlock().getLocation()))
                e.setCancelled(true);

            if (tooClose(e.getBlock().getLocation())
                    && !e.getPlayer().hasPermission("anni.buildbypass")) {
                e.getPlayer().sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("nexus.buildnear").replace("&", "§"));
                e.setCancelled(true);
            }
        } else {
            if (!e.getPlayer().hasPermission("anni.buildbypass"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        if (e.getPlayer().hasPermission("anni.buildbypass"))
            if (e.getLine(0).toLowerCase().contains("[Shop]".toLowerCase()))
                e.setLine(0, ChatColor.BLACK + "[Shop]");
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
        if (plugin.getPhase() > 0) {
            for (GameTeam t : GameTeam.teams()) {
                if (t.getNexus().getLocation()
                        .equals(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    if (t.getNexus().isAlive())
                        breakNexus(t, e.getPlayer());
                    return;
                }
            }

            if (tooClose(e.getBlock().getLocation())
                    && !e.getPlayer().hasPermission("anni.buildbypass")
                    && e.getBlock().getType() != Material.ENDER_STONE) {
                e.getPlayer().sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("nexus.destroynear").replace("&", "§"));
                e.setCancelled(true);
            }
        } else {
            if (!e.getPlayer().hasPermission("anni.buildbypass"))
                e.setCancelled(true);
        }
    }

    private boolean tooClose(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        for (GameTeam team : GameTeam.teams()) {
            Location nexusLoc = team.getNexus().getLocation();
            double nX = nexusLoc.getX();
            double nY = nexusLoc.getY();
            double nZ = nexusLoc.getZ();
            if (Math.abs(nX - x) <= plugin.build
                    && Math.abs(nY - y) <= plugin.build
                    && Math.abs(nZ - z) <= plugin.build)
                return true;
        }

        return false;
    }

    private void addHeart(Player player) {
        double maxHealth = player.getMaxHealth();
        if (maxHealth < 30.0) {
            double newMaxHealth = maxHealth + 2.0;
            player.setMaxHealth(newMaxHealth);
            player.setHealth(player.getHealth() + 2.0);
        }
    }

    private void breakNexus(final GameTeam victim, Player breaker) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
        final GameTeam attacker = PlayerMeta.getMeta(breaker).getTeam();
        if (victim == attacker)
            breaker.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("nexus.destroyyour").replace("&", "§"));
        else if (plugin.getPhase() == 1)
            breaker.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("nexus.inthisphase").replace("&", "§"));
        else {
            plugin.getScoreboardHandler().sb.getTeam(victim.name() + "SB").setPrefix(ChatColor.RESET.toString());
            victim.getNexus().damage(plugin.getPhase() == 5 ? 2 : 1);

            plugin.getStatsManager().incrementStat(StatType.NEXUS_DAMAGE, breaker, plugin.getPhase() == 5 ? 2 : 1);

            String msg = ChatUtil.nexusBreakMessage(breaker, attacker, victim);
            for (Player p : attacker.getPlayers())
                Bukkit.broadcastMessage(msg);

            plugin.getScoreboardHandler().scores.get(victim.name()).setScore(victim.getNexus().getHealth());
            Bukkit.getServer().getPluginManager().callEvent(new NexusDamageEvent(breaker, victim, victim.getNexus().getHealth()));

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getScoreboardHandler().sb.getTeam(victim.name() + "SB").setPrefix(victim.color().toString());
                }
            }, 2L);

            Random r = new Random();
            float pitch = 0.5F + r.nextFloat() * 0.5F;
            victim.getNexus()
                    .getLocation()
                    .getWorld()
                    .playSound(victim.getNexus().getLocation(),
                            Sound.ANVIL_LAND, 1F, pitch);
            }
      
            if (plugin.getPhase() > 1) {
            Random s = new Random();
            float pitch = 5F + s.nextFloat() * 2F;
            Location nexus = victim.getNexus().getLocation().clone();
            Util.ParticleEffect.FLAME.display(pitch, pitch, pitch, pitch, 10, nexus, pitch);
            Util.ParticleEffect.ENCHANTMENT_TABLE.display(pitch, pitch, pitch, pitch, 10, nexus, pitch);

            if (victim.getNexus().getHealth() == 0) {
                Bukkit.getServer().getPluginManager().callEvent(new NexusDestroyEvent(breaker, victim));
                ChatUtil.nexusDestroyed(attacker, victim, breaker);

                plugin.checkWin();

                for (Player p : victim.getPlayers()) {
                    plugin.getStatsManager().incrementStat(StatType.LOSSES, p);
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                	Util.ParticleEffect.EXPLOSION_NORMAL.display(pitch, pitch, pitch, pitch , 1, nexus, 2);
                    player.getWorld().playSound(player.getLocation(),
                            Sound.EXPLODE, 2F, 1.25F);
                }

                for (final Location spawn : victim.getSpawns()) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Util.spawnFirework(spawn,
                                    attacker.getColor(attacker),
                                    attacker.getColor(attacker));
                        }
                    }, new Random().nextInt(20));
                }

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Location nexus = victim.getNexus().getLocation()
                                .clone();
                        boolean found = false;
                        int y = 0;

                        while (!found) {
                            y++;

                            Block b = nexus.add(0, 1, 0).getBlock();

                            if (b != null && b.getType() == Material.BEACON)
                                b.setType(Material.AIR);

                            if (y > 10)
                                found = true;
                        }
                    }
                });
            }

            plugin.getSignHandler().updateSigns(victim);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equals("lobby")) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§").replace("%ARROW%", "»");
     	String msg1 = prefix + plugin.getConfigManager().getConfig("messages.yml").getString("class.selected").replace("&", "§");
    	String msg2 = prefix + plugin.getConfigManager().getConfig("messages.yml").getString("class.youget").replace("&", "§");
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        String namei = plugin.getConfig().getString("TitleClassMenu").replace("&", "§");
        if (inv.getTitle().startsWith(namei)) {
            if (e.getCurrentItem().getType() == Material.AIR)
                return;
            player.closeInventory();
            e.setCancelled(true);
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            PlayerMeta meta = PlayerMeta.getMeta(player);

            if (!Kit.valueOf(ChatColor.stripColor(name).toUpperCase())
                    .isOwnedBy(player)) {
                player.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("dontperm").replace("&", "§"));
                return;
            }

        	String msgT = plugin.getConfig().getString("ClassTitleSelect").replace("&", "§").replace("%CLASS%", (name));
        	String msgST = plugin.getConfig().getString("ClassSubTitleSelect").replace("&", "§").replace("%CLASS%", (name));
            
        	player.sendMessage(msg1 + (name));
        	TitleAPI.sendTitle(player, 20, 50, 20, msgT, msgST);
        	player.sendMessage(msg2);
            meta.setKit(Kit.getKit(ChatColor.stripColor(name)));
            if (Main.getInstance().getConfig().getBoolean("EnableClassTitle") == true) {
            	TitleAPI.sendTitle(player, 20, 50, 20, msgT, msgST);
            }
            if (plugin.getPortalPlayers().containsKey(player)) {
                plugin.getPortalPlayers().remove(player);
                player.setHealth(0D);
            }
        }
    }
}
