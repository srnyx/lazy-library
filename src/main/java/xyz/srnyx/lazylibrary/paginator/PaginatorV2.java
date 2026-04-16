package xyz.srnyx.lazylibrary.paginator;

import io.github.freya022.botcommands.api.components.Buttons;
import io.github.freya022.botcommands.api.components.data.InteractionConstraints;
import io.github.freya022.botcommands.api.components.event.ButtonEvent;

import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.replacer.ComponentReplacer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.lazylibrary.emoji.LazyEmoji;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


/**
 * A class that represents a paginator with buttons for navigating through pages of content. It uses the Buttons service to create pagination buttons and a function to generate the content for each page. The paginator also supports interaction constraints to control who can interact with the pagination buttons.
 */
public class PaginatorV2 {
    private static final int ID_BUTTONS = 629682327;

    /**
     * The Buttons service used to create pagination buttons. This field is final and should be provided during the construction of the PaginatorV2 instance.
     */
    @NotNull public final Buttons buttons;
    /**
     * The maximum number of pages for the paginator. This field is mutable and can be updated as needed, but it should be set to a positive integer to ensure proper functionality of the pagination buttons.
     */
    public int maxPages;
    @NotNull private final Function<PaginatorV2, ComponentReplacer> function;
    /**
     * The interaction constraints for the paginator's buttons. This field is mutable and can be updated using the setConstraints method. If not set, it defaults to empty constraints.
     */
    @NotNull public InteractionConstraints constraints = InteractionConstraints.empty();
    /**
     * The current page of the paginator, starting at 0. This field is mutable and will be updated when the user interacts with the pagination buttons.
     */
    public int currentPage = 0;

    /**
     * Constructs a new PaginatorV2 instance with the provided Buttons service, maximum number of pages, and a function that generates a ComponentReplacer for each page.
     *
     * @param   buttons     the Buttons service to be used for creating pagination buttons
     * @param   maxPages    the maximum number of pages for the paginator
     * @param   function    a function that takes a PaginatorV2 instance and returns a ComponentReplacer for each page
     */
    public PaginatorV2(@NotNull Buttons buttons, int maxPages, @NotNull Function<PaginatorV2, ComponentReplacer> function) {
        this.buttons = buttons;
        this.maxPages = maxPages;
        this.function = function;
    }

    /**
     * Sets the interaction constraints for the paginator's buttons. If the provided constraints are null, it defaults to empty constraints.
     *
     * @param   constraints the InteractionConstraints to apply to the paginator's buttons, or null to use empty constraints
     *
     * @return              the current instance of PaginatorV2 with the updated constraints
     */
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

    /**
     * Generates an ActionRow containing pagination buttons (first, previous, current page indicator, next, last) with appropriate constraints and disabled states based on the current page and maximum pages.
     *
     * @return  an ActionRow with pagination buttons configured according to the current state of the paginator
     */
    @NotNull
    public ActionRow getButtonRow() {
        return ActionRow.of(
                        buttons.primary(LazyEmoji.BACK_CLEAR_DARK).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = 0;
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage == 0),
                        buttons.primary(LazyEmoji.LEFT2_CLEAR_DARK).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = Math.max(0, currentPage - 1);
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage == 0),
                        buttons.secondary((currentPage + 1) + " / " + maxPages).toLabelButton(),
                        buttons.primary(LazyEmoji.RIGHT2_CLEAR_DARK).ephemeral()
                                .bindTo(event -> {
                                    event.deferEdit().queue();
                                    currentPage = Math.min(maxPages - 1, currentPage + 1);
                                    updateMessage(event);
                                })
                                .constraints(constraints)
                                .build()
                                .withDisabled(currentPage >= maxPages - 1),
                        buttons.primary(LazyEmoji.FORWARD_CLEAR_DARK).ephemeral()
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
