package xyz.srnyx.lazylibrary;

import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.section.SectionAccessoryComponent;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * A mutable builder class for creating and modifying Container components in a more flexible way.
 * <p>
 * It allows for adding, inserting, and clearing components, as well as setting properties like color, unique ID, disabled state, and spoiler state.
 * <p>
 * The LazyContainer can be converted to a Factory for creating multiple instances of the same configuration or built into an immutable Container instance.
 */
public class LazyContainer {
    /**
     * The list of ContainerChildComponents that will be included in this LazyContainer. This is initialized as an empty list and can be modified using the provided methods.
     */
    @NotNull protected final List<ContainerChildComponent> components = new ArrayList<>();
    /**
     * The accent color for this LazyContainer, which will be applied to the built Container instance. This is optional and can be set to null if no color is desired.
     */
    @Nullable protected Color color;
    /**
     * A unique identifier for this LazyContainer, which can be used to identify it when built into a Container instance. This is optional and can be set to null if not needed.
     */
    @Nullable protected Integer uniqueId;
    /**
     * Indicates whether this LazyContainer should be marked as disabled, which will affect how it is displayed when built into a Container instance
     */
    protected boolean disabled = false;
    /**
     * Indicates whether this LazyContainer should be marked as a spoiler, which will affect how it is displayed when built into a Container instance
     */
    protected boolean spoiler = false;

    /**
     * Constructs a new LazyContainer with default values (empty components, no color, no unique ID, enabled, and not a spoiler)
     */
    public LazyContainer() {}

    /**
     * Constructs a new LazyContainer by copying the properties and components from another LazyContainer instance.
     *
     * @param   container   the LazyContainer instance to copy from
     */
    public LazyContainer(@NotNull LazyContainer container) {
        this.components.addAll(container.components);
        this.color = container.color;
        this.uniqueId = container.uniqueId;
        this.disabled = container.disabled;
        this.spoiler = container.spoiler;
    }

    /**
     * Constructs a new LazyContainer by copying the properties and components from an existing Container instance.
     *
     * @param   container   the Container instance to copy from
     */
    public LazyContainer(@NotNull Container container) {
        this.components.addAll(container.getComponents());
        this.color = container.getAccentColor();
        this.uniqueId = container.getUniqueId();
        this.disabled = container.isDisabled();
        this.spoiler = container.isSpoiler();
    }

    /**
     * Creates a copy of this LazyContainer instance, including all components and properties.
     *
     * @return  a new LazyContainer instance that is a copy of this instance
     */
    @NotNull
    public LazyContainer copy() {
        return new LazyContainer(this);
    }

    /**
     * Converts this LazyContainer into a Factory that can be used to create multiple instances of the same configuration.
     *
     * @return  a new Factory instance initialized with the current state of this LazyContainer
     */
    @NotNull
    public Factory toFactory() {
        return new Factory(this);
    }

    /**
     * Builds an immutable Container instance based on the current state of this LazyContainer, including all components and properties.
     *
     * @return  a new Container instance that reflects the current configuration of this LazyContainer
     */
    @NotNull
    public Container build() {
        Container container = Container.of(components)
                .withAccentColor(color)
                .withDisabled(disabled)
                .withSpoiler(spoiler);
        if (uniqueId != null) container = container.withUniqueId(uniqueId);
        return container;
    }

