package xyz.e3ndr.consolidate;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

public class CommandRegistry<T> {
    private static final Pattern pattern = Pattern.compile("([^\"]\\S*|\".+?\")|([^\\']\\\\S*|\\'.+?\\')\\s*");

    private MultiValuedMap<String, ExecutionContext<T>> contexts = new HashSetValuedHashMap<>();

    public void addCommand(@NonNull CommandListener<T> listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Type[] parameters = method.getParameterTypes();

                if ((parameters.length == 1) && (parameters[0].equals(CommandEvent.class))) {
                    Command annotation = method.getAnnotationsByType(Command.class)[0];
                    ExecutionContext<T> context = new ExecutionContext<T>(listener, annotation, method);
                    String id = annotation.owner().toLowerCase() + ":" + annotation.name().toLowerCase();

                    this.contexts.put(annotation.name().toLowerCase(), context);
                    this.contexts.put(id, context);
                }
            }
        }
    }

    public Collection<Command> getCommands() {
        Set<Command> commands = new HashSet<>();

        for (ExecutionContext<T> context : this.contexts.values()) {
            commands.add(context.command);
        }

        return commands;
    }

    public void execute(@NonNull String input) throws CommandNameException, CommandExecutionException, ArgumentsLengthException {
        this.execute(input, null);
    }

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
                CommandEvent<T> event = new CommandEvent<>(args.toArray(new String[0]), executor);

                context.execute(event);
            } else {
                throw new ArgumentsLengthException();
            }
        } else {
            throw new CommandNameException();
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
