package me.emmy.clubs.club.packet;

import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.redis.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */
@Getter @Setter
public class ClubMessagePacket extends Packet {
    private String sender;
    private String name;
    private String message;

    /**
     * Constructor for the ClubMessagePacket.
     *
     * @param sender The sender of the message.
     * @param name The name of the club.
     * @param message The message being sent.
     */
    public ClubMessagePacket(String sender, String name, String message){
        this.sender = sender;
        this.name = name;
        this.message = message;
    }

    @Override
    public void onReceive() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Club " + ChatColor.GRAY + "» " + ChatColor.LIGHT_PURPLE + this.sender + ChatColor.GRAY + ": " + ChatColor.YELLOW + this.message);
        }
    }

    @Override
    public void onSend() {
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(this.name.toLowerCase());
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        for (UUID uuid : totalMembers){
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;
            player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Club " + ChatColor.GRAY + "» " + ChatColor.LIGHT_PURPLE + this.sender + ChatColor.GRAY + ": " + ChatColor.YELLOW + this.message);
        }
    }

}
