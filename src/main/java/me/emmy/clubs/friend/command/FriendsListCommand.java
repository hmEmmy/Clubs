package me.emmy.clubs.friend.command;

import com.google.common.collect.Lists;
import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import me.emmy.clubs.util.TimeUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */

public class FriendsListCommand {

    @Command(name = "list", desc = "")
    public void execute(@Sender CommandSender sender, @OptArg int page){
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
        if (profile.getFriends().isEmpty()){
            sender.sendMessage(CC.translate("&cThis friends list is empty."));
            return;
        }
        if (page == 0){
            page = 1;
        }
        List<List<UUID>> pages = Lists.partition(profile.getFriends(), 10);
        if (page > pages.size()){
            sender.sendMessage(CC.translate("&cThis friends list is empty."));
            return;
        }
        List<UUID> selectedPage = pages.get(page-1);
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&e&lFriends List (&d&l" + profile.getFriends().size() + "&e&l)"));
        sender.sendMessage("");
        String message;
        for (UUID uuid : selectedPage){
            Profile profile1 = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
            message = profile1.isOnline() ? " &a&l• &d{player} &eis on {server}" : " &c&l• &d{player} &ewas on {ago} ago";
            sender.sendMessage(CC.translate(message
                    .replace("{player}", profile1.getUsername())
                    .replace("{server}", profile1.getLastSeenServer())
                    .replace("{ago}", TimeUtil.convertLongToString(System.currentTimeMillis() - profile1.getLastSeenOn()))));
        }
        sender.sendMessage("");
        message = " &ePage &d{page} &eof &d{pages}";
        sender.sendMessage(CC.translate(message
                .replace("{page}", String.valueOf(page))
                .replace("{pages}", String.valueOf(pages.size()))));
        sender.sendMessage("");
    }

}
