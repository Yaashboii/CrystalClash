package yashasr.minigame;

import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerFighting implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof EnderCrystal){
            CrystalFight crystalFight = new CrystalFight((EnderCrystal) event.getEntity(), (Player) event.getDamager());
            crystalFight.setCrystalHits(event.getDamager().getUniqueId(), crystalFight.getcrystalHits((Player) event.getDamager()) + 1);
        }
        if(event.getEntity() instanceof Player){
            CrystalFight crystalFight = new CrystalFight(null, (Player)event.getEntity(), (Player) event.getDamager());
            crystalFight.setCrystalHits(event.getEntity().getUniqueId(), 0);
        }
    }
    public void onDeath(PlayerRespawnEvent event){
        event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        event.getPlayer().getInventory().setChestplate((new ItemStack(Material.LEATHER_CHESTPLATE)));
    }


}
