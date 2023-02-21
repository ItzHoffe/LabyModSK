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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class TestCommand implements CommandExecutor, PluginMessageListener {
    public TestCommand() {
        Main.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Main.getInstance(), "labymod3:media", this);
    }
    private File[] files = null;
    private int index = 0;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        File dir = new File(Main.getInstance().getDataFolder(), "nvr");
        files = dir.listFiles();
        Arrays.sort(files, Comparator.comparing(File::getName));
        for (File file : files) {
            Bukkit.broadcastMessage("Name: " + file);
        }

        Player player = (Player) commandSender;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "play");
        byte[] bytes = getBytesFromFile(files[index]);
        String compressBase64 = Base64.encodeBase64String(bytes);
        jsonObject.addProperty("data", compressBase64);
        jsonObject.addProperty("id", files[index].getName());
        index++;
        //player.sendMessage(jsonObject.toString());
        MediaProtocol.sendLabyModMessage(player, "sound", jsonObject);
        return true;
    }

    private static byte[] getBytesFromFile(File file) {
        try {
            File oggFile = file;
            byte[] bytes = new byte[(int) oggFile.length()];
            FileInputStream fis = new FileInputStream(oggFile);
            fis.read(bytes);
            fis.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            if(files[index] == null) {
                return;
            }
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("type", "play");
            byte[] bytes = getBytesFromFile(files[index]);
            String compressBase64 = Base64.encodeBase64String(bytes);
            jsonObject2.addProperty("data", compressBase64);
            jsonObject2.addProperty("id", files[index].getName());
            //player.sendMessage(jsonObject.toString());
            MediaProtocol.sendLabyModMessage(player, "sound", jsonObject2);
            index++;
        }
    }
}
