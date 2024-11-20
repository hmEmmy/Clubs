package me.emmy.clubs.friend.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.redis.packet.Packet;

import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */

@Getter @Setter
public class FriendRemovePacket extends Packet {
    private UUID uuid;
    private UUID uuid2;

    /**
     * Constructor for the FriendRemovePacket class
     *
     * @param uuid The UUID of the player
     * @param uuid2 The UUID of the player to remove
     */
    public FriendRemovePacket(UUID uuid, UUID uuid2){
        this.uuid = uuid;
        this.uuid2 = uuid2;
    }

    @Override
    public void onReceive() {
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
        Profile target = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid2);
        profile.getFriends().remove(target.getUuid());
        target.getFriends().remove(profile.getUuid());
        profile.save();
        target.save();
    }

    @Override
    public void onSend() {
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
        Profile target = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid2);
        profile.getFriends().remove(target.getUuid());
        target.getFriends().remove(profile.getUuid());
        profile.save();
        target.save();
    }
}