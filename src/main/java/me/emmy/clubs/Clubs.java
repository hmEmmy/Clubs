package me.emmy.clubs;

import com.jonahseguin.drink.command.DrinkCommandService;
import lombok.Getter;
import me.emmy.clubs.club.ClubHandler;
import me.emmy.clubs.club.command.*;
import me.emmy.clubs.friend.command.*;
import me.emmy.clubs.listener.ChatListener;
import me.emmy.clubs.listener.GeneralListener;
import me.emmy.clubs.mongo.MongoHandler;
import me.emmy.clubs.papi.ProfilePlaceholders;
import me.emmy.clubs.profile.Profile;
import me.emmy.clubs.profile.ProfileHandler;
import me.emmy.clubs.profile.ProfileListener;
import me.emmy.clubs.profile.ProfileProvider;
import me.emmy.clubs.redis.RedisHandler;
import me.emmy.clubs.role.Role;
import me.emmy.clubs.role.RoleProvider;
import me.emmy.clubs.util.safelock.safelock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Clubs extends JavaPlugin {

    @Getter public static Clubs instance;

    private MongoHandler mongoHandler;
    private RedisHandler redisHandler;
    private ProfileHandler profileHandler;
    private ClubHandler clubHandler;

    @Override
    public void onEnable() {
        instance = this;

        if(!new safelock(this, getConfig().getString("LICENSE_KEY"), "https://license.revere.dev/api/client", "3016f575fa8f3c58b9224047695249f3ca4ca773").unlock()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }

        this.saveDefaultConfig();
        this.registerManagers();
        this.registerHandlers();
        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        for (Profile profile : this.profileHandler.getProfiles()) {
            profile.setLastSeenOn(System.currentTimeMillis());
            profile.save();
            this.profileHandler.getProfiles().remove(profile);
        }
        instance = null;
    }

    private void setupMongoRedisHandler() {
        String host;
        int port;
        String password;
        boolean uri = getConfig().getBoolean("MONGO.URI.ENABLED");
        String connectionString = getConfig().getString("MONGO.URI.CONNECTION-STRING");
        host = getConfig().getString("MONGO.DEFAULT.HOST");
        port = getConfig().getInt("MONGO.DEFAULT.PORT");
        String database = getConfig().getString("MONGO.DEFAULT.DATABASE");
        boolean authentication = getConfig().getBoolean("MONGO.DEFAULT.AUTHENTICATION.ENABLED");
        String username = getConfig().getString("MONGO.DEFAULT.AUTHENTICATION.USERNAME");
        password = getConfig().getString("MONGO.DEFAULT.AUTHENTICATION.PASSWORD");
        this.mongoHandler = new MongoHandler(uri, connectionString, host, port, database, authentication, username, password);
        host = getConfig().getString("REDIS.HOST");
        port = getConfig().getInt("REDIS.PORT");
        String channel = getConfig().getString("REDIS.CHANNEL");
        password = getConfig().getString("REDIS.PASSWORD");
        this.redisHandler = new RedisHandler(host, port, channel, password);
        this.redisHandler.connect();
    }

    private void registerManagers() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new ProfilePlaceholders().register();
        }
    }

    private void registerHandlers() {
        this.setupMongoRedisHandler();
        this.profileHandler = new ProfileHandler();
        this.clubHandler = new ClubHandler();
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
    }

    private void registerCommands(){
        DrinkCommandService drink = new DrinkCommandService(this);
        drink.bind(Profile.class).toProvider(new ProfileProvider());
        drink.bind(Role.class).toProvider(new RoleProvider());
        drink.register(new FriendsHelpCommand(), "friends", "friend", "fri", "fr", "f")
                .registerSub(new FriendsListCommand())
                .registerSub(new FriendsAddCommand())
                .registerSub(new FriendsAcceptCommand())
                .registerSub(new FriendsRemoveCommand());
        drink.register(new ClubsHelpCommand(), "clubs", "club", "cl")
                .registerSub(new ClubsCreateCommand())
                .registerSub(new ClubsLeaveCommand())
                .registerSub(new ClubsInviteCommand())
                .registerSub(new ClubsAcceptCommand())
                .registerSub(new ClubsRoleCommand())
                .registerSub(new ClubsKickCommand())
                .registerSub(new ClubsInformationCommand())
                .registerSub(new ClubsShowCommand())
                .registerSub(new ClubChatCommand())
                .registerSub(new ClubsDisbandCommand());
        drink.register(new CCCommand(), "cc");
        drink.register(new ClCommand(), "cl");
        drink.registerCommands();
    }
}