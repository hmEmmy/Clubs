package me.emmy.clubs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.club.Club;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.util.CC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class ProfilePlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "clubs";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Emmy";
    }

    @Override
    public @NotNull String getVersion() {
        return Clubs.getInstance().getDescription().getVersion();
    }

    /**
     * This method is called when a placeholder with our identifier is found and needs a value.
     *
     * @param player The player who is requesting the placeholder
     * @param params String after the identifier
     * @return String value of the placeholder
     */
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        Profile profile = Clubs.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
        if (params.equalsIgnoreCase("friends")) {
            if (profile.getFriends().isEmpty()){
                return CC.translate("&cNone");
            }
            int count = 0;
            for (UUID uuid : profile.getFriends()){
                Profile profile1 = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
                if (profile1.isOnline()) count++;
            }
            return String.valueOf(count);
        } else if (params.equalsIgnoreCase("club")){
            if (profile.getClub().isEmpty()){
                return CC.translate("&cNone");
            } else {
                Club club = Clubs.getInstance().getClubHandler().getClubByLowercaseName(profile.getClub().toLowerCase());
                List<UUID> totalUUIDs = new ArrayList<>();
                totalUUIDs.add(club.getLeader());
                totalUUIDs.addAll(club.getAdmins());
                totalUUIDs.addAll(club.getMembers());
                totalUUIDs.remove(profile.getUuid());
                int count = 0;
                for (UUID uuid : totalUUIDs){
                    Profile profile1 = Clubs.getInstance().getProfileHandler().getProfileByUUID(uuid);
                    if (profile1.isOnline()) count++;
                }
                return String.valueOf(count);
            }
        }
        return null;
    }
}