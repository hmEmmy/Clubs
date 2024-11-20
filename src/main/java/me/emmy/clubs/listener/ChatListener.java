package me.emmy.clubs.listener;

import me.emmy.clubs.Clubs;
import me.emmy.clubs.channel.ChatChannel;
import me.emmy.clubs.club.packet.ClubMessagePacket;
import me.emmy.clubs.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        if (profile.getChatChannel() == ChatChannel.CLUB){
            event.setCancelled(true);
            ClubMessagePacket packet = new ClubMessagePacket(player.getName(), profile.getClub(), event.getMessage());
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
        }
    }
}