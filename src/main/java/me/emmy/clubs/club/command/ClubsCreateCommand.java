package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import com.jonahseguin.drink.annotation.Text;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ClubsCreateCommand {
    @Command(name = "create", desc = "")
    @Require("clubs.create")
    public void execute(@Sender CommandSender sender, @Text String name){
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
        if (club != null){
            sender.sendMessage(CC.translate("&cThe name &d" + name + " &chas been taken."));
            return;
        }
        Club newClub = new Club(name, name.toLowerCase(), player.getUniqueId());
        if (player.hasPermission("friends.limit.pro")){
            newClub.setLimit(25);
        }
        if (player.hasPermission("friends.limit.mvp")){
            newClub.setLimit(35);
        }
        if (player.hasPermission("friends.limit.ace")){
            newClub.setLimit(50);
        }
        newClub.save();
        profile.setClub(name);
        profile.save();
        sender.sendMessage(CC.translate("&aYou've created a club with the name &2" + name + "&a."));
    }

}
