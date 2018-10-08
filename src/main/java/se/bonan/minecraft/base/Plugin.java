package se.bonan.minecraft.base;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import se.bonan.minecraft.commands.CommandInterface;
import se.bonan.minecraft.webhook.Str;

import java.io.File;
import java.util.*;

abstract public class Plugin extends JavaPlugin {

    /**
     * Static config for access from Str
     */
    public static FileConfiguration config;

    /**
     * Available command instances, resets on reload
     */
    private List<CommandInterface> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        File conf = new File(getDataFolder(), "config.yml");
        if (!conf.exists()) {
            saveDefaultConfig();

        }
        load();
    }

    @Override
    public void onDisable() {

    }

    /**
     * Reloads configuration and data
     * @return Empty string on success, error string otherwise
     */
    public String reload() {
        reloadConfig();
        return load();
    }

    /**
     * Loads data from file and sets everything up
     * @return Empty string on success, error string otherwise
     */
    public String load() {
        try {
            /**
             * Static config used by Str
             */
            config = getConfig();

            /**
             * Define commands
             *
             * Command names can be overriden in config
             */
            this.commands = new LinkedList<>();
            this.loadCommands();

            // this.commands.add(new ShowCommand(config.getString("cmdShow", "show"), this));

        } catch (Exception e) {
            return ChatColor.RED + "Reload error: " + e.getMessage();
        }
        return "";
    }

    protected Plugin addCommand(CommandInterface command) {
        this.commands.add(command);
        return this;
    }

    abstract protected void loadCommands();
    abstract public String getCmd();

    /**
     * Invoked when console/player issues /cmd
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (!player.hasPermission(command.getPermission())) {
                sender.sendMessage(Str.ErrorPermission.str());
                return true;
            }
        }

        if (command.getName().equalsIgnoreCase(getCmd())) {

            if (args.length > 0) {
                /**
                 * Look for command
                 */
                for (CommandInterface action: commands) {
                    if (action.getName().equalsIgnoreCase(args[0])) {
                        /**
                         * Check permission for command
                         */
                        if (!action.hasPerm(player)) {
                            sender.sendMessage(Str.ErrorPermission.str());
                            return true;
                        }

                        /**
                         * Invoke command and return result to CommandSender
                         */
                        sender.sendMessage(
                                action.invoke(
                                        player,
                                        Arrays.copyOfRange(args, 1, args.length)
                                )
                        );
                        return true;
                    }
                }
            }

            /**
             * If no command was found, show help
             */

            String opts = "";
            String usage = "";

            /**
             * Loop through commands to get usage and help strings
             */
            for (CommandInterface action: commands) {
                /**
                 * Don't show commands that the player doesn't have permission for
                 */
                if (action.hasPerm(player)) {
                    /**
                     * Add command to opts string
                     */
                    opts = opts + (opts.length() > 0 ? Str.HelpCmdSep.str() : "") + action.getName();

                    /**
                     * Get usage help
                     */
                    usage = usage + action.usage(player) + "\n";

                    /**
                     * Check for additional help strings
                     */
                    String help = action.help(player);
                    if (help != null && !help.equals("")) {
                        usage = usage + "    " + help + "\n";
                    }
                }
            }

            /**
             * Shows short usage line
             * "Usage: /cmd <a|b|c|...>"
             */
            sender.sendMessage(Str.HelpUsage.format(opts));

            /**
             * Shows help
             */
            sender.sendMessage(usage);
            return true;

        }

        return false;
    }
}
