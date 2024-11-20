package me.emmy.clubs.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.emmy.clubs.Clubs;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@Getter
public class ProfileHandler {
    private final List<Profile> profiles;
    private final MongoCollection<Document> collection;

    public ProfileHandler(){
        profiles = new ArrayList<>();
        collection = Clubs.getInstance().getMongoHandler().getDatabase().getCollection("Profiles");
    }

    /**
     * Get a profile by its username
     *
     * @param username The username of the profile
     * @return The profile with the given username
     */
    public Profile getProfileByUsername(String username){
        Player player = Bukkit.getPlayer(username);
        if (player != null) {
            for (Profile profile : profiles){
                if (profile.getUsername().equalsIgnoreCase(username)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(username);
        return null;
    }

    /**
     * Get a profile by its UUID
     *
     * @param uuid The UUID of the profile
     * @return The profile with the given UUID
     */
    public Profile getProfileByUUID(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            for (Profile profile : profiles){
                if (profile.getUuid().equals(uuid)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(uuid);
        return null;
    }
}