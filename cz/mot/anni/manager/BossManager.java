package cz.mot.anni.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

import cz.mot.anni.Main;
import cz.mot.anni.Util;
import cz.mot.anni.object.Boss;

public class BossManager {
    public HashMap<String, Boss> bosses = new HashMap<String, Boss>();
    public HashMap<String, Boss> bossNames = new HashMap<String, Boss>();

    private Main plugin;

    public BossManager(Main instance) {
        this.plugin = instance;
    }

    public void loadBosses(HashMap<String, Boss> b) {
        bosses = b;
    }

    public void spawnBosses() {
        for (Boss b : bosses.values())
            spawn(b);
    }

    @SuppressWarnings("deprecation")
    public void spawn(Boss b) {
        Location spawn = b.getSpawn();

        if (spawn != null && spawn.getWorld() != null) {
            Bukkit.getWorld(spawn.getWorld().getName()).loadChunk(spawn.getChunk());
            IronGolem boss = (IronGolem) spawn.getWorld().spawnCreature(spawn,
                    EntityType.IRON_GOLEM);
            boss.setMaxHealth(b.getHealth());
            boss.setHealth(b.getHealth());
            boss.setCanPickupItems(false);
            boss.setPlayerCreated(false);
            boss.setRemoveWhenFarAway(false);
            boss.setCustomNameVisible(true);
            boss.setCustomName(ChatColor.translateAlternateColorCodes('&',
                    b.getBossName() + " &8» &a" + (int) b.getHealth() + " HP"));
            bossNames.put(boss.getCustomName(), b);
            Util.spawnFirework(b.getSpawn());
            Util.spawnFirework(b.getSpawn());
            Util.spawnFirework(b.getSpawn());
        }
    }

    public void update(Boss boss, IronGolem g) {
        boss.setHealth((int) g.getHealth());
        g.setCustomName(ChatColor.translateAlternateColorCodes('&',
                boss.getBossName() + " &8» &a" + (int) boss.getHealth() + " HP"));
        bossNames.put(g.getCustomName(), boss);
        bosses.put(boss.getConfigName(), boss);
    }

    public Boss newBoss(Boss b) {
        String boss = b.getConfigName();
        bosses.remove(boss);
        bossNames.remove(boss);

        FileConfiguration config = plugin.getConfigManager().getConfig(
                "maps.yml");
        ConfigurationSection section = config.getConfigurationSection(plugin
                .getMapManager().getCurrentMap().getName());
        ConfigurationSection sec = section.getConfigurationSection("bosses");

        Boss bb = new Boss(boss, sec.getInt(boss + ".hearts") * 2,
                sec.getString(boss + ".name"), Util.parseLocation(plugin
                        .getMapManager().getCurrentMap().getWorld(),
                        sec.getString(boss + ".spawn")), Util.parseLocation(
                        plugin.getMapManager().getCurrentMap().getWorld(),
                        sec.getString(boss + ".chest")));
        bosses.put(boss, bb);

        return bb;
    }
}
