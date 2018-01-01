package cz.mot.anni.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cz.mot.anni.Main;

public class AnniCommand implements CommandExecutor {
    private Main plugin;

    public AnniCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = plugin.getConfig().getString("prefix").replace("&", "§");
        
        if (args.length == 0) {
        	sender.sendMessage("");
            sender.sendMessage(prefix + " §eAnnihilation §cv" + plugin.getDescription().getVersion());
            sender.sendMessage(prefix + " §7Recoded by §eMot458");
            sender.sendMessage(prefix + " §eAuthor spigot: §7http://bit.ly/Mot458");
            sender.sendMessage(prefix + " §ePlugin: §7http://bit.ly/AnniRecoded");
            sender.sendMessage("");
            sender.sendMessage("§8=-=-=-=-=-=[ §eHelp§8 ]=-=-=-=-=-=");
            sender.sendMessage("");
            sender.sendMessage(prefix + " §e/anni §7» §aShow you info about plugin");
            sender.sendMessage(prefix + " §e/anni start §7» §aStart game");
            sender.sendMessage(prefix + " §e/stats §7» §aShow you stats");
            sender.sendMessage(prefix + " §e/team §7» §aShow you info about teams");
            sender.sendMessage(prefix + " §e/team <team> §7» §aJoin you to team");
            sender.sendMessage(prefix + " §e/vote <map> §7» §aVote for map");
            sender.sendMessage(prefix + " §e/class §7» §aOpen you class (kit) menu");
            sender.sendMessage(prefix + " §e/distance §7» §aShow you nearest nexus");
            sender.sendMessage(prefix + " §e/map <edit/save> <map name> §7» §aEdit/Save anni map");
            sender.sendMessage(prefix + " §e/yellow, /red, /blue, /green §7» §aJoin you to team");
        }
        
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                if (sender.hasPermission("anni.command.start")) {
                    if (!plugin.startTimer()) {
                        sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("game.alreadystart").replace("&", "§"));
                    } else {
                        sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("game.starting").replace("&", "§"));
                    }
                } else sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("dontperm").replace("&", "§"));
            }
        }
        return false;
    }
}
