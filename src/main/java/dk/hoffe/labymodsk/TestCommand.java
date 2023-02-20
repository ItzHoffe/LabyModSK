package dk.hoffe.labymodsk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.labymod.opus.OpusCodec;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class TestCommand implements CommandExecutor, PluginMessageListener {
    public TestCommand() {
        Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "labymod3:media", this);
    }
    private List<String> files = Arrays.asList("test.ogg", "test2.ogg");
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "play");
        byte[] bytes = getBytesFromFile(files.get(0));
        String compressBase64 = Base64.encodeBase64String(bytes);
        jsonObject.addProperty("data", compressBase64);
        jsonObject.addProperty("id", files.get(0));
        //player.sendMessage(jsonObject.toString());
        MediaProtocol.sendLabyModMessage(player, "sound", jsonObject);
        return true;
    }

    private static byte[] getBytesFromFile(String file) {
        try {
            byte[] oggBytes = Files.readAllBytes(new File(Main.getInstance().getDataFolder(), file).toPath());
            return oggBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("labymod3:media")) {
            return;
        }

        ByteBuf buf = Unpooled.wrappedBuffer(message);
        String key = MediaProtocol.readString(buf, Short.MAX_VALUE);
        String json = MediaProtocol.readString(buf, Short.MAX_VALUE);

        if(key.equals("done")) {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            if(jsonObject.get("id").getAsString().equals(files.get(0))) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("type", "play");
                byte[] bytes = getBytesFromFile(files.get(1));
                String compressBase64 = Base64.encodeBase64String(bytes);
                jsonObject2.addProperty("data", compressBase64);
                jsonObject2.addProperty("id", files.get(1));
                //player.sendMessage(jsonObject.toString());
                MediaProtocol.sendLabyModMessage(player, "sound", jsonObject2);

            }
        }
    }
}
