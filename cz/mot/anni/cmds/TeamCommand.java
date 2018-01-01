package cz.mot.anni.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;

public class TeamCommand implements CommandExecutor {
    private final Main plugin;

    public TeamCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "§");
        if (args.length == 0)
            plugin.listTeams(sender);
        else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("ouseplayer").replace("&", "§"));
            } else {
                plugin.joinTeam((Player) sender, args[0]);
            }
        }
        return true;
    }
}
