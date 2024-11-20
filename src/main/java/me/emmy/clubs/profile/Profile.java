package me.emmy.clubs.profile;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.channel.ChatChannel;
import me.emmy.clubs.friend.FriendRequest;
import me.emmy.clubs.friend.FriendRequestDeserializer;
import me.emmy.clubs.friend.FriendRequestSerializer;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@Getter @Setter
public class Profile {
    private UUID uuid;
    private String username;
    private String club;
    private List<FriendRequest> friendRequests;
    private List<UUID> friends;
    private long lastSeenOn;
    private String lastSeenServer;
    private boolean online;
    private ChatChannel chatChannel;

    /**
     * Constructor for the Profile class.
     *
     * @param uuid The UUID of the player
     */
    public Profile(UUID uuid){
        this.uuid = uuid;
        this.username = Bukkit.getOfflinePlayer(uuid).getName();
        this.club = "";
        this.friendRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.lastSeenOn = System.currentTimeMillis();
        this.lastSeenServer = "!@#$%^&*()";
        this.online = false;
        this.chatChannel = ChatChannel.NORMAL;
        this.load();
    }

    /**
     * Constructor for the Profile class.
     *
     * @param username The username of the player
     */
    public Profile(String username){
        this.uuid = Bukkit.getOfflinePlayer(username).getUniqueId();
        this.username = username;
        this.club = "";
        this.friendRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.lastSeenOn = System.currentTimeMillis();
        this.lastSeenServer = "!@#$%^&*()";
        this.online = false;
        this.chatChannel = ChatChannel.NORMAL;
        this.load();
    }

    public void save(){
        Document document = new Document();
        document.append("uuid", uuid.toString());
        document.append("username", username);
        if (!club.isEmpty()) document.append("club", club);
        if (!friendRequests.isEmpty()){
            JsonArray jsonArray = new JsonArray();
            for (FriendRequest request : friendRequests){
                if (request.isExpired()) continue;;
                jsonArray.add(FriendRequestSerializer.serialize(request));
            }
            document.append("friendRequests", jsonArray.toString());
        }
        if (!friends.isEmpty()){
            document.append("friends", friends.toString());
        }
        document.append("lastSeenOn", lastSeenOn);
        document.append("lastSeenServer", lastSeenServer);
        Bson filter = Filters.eq("uuid", uuid.toString());
        Clubs.getInstance().getProfileHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    public void load(){
        Bson filter = Filters.eq("uuid", uuid.toString());
        Document document = Clubs.getInstance().getProfileHandler().getCollection().find(filter).first();
        if (document == null) return;
        uuid = UUID.fromString(document.getString("uuid"));
        username = document.getString("username");
        if (document.getString("club") != null) club = document.getString("club");
        if (document.getString("friendRequests") != null){
            for (JsonElement element : new JsonParser().parse(document.getString("friendRequests")).getAsJsonArray()){
                JsonObject object = element.getAsJsonObject();
                FriendRequest request = FriendRequestDeserializer.deserialize(object);
                if (request.isExpired()) continue;
                friendRequests.add(FriendRequestDeserializer.deserialize(object));
            }
        }
        if (document.getString("friends") != null){
            List<String> arrayList = new Gson().fromJson(document.getString("friends"), new TypeToken<List<String>>(){}.getType());
            for (String string : arrayList){
                friends.add(UUID.fromString(string));
            }
        }
        lastSeenOn = document.getLong("lastSeenOn");
        lastSeenServer = document.getString("lastSeenServer");
        if (Bukkit.getPlayer(uuid) != null) online = true;
    }
}