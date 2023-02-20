package dk.hoffe.labymodsk.Classes;

import lombok.Getter;
import org.bukkit.entity.Player;

public class LabyPlayer {
    @Getter
    private Player player;
    @Getter
    private LabyVersion version;
    public LabyPlayer(Player player, LabyVersion version) {
        this.player = player;
        this.version = version;
    }

    @Override
    public String toString() {
        return "LabyPlayer{" +
                "player=" + player +
                ", version=" + version +
                '}';
    }
}
