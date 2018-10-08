package se.bonan.minecraft.exception;

import org.bukkit.ChatColor;

/**
 * User: Björn
 * Date: 2015-03-14
 * Time: 22:50
 */
abstract public class PluginException extends Exception {
    public PluginException(String message) {
        super(ChatColor.stripColor(message));
    }

    protected PluginException(String message, Throwable cause) {
        super(ChatColor.stripColor(message), cause);
    }

    protected PluginException(Throwable cause) {
        super(cause);
    }

    protected PluginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ChatColor.stripColor(message), cause, enableSuppression, writableStackTrace);
    }

    protected PluginException() {
    }
}
