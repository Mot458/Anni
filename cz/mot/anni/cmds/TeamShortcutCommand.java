package cz.mot.anni.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;

public class TeamShortcutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	String prefix = Main.getInstance().getConfig().getString("prefix").replace("&", "§");
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + Main.getInstance().getConfigManager().getConfig("messages.yml").getString("ouseplayer").replace("&", "§"));
            return true;
        }
        
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("red")) p.performCommand("team red");
        if (label.equalsIgnoreCase("blue")) p.performCommand("team blue");
        if (label.equalsIgnoreCase("green")) p.performCommand("team green");
        if (label.equalsIgnoreCase("yellow")) p.performCommand("team yellow");
        
        return false;
    }

}
