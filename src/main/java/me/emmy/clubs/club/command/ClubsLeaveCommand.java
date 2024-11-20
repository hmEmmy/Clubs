package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.club.packet.ClubDisbandPacket;
import me.emmy.clubs.club.packet.ClubLeavePacket;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 22/10/2023
 */

public class ClubsLeaveCommand {

    @Command(name = "leave", desc = "")
    public void execute(@Sender CommandSender sender){
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
        if (profile.getClub().isEmpty()){
            sender.sendMessage(CC.translate("&cYou're not in a club."));
            return;
        }
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(profile.getClub().toLowerCase());
        if (player.getUniqueId().equals(club.getLeader())){
            ClubDisbandPacket packet = new ClubDisbandPacket(club.getName());
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
        } else {
            ClubLeavePacket packet = new ClubLeavePacket(club.getName(), player.getUniqueId());
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
        }
    }

}
