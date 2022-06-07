package com.yashasr.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;


public class CrystalClash implements CommandExecutor, Listener {
    private MiniGame plugin = MiniGame.instance;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Msg.send(sender, "&cOnly players can use this command");
            return true;
        }
        if(!plugin.getCrystalManager().isInFight(Bukkit.getPlayer(args[0])) && !plugin.getCrystalManager().isInFight(Bukkit.getPlayer(args[1]))){
                plugin.getCrystalManager().startFight(Bukkit.getPlayer(args[0]), Bukkit.getPlayer(args[1]));
            }


        return true;
    }
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof EnderCrystal && event.getDamager() instanceof Player){
            if(plugin.getCrystalManager().isInFight((Player) event.getDamager())){
                plugin.getCrystalManager().getFight((Player) event.getDamager()).onHit((Player) event.getDamager());
                event.setCancelled(true);
            }
        }

        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            if(plugin.getCrystalManager().getFight((Player) event.getEntity()) == null){
                return;
            }
            plugin.getCrystalManager().getFight((Player) event.getEntity()).setZero((Player) event.getEntity());

        }


    }
    @EventHandler
    public void onDeath(PlayerRespawnEvent event){
        event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        event.getPlayer().getInventory().setChestplate((new ItemStack(Material.LEATHER_CHESTPLATE)));
    }

}
