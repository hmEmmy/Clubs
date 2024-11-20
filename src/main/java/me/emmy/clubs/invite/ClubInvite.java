package me.emmy.clubs.invite;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author hieu
 * @since 21/10/2023
 */
@Getter @Setter
public class ClubInvite {
    private UUID target;
    private long expiration;

    /**
     * Constructor for the ClubInvite class.
     *
     * @param target The target of the club invite
     * @param expiration The expiration time of the club invite
     */
    public ClubInvite(UUID target, long expiration){
        this.target = target;
        this.expiration = expiration;
    }

    /**
     * Check if the club invite is expired.
     *
     * @return True if the club invite is expired, false otherwise
     */
    public boolean isExpired(){
        return System.currentTimeMillis() > expiration;
    }
}