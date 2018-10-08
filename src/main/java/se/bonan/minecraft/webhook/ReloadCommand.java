package se.bonan.minecraft.webhook;

import org.bukkit.entity.Player;
import se.bonan.minecraft.commands.CommandBase;
import se.bonan.minecraft.commands.CommandInterface;

/**
 * PlayerInvites
 *
 * @Author Björn Enochsson
 * @Copyright 2015-2018 Björn Enochsson
 */
public class ReloadCommand extends CommandBase<WebHook> implements CommandInterface {
    public ReloadCommand(String name, WebHook plugin, String permission) {
        super(name, plugin, permission);
    }

    @Override
    public String invoke(Player player, String[] args) {
        plugin.reloadConfig();
        return "";
    }

    @Override
    public String usage(Player player) {
        return "";
    }
}
