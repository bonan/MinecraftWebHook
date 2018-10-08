package se.bonan.minecraft.exception;

/**
 * User: Björn
 * Date: 2015-03-15
 * Time: 00:16
 */
public class PlayerNotFoundException extends PluginException {

    private String name;

    public PlayerNotFoundException(String name) {
        super("PlayerInvites: Player "+name+" not found");
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
