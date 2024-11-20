package me.emmy.clubs.friend.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.friend.FriendRequest;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */

public class FriendsAcceptCommand {

    @Command(name = "accept", desc = "")
    public void execute(@Sender CommandSender sender, Player target){
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
        Profile targetProfile = Clubs.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        if (target == player){
            sender.sendMessage(CC.translate("&cYou can't accept yourself."));
            return;
        }
        if (profile.getFriends().contains(target.getUniqueId())){
            sender.sendMessage(CC.translate("&d" + target.getName() + " &eis already your friend."));
            return;
        }
        List<UUID> friends = profile.getFriends();
        if (friends.size() + 1 > 300 && !player.hasPermission("friends.limit.ace")){
            sender.sendMessage(CC.translate("&cYou've reached the maximum number of friends."));
            return;
        }
        if (friends.size() + 1 > 200 && !player.hasPermission("friends.limit.mvp")){
            sender.sendMessage(CC.translate("&cYou've reached the maximum number of friends."));
            return;
        }
        if (friends.size() + 1 > 175 && !player.hasPermission("friends.limit.pro")){
            sender.sendMessage(CC.translate("&cYou've reached the maximum number of friends."));
            return;
        }
        if (friends.size() + 1 > 150 && !player.hasPermission("friends.limit.vip")){
            sender.sendMessage(CC.translate("&cYou've reached the maximum number of friends."));
            return;
        }
        FriendRequest lookingForRequest = null;
        for (FriendRequest request : targetProfile.getFriendRequests()){
            if (request.isExpired()){
                targetProfile.getFriendRequests().remove(request);
                return;
            }
            if (request.getTarget().equals(player.getUniqueId())){
                lookingForRequest = request;
                if (request.isExpired()){
                    targetProfile.getFriendRequests().remove(request);
                    sender.sendMessage(CC.translate("&cYou don't have a friend request from &d" + target.getName() + "&c."));
                    return;
                } else {
                    profile.getFriends().add(target.getUniqueId());
                    targetProfile.getFriends().add(player.getUniqueId());
                    sender.sendMessage("");
                    sender.sendMessage(CC.translate("&aYou've accepted &d" + target.getName() + "&a's friend request."));
                    sender.sendMessage("");
                    target.sendMessage("");
                    target.sendMessage(CC.translate("&d" + sender.getName() + " &ahas accepted your friend request."));
                    target.sendMessage("");
                    profile.save();
                    targetProfile.save();
                }
            }
        }
        if (lookingForRequest == null){
            sender.sendMessage(CC.translate("&cYou don't have a friend request from &d" + target.getName() + "&c."));
        }
    }

}
