package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.club.packet.ClubKickPacket;
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

public class ClubsKickCommand {

    @Command(name = "kick", desc = "")
    public void execute(@Sender CommandSender sender, Profile target){
        if (!(sender instanceof Player)){
            sender.sendMessage(Locale.IN_GAME_COMMAND_ONLY);
            return;
        }
        if (target == null){
            sender.sendMessage(Locale.COULDNT_RESOLVE_PROFILE);
            return;
        }
        Player player = (Player) sender;
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        if (profile == null){
            sender.sendMessage(Locale.PROFILE_ERROR);
            return;
        }
        if (profile.getClub().isEmpty()){
            sender.sendMessage(CC.translate("&cYou're not in a club."));
            return;
        }
        if (player.getUniqueId().equals(target.getUuid())){
            sender.sendMessage(CC.translate("&cYou kick yourself from your club."));
            return;
        }
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(profile.getClub().toLowerCase());
        if (target.getUuid().equals(club.getLeader())){
            sender.sendMessage(CC.translate("&cYou can't kick the leader &d" + target.getUsername() + "&c."));
            return;
        }
        if (club.getMembers().contains(player.getUniqueId()) || club.getAdmins().contains(player.getUniqueId())){
            sender.sendMessage(CC.translate("&cKicking &d" + target.getUsername() + " &crequires &dLeader &crole."));
            return;
        }
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        if (!totalMembers.contains(target.getUuid())){
            sender.sendMessage(CC.translate("&d" + target.getUsername() + " &cisn't in your club."));
            return;
        }
        ClubKickPacket packet = new ClubKickPacket(target.getUuid(), player.getName(), club.getName());
        Clubs.getInstance().getRedisHandler().sendPacket(packet);
    }

}
