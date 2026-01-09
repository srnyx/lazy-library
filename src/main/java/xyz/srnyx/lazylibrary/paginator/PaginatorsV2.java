package xyz.srnyx.lazylibrary.paginator;

import io.github.freya022.botcommands.api.components.Buttons;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import net.dv8tion.jda.api.components.replacer.ComponentReplacer;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;


@BService
public class PaginatorsV2 {
    @NotNull private final Buttons buttons;

    public PaginatorsV2(@NotNull Buttons buttons) {
        this.buttons = buttons;
    }

    @NotNull
    public PaginatorV2 createPaginator(int maxPages, @NotNull Function<PaginatorV2, ComponentReplacer> function) {
        return new PaginatorV2(buttons, maxPages, function);
    }
}
