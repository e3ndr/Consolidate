package xyz.e3ndr.consolidate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import xyz.e3ndr.consolidate.exception.ArgumentsLengthException;
import xyz.e3ndr.consolidate.exception.CommandExecutionException;
import xyz.e3ndr.consolidate.exception.CommandNameException;

@Getter
@ToString
@AllArgsConstructor
public class CommandEvent<T> {
    private final @NonNull CommandRegistry<T> registry;
    private final @NonNull List<String> args;
    private final @NonNull T executor;

    public String[] getArgsArray() {
        return this.args.toArray(new String[0]);
    }

    /**
     * Resolves an object using the registry's resolvers.
     *
     * @param <E> the element type
     * @param position the position in the arguments
     * @param clazz the type of element
     * @return the element
     * @throws CommandNameException thrown if a command can't be found for the given input
     * @throws CommandExecutionException thrown if the command generates an exception
     * @throws ArgumentsLengthException thrown if the input's arguments are less than the required amount
     */
    public <E> E resolve(int position, Class<E> clazz) {
        return this.registry.resolve(this.args.get(position), clazz);
    }

}
