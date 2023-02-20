package dk.hoffe.labymodsk.Skript.Expression;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.expressions.base.WrapperExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.localization.Language;
import ch.njol.util.Kleenean;
import dk.hoffe.labymodsk.Classes.LabyPlayer;
import dk.hoffe.labymodsk.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static ch.njol.skript.classes.data.BukkitClasses.UUID_PATTERN;

public class ExprLabyPlayer extends WrapperExpression<LabyPlayer> {
    static  {
        Skript.registerExpression(ExprLabyPlayer.class, LabyPlayer.class, ExpressionType.SIMPLE, "[the] [event-]player");
    }
    private int mark;
    private Player labyPlayer;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends LabyPlayer> getReturnType() {
        return LabyPlayer.class;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        mark = parser.mark;
        return true;
    }

    @Override
    protected @Nullable LabyPlayer[] get(Event e) {
        return null;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "test";
    }
}
