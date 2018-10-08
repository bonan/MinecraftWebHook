package se.bonan.minecraft.exception;

import se.bonan.minecraft.webhook.Str;

/**
 * User: Björn
 * Date: 2015-03-15
 * Time: 00:38
 */
public class PlayerNotSpecifiedException extends PluginException {

    public PlayerNotSpecifiedException() {
        super(Str.ErrorNoPlayer.format());
    }
}
