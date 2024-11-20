package me.emmy.clubs.profile;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import me.emmy.clubs.Clubs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @since 21/10/2023
 */
public class ProfileProvider extends DrinkProvider<Profile> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean allowNullArgument() {
        return true;
    }

    @Nullable
    @Override
    public Profile defaultNullValue() {
        return null;
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (Profile profile : Clubs.getInstance().getProfileHandler().getProfiles()){
            if (profile.getUsername().toLowerCase().startsWith(prefix.toLowerCase())){
                suggestions.add(profile.getUsername());
            }
        }
        return suggestions;
    }

    @Nullable
    @Override
    public Profile provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        if (name == null) return null;
        return Clubs.getInstance().getProfileHandler().getProfileByUsername(name);
    }

    @Override
    public String argumentDescription() {
        return "player";
    }
}
