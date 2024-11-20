package me.emmy.clubs.role;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @since 22/10/2023
 */
public class RoleProvider extends DrinkProvider<Role> {

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Nullable
    @Override
    public Role provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        for (Role role : Role.values()){
            if (role.getName().equalsIgnoreCase(name)){
                return role;
            }
        }
        return null;
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = new ArrayList<>();
        for (Role role : Role.values()){
            if (role.getName().toLowerCase().startsWith(prefix.toLowerCase())) suggestions.add(role.getName());
        }
        return suggestions;
    }

    @Override
    public String argumentDescription() {
        return null;
    }
}