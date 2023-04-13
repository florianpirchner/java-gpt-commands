# GPT-4 Java Command Generator

This project utilizes GPT-4 to generate commands that are defined in Java, based on user input. GPT-4 determines the most suitable command and returns the corresponding Java command.

To create classifiers that GPT-4 can efficiently process internally, a brief description of the command, consisting of 3-4 words, is sufficient. However, to aid GPT-4 in the decision-making process, these hints can be made as complex as desired.

The implementation has been meticulously crafted to offer optimal default configurations for Java developers, while simultaneously allowing the flexibility to substitute or override these with custom solutions as needed.

To ensure seamless extensibility, we have declared attributes as protected wherever it is logical to do so. Furthermore, our approach predominantly relies on interface-based references to promote adaptability.

We encourage the Java developer community to unleash their creativity by building their own frameworks based on this foundation.

Your valuable contributions through pull requests are always appreciated and welcomed.

## Sample Command

In the system prompt this command will be represented as ```- {ASK_YOUR_FRIENDS} - Ask your friends```

```java
public class AskYourFriendsCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_FRIENDS";
	public static final String HINT = "Ask your friends";

	public AskYourFriendsCommand() {
		super(NAME, HINT);
	}

}
```

## Command Test Example

This example demonstrates how to create a command test using Java. The test is designed to ensure that the correct command is executed based on the input query.

```java
import org.junit.Test;
//... other imports

public class CommandTest {

    @Test
    public void testCommandExecution() {
        CommandApi api = new CommandApi(OPENAI_KEY);
        
        // Register commands at the API
        api.setCommands(this::createCommands);

        // Test different queries and their expected commands
        testCommand(api, "Need more money", AskYourBossCommand.NAME);
        testCommand(api, "Info about quantum physics.", WebSearchCommand.NAME);
        testCommand(api, "I need some one to talk.", AskYourFriendsCommand.NAME);
        testCommand(api, "My friend is physicist. I need some info about quantum physics.", AskYourFriendsCommand.NAME);
        testCommand(api, "My head feels hot", AskYourDoctorCommand.NAME);
    }

    private void testCommand(CommandApi api, String query, String expectedCommandName) {
        ICommand command = api.queryCommand(query);
        assertEquals(expectedCommandName, command.getName());
    }

    /**
     * Creates and returns a list of commands.
     */
    private List<ICommand> createCommands() {
        List<ICommand> commands = new ArrayList<>();
        commands.add(new AskYourBossCommand());
        commands.add(new AskYourFriendsCommand());
        commands.add(new AskYourDoctorCommand());
        commands.add(new WebSearchCommand());
        return commands;
    }
}
```

This test class has two main components:

1. `testCommandExecution()`: This method sets up the `CommandApi` and tests different queries to ensure the correct command is being executed.
2. `createCommands()`: This method creates and returns a list of commands that will be registered at the `CommandApi`.




## How to build with Maven

In the terminal, navigate to the project directory and run the following command:

```
mvn clean install -DOPENAI_KEY={YOUR_KEY}
```


This will build the project using Maven and create a `target` folder containing the generated artifacts.
