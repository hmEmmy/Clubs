package me.emmy.clubs.friend;

import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@UtilityClass
public class FriendRequestDeserializer {
    /**
     * Deserialize a JsonObject into a FriendRequest object
     *
     * @param object The JsonObject to deserialize
     * @return The deserialized FriendRequest object
     */
    public FriendRequest deserialize(JsonObject object){
        UUID target = UUID.fromString(object.get("target").getAsString());
        long expiration = object.get("expiration").getAsLong();
        return new FriendRequest(target, expiration);
    }
}