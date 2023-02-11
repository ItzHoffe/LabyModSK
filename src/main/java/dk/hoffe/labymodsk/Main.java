package dk.hoffe.labymodsk;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    @Getter
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        playerManager = new PlayerManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
