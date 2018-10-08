package se.bonan.minecraft.webhook;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import se.bonan.minecraft.commands.CommandBase;
import se.bonan.minecraft.commands.CommandInterface;


/**
 * PlayerInvites
 *
 * @Author Björn Enochsson
 * @Copyright 2015-2018 Björn Enochsson
 */
public class WebCommand extends CommandBase<WebHook> implements CommandInterface {

    private final ConfigurationSection config;

    public WebCommand(String name, WebHook plugin, String permission, ConfigurationSection config) {
        super(name, plugin, permission);
        this.config = config;
    }

    @Override
    public String invoke(Player player, String[] args) {
        try {
            for (String key: config.getKeys(false)) {
                ConfigurationSection cfg = config.getConfigurationSection(key);
                WebFetch fetch = new WebFetch(cfg, player);
                fetch.runTaskAsynchronously(plugin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String usage(Player player) {
        return "";
    }


}
