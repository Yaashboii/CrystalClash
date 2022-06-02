package yashasr.minigame;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrystalFight {
    private Map<UUID, Location> spawnLocation = new HashMap<>();
    private EnderCrystal endCrystal;
    private Map<UUID, Integer>crystalHits = new HashMap<>();

    public void setSpawnLocation(Map<UUID, Location> spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void setEndCrystal(EnderCrystal endCrystal) {
        this.endCrystal = endCrystal;
    }


    public CrystalFight(EnderCrystal endCrystal, Player... players) {
        for(Player p: players) {
            spawnLocation.put(p.getUniqueId(), p.getLocation());
            crystalHits.put(p.getUniqueId(), 0);

        }
        this.endCrystal = endCrystal;

    }

    public EnderCrystal getEndCrystal(){
        return endCrystal;
    }
    public int getcrystalHits(Player player){
        return crystalHits.get(player.getUniqueId());
    }
    public Location getSpawnLocation(Player player){
        return this.spawnLocation.get(player.getUniqueId());
    }

    public void onHit(Player player){
        if(this.crystalHits.containsKey(player.getUniqueId())){
            this.crystalHits.forEach((uuid, integer) ->{
                if(uuid.equals(player.getUniqueId())){
                    crystalHits.put(uuid, integer+1);
                }else{
                    crystalHits.put(uuid, 0);
                }
            } );
            return;
        }
    }

}
