package yashasr.minigame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Minigame extends JavaPlugin {
    private CrystalManager crystalManager;

    @Override
    public void onEnable() {
        crystalManager = new CrystalManager(this);
        CrystalClash crystalClash = new CrystalClash();
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(crystalClash, this);


        getCommand("CrystalClash").setExecutor(crystalClash);

    }
    public CrystalManager getCrystalManager(){
        return crystalManager;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
