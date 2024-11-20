package me.emmy.clubs.club;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.invite.ClubInvite;
import me.emmy.clubs.invite.ClubInviteDeserializer;
import me.emmy.clubs.invite.ClubInviteSerializer;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@Getter @Setter
public class Club {
    private String name;
    private String lowercaseName;
    private UUID leader;
    private List<UUID> admins;
    private List<UUID> members;
    private List<ClubInvite> clubInvites;
    private int limit;

    /**
     * Constructor for the Club class
     *
     * @param name The name of the club
     * @param lowercaseName The lowercase name of the club
     */
    public Club(String name, String lowercaseName){
        this.name = name;
        this.lowercaseName = lowercaseName;
        this.leader = UUID.randomUUID();
        this.admins = new ArrayList<>();
        this.members = new ArrayList<>();
        this.clubInvites = new ArrayList<>();
        this.limit = 0;
        this.load();
    }

    /**
     * Constructor for the Club class
     *
     * @param name The name of the club
     * @param lowercaseName The lowercase name of the club
     * @param leader The UUID of the leader of the club
     */
    public Club(String name, String lowercaseName, UUID leader){
        this.name = name;
        this.lowercaseName = lowercaseName;
        this.leader = leader;
        this.admins = new ArrayList<>();
        this.members = new ArrayList<>();
        this.clubInvites = new ArrayList<>();
        this.limit = 0;
        this.load();
    }

    /**
     * Constructor for the Club class
     *
     * @param name The name of the club
     * @param lowercaseName The lowercase name of the club
     * @param leader The UUID of the leader of the club
     * @param admins The list of UUIDs of the admins of the club
     * @param members The list of UUIDs of the members of the club
     * @param clubInvites The list of club invites
     * @param limit The limit of the club
     */
    public Club(String name, String lowercaseName, UUID leader, List<UUID> admins, List<UUID> members, List<ClubInvite> clubInvites, int limit){
        this.name = name;
        this.lowercaseName = lowercaseName;
        this.leader = leader;
        this.admins = admins;
        this.members = members;
        this.clubInvites = clubInvites;
        this.limit = limit;
        this.load();
    }

    public void save(){
        Document document = new Document();
        document.append("name", this.name);
        document.append("lowercaseName", this.lowercaseName);
        document.append("leader", this.leader.toString());
        if (!this.admins.isEmpty()){
            document.append("admins", this.admins.toString());
        }
        if (!this.members.isEmpty()){
            document.append("members", this.members.toString());
        }
        if (!this.clubInvites.isEmpty()){
            JsonArray jsonArray = new JsonArray();
            for (ClubInvite invite : this.clubInvites){
                if (invite.isExpired()) continue;;
                jsonArray.add(ClubInviteSerializer.serialize(invite));
            }
            document.append("clubInvites", jsonArray.toString());
        }
        document.append("limit", this.limit);
        Bson filter = Filters.eq("name", this.name);
        Clubs.getInstance().getClubHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    public void load(){
        Bson filter = Filters.eq("name", this.name);
        Document document = Clubs.getInstance().getClubHandler().getCollection().find(filter).first();
        if (document == null) return;
        this.name = document.getString("name");
        this.lowercaseName = document.getString("lowercaseName");
        this.leader = UUID.fromString(document.getString("leader"));
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> arrayList;
        if (document.getString("admins") != null){
            arrayList = gson.fromJson(document.getString("admins"), type);
            for (String string : arrayList){
                this.admins.add(UUID.fromString(string));
            }
            arrayList.clear();
        }
        if (document.getString("members") != null){
            arrayList = gson.fromJson(document.getString("members"), type);
            for (String string : arrayList){
                this.members.add(UUID.fromString(string));
            }
        }
        if (document.getString("clubInvites") != null){
            for (JsonElement element : new JsonParser().parse(document.getString("clubInvites")).getAsJsonArray()){
                JsonObject object = element.getAsJsonObject();
                ClubInvite invite = ClubInviteDeserializer.deserialize(object);
                if (invite.isExpired()) continue;
                this.clubInvites.add(ClubInviteDeserializer.deserialize(object));
            }
        }
        this.limit = document.getInteger("limit");
    }
}