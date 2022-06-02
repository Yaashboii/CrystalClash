package yashasr.minigame;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;

import java.time.LocalDate;
import java.util.*;

public class CrystalManager {
    private Set<CrystalFight> fights = new HashSet<>();
    private Map<UUID, CrystalFight> playerFightsMap = new HashMap<>();
    private Map<EnderCrystal, CrystalFight> crystalFightMap = new HashMap<>();
    private Entity enderCrystal;

    private Minigame plugin;

    public CrystalManager(Minigame plugin) {
        this.plugin = plugin;
    }

    public boolean isInFight(Player player) {
        return playerFightsMap.get(player.getUniqueId()) != null;
    }

    public CrystalFight getFight(Player player) {
        return this.playerFightsMap.get(player.getUniqueId());
    }

    public void startFight(Player player1, Player player2) {
        if (isInFight(player1) || isInFight(player2)){
            throw new IllegalArgumentException("Failed to start fight because someone is in another fight");
        }

        Location loc = new Location(player1.getWorld(), (player1.getLocation().getX()+player2.getLocation().getX())/2,
                (player1.getLocation().getY()+player2.getLocation().getY())/2,
                (player1.getLocation().getZ()+player2.getLocation().getZ())/2);
        this.enderCrystal = player1.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);

        CrystalFight crystalFight = new CrystalFight((EnderCrystal) enderCrystal, player1, player2);

        playerFightsMap.put(player1.getUniqueId(), crystalFight);
        playerFightsMap.put(player2.getUniqueId(), crystalFight);
        fights.add(crystalFight);


        crystalFightMap.put((EnderCrystal) enderCrystal, crystalFight);
        PlayerInventory p1 = player1.getInventory();
        PlayerInventory p2 = player2.getInventory();
        p1.addItem(new ItemStack(Material.STONE_SWORD));
        p1.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        p2.addItem(new ItemStack(Material.STONE_SWORD));
        p2.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    }

    public boolean victory(Player... players){
        //CrystalFight crystalFight = new CrystalFight((EnderCrystal) enderCrystal, players);
        for(Player p : players){
            if(playerFightsMap.get(p.getUniqueId()).getcrystalHits(p) == 10){
                p.getWorld().createExplosion(p.getLocation(), 20, false, false);
                Location loc = enderCrystal.getLocation();
                Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();
                fwm.setPower(2);
                fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

                fw.setFireworkMeta(fwm);
                fw.detonate();
                p.sendTitle("Victory!", "You won!");

                return true;
            }

        }
        return false;
    }
}
