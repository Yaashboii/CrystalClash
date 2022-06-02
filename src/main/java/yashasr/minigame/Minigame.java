package yashasr.minigame;

import org.bukkit.plugin.java.JavaPlugin;

public class Minigame extends JavaPlugin {
    private CrystalManager crystalManager;

    @Override
    public void onEnable() {
        crystalManager = new CrystalManager(this);

    }
    public CrystalManager getCrystalManager(){
        return crystalManager;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
