package me.emmy.clubs.club.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.redis.packet.Packet;
import me.emmy.clubs.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */
@Getter @Setter
public class ClubJoinPacket extends Packet {
    private UUID uuid;
    private String name;

    /**
     * Constructor for the ClubJoinPacket.
     *
     * @param uuid The UUID of the player joining the club.
     * @param name The name of the club.
     */
    public ClubJoinPacket(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        profile.setClub(this.name);
        profile.save();
        club.getMembers().add(this.uuid);
        club.save();
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + " &ejoined the club."));
        }
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        profile.setClub(this.name);
        profile.save();
        club.getMembers().add(this.uuid);
        club.save();
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + " &ejoined the club."));
        }
    }
}