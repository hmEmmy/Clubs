package me.emmy.clubs.friend.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.friend.FriendRequest;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import me.emmy.clubs.util.fanciful.mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hieu
 * @since 21/10/2023
 */

public class FriendsAddCommand {

    @Command(name = "add", desc = "")
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
        if (target == player){
            sender.sendMessage(CC.translate("&cYou can't add yourself."));
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
        if (!profile.getFriendRequests().isEmpty()){
            for (FriendRequest request : profile.getFriendRequests()){
                if (request.isExpired()){
                    profile.getFriendRequests().remove(request);
                }
                if (!request.isExpired() && request.getTarget().equals(target.getUniqueId())){
                    sender.sendMessage(CC.translate("&cYou've already sent a friend request to &d" + target.getName() + "&c, please wait."));
                    return;
                }
            }
        }
        profile.getFriendRequests().add(new FriendRequest(target.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60)));
        profile.save();
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&eYou've sent a friend request to &d" + target.getName() + "&e."));
        sender.sendMessage("");
        target.sendMessage("");
        new FancyMessage("Friend Request\n")
                .color(ChatColor.YELLOW)
                .style(ChatColor.BOLD)
                .then(" • ")
                .style(ChatColor.BOLD)
                .color(ChatColor.YELLOW)
                .then("From: ")
                .color(ChatColor.YELLOW)
                .then(player.getName() + "\n")
                .color(ChatColor.LIGHT_PURPLE)
                .then(" [CLICK TO ACCEPT]")
                .color(ChatColor.GREEN)
                .style(ChatColor.BOLD)
                .tooltip(ChatColor.GREEN + "Click to accept")
                .command("/friends accept " + sender.getName()).send(target);
        target.sendMessage("");
    }

}
