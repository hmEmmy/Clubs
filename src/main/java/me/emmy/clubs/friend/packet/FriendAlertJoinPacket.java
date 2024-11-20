package me.emmy.clubs.friend.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.redis.packet.Packet;
import me.emmy.clubs.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */
@Getter @Setter
public class FriendAlertJoinPacket extends Packet {
    private Profile profile;
    private boolean join;

    /**
     * Constructor for the FriendAlertJoinPacket class
     *
     * @param profile The profile of the player
     * @param join Whether the player joined or left
     */
    public FriendAlertJoinPacket(Profile profile, boolean join){
        this.profile = profile;
        this.join = join;
    }

    @Override
    public void onReceive() {
        List<UUID> friends = profile.getFriends();
        for (UUID uuid : friends){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            if (join){
                player.sendMessage(CC.translate("&d&lFriend &7&l» &d" + profile.getUsername() + " &ejoined."));
            } else {
                player.sendMessage(CC.translate("&d&lFriend &7&l» &d" + profile.getUsername() + " &eleft."));
            }
        }
    }

    @Override
    public void onSend() {
        List<UUID> friends = profile.getFriends();
        for (UUID uuid : friends){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            if (join){
                player.sendMessage(CC.translate("&d&lFriend &7&l» &d" + profile.getUsername() + " &ejoined."));
            } else {
                player.sendMessage(CC.translate("&d&lFriend &7&l» &d" + profile.getUsername() + " &eleft."));
            }
        }
    }
}