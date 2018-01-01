package cz.mot.anni.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;
import cz.mot.anni.object.GameTeam;
import cz.mot.anni.object.PlayerMeta;


public class DistanceCommand implements CommandExecutor {
    private Main plugin;

    public DistanceCommand(Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String prefix = plugin.getConfig().getString("prefix").replace("&", "§");

            if (plugin.getPhase() == 0) {
                p.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("game.isntstart").replace("&", "§"));
                return false;
            }

            if (PlayerMeta.getMeta(p).getTeam() == GameTeam.NONE) {
                p.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("team.notjoin").replace("&", "§"));
                return false;
            }

            p.sendMessage("§8=========[ §eDistance §8]=========");

            for (GameTeam t : GameTeam.values()) {
                if (t != GameTeam.NONE) {
                    showTeam(p, t);
                }
            }

            p.sendMessage("§8==============================");
        } else {
        	String prefix = plugin.getConfig().getString("prefix").replace("&", "§");
            sender.sendMessage(prefix + " §cThis command can use only player!");
        }

        return true;
    }

    private void showTeam(Player p, GameTeam t) {
        try {
            if (t.getNexus() != null && t.getNexus().getHealth() > 0)
                p.sendMessage(t.coloredName() + ChatColor.GRAY + " Nexus: " + ChatColor.WHITE + ((int) p.getLocation().distance(t.getNexus().getLocation())) + ChatColor.GRAY + " blokcks");
        } catch (IllegalArgumentException ex) {

        }
    }
}
