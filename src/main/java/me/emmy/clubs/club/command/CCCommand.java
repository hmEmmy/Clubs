package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.annotation.Sender;
import com.jonahseguin.drink.annotation.Text;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.channel.ChatChannel;
import me.emmy.clubs.club.packet.ClubMessagePacket;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class CCCommand {
    @Command(name = "", desc = "")
    public void execute(@Sender CommandSender sender, @OptArg @Text String message){
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
            sender.sendMessage(CC.translate(Locale.NOT_IN_A_CLUB));
            return;
        }
        if (!message.isEmpty()){
            ClubMessagePacket packet = new ClubMessagePacket(player.getName(), profile.getClub(), message);
            Clubs.getInstance().getRedisHandler().sendPacket(packet);
            return;
        }
        if (profile.getChatChannel() == ChatChannel.NORMAL){
            profile.setChatChannel(ChatChannel.CLUB);
            player.sendMessage(CC.translate(Locale.ENABLED_CLUB_CHAT));
        } else {
            profile.setChatChannel(ChatChannel.NORMAL);
            player.sendMessage(CC.translate(Locale.DISABLED_CLUB_CHAT));
        }
    }
}
