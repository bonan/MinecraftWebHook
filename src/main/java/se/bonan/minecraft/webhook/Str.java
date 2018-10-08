package se.bonan.minecraft.webhook;

/**
 * Strings used throughout the plugin
 *
 * Any string here can be overriden in config by setting variable "string<name>"
 * Example:
 *   stringBuySuccess: "Congratulations! You have bought %1 invites for a cost of %2"
 */
public enum Str {

    // 0 = BLACK,
    // 1 = DARK_BLUE,
    // 2 = DARK_GREEN,
    // 3 = DARK_AQUA,
    // 4 = DARK_RED,
    // 5 = DARK_PURPLE,
    // 6 = GOLD,
    // 7 = GRAY,
    // 8 = DARK_GRAY,
    // 9 = BLUE,
    // a = GREEN,
    // b = AQUA,
    // c = RED,
    // d = LIGHT_PURPLE,
    // e = YELLOW,
    // f = WHITE,
    // k = MAGIC,
    // l = BOLD,
    // m = STRIKETHROUGH,
    // n = UNDERLINE,
    // o = ITALIC,
    // r = RESET

    Usage("Usage:"),
    Help("  §7/%c §6%1§r - %2"),
    HelpUsage("Usage: §7/%c §r<§6%1§r>"),
    HelpCmdSep("§r|§6"),
    ErrorPermission("§cYou don't have permission to use that command"),
    ErrorConsole("§cThis command cannot be run from console"),
    ErrorNoPlayer("§cNo player specified"),
    ErrorNotFound("§cPlayer §b%1§c was not found");

    private String def;
    private Str(String def) {
        this.def = def;
    }
    public String toString() {
        return WebHook.config.getString("string" + this.name(), def).replace("%%","%");
    }
    public String str() {
        return toString();
    }
    public String def() {
        return def;
    }
    public String format(String ... args) {
        String string = WebHook.config.getString("string" + this.name(), def);
        if (string.contains("%c"))
            string = string.replace("%c", WebHook.cmd);
        if (string.contains("%u"))
            string = string.replace("%u", WebHook.config.getString("cmdUse", "use"));
        for (int i = args.length; i > 0; i--) {
            if (string.contains("%"+i))
                string = string.replace("%"+i, args[i-1]);
        }

        return string.replace("%%", "%");
    }

    public static String getConfig() {
        StringBuilder conf = new StringBuilder("\r\n\r\n##\r\n" +
                "## Language/strings configuration\r\n" +
                "## Uncomment lines to replace strings with localized version\r\n" +
                "##\r\n");

        for (Str str: Str.values()) {
            conf.append("#string")
                    .append(str.name())
                    .append(": \"")
                    .append(str.def().replace("\"", "\\\""))
                    .append("\"\r\n");
        }

        return conf.toString();
    }
}