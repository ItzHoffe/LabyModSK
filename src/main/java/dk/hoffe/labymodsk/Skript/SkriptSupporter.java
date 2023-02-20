package dk.hoffe.labymodsk.Skript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.localization.Language;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Getter;
import dk.hoffe.labymodsk.Classes.LabyPlayer;
import dk.hoffe.labymodsk.Classes.LabyVersion;
import dk.hoffe.labymodsk.Events.LabyPlayerJoinEvent;
import dk.hoffe.labymodsk.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static ch.njol.skript.registrations.EventValues.registerEventValue;

public class SkriptSupporter {
    private SkriptAddon addonInstance;
    public SkriptSupporter() {
        this.addonInstance = Skript.registerAddon(Main.getInstance());
        this.registerClasses();
        this.registerEvents();
        try {
            addonInstance.loadClasses("dk.hoffe.labymodsk.Skript.Expression");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pattern UUID_PATTERN = Pattern.compile("(?i)[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}");

    public void registerEvents() {
        Skript.registerEvent("labymod join", SimpleEvent.class, LabyPlayerJoinEvent.class, "labymod join");
        registerEventValue(LabyPlayerJoinEvent.class, LabyPlayer.class, new Getter<LabyPlayer, LabyPlayerJoinEvent>() {
            @Override
            public LabyPlayer get(LabyPlayerJoinEvent event) {
                return event.getPlayer();
            }
        }, 0);
    }
    public void registerClasses() {
        Classes.registerClass(new ClassInfo<>(LabyPlayer.class, "labyplayer")
                .defaultExpression(new EventValueExpression<>(LabyPlayer.class))
                .user("labyplayer")
                .name("labyplayer")
                .parser(new Parser<LabyPlayer>() {
                    @Override
                    public LabyPlayer parse(String s, ParseContext context) {
                        if(context.equals(ParseContext.COMMAND)) {
                            if (s.isEmpty())
                                return null;
                            if (UUID_PATTERN.matcher(s).matches())
                                return Main.getInstance().getPlayerManager().getLabyPlayer(Bukkit.getPlayer(UUID.fromString(s)));
                            List<Player> ps = Bukkit.matchPlayer(s);
                            if (ps.size() == 1)
                                return Main.getInstance().getPlayerManager().getLabyPlayer(ps.get(0));
                            if (ps.size() == 0)
                                Skript.error(String.format(Language.get("commands.no player starts with"), s));
                            else
                                Skript.error(String.format(Language.get("commands.multiple players start with"), s));
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return context.equals(ParseContext.COMMAND);
                    }

                    @Override
                    public String toString(LabyPlayer arg0, int arg1) {
                        return "" + arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(LabyPlayer arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(LabyVersion.class, "version")
                .defaultExpression(new EventValueExpression<>(LabyVersion.class))
                .user("version")
                .name("version")
        );
    }
}
