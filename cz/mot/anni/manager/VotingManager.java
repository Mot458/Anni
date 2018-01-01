package cz.mot.anni.manager;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import cz.mot.anni.Main;

public class VotingManager {
    private final Main plugin;
    private final HashMap<Integer, String> maps = new HashMap<Integer, String>();
    private final HashMap<String, String> votes = new HashMap<String, String>();
    private boolean running = false;

    public VotingManager(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
	public void start() {
        maps.clear();
        votes.clear();
        int count = 0;
        int size = plugin.getMapManager().getRandomMaps().size();
        for (String map : plugin.getMapManager().getRandomMaps()) {
            count++;
            size--;
            maps.put(count, map);
            plugin.getScoreboardHandler().scores.put(map, plugin.getScoreboardHandler().obj.getScore(Bukkit.getOfflinePlayer(map)));
            plugin.getScoreboardHandler().scores.get(map).setScore(size);
            plugin.getScoreboardHandler().teams.put(map, plugin.getScoreboardHandler().sb.registerNewTeam(map));
            plugin.getScoreboardHandler().teams.get(map).addPlayer(Bukkit.getOfflinePlayer(map));
            plugin.getScoreboardHandler().teams.get(map).setPrefix(ChatColor.AQUA + "[" + count + "] " + ChatColor.GRAY);
            plugin.getScoreboardHandler().teams.get(map).setSuffix(ChatColor.RED + " � " + ChatColor.GREEN + "0 Votes");
        }

        running = true;

        plugin.getScoreboardHandler().update();
    }

    public boolean vote(CommandSender voter, String vote) {
    	String prefix = plugin.getConfig().getString("prefix").replace("&", "�");
        try {
            int val = Integer.parseInt(vote);

            if (maps.containsKey(val)) {
                vote = maps.get(val);
                for (String map : maps.values()) {
                    if (vote.equalsIgnoreCase(map)) {
                        votes.put(voter.getName(), map);
                        voter.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("votedfor").replace("&", "�") + map);
                        updateScoreboard();
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            for (String map : maps.values()) {
                if (vote.equalsIgnoreCase(map)) {
                    votes.put(voter.getName(), map);
                    voter.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("votedfor").replace("&", "�") + map);
                    updateScoreboard();
                    return true;
                }
            }
        }

        voter.sendMessage(prefix + plugin.getConfigManager().getConfig("messages.yml").getString("mapisntexist").replace("&", "�"));
        return false;
    }

    public String getWinner() {
        String winner = null;
        Integer highest = -1;
        for (String map : maps.values()) {
            int totalVotes = countVotes(map);
            if (totalVotes > highest) {
                winner = map;
                highest = totalVotes;
            }
        }
        return winner;
    }

    public void end() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public HashMap<Integer, String> getMaps() {
        return maps;
    }

    private int countVotes(String map) {
        int total = 0;
        for (String vote : votes.values())
            if (vote.equals(map))
                total++;
        return total;
    }

    private void updateScoreboard() {
        for (String map : maps.values())
            plugin.getScoreboardHandler().teams.get(map).setSuffix(
                    ChatColor.RED + " � " + ChatColor.GREEN + countVotes(map) + " vote" + (countVotes(map) == 1 ? "" : "s"));
    }
}
