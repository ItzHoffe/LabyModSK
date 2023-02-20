package dk.hoffe.labymodsk;

import dk.hoffe.labymodsk.Skript.SkriptSupporter;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Getter
    private static Main instance;
    @Getter
    private PlayerManager playerManager;
    @Getter
    private SkriptSupporter skriptSupporter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.playerManager = new PlayerManager();
        this.skriptSupporter = new SkriptSupporter();
        this.getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
