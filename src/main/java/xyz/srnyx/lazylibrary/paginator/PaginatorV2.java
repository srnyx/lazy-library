package xyz.srnyx.lazylibrary.paginator;

import io.github.freya022.botcommands.api.components.Buttons;
import io.github.freya022.botcommands.api.components.data.InteractionConstraints;
import io.github.freya022.botcommands.api.components.event.ButtonEvent;

import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.replacer.ComponentReplacer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.lazylibrary.LazyEmoji;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


public class PaginatorV2 {
    private static final int ID_BUTTONS = 629682327;

    @NotNull public final Buttons buttons;
    public int maxPages;
    @NotNull private final Function<PaginatorV2, ComponentReplacer> function;
    @NotNull public InteractionConstraints constraints = InteractionConstraints.empty();
    public int currentPage = 0;

    public PaginatorV2(@NotNull Buttons buttons, int maxPages, @NotNull Function<PaginatorV2, ComponentReplacer> function) {
        this.buttons = buttons;
        this.maxPages = maxPages;
        this.function = function;
    }

    @NotNull
    public PaginatorV2 setConstraints(@Nullable InteractionConstraints constraints) {
        this.constraints = Objects.requireNonNullElseGet(constraints, InteractionConstraints::empty);
        return this;
    }

    private void updateMessage(@NotNull ButtonEvent event) {
        final List<ComponentReplacer> replacers = new ArrayList<>();
        replacers.add(ComponentReplacer.byUniqueId(ID_BUTTONS, getButtonRow()));
        replacers.add(function.apply(this));
        event.getHook().editOriginalComponents(event.getMessage().getComponentTree().replace(ComponentReplacer.all(replacers))).useComponentsV2().queue();
    }

    @NotNull
    public ActionRow getButtonRow() {
        return ActionRow.of(
                        buttons.primary(LazyEmoji.BACK_CLEAR_DARK.emoji).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = 0;
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage == 0),
                        buttons.primary(LazyEmoji.LEFT2_CLEAR_DARK.emoji).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = Math.max(0, currentPage - 1);
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage == 0),
                        buttons.secondary((currentPage + 1) + " / " + maxPages).toLabelButton(),
                        buttons.primary(LazyEmoji.RIGHT2_CLEAR_DARK.emoji).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = Math.min(maxPages - 1, currentPage + 1);
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage >= maxPages - 1),
                        buttons.primary(LazyEmoji.FORWARD_CLEAR_DARK.emoji).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = maxPages - 1;
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage >= maxPages - 1))
                .withUniqueId(ID_BUTTONS);
    }
}
