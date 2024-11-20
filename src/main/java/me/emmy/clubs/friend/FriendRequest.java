package me.emmy.clubs.friend;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@Getter @Setter
public class FriendRequest {
    private UUID target;
    private long expiration;

    /**
     * Constructor for the FriendRequest class
     *
     * @param target The target of the friend request
     * @param expiration The expiration time of the friend request
     */
    public FriendRequest(UUID target, long expiration){
        this.target = target;
        this.expiration = expiration;
    }

    /**
     * Check if the friend request is expired
     *
     * @return True if the friend request is expired, false otherwise
     */
    public boolean isExpired(){
        return System.currentTimeMillis() > expiration;
    }
}