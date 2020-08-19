package xyz.e3ndr.consolidate.command;

import xyz.e3ndr.consolidate.CommandEvent;

/**
 * The listener interface for tagging command listeners. Any receiving methods must be annotated with {@link Command}.
 * 
 * @param <T> the generic type
 * @see CommandEvent
 */
public interface CommandListener<T> {

}
