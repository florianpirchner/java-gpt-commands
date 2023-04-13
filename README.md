# GPT-4 Java Command Generator

This project utilizes GPT-4 to generate commands that are defined in Java, based on user input. GPT-4 determines the most suitable command and returns the corresponding Java command.

To create classifiers that GPT-4 can efficiently process internally, a brief description of the command, consisting of 3-4 words, is sufficient. However, to aid GPT-4 in the decision-making process, these hints can be made as complex as desired.

## Sample Command

```java
public class AskYourFriendsCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_FRIENDS";
	public static final String HINT = "Ask your friends";

	public AskYourFriendsCommand() {
		super(NAME, HINT);
	}

}
```

## Usage Example

```java
import org.junit.Test;
//... other imports

public class CommandTest {

	@Test
	public void test() {
		CommandApi api = new CommandApi(OPENAI_KEY);
		api.setCommands(this::createCommands);

		// Ask your boss
		ICommand command = api.queryCommand("Need more money");
		assertEquals(AskYourBossCommand.NAME, command.getName());
		
		// Do web query
		command = api.queryCommand("Info about quantum physics.");
		assertEquals(WebSearchCommand.NAME, command.getName());
		
		// As friends
		command = api.queryCommand("I need some one to talk.");
		assertEquals(AskYourFriendsCommand.NAME, command.getName());
		
		// Also ask friends
		command = api.queryCommand("My friend is physicist. I need some info about quantum physics.");
		assertEquals(AskYourFriendsCommand.NAME, command.getName());
		
		// Ask you doctor
		command = api.queryCommand("My head feels hot");
		assertEquals(AskYourDoctorCommand.NAME, command.getName());
	}

	/**
	 * Create the list of commands.
	 * @return
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

## How to build with Maven

In the terminal, navigate to the project directory and run the following command:

```
mvn clean install -DOPENAI_KEY={YOUR_KEY}
```


This will build the project using Maven and create a `target` folder containing the generated artifacts.
