package cz.mot.anni.cmds;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;
import cz.mot.anni.stats.StatType;
import cz.mot.anni.stats.StatsManager;


public class StatsCommand implements CommandExecutor {
    private StatsManager manager;
    private Main plugin;

    public StatsCommand(StatsManager manager, Main plugin) {
        this.manager = manager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                List<StatType> types = new LinkedList<StatType>(
                        Arrays.asList(StatType.values()));
                Iterator<StatType> iterator = types.iterator();
                while (iterator.hasNext()) {
                    StatType type = iterator.next();
                    boolean keep = false;
                    for (String arg : args) {
                        if (type.name().toLowerCase()
                                .contains(arg.toLowerCase()))
                            keep = true;
                    }
                    if (!keep)
                        iterator.remove();
                }
                listStats((Player) sender,
                        types.toArray(new StatType[types.size()]));
            } else {
                listStats((Player) sender);
            }
        } else {
            sender.sendMessage(plugin.getConfigManager().getConfig("messages.yml").getString("ouseplayer").replace("&", "§"));
        }

        return true;
    }

    private void listStats(Player player) {
        listStats(player, StatType.values());
    }

    private void listStats(Player player, StatType[] stats) {

        player.sendMessage("§8=========[ §eStats §8]=========");

        for (StatType stat : stats) {
            if (stat == null)
                continue;
            String name = WordUtils.capitalize(stat.name().toLowerCase().replace('_', ' '));

            player.sendMessage("§a" + name + ": §c" + manager.getStat(stat, player));
        }
        player.sendMessage("§8===========================");
    }
}
