package xyz.e3ndr.consolidate.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Command annotation, for annotating methods for commands.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String DEFAULT_PERMISSION = "_default_";
    String NO_OWNER = "_default_";

    String name();

    String description();

    String[] aliases() default {};

    int minimumArguments() default 0;

    String owner() default NO_OWNER;

    String permission() default DEFAULT_PERMISSION;

}
