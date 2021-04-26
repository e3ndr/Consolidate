package xyz.e3ndr.consolidate;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.command.CommandListener;
import xyz.e3ndr.consolidate.exception.ArgumentsLengthException;
import xyz.e3ndr.consolidate.exception.CommandExecutionException;
import xyz.e3ndr.consolidate.exception.CommandNameException;
import xyz.e3ndr.consolidate.resolvers.BooleanResolver;
import xyz.e3ndr.consolidate.resolvers.ByteResolver;
import xyz.e3ndr.consolidate.resolvers.CharResolver;
import xyz.e3ndr.consolidate.resolvers.DoubleResolver;
import xyz.e3ndr.consolidate.resolvers.FloatResolver;
import xyz.e3ndr.consolidate.resolvers.IntegerResolver;
import xyz.e3ndr.consolidate.resolvers.LongResolver;
import xyz.e3ndr.consolidate.resolvers.ShortResolver;
import xyz.e3ndr.consolidate.resolvers.StringResolver;

/**
 * The Class CommandRegistry.
 *
 * @param <T> the type of any executor object to be carried
 */
public class CommandRegistry<T> {
    private static final Pattern pattern = Pattern.compile("([^\"]\\S*|\".+?\")|([^\\']\\\\S*|\\'.+?\\')\\s*");

    private MultiValuedMap<String, ExecutionContext<T>> contexts = new HashSetValuedHashMap<>();
    private Map<Class<?>, Resolver<?>> resolvers = new HashMap<>();

    public CommandRegistry() {
        this.addResolver(new BooleanResolver(), Boolean.class, boolean.class);
        this.addResolver(new ByteResolver(), Byte.class, byte.class);
        this.addResolver(new CharResolver(), Character.class, char.class);
        this.addResolver(new DoubleResolver(), Double.class, double.class);
        this.addResolver(new FloatResolver(), Float.class, float.class);
        this.addResolver(new IntegerResolver(), Integer.class, int.class);
        this.addResolver(new LongResolver(), Long.class, long.class);
        this.addResolver(new ShortResolver(), Short.class, short.class);
        this.addResolver(new StringResolver(), String.class);
    }

    /**
     * Adds a command listener. Listeners must be annotated with {@link Command}.
     *
     * @param listener the listener
     */
    public void addCommand(@NonNull CommandListener<T> listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Type[] parameters = method.getParameterTypes();

                if ((parameters.length == 1) && (parameters[0].equals(CommandEvent.class))) {
                    Command annotation = method.getAnnotationsByType(Command.class)[0];
                    ExecutionContext<T> context = new ExecutionContext<T>(listener, annotation, method);

                    for (String alias : annotation.aliases()) {
                        String id = annotation.owner().toLowerCase() + ":" + alias.toLowerCase();

                        this.contexts.put(id, context);
                    }

                    String id = annotation.owner().toLowerCase() + ":" + annotation.name().toLowerCase();

                    this.contexts.put(annotation.name().toLowerCase(), context);
                    this.contexts.put(id, context);
                }
            }
        }
    }

    /**
     * Gets all registered commands.
     *
     * @return the commands
     */
    public Collection<Command> getCommands() {
        Set<Command> commands = new HashSet<>();

        for (ExecutionContext<T> context : this.contexts.values()) {
            commands.add(context.command);
        }

        return commands;
    }

    /**
     * Execute.
     *
     * @param input the input
     * @param executor the executor
     * @throws CommandNameException thrown if a command can't be found for the given input
     * @throws CommandExecutionException thrown if the command generates an exception
     * @throws ArgumentsLengthException thrown if the input's arguments are less than the required amount
     */
    public void execute(@NonNull String input, T executor) throws CommandNameException, CommandExecutionException, ArgumentsLengthException {
        input = input.trim();

        List<String> args = new ArrayList<>();
        Matcher m = pattern.matcher(input);

        // Use a matcher to split input by space, and allowing spaces in arguments via quotes
        while (m.find()) {
            args.add(m.group(1).trim().replace("\"", ""));
        }

        String id = args.get(0).toLowerCase();
        Collection<ExecutionContext<T>> possible = this.contexts.get(id);

        args.remove(0); // Remove the id

        if (possible.size() != 0) {
            ExecutionContext<T> context = possible.iterator().next();

            if (context.command.minimumArguments() <= args.size()) {
                CommandEvent<T> event = new CommandEvent<>(this, Collections.unmodifiableList(args), executor);

                context.execute(event);
            } else {
                throw new ArgumentsLengthException();
            }
        } else {
            throw new CommandNameException();
        }
    }

    /**
     * Adds a resolver.
     *
     * @param <E> the element type
     * @param resolver the resolver
     * @param clazzes the clazzes to register under
     */
    @SafeVarargs
    public final <E> void addResolver(@NonNull Resolver<E> resolver, @NonNull Class<E>... clazzes) {
        for (Class<E> clazz : clazzes) {
            this.resolvers.put(clazz, resolver);
        }
    }

    /**
     * Resolves a value.
     *
     * @param <E> the element type
     * @param value the value
     * @param clazz the clazz
     * @return the element
     * @throws IllegalArgumentException to be thrown if the value cannot be parsed
     */
    @SuppressWarnings("unchecked")
    public <E> E resolve(@NonNull String value, @NonNull Class<E> clazz) {
        Resolver<?> resolver = this.resolvers.get(clazz);

        if (resolver != null) {
            return (E) resolver.resolve(value);
        } else {
            throw new IllegalArgumentException("No resolver is available for type " + clazz.getSimpleName());
        }
    }

    @AllArgsConstructor
    private static class ExecutionContext<T> {
        private final CommandListener<T> instance;
        private final Command command;
        private final Method method;

        public void execute(CommandEvent<T> event) throws CommandExecutionException {
            try {
                this.method.invoke(this.instance, event);
            } catch (Exception e) {
                throw new CommandExecutionException(e);
            }
        }
    }

}
