package cz.mot.anni.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import cz.mot.anni.Main;
import cz.mot.anni.object.Kit;
import cz.mot.anni.object.PlayerMeta;

public class ResourceListener implements Listener {
    private class Resource {
        public final Material drop;
        public final Integer xp;
        public final Integer delay;

        public Resource(Material drop, Integer xp, Integer delay) {
            this.drop = drop;
            this.xp = xp;
            this.delay = delay;
        }
    }

    private final Main plugin;
    private final HashMap<Material, Resource> resources = new HashMap<Material, Resource>();
    private final HashSet<Location> queue = new HashSet<Location>();
    private final Set<Location> diamonds = new HashSet<Location>();
    private Random rand = new Random();

    public ResourceListener(Main plugin) {
        this.plugin = plugin;
        
        addResource(Material.COAL_ORE, 8, 10);
        addResource(Material.IRON_ORE, 10, 20);
        addResource(Material.GOLD_ORE, 10, 20);
        addResource(Material.DIAMOND_ORE, 12, 30);
        addResource(Material.EMERALD_ORE, 18, 40);
        addResource(Material.REDSTONE_ORE, 10, 20);
        addResource(Material.GLOWING_REDSTONE_ORE, 10, 20);
        addResource(Material.LAPIS_ORE, 10, 20);
        addResource(Material.LOG, 2, 10);
        addResource(Material.GRAVEL, 2, 20);
        addResource(Material.MELON_BLOCK, 0, 10);
        addResource(Material.PUMPKIN, 0, 17);
        addResource(Material.HAY_BLOCK, 0, 20);
    }

