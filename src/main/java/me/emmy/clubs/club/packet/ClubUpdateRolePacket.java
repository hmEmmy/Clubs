package me.emmy.clubs.club.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.redis.packet.Packet;
import me.emmy.clubs.role.Role;
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
public class ClubUpdateRolePacket extends Packet {
    private UUID uuid;
    private Role role;
    private String name;

    /**
     * Constructor for the ClubUpdateRolePacket.
     *
     * @param uuid The UUID of the player being updated.
     * @param role The role being set.
     * @param name The name of the club.
     */
    public ClubUpdateRolePacket(UUID uuid, Role role, String name){
        this.uuid = uuid;
        this.role = role;
        this.name = name;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        if (this.role == Role.LEADER){
            UUID leaderUUID = club.getLeader();
            club.setLeader(uuid);
            club.getMembers().add(leaderUUID);
        } else if (this.role == Role.ADMIN){
            club.getAdmins().add(this.uuid);
        } else {
            club.getMembers().add(this.uuid);
        }
        club.save();
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + "&e's role was set to &d" + this.role.getName() + "&e."));
        }
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(name.toLowerCase());
        club.getAdmins().remove(this.uuid);
        club.getMembers().remove(this.uuid);
        if (this.role == Role.LEADER){
            UUID leaderUUID = club.getLeader();
            club.setLeader(this.uuid);
            club.getMembers().add(leaderUUID);
        } else if (this.role == Role.ADMIN){
            club.getAdmins().add(this.uuid);
        } else {
            club.getMembers().add(this.uuid);
        }
        club.save();
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(this.uuid);
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + profile.getUsername() + "&e's role was set to &d" + this.role.getName().toLowerCase() + "&e."));
        }
    }
}