package me.emmy.clubs.invite;

import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

/**
 * @author hieu
 * @since 21/10/2023
 */
@UtilityClass
public class ClubInviteSerializer {
    /**
     * Serialize a ClubInvite object into a JsonObject
     *
     * @param clubInvite The ClubInvite object to serialize
     * @return The serialized JsonObject
     */
    public JsonObject serialize(ClubInvite clubInvite){
        JsonObject object = new JsonObject();
        object.addProperty("target", clubInvite.getTarget().toString());
        object.addProperty("expiration", clubInvite.getExpiration());
        return object;
    }
}