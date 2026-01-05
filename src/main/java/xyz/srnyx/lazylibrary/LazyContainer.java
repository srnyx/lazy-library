package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.section.SectionAccessoryComponent;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class LazyContainer {
    @NotNull protected final List<ContainerChildComponent> components = new ArrayList<>();
    @Nullable protected Color color;
    @Nullable protected Integer uniqueId;
    protected boolean disabled = false;
    protected boolean spoiler = false;

    public LazyContainer() {}

    public LazyContainer(@NotNull LazyContainer container) {
        this.components.addAll(container.components);
        this.color = container.color;
        this.uniqueId = container.uniqueId;
        this.disabled = container.disabled;
        this.spoiler = container.spoiler;
    }

    public LazyContainer(@NotNull Container container) {
        this.components.addAll(container.getComponents());
        this.color = container.getAccentColor();
        this.uniqueId = container.getUniqueId();
        this.disabled = container.isDisabled();
        this.spoiler = container.isSpoiler();
    }

    @NotNull
    public LazyContainer copy() {
        return new LazyContainer(this);
    }

    @NotNull
    public Factory toFactory() {
        return new Factory(this);
    }

    @NotNull
    public Container build() {
        Container container = Container.of(components)
                .withAccentColor(color)
                .withDisabled(disabled)
                .withSpoiler(spoiler);
        if (uniqueId != null) container = container.withUniqueId(uniqueId);
        return container;
    }

    @NotNull
    public LazyContainer addComponents(@NotNull ContainerChildComponent... components) {
        return addComponents(Arrays.asList(components));
    }

    @NotNull
    public LazyContainer addComponents(@NotNull List<ContainerChildComponent> components) {
        this.components.addAll(components);
        return this;
    }

    @NotNull
    public LazyContainer addSection(@Nullable SectionAccessoryComponent accessory, @NotNull TextDisplay textDisplay) {
        return addComponents(accessory == null ? textDisplay : Section.of(accessory, textDisplay));
    }

    @NotNull
    public LazyContainer insertComponents(int index, @NotNull ContainerChildComponent... components) {
        return insertComponents(index, Arrays.asList(components));
    }

    @NotNull
    public LazyContainer insertComponents(int index, @NotNull List<ContainerChildComponent> components) {
        this.components.addAll(index, components);
        return this;
    }

    @NotNull
    public LazyContainer insertSection(int index, @Nullable SectionAccessoryComponent accessory, @NotNull TextDisplay textDisplay) {
        return insertComponents(index, accessory == null ? textDisplay : Section.of(accessory, textDisplay));
    }

    @NotNull
    public LazyContainer clearComponents() {
        this.components.clear();
        return this;
    }

    @NotNull
    public LazyContainer setColor(@Nullable Color color) {
        this.color = color;
        return this;
    }

    @NotNull
    public LazyContainer removeColor() {
        this.color = null;
        return this;
    }

    @NotNull
    public LazyContainer setUniqueId(@Nullable Integer uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    @NotNull
    public LazyContainer removeUniqueId() {
        this.uniqueId = null;
        return this;
    }

    @NotNull
    public LazyContainer setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    @NotNull
    public LazyContainer asDisabled() {
        this.disabled = true;
        return this;
    }

    @NotNull
    public LazyContainer asEnabled() {
        this.disabled = false;
        return this;
    }

    @NotNull
    public LazyContainer setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
        return this;
    }

    @NotNull
    public LazyContainer asSpoiler() {
        this.spoiler = true;
        return this;
    }

    public static class Factory {
        @NotNull private LazyContainer container;

        public Factory() {
            this.container = new LazyContainer();
        }

        public Factory(@NotNull LazyContainer container) {
            this.container = container;
        }

        @NotNull
        public LazyContainer newContainer() {
            return container.copy();
        }

        @NotNull
        public Factory setContainer(@NotNull LazyContainer embed) {
            this.container = embed;
            return this;
        }

        @NotNull
        public Factory updateContainer(@NotNull Consumer<LazyContainer> updater) {
            updater.accept(container);
            return this;
        }
    }
}
