package me.emmy.clubs.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author hieu
 * @since 21/10/2023
 */
public class GeneralListener implements Listener {

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event){
        event.setJoinMessage(null);
    }

    @EventHandler
    private void onPlayerQuitEvent(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }
}