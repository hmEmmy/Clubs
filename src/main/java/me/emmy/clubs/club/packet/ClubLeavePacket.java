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
public class ClubLeavePacket extends Packet {
    private String name;
    private UUID uuid;

    /**
     * Constructor for the ClubLeavePacket.
     *
     * @param name The name of the club.
     * @param uuid The UUID of the player leaving the club.
     */
    public ClubLeavePacket(String name, UUID uuid){
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        profile.setClub("");
        profile.save();
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        club.save();
        Player player = Bukkit.getPlayer(this.uuid);
        if (player != null) player.sendMessage(CC.translate("&cYou've left the club."));
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player1 = Bukkit.getPlayer(uuid);
            if (player1 == null) return;
            player1.sendMessage(CC.translate("&d&lClub &7» &d" + player.getName() + " &eleft your club."));
        }
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        profile.setClub("");
        profile.save();
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        club.save();
        Player player = Bukkit.getPlayer(this.uuid);
        if (player != null) player.sendMessage(CC.translate("&cYou've left the club."));
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player1 = Bukkit.getPlayer(uuid);
            if (player1 == null) return;
            player1.sendMessage(CC.translate("&d&lClub &7» &d" + player.getName() + " &eleft your club."));
        }
    }
}