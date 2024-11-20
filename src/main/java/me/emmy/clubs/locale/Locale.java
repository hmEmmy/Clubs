package me.emmy.clubs.locale;

import lombok.experimental.UtilityClass;
import me.emmy.clubs.Clubs;
import me.emmy.clubs.util.CC;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author hieu
 * @since 21/10/2023
 */
@UtilityClass
public class Locale {
    FileConfiguration config = Clubs.getInstance().getConfig();

    public String IN_GAME_COMMAND_ONLY = CC.translate("&cOnly in-game players can execute this command.");
    public String COULDNT_RESOLVE_PROFILE = CC.translate("&cCouldn't resolve that profile.");
    public String PROFILE_ERROR = CC.translate("&cFailed to load your profile, please login again.");
    public String COULDNT_RESOLVE_CLUB = CC.translate("&cCouldn't resolve that club.")
            ;
    public String NOT_IN_A_CLUB = config.getString("MESSAGES.NOT-IN-A-CLUB");
    public String ALREADY_IN_A_CLUB = config.getString("MESSAGES.ALREADY-IN-A-CLUB");
    public String ENABLED_CLUB_CHAT = config.getString("MESSAGES.ENABLED_CLUB_CHAT");
    public String DISABLED_CLUB_CHAT = config.getString("MESSAGES.DISABLED_CLUB_CHAT");
}