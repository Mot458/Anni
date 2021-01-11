package cz.mot.anni.object;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import cz.mot.anni.Main;
import cz.mot.anni.listeners.SoulboundListener;

import static org.bukkit.potion.PotionType.INVISIBILITY;

@SuppressWarnings("unused")
public enum Kit {
    CIVILIAN(Material.WORKBENCH) {
        {
            spawnItems.add(new ItemStack(Material.WOOD_SWORD));
            spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            spawnItems.add(new ItemStack(Material.WORKBENCH));
            lore.add("You are the Civilian.");
            lore.add("");
            lore.add("You are nothing special");
        }
    },
    WARRIOR(Material.STONE_SWORD) {
        {
            spawnItems.add(new ItemStack(Material.STONE_SWORD));
            spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            spawnItems.add(new Potion(PotionType.INSTANT_HEAL, 1).toItemStack(1));
            spawnItems.get(0).addEnchantment(Enchantment.KNOCKBACK, 1);
            lore.add("You are the Warrior.");
            lore.add("");
            lore.add("You deal +1 damage with");
            lore.add("any melee weapon.");
        }
    },
    ARCHER(Material.BOW) {
        {
            spawnItems.add(new ItemStack(Material.WOOD_SWORD));
            spawnItems.add(new ItemStack(Material.BOW));
            spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            spawnItems.add(new ItemStack(Material.WOOD_SPADE));
            spawnItems.add(new Potion(PotionType.INSTANT_HEAL, 1).toItemStack(1));
            spawnItems.add(new ItemStack(Material.ARROW, 1));
            spawnItems.get(1).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
            spawnItems.get(1).addEnchantment(Enchantment.ARROW_DAMAGE, 1);
            lore.add("You are the Archer.");
            lore.add("");
            lore.add("You deal +1 damage with");
            lore.add("a bow and can craft arrows");
            lore.add("without feathers.");
            lore.add("");
            lore.add("Place flint on top of sticks");
            lore.add("in your crafting interface");
            lore.add("to create 3 arrows!");
        }
    },

