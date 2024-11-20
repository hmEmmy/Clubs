package me.emmy.clubs.friend.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.friend.packet.FriendRemovePacket;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 21/10/2023
 */

public class FriendsRemoveCommand {

    @Command(name = "remove", desc = "")
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
        if (target.getUuid() == player.getUniqueId()){
            sender.sendMessage(CC.translate("&cYou can't remove yourself."));
            return;
        }
        if (!profile.getFriends().contains(target.getUuid())){
            sender.sendMessage(CC.translate("&d" + target.getUsername() + " &cis not on your friends list."));
            return;
        }
        FriendRemovePacket packet = new FriendRemovePacket(player.getUniqueId(), target.getUuid());
        Clubs.getInstance().getRedisHandler().sendPacket(packet);
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&aYou've removed &d" + target.getUsername() + " &afrom your friends list."));
        sender.sendMessage("");
    }

}
