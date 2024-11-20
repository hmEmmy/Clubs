package me.emmy.clubs.club.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.club.Club;
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
public class ClubInvitePacket extends Packet {
    private String sender;
    private String inviting;
    private String lowercaseName;

    /**
     * Constructor for the ClubInvitePacket.
     *
     * @param sender The sender of the invite.
     * @param inviting The player being invited.
     * @param lowercaseName The lowercase name of the club.
     */
    public ClubInvitePacket(String sender, String inviting, String lowercaseName){
        this.sender = sender;
        this.inviting = inviting;
        this.lowercaseName =  lowercaseName;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.lowercaseName);
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + this.inviting + " &ewas invited by &d" + this.sender + "&e."));
        }
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.lowercaseName);
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(CC.translate("&d&lClub &7» &d" + this.inviting + " &ewas invited by &d" + this.sender + "&e."));
        }
    }
}