package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.club.packet.ClubJoinPacket;
import me.emmy.clubs.invite.ClubInvite;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ClubsAcceptCommand {
    @Command(name = "accept", desc = "")
    public void execute(@Sender CommandSender sender, String name){
        if (!(sender instanceof Player)){
            sender.sendMessage(Locale.IN_GAME_COMMAND_ONLY);
            return;
        }
        Player player = (Player) sender;
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        if (profile == null){
            sender.sendMessage(Locale.PROFILE_ERROR);
            return;
        }
        if (!(profile.getClub().isEmpty())){
            sender.sendMessage(CC.translate(Locale.ALREADY_IN_A_CLUB));
            return;
        }
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(name.toLowerCase());
        if (club == null){
            sender.sendMessage(Locale.COULDNT_RESOLVE_CLUB);
            return;
        }
        if (!profile.getClub().isEmpty()){
            sender.sendMessage(CC.translate(Locale.ALREADY_IN_A_CLUB));
            return;
        }
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        if (totalMembers.size() + 1 > club.getLimit()){
            sender.sendMessage(CC.translate("&cThis club has reached the maximum number of players."));
            return;
        }
        ClubInvite lookingforInvite = null;
        for (ClubInvite invite : club.getClubInvites()){
            if (invite.isExpired()){
                club.getClubInvites().remove(invite);
                return;
            }
            if (invite.getTarget().equals(player.getUniqueId())){
                lookingforInvite = invite;
                if (invite.isExpired()){
                    club.getClubInvites().remove(invite);
                    sender.sendMessage(CC.translate("&cYou don't have a club invite from &d" + club.getName() + "&c."));
                    return;
                } else {
                    sender.sendMessage("");
                    sender.sendMessage(CC.translate("&aYou've accepted &d" + club.getName() + "&a's club invitation."));
                    sender.sendMessage("");
                    ClubJoinPacket packet = new ClubJoinPacket(player.getUniqueId(), name);
                    Clubs.getInstance().getRedisHandler().sendPacket(packet);
                }
            }
        }
        if (lookingforInvite == null){
            sender.sendMessage(CC.translate("&cYou don't have a club invite from &d" + club.getName() + "&c."));
        }
    }

}
