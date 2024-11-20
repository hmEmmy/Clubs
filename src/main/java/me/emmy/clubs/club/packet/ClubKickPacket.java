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
public class ClubKickPacket extends Packet {
    private UUID uuid;
    private String kicker;
    private String name;

    /**
     * Constructor for the ClubKickPacket.
     *
     * @param uuid The UUID of the player being kicked.
     * @param kicker The name of the player kicking the player.
     * @param name The name of the club.
     */
    public ClubKickPacket(UUID uuid, String kicker, String name){
        this.uuid = uuid;
        this.kicker = kicker;
        this.name = name;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + " &ewas kicked by &d" + kicker + "&e."));
        }
        profile.setClub("");
        profile.save();
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        club.save();
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + " &ewas kicked by &d" + this.kicker + "&e."));
        }
        profile.setClub("");
        profile.save();
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        club.save();
    }
}