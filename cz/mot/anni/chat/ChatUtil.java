package cz.mot.anni.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;
import cz.mot.anni.Util;
import cz.mot.anni.VaultHooks;
import cz.mot.anni.api.TitleAPI;
import cz.mot.anni.object.Boss;
import cz.mot.anni.object.GameTeam;
import cz.mot.anni.object.PlayerMeta;

public class ChatUtil {

    private static final String RESET = ChatColor.RESET.toString();

    private static boolean roman = false;
    
    public static Main plugin;

    public static void setRoman(boolean b) {
        roman = b;
    }

    public static void allMessage(GameTeam team, Player sender, String message,
            boolean dead) {
        String group;
        String username;
        if (team == GameTeam.NONE) {
            group = Main.getInstance().getConfig().getString("LobbyChat").replace("&", "§");
            username = RESET + sender.getName();
        } else {
            group = Main.getInstance().getConfig().getString("AllChat").replace("&", "§").replace("%color%", String.valueOf(team.color()));
            username = team.color() + sender.getName();
            if (dead) {
                group = Main.getInstance().getConfig().getString("DeadChat").replace("&", "§") + " "
                        + group;
            }
        }
        String msg = message;
        String permGroup = VaultHooks.getGroup(sender.getName());
        if (!permGroup.equals(""))
            group += " " + permGroup + RESET;
        String toSend = group + " " + username + RESET + " " + Main.getInstance().getConfig().getString("ChatEnd").replace("&", "§") + "§r " + msg;
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(toSend);
    }

