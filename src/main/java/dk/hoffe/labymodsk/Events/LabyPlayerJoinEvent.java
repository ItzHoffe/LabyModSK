package dk.hoffe.labymodsk.Events;

import ch.njol.skript.lang.SkriptEvent;
import dk.hoffe.labymodsk.Classes.LabyPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LabyPlayerJoinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    protected LabyPlayer labyPlayer;
    public LabyPlayerJoinEvent(LabyPlayer player) {
        this.labyPlayer = player;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public final LabyPlayer getPlayer() {
        return this.labyPlayer;
    }
}
