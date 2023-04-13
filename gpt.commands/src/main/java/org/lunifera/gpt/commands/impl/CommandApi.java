/**
 * 
 */
package org.lunifera.gpt.commands.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.lunifera.gpt.commands.api.ICommand;
import org.lunifera.gpt.commands.api.ICommandApi;
import org.lunifera.gpt.commands.api.IPrompter;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

/**
 * 
 */
public class CommandApi implements ICommandApi {

	private static final String GPT_4 = "gpt-4";

	private final String OPENAI_KEY;

	private IPrompter prompter = new Prompter();
	private Supplier<List<? extends ICommand>> commands;

	public CommandApi(String OPENAI_KEY) {
		this.OPENAI_KEY = OPENAI_KEY;
	}

	public void setPrompter(IPrompter prompter) {
		this.prompter = prompter;
	}

	/**
	 * Returns the {@link IPrompter}.
	 * 
	 * @return
	 */
	public IPrompter getPrompter() {
		return prompter;
	}

	public void setCommands(Supplier<List<? extends ICommand>> commands) {
		this.commands = commands;
	}

	/**
	 * Returns the supplier of commands.
	 * 
	 * @return
	 */
	public Supplier<List<? extends ICommand>> getCommands() {
		return commands;
	}

	@Override
	public ICommand queryCommand(String userInput) {
		List<ChatMessage> messages = createMessages(userInput);

		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model(GPT_4).messages(messages)
				.n(1).temperature(0d).maxTokens(50).build();

		OpenAiService service = new OpenAiService(OPENAI_KEY);
		List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
		String commandText = choices.get(0).getMessage().getContent();

		return prompter.findCommand(commandText, commands);
	}

	protected List<ChatMessage> createMessages(String userInput) {
		List<ChatMessage> messages = new ArrayList<>();
		ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompter.getSystemPrompt(commands));
		messages.add(systemMessage);
		ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), userInput);
		messages.add(userMessage);
		return messages;
	}

}