    public static void teamMessage(GameTeam team, Player sender,
            String message, boolean dead) {
        String group;
        if (team == GameTeam.NONE) {
            allMessage(team, sender, message, false);
            return;
        } else {
            group = Main.getInstance().getConfig().getString("TeamChat").replace("&", "§").replace("%color%", String.valueOf(team.color()));
            if (dead) {
                group = Main.getInstance().getConfig().getString("DeadChat").replace("&", "§") + " " + group;
            }
        }
        String permGroup = VaultHooks.getGroup(sender.getName());
        if (!permGroup.equals(""))
            group += " " + permGroup + RESET;
        String toSend = group + " " + team.color() + sender.getName() + RESET + " " + Main.getInstance().getConfig().getString("ChatEnd").replace("&", "§") + "§r " + message;
        for (Player player : team.getPlayers())
            player.sendMessage(toSend);
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void nexusDestroyed(GameTeam attacker, GameTeam victim, Player p) {
    	String title = Main.getInstance().getConfig().getString("NexusDestroyedTitle").replace("&", "§").replace("%DESTOYED%", victim.coloredName()).replace("%KILLER%", attacker.color().toString() + p.getName()).replace("%TEAM%", attacker.coloredName());
    	String sub = Main.getInstance().getConfig().getString("NexusDestroyedSubTitle").replace("&", "§").replace("%DESTROYED%", victim.coloredName()).replace("%KILLER%", attacker.color().toString() + p.getName()).replace("%TEAM%", attacker.coloredName());
    	
    	broadcast("§8===============[ §eNexus §8]===============");
        broadcast(attacker.color().toString() + p.getName() + " §7from team §r" + attacker.coloredName() + " §7destroy " + victim.coloredName() + " §7nexus!");
        broadcast("§8==============================================");
        if (Main.getInstance().getConfig().getBoolean("EnableNexusTitle") == true) {
        	TitleAPI.AllTitle(title, sub);
        }
    }

    public static String nexusBreakMessage(Player breaker, GameTeam attacker,
            GameTeam victim) {
        return colorizeName(breaker, attacker) + " §7destroying " + victim.coloredName() + " §7nexus!";
    }

    private static String colorizeName(Player player, GameTeam team) {
        return team.color() + player.getName();
    }

    public static void phaseMessage(int phase) {
    	String title = Main.getInstance().getConfig().getString("PhaseTitle").replace("&", "§").replace("%PHASE%", translateRoman(phase));
    	String sub = Main.getInstance().getConfig().getString("PhaseSubTitle").replace("&", "§").replace("%PHASE%", translateRoman(phase));
        broadcast("§8===========[ §eProcedure §8]===========");
        broadcast(Util.getPhaseColor(phase) + "Phase " + translateRoman(phase) + " §7started!");
        switch (phase) {
        case 1:
        	broadcast("");
            broadcast("§7Nexuses are invisible ");
            if (Main.getInstance().getConfig().getBoolean("EnablePhaseTitle") == true) {
            	TitleAPI.AllTitle(title, sub);
            }
            break;
        case 2:
        	broadcast("");
            broadcast("§7Nexuses are visible!");
            broadcast("§7Bosses spawned!");
            if (Main.getInstance().getConfig().getBoolean("EnablePhaseTitle") == true) {
            	TitleAPI.AllTitle(title, sub);
            }
            break;
        case 3:
        	broadcast("");
            broadcast("§bDiamonds §7spawned on the mid!");
            if (Main.getInstance().getConfig().getBoolean("EnablePhaseTitle") == true) {
            	TitleAPI.AllTitle(title, sub);
            }
            break;
        case 4:
        	broadcast("");
        	broadcast("§eShop brewing §7is now aviable!");
            if (Main.getInstance().getConfig().getBoolean("EnablePhaseTitle") == true) {
            	TitleAPI.AllTitle(title, sub);
            }
            break;
        case 5:
        	broadcast("");
            broadcast("§cDouble nexus damage!");
            if (Main.getInstance().getConfig().getBoolean("EnablePhaseTitle") == true) {
            	TitleAPI.AllTitle(title, sub);
            }
        }
        broadcast("§8================================");
    }

    public static void winMessage(GameTeam winner) {
    	String title = Main.getInstance().getConfig().getString("EndGameTitle").replace("&", "§").replace("%TEAM%", winner.coloredName());
    	String sub = Main.getInstance().getConfig().getString("EndGameSubTitle").replace("&", "§").replace("%TEAM%", winner.coloredName());
        broadcast("§8================[ §eGame Over§8 ]================");
        broadcast("§7Team §r" + winner.coloredName() + " §7win this game!");
        broadcast("");
        broadcast("§7We wish you luck in next game! §cRestarting...");
        broadcast("§8=============================================");
        if (Main.getInstance().getConfig().getBoolean("EnableEndTitle") == true) {
        	TitleAPI.AllTitle(title, sub);
        }
    }

    public static void bossDeath(Boss b, Player killer, GameTeam team) {
        broadcast("§8==========[ §eBoss §8]==========");
        broadcast("§7Boss §r" + b.getBossName() + " §7was killed by " + colorizeName(killer, team));
        broadcast("§8===================================");
    }

    public static void bossRespawn(Boss b) {
        broadcast("§8================[ §eBoss §8]================");
        broadcast("§7Boss §r" + b.getBossName() + " §7was spawned!");
        broadcast("§8========================================");
    }

    public static String formatDeathMessage(Player victim, Player killer, String original)
    {
      GameTeam killerTeam = PlayerMeta.getMeta(killer).getTeam();
      String killerColor = killerTeam != null ? killerTeam.color().toString() : ChatColor.DARK_PURPLE.toString();
      String killerName = killerColor + killer.getName() + ChatColor.GRAY;
      
      String message = ChatColor.GRAY + formatDeathMessage(victim, original);
      message = message.replace(killer.getName(), killerName);
      
      return message;
    }
    
    public static String formatDeathMessage(Player victim, String original)
    {
      GameTeam victimTeam = PlayerMeta.getMeta(victim).getTeam();
      String victimColor = victimTeam != null ? victimTeam.color().toString() : 
        ChatColor.DARK_PURPLE.toString();
      String victimName = victimColor + victim.getName() + ChatColor.GRAY;
      
      String message = ChatColor.GRAY + original;
      message = message.replace(victim.getName(), victimName);
      if (message.contains(" §8§"))
      {
        String[] arr = message.split(" §8§");
        message = arr[0];
      }
      return message.replace("was slain by", "was killed by");
    }

    public static String translateRoman(int number) {
        if (!roman)
            return String.valueOf(number);

        switch (number) {
        case 0:
            return "0";
        case 1:
            return "I";
        case 2:
            return "II";
        case 3:
            return "III";
        case 4:
            return "IV";
        case 5:
            return "V";
        case 6:
            return "VI";
        case 7:
            return "VII";
        case 8:
            return "VIII";
        case 9:
            return "IX";
        case 10:
            return "X";
        default:
            return String.valueOf(number);
        }
    }

    private ChatUtil() {
    }
}