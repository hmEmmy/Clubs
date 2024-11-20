package me.emmy.clubs.profile;

import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.channel.ChatChannel;
import me.emmy.clubs.friend.packet.FriendAlertJoinPacket;
import me.emmy.clubs.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author hieu
 * @since 21/10/2023
 */
public class ProfileListener implements Listener {

    ProfileHandler profileHandler = Clubs.getInstance().getProfileHandler();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Profile profile = new Profile(player.getUniqueId());
        profile.setChatChannel(ChatChannel.NORMAL);
        profile.setUsername(player.getName());
        profile.setLastSeenOn(System.currentTimeMillis());
        if (profile.getLastSeenServer().equalsIgnoreCase("!@#$%^&*()")){
            if (profile.getFriends().isEmpty()) return;
            FriendAlertJoinPacket packet = new FriendAlertJoinPacket(profile, true);
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
        }
        profile.setLastSeenServer(Bukkit.getServerName());
        if (profileHandler.getProfileByUUID(player.getUniqueId()) == null){
            player.kickPlayer(Locale.PROFILE_ERROR);
            profile.save();
            return;
        }
        profile.save();
        profileHandler.getProfiles().add(profile);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());
        long lastSeenOn = System.currentTimeMillis();
        profile.setLastSeenOn(lastSeenOn);
        profile.save();
        TaskUtil.runTaskLater(() -> {
            Profile newProfile = profileHandler.getProfileByUUID(player.getUniqueId());
            if (newProfile.getLastSeenOn() != lastSeenOn) return;
            newProfile.setLastSeenServer("!@#$%^&*()");
            newProfile.save();
            if (newProfile.getFriends().isEmpty()) return;
            FriendAlertJoinPacket packet = new FriendAlertJoinPacket(profile, false);
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
        }, 3 * 20L);
        profileHandler.getProfiles().remove(profile);
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event){
        Player player = event.getPlayer();
        Profile profile = profileHandler.getProfileByUUID(player.getUniqueId());
        long lastSeenOn = System.currentTimeMillis();
        profile.setLastSeenOn(lastSeenOn);
        profile.setLastSeenServer("!@#$%^&*()");
        profile.save();
        profileHandler.getProfiles().remove(profile);
    }

}