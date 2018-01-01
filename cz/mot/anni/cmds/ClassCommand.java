package cz.mot.anni.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;
import cz.mot.anni.Util;

public class ClassCommand implements CommandExecutor {
    private Main plugin;

    public ClassCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = plugin.getConfig().getString("prefix").replace("&", "§");
        Player p = (Player) sender;
        
        if (args.length == 0) {
        	if (!(sender instanceof Player)) {
        		sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("ouseplayer").replace("&", "§"));
        	} else {
            	String namei = plugin.getConfig().getString("TitleClassMenu").replace("&", "§");
                Util.showClassSelector(p,
                        namei);
        	}
        }
        
        if (args.length == 1) {
        	if (!(sender instanceof Player)) {
        		sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("ouseplayer").replace("&", "§"));
        	} else {
        	sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("useclass").replace("&", "§"));
            }
        }
        return false;
    }
}