    MINER(Material.IRON_PICKAXE) {
        {
            spawnItems.add(new ItemStack(Material.WOOD_SWORD));
            spawnItems.add(new ItemStack(Material.IRON_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            spawnItems.get(1).addEnchantment(Enchantment.DIG_SPEED, 1);
            lore.add("You are the Miner.");
            lore.add("");
            lore.add("Spawn with an efficiency");
            lore.add("iron pickaxe and mine");
            lore.add("precious resources for");
            lore.add("your team more quickly with");
            lore.add("your doubled ingot drops!");
        }
    },
    LUMBERJACK(Material.STONE_AXE) {
        {
            spawnItems.add(new ItemStack(Material.WOOD_SWORD));
            spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
            spawnItems.add(new ItemStack(Material.STONE_AXE));
            spawnItems.get(2).addEnchantment(Enchantment.DIG_SPEED, 1);
            lore.add("You are the Lumberjack.");
            lore.add("");
            lore.add("Spawn with an efficiency");
            lore.add("stone axe and use your");
            lore.add("doubled log drops to obtain");
            lore.add("large amounts of wood for");
            lore.add("your team's builders!");
        }
    },
    SCOUT(Material.FISHING_ROD) {
        {
            spawnItems.add(new ItemStack(Material.GOLD_SWORD));
            spawnItems.add(new ItemStack(Material.FISHING_ROD));
            spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            ItemMeta meta = spawnItems.get(1).getItemMeta();
            meta.setDisplayName("Grapple");
            spawnItems.get(1).setItemMeta(meta);
            lore.add("You are the Scout.");
            lore.add("");
            lore.add("Use your permanent speed");
            lore.add("boost to maneuver around");
            lore.add("the battlefield and your");
            lore.add("grapple to climb obstacles");
            lore.add("and enemy defenses!");
            lore.add("");
            lore.add("You can only wear light");
            lore.add("armor; anything heavier");
            lore.add("than chainmail will slow");
            lore.add("you down.");
        }
    },
    DESTROYER(Material.COAL_BLOCK) {
        {
            spawnItems.add(new ItemStack(Material.WOOD_SWORD));
            spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
            spawnItems.add(new ItemStack(Material.WOOD_AXE));
            spawnItems.add(new ItemStack(Material.WOOD_SPADE));
            lore.add("You are the destroyer.");
            lore.add("");
            lore.add("When you hit some nexus,");            
            lore.add("nexus will get -2hp.");
            }
    },
    DEFENDER(Material.IRON_CHESTPLATE) {
        {
            spawnItems.add(new ItemStack(Material.STONE_SWORD));
            spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
            spawnItems.add(new ItemStack(Material.STONE_AXE));
            lore.add("You are the Defender");
            lore.add("");
            lore.add("Killer is low Damage");
            lore.add("You KILLER!");
        }
    };

    static {
        for (Kit kit : values())
            kit.init();
    }

    private ItemStack icon;
    List<String> lore = new ArrayList<String>();
    List<ItemStack> spawnItems = new ArrayList<ItemStack>();
    ItemStack[] spawnArmor = new ItemStack[] {
            new ItemStack(Material.LEATHER_BOOTS),
            new ItemStack(Material.LEATHER_LEGGINGS),
            new ItemStack(Material.LEATHER_CHESTPLATE),
            new ItemStack(Material.LEATHER_HELMET) };

    Kit(Material m) {
        icon = new ItemStack(m);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(getName());
        icon.setItemMeta(meta);
    }

    private void init() {
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            s = ChatColor.AQUA + s;
            lore.set(i, s);
        }
        ItemMeta meta = icon.getItemMeta();
        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    public static Kit getKit(String name) {
        for (Kit type : values()) {
            if (type.name().equalsIgnoreCase(name))
                return type;
        }
        return null;
    }

    public void give(Player recipient, GameTeam team) {
        PlayerInventory inv = recipient.getInventory();
        inv.clear();

        for (ItemStack item : spawnItems) {
            ItemStack toGive = item.clone();
            SoulboundListener.soulbind(toGive);
            inv.addItem(toGive);
        }

        recipient.removePotionEffect(PotionEffectType.SPEED);

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        Main plugin = Main.getInstance();
        String kompas = plugin.getConfig().getString("CompassItem").replace("&", "§").replace("%TEAM%", String.valueOf(team.toString())).replaceAll("%COLOR%", String.valueOf(team.color()));
        compassMeta.setDisplayName(kompas);
        compass.setItemMeta(compassMeta);
        SoulboundListener.soulbind(compass);

        inv.addItem(compass);
        recipient.setCompassTarget(team.getNexus().getLocation());

        inv.setArmorContents(spawnArmor);
        colorizeArmor(inv, getTeamColor(team));

        for (ItemStack armor : inv.getArmorContents())
            SoulboundListener.soulbind(armor);

        if (this == SCOUT)
            addScoutParticles(recipient);
    }

    private Color getTeamColor(GameTeam team) {
        switch (team) {
        case RED:
            return Color.RED;
        case YELLOW:
            return Color.YELLOW;
        case GREEN:
            return Color.GREEN;
        case BLUE:
            return Color.BLUE;
        default:
            return Color.WHITE;
        }
    }

    private void colorizeArmor(PlayerInventory inv, Color color) {
        for (ItemStack item : inv.getArmorContents()) {
            if (item.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
            }
        }
    }

    public String getName() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }

    public boolean isOwnedBy(Player p) {
        return p.isOp()
                || this == CIVILIAN
                || p.hasPermission("anni.class."
                        + getName().toLowerCase());
    }

    public void addScoutParticles(Player p) {
        if (this != SCOUT)
            return;
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                Integer.MAX_VALUE, 0, true), true);
    }

    public ItemStack getIcon() {
        return icon;
    }
}
