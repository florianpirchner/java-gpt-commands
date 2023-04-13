# GPT-4 Java Command Generator

This project utilizes GPT-4 to generate commands that are defined in Java, based on user input. GPT-4 determines the most suitable command and returns the corresponding Java command.

To create classifiers that GPT-4 can efficiently process internally, a brief description of the command, consisting of 3-4 words, is sufficient. However, to aid GPT-4 in the decision-making process, the hints can be made as complex as desired.

The implementation has been meticulously crafted to offer optimal default configurations for Java developers, while simultaneously allowing the flexibility to substitute or override these with custom solutions as needed.

To ensure seamless extensibility, we have declared attributes as protected wherever it is logical to do so. Furthermore, our approach predominantly relies on interface-based references to promote adaptability.

We encourage the Java developer community to unleash their creativity by building their own frameworks based on this foundation.

Your valuable contributions through pull requests are always appreciated and welcomed.

## Ask your friends

To tell GPT-4, that there is a choice to select "Ask your friends". It is that simple.

```java
public class AskYourFriendsCommand extends AbstractCommand {

	public static final String NAME = "ASK_YOUR_FRIENDS";
	public static final String HINT = "Ask your friends";

	public AskYourFriendsCommand() {
		super(NAME, HINT);
	}

}
```

## CommandApi
The CommandApi is your entry point to the functionality. It is highly configurable.

```
// create a new intance with the GPT-4 OPENAI_KEY
CommandApi api = new CommandApi(OPENAI_KEY);

// you need to register all commands, GPT-4 should be aware of.
api.setCommands(myCommands);

// and now you can query for commands
ICommand command = api.queryCommand("Info about quantum physics");

// if ask the doctor, then schedule meeting
if(command.getName().equals(AskYourDoctorCommand.NAME)) {
	prepareMail("doctor@doctor.com");
// if ask the web, then open the Chrome browser and pass the user question
} else if(command.getName().equals(WebSearchCommand.NAME)) {
	openChromeNow("Info about quantum physics");
}
```

This code is like a helpful robot that can understand the semantics of your input. First, it connects to a smart computer system called GPT-4 using the OpenAI Bearer Token (simply called OpenAI Key). Then, it tells GPT-4 all the tasks it needs to know, which are called "commands".

Next, it asks GPT-4 for help with a specific task, like finding "Info about quantum physics." GPT-4 will tell the robot which command to use for this task.

If GPT-4 says the best way to get this information is to "Ask Your Doctor," the robot will prepare an email to send to a doctor's email address. But if GPT-4 says the best way is to search the internet, the robot will open the Chrome browser and search for "Info about quantum physics" online.

**Attention:**
You can utilize this framework to enable a user to write emails or open a browser. In case you have successfully implemented these features, I would be more than happy to accept a pull request from you. 

Please note that the existing framework is merely a basic implementation and the commands are exclusively designed for unit testing purposes.


## Current CommandApi UnitTests

This example demonstrates how to use the framework to find for the best command. We decided going with JUnit, since it is a well known framework to java developers.

The test is designed to ensure that the correct command is executed based on the input query.

```java
import org.junit.Test;
//... other imports

public class CommandTest {

    @Test
    public void testCommandExecution() {
        CommandApi api = new CommandApi(OPENAI_KEY);
        
        // Register commands at the API
		// AskYourBossCommand
		// AskYourFriendsCommand
		// AskYourDoctorCommand
		// WebSearchCommand
        api.setCommands(this::createCommands);

        // Test different queries and their expected commands
	
	// Need more money --> Ask your boss
        testCommand(api, "Need more money", AskYourBossCommand.NAME);
	
	// Info about quantum physics --> Ask the Web
        testCommand(api, "Info about quantum physics.", WebSearchCommand.NAME);
	
	// I need some one to talk --> Ask your friends
        testCommand(api, "I need some one to talk.", AskYourFriendsCommand.NAME);
	
	// My friend is physicist. I need some info about quantum physics --> Ask your friends
        testCommand(api, "My friend is physicist. I need some info about quantum physics.", AskYourFriendsCommand.NAME);
	
	// My head feels hot --> Ask your doctor
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


## The magic about prompting

For Java developers who have never ventured into the realm of GPT (Generative Pre-trained Transformer), it might seem like sorcery at first glance. One might ponder about the complexity and the thousands of lines of code required to implement such logic.

As Java enthusiasts, we are accustomed to crafting our logic in Java. However, GPT has heralded a new age of programming. Programming GPT is no longer referred to as "programming" but rather as "prompting".

In the world of GPT, we employ our natural language to communicate with the transformer. Our goal is to provide GPT with the bare minimum of information. Feeding GPT just the ABSOLUTE ESSENTIALS is crucial as providing excessive information acts as a constraint, limiting GPT's ability to harness its high abstraction capabilities.

When it comes to prompting (GPT programming), less is indeed more!

Above, you saw a unit test that demonstrates how minimal prompting was utilized to implement this project using GPT-4. Achieving the same results with GPT-3.5 would have required a substantially greater prompting effort. 

Embrace the magic of GPT and witness the wonders it can do for your Java projects!

**These few lines of natural language prompting, do all the magic, to let the UnitTests you found above become green:**

Its a static text. Except the commands are inserted dynamicly by the registered commands.

```
You are a Command Engine. 
Your primary responsibility is to deliver the best matching {COMMAND} from a list of COMMANDS as a response.

To determine the most appropriate response, you will receive a text. 
Based on this text, you should select the best matching {COMMAND} and respond with it.

List of available COMMANDS:
- {ASK_YOUR_BOSS} - Ask your boss
- {ASK_YOUR_DOCTOR} - Ask your doctor
- {ASK_YOUR_FRIENDS} - Ask your friends
- {WEB_SEARCH} - Ask the web

Please note that you are not allowed to ask any follow-up questions, 
and you must only provide the {COMMAND} as your response.
```

**Thats it. 14 lines of natural language to do all the magic!**

## How to build with Maven

In the terminal, navigate to the project directory and run the following command:

```
mvn clean install -DOPENAI_KEY={YOUR_KEY}
```


This will build the project using Maven and create a `target` folder containing the generated artifacts.
