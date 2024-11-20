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
public class ClubDisbandPacket extends Packet {
    private String name;

    /**
     * Constructor for the ClubDisbandPacket.
     *
     * @param name The name of the club.
     */
    public ClubDisbandPacket(String name){
        this.name = name;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if (club.getLeader() == uuid){
                player.sendMessage(CC.translate("&cYou've disbanded your club."));
            } else {
                if (player == null) return;
                player.sendMessage(CC.translate("&d&lClub &7» &d" + club.getName() + " &ehas been disbanded."));
            }
            profile.setClub("");
            profile.save();
        }
        Clubs.getInstance().getClubHandler().deleteClubByLowercaseName(club.getLowercaseName());
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if (club.getLeader() == uuid){
                player.sendMessage(CC.translate("&cYou've disbanded your club."));
            } else {
                if (player == null) return;
                player.sendMessage(CC.translate("&d&lClub &7» &d" + club.getName() + " &ehas been disbanded."));
            }
            profile.setClub("");
            profile.save();
        }
        Clubs.getInstance().getClubHandler().deleteClubByLowercaseName(club.getLowercaseName());
    }
}