package me.emmy.clubs.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hieu
 * @since 27/09/2023
 */
@UtilityClass
public class CC {
    /**
     * Translate a string with color codes
     *
     * @param string The string to translate
     * @return The translated string
     */
    public String translate(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Translate a list of strings with color codes
     *
     * @param lines The list of strings to translate
     * @return The translated list of strings
     */
    public List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    /**
     * Translate an array of strings with color codes
     *
     * @param lines The array of strings to translate
     * @return The translated list of strings
     */
    public List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

    public String MENU_BAR = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------";
    public String CHAT_BAR = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";
    public String SB_BAR = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------";
}