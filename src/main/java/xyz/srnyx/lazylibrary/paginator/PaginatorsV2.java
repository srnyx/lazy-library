package xyz.srnyx.lazylibrary.paginator;

import io.github.freya022.botcommands.api.components.Buttons;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.components.replacer.ComponentReplacer;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;


/**
 * A service that provides methods for creating paginators. It uses the Buttons service to create instances of PaginatorV2, which can be used to create paginated components in Discord messages.
 */
@BService
public class PaginatorsV2 {
    @NotNull private final Buttons buttons;

    /**
     * Constructs a new PaginatorsV2 instance with the provided Buttons service.
     *
     * @param   buttons the Buttons service to be used for creating paginators
     */
    public PaginatorsV2(@NotNull Buttons buttons) {
        this.buttons = buttons;
    }

    /**
     * Creates a new {@link PaginatorV2} with the specified maximum number of pages and a function that generates a {@link ComponentReplacer} for each page.
     *
     * @param   maxPages    the maximum number of pages for the paginator
     * @param   function    a function that takes a {@link PaginatorV2} and returns a {@link ComponentReplacer} for each page
     *
     * @return              a new instance of {@link PaginatorV2} configured with the provided parameters
     */
    @NotNull
    public PaginatorV2 createPaginator(int maxPages, @NotNull Function<PaginatorV2, ComponentReplacer> function) {
        return new PaginatorV2(buttons, maxPages, function);
    }
}
