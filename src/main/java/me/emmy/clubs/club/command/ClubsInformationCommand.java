package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */

public class ClubsInformationCommand {
    @Command(name = "information", aliases = {"info", "i"}, desc = "")
    public void execute(@Sender CommandSender sender, @OptArg Profile target){
        if (!(sender instanceof Player)){
            sender.sendMessage(Locale.IN_GAME_COMMAND_ONLY);
            return;
        }
        Player player = (Player) sender;
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        if (target == null){
            target = profile;
        }
        if (target.getClub().isEmpty()){
            sender.sendMessage(CC.translate("&d" + target.getUsername() + " &cisn't in a club."));
            return;
        }
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(target.getClub().toLowerCase());
        if (club == null){
            sender.sendMessage(CC.translate("&d" + target.getUsername() + " &cisn't in a club."));
            return;
        }
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&e&l" + club.getName()));
        Profile leaderProfile = Clubs.getInstance().getProfileHandler().getProfileByUUID(club.getLeader());
        sender.sendMessage(CC.translate("&e&l• &eLeader: &d" + leaderProfile.getUsername() + (leaderProfile.isOnline() ? " &a&l•" : " &c&l•")));
        if (!club.getAdmins().isEmpty()){
            List<Profile> adminProfiles = new ArrayList<>();
            for (UUID uuid : club.getAdmins()){
                adminProfiles.add(Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid));
            }
            List<String> adminStrings = new ArrayList<>();
            for (Profile profile1 : adminProfiles){
                adminStrings.add("&d" + profile1.getUsername() + (profile1.isOnline() ? " &a&l•" : " &c&l•"));
            }
            sender.sendMessage(CC.translate("&e&l• &eAdmins (&d" + club.getAdmins().size() + "&e): &r" + StringUtils.join(adminStrings, "&e,")));
        }
        if (!club.getMembers().isEmpty()){
            List<Profile> memberProfiles = new ArrayList<>();
            for (UUID uuid : club.getMembers()){
                memberProfiles.add(Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid));
            }
            List<String> memberStrings = new ArrayList<>();
            for (Profile profile1 : memberProfiles){
                memberStrings.add("&d" + profile1.getUsername() + (profile1.isOnline() ? " &a&l•" : " &c&l•"));
            }
            sender.sendMessage(CC.translate("&e&l• &eMembers (&d" + club.getMembers().size() + "&e): &r" + StringUtils.join(memberStrings, "&e,")));
        }
        sender.sendMessage("");
    }

}
