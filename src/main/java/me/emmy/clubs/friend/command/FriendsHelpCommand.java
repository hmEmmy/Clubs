package me.emmy.clubs.friend.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hieu
 * @since 21/10/2023
 */

public class FriendsHelpCommand {

    @Command(name = "", aliases = "help", desc = "")
    public void execute(@Sender CommandSender sender){
        if (!(sender instanceof Player)){
            sender.sendMessage(Locale.IN_GAME_COMMAND_ONLY);
            return;
        }
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&e&l/friends Help"));
        sender.sendMessage(CC.translate(" &e&ladd &d<player> &e- &7Send a friend request."));
        sender.sendMessage(CC.translate(" &e&llist &d[page] &e- &7View your current friends."));
        sender.sendMessage(CC.translate(" &e&lremove &d<player> &e- &7Remove a friend."));
        sender.sendMessage(CC.translate(" &e&laccept &d<player> &e- &7Accept a friend request."));
        sender.sendMessage("");
    }

}
