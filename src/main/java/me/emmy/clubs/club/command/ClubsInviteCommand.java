package me.emmy.clubs.club.command;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Sender;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.locale.Locale;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.club.packet.ClubInvitePacket;
import me.emmy.clubs.invite.ClubInvite;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import me.emmy.clubs.util.fanciful.mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ClubsInviteCommand {
    @Command(name = "invite", desc = "")
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
        if (profile.getClub().isEmpty()){
            sender.sendMessage(CC.translate("&cYou're not in a club."));
            return;
        }
        Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(profile.getClub().toLowerCase());
        Profile targetProfile = Clubs.getInstance().getProfileHandler().getProfileByUUID(target.getUniqueId());
        if (!targetProfile.getClub().isEmpty()){
            sender.sendMessage(CC.translate("&d" + target.getName() + " &cis already in a club."));
            return;
        }
        if (club.getMembers().contains(player.getUniqueId())){
            sender.sendMessage(CC.translate("&cInviting &d" + target.getName() + " &crequires &dLeader &cor &dAdmin &crole."));
            return;
        }
        List<UUID> totalMembers = new ArrayList<>();
        totalMembers.add(club.getLeader());
        totalMembers.addAll(club.getAdmins());
        totalMembers.addAll(club.getMembers());
        if (totalMembers.size() + 1 > club.getLimit()){
            sender.sendMessage(CC.translate("&cThis club has reached the maximum number of players."));
            return;
        }
        if (!club.getClubInvites().isEmpty()){
            for (ClubInvite invite : club.getClubInvites()){
                if (invite.isExpired()){
                    club.getClubInvites().remove(invite);
                }
                if (!invite.isExpired() && invite.getTarget().equals(target.getUniqueId())){
                    sender.sendMessage(CC.translate("&cYou've already sent a club invitation to &d" + target.getName() + "&c, please wait."));
                    return;
                }
            }
        }
        club.getClubInvites().add(new ClubInvite(target.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60)));
        club.save();
        ClubInvitePacket packet = new ClubInvitePacket(player.getName(), target.getName(), club.getLowercaseName());
        Clubs.getInstance().getRedisHandler().sendPacket(packet);
        target.sendMessage("");
        new FancyMessage("Club Invitation\n")
                .color(ChatColor.YELLOW)
                .style(ChatColor.BOLD)
                .then(" • ")
                .style(ChatColor.BOLD)
                .color(ChatColor.YELLOW)
                .then("From: ")
                .color(ChatColor.YELLOW)
                .then(club.getName() + "\n")
                .color(ChatColor.LIGHT_PURPLE)
                .then(" [CLICK TO ACCEPT]")
                .color(ChatColor.GREEN)
                .style(ChatColor.BOLD)
                .tooltip(ChatColor.GREEN + "Click to accept")
                .command("/clubs accept " + club.getName()).send(target);
        target.sendMessage("");
    }

}
