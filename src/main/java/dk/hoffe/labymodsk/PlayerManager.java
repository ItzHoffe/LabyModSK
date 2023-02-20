package dk.hoffe.labymodsk;

import dk.hoffe.labymodsk.Classes.LabyPlayer;
import dk.hoffe.labymodsk.Classes.LabyVersion;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements PluginMessageListener, Listener {
    private List<LabyPlayer> labyPlayers = new ArrayList<>();
    public PlayerManager() {
        Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "labymod3:main", this);
    }

    public LabyPlayer getLabyPlayer(Player player) {
        for (LabyPlayer labyPlayer : labyPlayers) {
            if(labyPlayer.getPlayer().equals(player)) {
                return labyPlayer;
            }
        }
        return new LabyPlayer(player, new LabyVersion("0.0.0"));
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("labymod3:main")) {
            return;
        }

        ByteBuf buf = Unpooled.wrappedBuffer(message);
        String key = MediaProtocol.readString(buf, Short.MAX_VALUE);
        String json = MediaProtocol.readString(buf, Short.MAX_VALUE);

        // LabyMod user joins the server
        if(key.equals("INFO")) {
            labyPlayers.add(new LabyPlayer(player, new LabyVersion("1.0.0")));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        LabyPlayer labyPlayer = getLabyPlayer(player);
        if(labyPlayer != null) {
            labyPlayers.remove(labyPlayer);
        }
    }
}
