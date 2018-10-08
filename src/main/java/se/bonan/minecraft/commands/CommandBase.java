package se.bonan.minecraft.commands;

import org.bukkit.entity.Player;
import se.bonan.minecraft.webhook.Str;

/**
 * User: Björn
 * Date: 2015-03-14
 * Time: 23:59
 */
abstract public class CommandBase<P> {

    protected String permission = null;
    protected String name;
    protected P plugin;

    public CommandBase(String name, P plugin, String permission) {
        this.name = name;
        this.plugin = plugin;
        this.permission = permission;
    }

    public Boolean hasPerm(Player player) {
        return permission == null || player == null || player.hasPermission(permission);
    }

    public String getName() {
        return name;
    }

    abstract public String usage(Player player);

    public String usagePrefix(Player player) {
        return Str.Usage.str() + usage(player);
    }

    public String help(Player player) {
        return "";
    }

    public String usageStr(String text, String ... args) {
        String n = name;
        for (String arg: args) {
            n = n + " " + arg;
        }

        return Str.Help.format(n, text);
    }

    public String helpExtra() {
        return null;
    }

}
