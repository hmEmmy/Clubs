package me.emmy.clubs.invite;

import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@UtilityClass
public class ClubInviteDeserializer {
    /**
     * Deserialize a JsonObject into a ClubInvite object
     *
     * @param object The JsonObject to deserialize
     * @return The deserialized ClubInvite object
     */
    public ClubInvite deserialize(JsonObject object){
        UUID target = UUID.fromString(object.get("target").getAsString());
        long expiration = object.get("expiration").getAsLong();
        return new ClubInvite(target, expiration);
    }
}