    /**
     * Adds one or more ContainerChildComponents to this LazyContainer's list of components.
     *
     * @param   components  the ContainerChildComponents to add to this LazyContainer
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer addComponents(@NotNull ContainerChildComponent... components) {
        return addComponents(Arrays.asList(components));
    }

    /**
     * Adds a list of ContainerChildComponents to this LazyContainer's list of components.
     *
     * @param   components  the List of ContainerChildComponents to add to this LazyContainer
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer addComponents(@NotNull List<ContainerChildComponent> components) {
        this.components.addAll(components);
        return this;
    }

    /**
     * Adds a section to this LazyContainer, which can include an optional accessory component and a required text display.
     *
     * @param   accessory   the SectionAccessoryComponent to include in the section, or null if no accessory is needed
     * @param   textDisplay the TextDisplay to include in the section
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer addSection(@Nullable SectionAccessoryComponent accessory, @NotNull TextDisplay textDisplay) {
        return addComponents(LazyComponent.getSectionElseText(accessory, textDisplay));
    }

    /**
     * Inserts one or more ContainerChildComponents at a specific index in this LazyContainer's list of components.
     *
     * @param   index       the index at which to insert the components
     * @param   components  the ContainerChildComponents to insert into this LazyContainer
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer insertComponents(int index, @NotNull ContainerChildComponent... components) {
        return insertComponents(index, Arrays.asList(components));
    }

    /**
     * Inserts a list of ContainerChildComponents at a specific index in this LazyContainer's list of components.
     *
     * @param   index       the index at which to insert the components
     * @param   components  the List of ContainerChildComponents to insert into this LazyContainer
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer insertComponents(int index, @NotNull List<ContainerChildComponent> components) {
        this.components.addAll(index, components);
        return this;
    }

    /**
     * Inserts a section at a specific index in this LazyContainer, which can include an optional accessory component and a required text display.
     *
     * @param   index       the index at which to insert the section
     * @param   accessory   the SectionAccessoryComponent to include in the section, or null if no accessory is needed
     * @param   textDisplay the TextDisplay to include in the section
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer insertSection(int index, @Nullable SectionAccessoryComponent accessory, @NotNull TextDisplay textDisplay) {
        return insertComponents(index, LazyComponent.getSectionElseText(accessory, textDisplay));
    }

    /**
     * Clears all components from this LazyContainer, leaving it with an empty list of components.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer clearComponents() {
        this.components.clear();
        return this;
    }

    /**
     * Sets the accent color for this LazyContainer, which will be applied to the built Container instance.
     *
     * @param   color   the Color to set as the accent color for this LazyContainer, or null to remove any existing color
     *
     * @return          this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer setColor(@Nullable Color color) {
        this.color = color;
        return this;
    }

    /**
     * Removes any existing accent color from this LazyContainer, setting it back to null.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer removeColor() {
        this.color = null;
        return this;
    }

    /**
     * Sets the unique ID for this LazyContainer, which will be applied to the built Container instance.
     *
     * @param   uniqueId    the Integer to set as the unique ID for this LazyContainer, or null to remove any existing unique ID
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer setUniqueId(@Nullable Integer uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    /**
     * Removes any existing unique ID from this LazyContainer, setting it back to null.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer removeUniqueId() {
        this.uniqueId = null;
        return this;
    }

    /**
     * Sets the disabled state for this LazyContainer, which will be applied to the built Container instance.
     *
     * @param   disabled    the boolean value to set as the disabled state for this LazyContainer (true to disable, false to enable)
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    /**
     * Sets this LazyContainer to be disabled, which will be applied to the built Container instance.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer asDisabled() {
        this.disabled = true;
        return this;
    }

    /**
     * Sets this LazyContainer to be enabled (not disabled), which will be applied to the built Container instance.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer asEnabled() {
        this.disabled = false;
        return this;
    }

    /**
     * Sets the spoiler state for this LazyContainer, which will be applied to the built Container instance.
     *
     * @param   spoiler     the boolean value to set as the spoiler state for this LazyContainer (true to mark as spoiler, false to not mark as spoiler)
     *
     * @return              this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
        return this;
    }

    /**
     * Sets this LazyContainer to be marked as a spoiler, which will be applied to the built Container instance.
     *
     * @return  this LazyContainer instance, allowing for method chaining
     */
    @NotNull
    public LazyContainer asSpoiler() {
        this.spoiler = true;
        return this;
    }

    /**
     * A Factory class for creating multiple instances of LazyContainer with the same configuration.
     */
    public static class Factory {
        @NotNull private LazyContainer container;

        /**
         * Constructs a new Factory with a default LazyContainer instance, which can be used to create multiple instances of the same configuration.
         */
        public Factory() {
            this.container = new LazyContainer();
        }

        /**
         * Constructs a new Factory with the provided LazyContainer instance, which can be used to create multiple instances of the same configuration.
         *
         * @param   container   the LazyContainer instance to use as the template for creating new instances
         */
        public Factory(@NotNull LazyContainer container) {
            this.container = container;
        }

        /**
         * Creates a new LazyContainer instance by copying the properties and components from the Factory's current LazyContainer.
         *
         * @return  a new LazyContainer instance that is a copy of the Factory's current LazyContainer
         */
        @NotNull
        public LazyContainer newContainer() {
            return container.copy();
        }

        /**
         * Sets the LazyContainer instance used by this Factory, allowing for changes to the template configuration for new instances created by this Factory.
         *
         * @param   embed   the LazyContainer instance to set as the template for this Factory
         *
         * @return          this Factory instance, allowing for method chaining
         */
        @NotNull
        public Factory setContainer(@NotNull LazyContainer embed) {
            this.container = embed;
            return this;
        }

        /**
         * Updates the LazyContainer instance used by this Factory by applying a Consumer function to it, allowing for modifications to the template configuration for new instances created by this Factory.
         *
         * @param   updater the Consumer function that takes a LazyContainer and applies modifications to it
         *
         * @return          this Factory instance, allowing for method chaining
         */
        @NotNull
        public Factory updateContainer(@NotNull Consumer<LazyContainer> updater) {
            updater.accept(container);
            return this;
        }
    }
}
