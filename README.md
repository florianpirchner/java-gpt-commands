# GPT-4 Java Command Generator

This project utilizes GPT-4 to generate commands that are defined in Java, based on user input. GPT-4 determines the most suitable command and returns the corresponding Java command.

If you are not familiar with GPT, read the subsections "[Magic about prompting"](#the_magic_about_prompting) and ["Putting GPT-4 into a cage"](#putting_gpt-4_into_a_cage) in this document first. (Links do not work right now. Just scroll down to "Magic about prompting".)

To create classifiers that GPT-4 can efficiently process internally, a brief description of the command, consisting of 3-4 words, is sufficient. However, to aid GPT-4 in the decision-making process, the hints can be made as complex as desired.

The implementation has been meticulously crafted to offer optimal default configurations for Java developers, while simultaneously allowing the flexibility to substitute or override these with custom solutions as needed.

To ensure seamless extensibility, we have declared attributes as protected wherever it is logical to do so. Furthermore, our approach predominantly relies on interface-based references to promote adaptability.

We encourage the Java developer community to unleash their creativity by building their own frameworks based on this foundation.

Your valuable contributions through pull requests are always appreciated and welcomed.

## Ask your friends

It's as easy as selecting "Ask your friends" to inform GPT-4 of the available option. 

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
The CommandApi serves as your gateway to the available features and is easily customizable

```
// create a new instance with the GPT-4 OPENAI_KEY
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

**The simple prompt in natural language following has the ability to work wonders and turn the UnitTests you discovered earlier into a green state:**

_The text itself is static, but the commands are dynamically inserted by registered commands._

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

**That's all it takes - 14 lines of natural language to work wonders!**

See https://github.com/florianpirchner/java-gpt-commands/blob/main/src/main/java/org/lunifera/gpt/commands/api/IPrompter.java as the default system-prompt.

## Putting GPT-4 into a cage
As previously discussed, it is advisable to provide GPT-4 with minimal information. The more data you supply, the more you establish the context in which GPT can function. Each input on GPT serves as a constraint, restricting GPT's capacity to generalize.

A discerning reader of the prompt in "[magic about prompting"](#the_magic_about_prompting) may have observed a contradiction at this point.

I initially asserted that "less is more" when it comes to prompting.

It seems that I am contradicting myself. On one hand, I argue that GPT-4 should not be swayed by overly potent prompts.

Yet in the subsequent system prompt, I outline specific objectives:
- you are a Command Engine
- your primary duty is to generate the most suitable {COMMAND} from a list of COMMANDS as a response
- be aware that you are not permitted to ask any follow-up questions
- and your response must solely consist of the {COMMAND}

By doing this, I am restricting GPT-4's autonomy. I am imposing well-defined constraints on GPT-4. Essentially, I am confining GPT-4 within a cage.

But here's the caveat!

Nevertheless, I did not inhibit GPT-4 from utilizing its generalization capabilities to identify the optimal variation of the commands.

I merely confined GPT-4 within a cage. This cage delineates the boundaries in which GPT-4 is allowed to operate.

In human terms, this would be analogous to hypnosis—a strikingly similar concept.


## How to build with Maven

In the terminal, navigate to the project directory and run the following command:

```
mvn clean install -DOPENAI_KEY={YOUR_KEY}
```


This will build the project using Maven and create a `target` folder containing the generated artifacts.