    @EventHandler(ignoreCancelled = false)
    public void onResourceBreak(BlockBreakEvent e) {
       	Player player = e.getPlayer();
    	Block block = e.getBlock();
        Material type = block.getType();
        Kit kit = PlayerMeta.getMeta(player).getKit();
        int qty = getDropQuantity(type);
        Resource resource = resources.get(type);
    	
        if (resources.containsKey(e.getBlock().getType())) {
            e.setCancelled(true);
            
          if (kit == Kit.MINER) {
                qty *= rand.nextFloat() < 0.9 ? 2 : 1;
                
                if (block.getType() == Material.IRON_ORE) {
                    ItemStack iron = new ItemStack(Material.IRON_INGOT, Math.max(1, qty));
                	player.getInventory().addItem(iron);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                }
                
                if (block.getType() == Material.GOLD_ORE) {
                    ItemStack gold = new ItemStack(Material.GOLD_INGOT, Math.max(1, qty));
                	player.getInventory().addItem(gold);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.COAL_ORE) {
                    ItemStack coal = new ItemStack(Material.COAL, Math.max(1, qty));
                	player.getInventory().addItem(coal);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.DIAMOND_ORE) {
                    ItemStack dmd = new ItemStack(Material.DIAMOND, Math.max(1, qty));
                	player.getInventory().addItem(dmd);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.REDSTONE_ORE) {
                    ItemStack redstone = new ItemStack(Material.REDSTONE, 3 + rand.nextInt(3));
                	player.getInventory().addItem(redstone);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.GLOWING_REDSTONE_ORE) {
                    ItemStack redstone = new ItemStack(Material.REDSTONE, 3 + rand.nextInt(3));
                	player.getInventory().addItem(redstone);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.EMERALD_ORE) {
                    ItemStack air = new ItemStack(Material.AIR);
                	player.getInventory().addItem(air);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.PUMPKIN) {
                    ItemStack pie = new ItemStack(Material.PUMPKIN_PIE, 1 + rand.nextInt(2));
                	player.getInventory().addItem(pie);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.MELON_BLOCK) {
                    ItemStack melon = new ItemStack(Material.MELON, 3 + rand.nextInt(9));
                	player.getInventory().addItem(melon);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.HAY_BLOCK) {
                    ItemStack bread = new ItemStack(Material.BREAD, 1 + rand.nextInt(1));
                	player.getInventory().addItem(bread);
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
                
                if (block.getType() == Material.LOG) {
                    Material dropType = resource.drop;
                    player.getInventory().addItem(new ItemStack(dropType, 1));
                    player.giveExp(resource.xp);
                    queueRespawn(block);
                    }
              }
          
          if (kit != Kit.MINER) {
              breakResource(e.getPlayer(), e.getBlock());
              e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.FLAME, e.getBlock().getTypeId());
          }
    }
            
        else if (queue.contains(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false)
    public void placeResource(BlockPlaceEvent e) {
        if (resources.containsKey(e.getBlock().getType())) {
            e.setCancelled(true);
        }
    }

    public void loadDiamonds(Set<Location> diamondLocations) {
        for (Location loc : diamondLocations) {
            if (loc.getBlock().getType() == Material.DIAMOND_ORE)
                loc.getBlock().setType(Material.AIR);
            diamonds.add(loc);
        }
    }

    public void spawnDiamonds() {
        for (Location loc : diamonds)
            loc.getBlock().setType(Material.DIAMOND_ORE);
    }

    private void breakResource(Player player, Block block) {
        Material type = block.getType();
        Kit kit = PlayerMeta.getMeta(player).getKit();
        Resource resource = resources.get(type);

        if (type.equals(Material.GRAVEL)) {
            ItemStack[] drops = getGravelDrops();
            for (ItemStack stack : drops)
                if (stack.getAmount() > 1)
                    player.getInventory().addItem(stack);
        } else {
            Material dropType = resource.drop;
            int qty = getDropQuantity(type);
            if ((type.name().contains("ORE") && kit == Kit.MINER) || (type.name().contains("LOG") && kit == Kit.LUMBERJACK))
                qty *= rand.nextFloat() < 0.9 ? 2 : 1;
            player.getInventory().addItem(new ItemStack(dropType, qty));
        }

        if (resource.xp > 0) {
            player.giveExp(resource.xp);
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F,
                    (rand.nextFloat() * 0.2F) + 0.9F);
        }

        queueRespawn(block);
    }

    private void queueRespawn(final Block block) {
        final Material type = block.getType();
        block.setType(getRespawnMaterial(type));
        queue.add(block.getLocation());
        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                block.setType(type);
                queue.remove(block.getLocation());
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
            }
        }, resources.get(type).delay * 20L);
    }

    private int getDropQuantity(Material type) {
        switch (type) {
        case MELON_BLOCK:
            return 3 + rand.nextInt(5);
        case PUMPKIN:
            return 1 + rand.nextInt(3);
        case HAY_BLOCK:
            return 1 + rand.nextInt(2);
        case REDSTONE_ORE:
        case GLOWING_REDSTONE_ORE:
            return 4 + (rand.nextBoolean() ? 1 : 0);
        default:
            return 1;
        }
    }

    private Material getRespawnMaterial(Material type) {
        switch (type) {
        case LOG:
        case MELON_BLOCK:
            return Material.AIR;
        case PUMPKIN:
            return Material.AIR;
        case HAY_BLOCK:
            return Material.AIR;
        default:
            return Material.COBBLESTONE;
        }
    }

    private ItemStack[] getGravelDrops() {
        ItemStack arrows = new ItemStack(Material.ARROW, Math.max(rand.nextInt(5), 2));
        return new ItemStack[] { arrows };
    }
    
    private void addResource(Material type, int xp, int delay) {
        resources.put(type, new Resource(getDropMaterial(type), xp, delay));
    }

    private Material getDropMaterial(Material type) {
        switch (type) {
        case COAL_ORE:
            return Material.COAL;
        case IRON_ORE:
            return Material.IRON_ORE;
        case DIAMOND_ORE:
            return Material.DIAMOND;
        case EMERALD_ORE:
            return Material.AIR;
        case GLOWING_REDSTONE_ORE:
        case REDSTONE_ORE:
            return Material.REDSTONE;
        case MELON_BLOCK:
            return Material.MELON;
        case PUMPKIN:
            return Material.PUMPKIN_PIE;
        case HAY_BLOCK:
            return Material.BREAD;
        case GRAVEL:
            return null;
        default:
            return type;
        }
    }
}