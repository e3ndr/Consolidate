package xyz.e3ndr.consolidate;

import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.command.CommandListener;

public class Example implements CommandListener<Void> {

    public static void main(String[] args) throws Exception {
        CommandRegistry<Void> registry = new CommandRegistry<>();

        registry.addCommand(new Example());

        registry.execute("test a", null);
        registry.execute("_default_:test \"a\"", null);

        System.out.println(registry.getCommands());

        registry.execute("this command doesnt exist", null);
    }

    @Command(description = "A test command!", name = "Test")
    public void test(CommandEvent<Void> event) {
        System.out.println(event);
    }

    @Command(description = "Another test command!", name = "Test2")
    public void test2(CommandEvent<Void> event) {
        System.out.println(event);
    }

}
