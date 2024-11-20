package me.emmy.clubs.friend;

import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

/**
 * @author hieu
 * @since 21/10/2023
 */
@UtilityClass
public class FriendRequestSerializer {
    /**
     * Serialize a FriendRequest object into a JsonObject
     *
     * @param friendRequest The FriendRequest object to serialize
     * @return The serialized JsonObject
     */
    public JsonObject serialize(FriendRequest friendRequest){
        JsonObject object = new JsonObject();
        object.addProperty("target", friendRequest.getTarget().toString());
        object.addProperty("expiration", friendRequest.getExpiration());
        return object;
    }
}