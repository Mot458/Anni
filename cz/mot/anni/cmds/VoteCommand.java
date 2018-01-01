package cz.mot.anni.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cz.mot.anni.Main;
import cz.mot.anni.manager.VotingManager;


public class VoteCommand implements CommandExecutor {
    private final VotingManager manager;

    public VoteCommand(VotingManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	final String prefix = Main.getInstance().getConfig().getString("prefix").replace("&", "§");
        if (!manager.isRunning())
            sender.sendMessage(prefix + Main.getInstance().getConfigManager().getConfig("messages.yml").getString("voting.alreadyend").replace("&", "§"));
        else if (args.length == 0)
            listMaps(sender);
        else if (!manager.vote(sender, args[0])) {
            sender.sendMessage(prefix + Main.getInstance().getConfigManager().getConfig("messages.yml").getString("mapisntexist").replace("&", "§"));
        }
        return true;
    }

    private void listMaps(CommandSender sender) {
    	final String prefix = Main.getInstance().getConfig().getString("prefix").replace("&", "§");
        sender.sendMessage(prefix + " §7Maps:");
        int count = 0;
        for (String map : manager.getMaps().values()) {
            count ++;
            sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + "[" + count + "] " + ChatColor.GRAY + map);
        }
    }
}
