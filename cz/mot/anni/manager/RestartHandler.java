package cz.mot.anni.manager;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import cz.mot.anni.Main;
import cz.mot.anni.Util;
import cz.mot.anni.api.ActionAPI;
import cz.mot.anni.object.GameTeam;

public class RestartHandler {
    private final Main plugin;
    private long time;
    private long delay;
    private int taskID;
    private int fwID;

    public RestartHandler(Main plugin, final long delay) {
        this.plugin = plugin;
        this.delay = delay;
    }

    public void start(final long gameTime, final Color c) {
        for (Iterator<Entity> iterator = plugin.getMapManager().getCurrentMap().getWorld().getEntities().iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if (entity.getType() == EntityType.IRON_GOLEM) {
                entity.remove();
            }
        }

        time = delay;
        final String totalTime = PhaseManager.timeString(gameTime);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                new Runnable() {
                    @SuppressWarnings("unused")
					@Override
                    public void run() {
                        if (time <= 0) {
                            stop();
                            return;
                        }
                        String msg = plugin.getConfig().getString("ActionRestart").replace("&", "§").replace("%TOTAL%", totalTime).replace("%RESTART%", String.valueOf(time));
                        float percent = (float) time / (float) delay;
                        for (Player p : Bukkit.getOnlinePlayers())
                            ActionAPI.sendPlayerAnnouncement(p, msg);
                        time--;
                    }
                }, 0L, 20L);

        fwID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                    for (GameTeam gt : GameTeam.values()) {
                        if (gt != GameTeam.NONE) {
                            for (Location l : gt.getSpawns()) {
                                Util.spawnFirework(l, c, c);
                            }
                        }
                    }
                }
            }, 0L, 40L);
    }

    private void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(fwID);

        if (plugin.runCommand) {
            for (String c : plugin.commands)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        } else {
            plugin.reset();
        }
    }
}
