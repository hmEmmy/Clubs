package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ClubsHelpCommand {
    @Command(name = "", aliases = "help", desc = "")
    public void execute(@Sender CommandSender sender){
        if (!(sender instanceof Player)){
            sender.sendMessage(Locale.IN_GAME_COMMAND_ONLY);
            return;
        }
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&e&l/clubs Help"));
        sender.sendMessage(CC.translate(" &e&lrole &d<player> <role> &e- &7Changes a player's role in your club."));
        sender.sendMessage(CC.translate(" &e&lshow &d<name> &e- &7View a specific club."));
        sender.sendMessage(CC.translate(" &e&linfo &d[player] &e- &7View a player's club."));
        sender.sendMessage(CC.translate(" &e&laccept &d<player> &e- &7Accept an invitation."));
        sender.sendMessage(CC.translate(" &e&lkick &d<player> &e- &7Kick a player in your club."));
        sender.sendMessage(CC.translate(" &e&lchat &d[msg] &e- &7Enable club chat."));
        sender.sendMessage(CC.translate(" &e&lleave &e- &7Leave your club."));
        sender.sendMessage(CC.translate(" &e&lcreate &d<name> &e- &7Create a club."));
        sender.sendMessage(CC.translate(" &e&linvite &d<player> &e- &7Invite to your club."));
        sender.sendMessage("");
    }

}
