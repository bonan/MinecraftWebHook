package se.bonan.minecraft.webhook;

import org.bukkit.configuration.ConfigurationSection;

/**
 * PlayerInvites
 *
 * @Author Björn Enochsson
 * @Copyright 2015-2018 Björn Enochsson
 */
public class WebHook extends se.bonan.minecraft.base.Plugin {

    public static String cmd = "webhook";

    @Override
    protected void loadCommands() {
        ConfigurationSection section = config.getConfigurationSection("commands");

        ReloadCommand reload = new ReloadCommand("reload", this, "webhook.reload");
        addCommand(reload);

        for (String key: section.getKeys(false)) {
            WebCommand cmd = new WebCommand(key, this, "webhook."+key, section.getConfigurationSection(key));
            addCommand(cmd);
        }
    }

    @Override
    public String getCmd() {
        return cmd;
    }
}
