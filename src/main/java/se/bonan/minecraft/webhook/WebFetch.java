package se.bonan.minecraft.webhook;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * PlayerInvites
 *
 * @Author Björn Enochsson
 * @Copyright 2015-2018
 */
public class WebFetch extends BukkitRunnable {

    private final ConfigurationSection config;
    private final Player player;

    public WebFetch(ConfigurationSection config, Player player) {
        this.config = config;
        this.player = player;
    }

    @Override
    public void run() {
        String url = config.getString("url");
        String method = config.getString("method", "GET");
        String data = config.getString("data", "");

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod(method);
            //conn.setDoInput(true);
            for (String key: config.getKeys(false)) {
                if (!key.equals("url") && !key.equals("method") && !key.equals("data"));
                String value = config.getString(key);
                conn.setRequestProperty(key, value);
            }
            if (!method.equals("GET") && !data.equals("")) {
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.close();
            }
            int code = conn.getResponseCode();
            /*InputStream in = conn.getInputStream();
            byte[] resp = new byte[1024];
            String respStr;
            if (in.read(resp) > 0) {
                respStr = new String(resp);
            }
            in.close();*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
