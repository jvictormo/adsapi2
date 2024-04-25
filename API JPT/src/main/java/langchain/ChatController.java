package langchain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

public class ChatController {
    private final ChatLanguageModel model;

    public ChatController() {
        // Inicialize o modelo de idioma aqui
        this.model = OpenAiChatModel.builder()
                .apiKey("lm-studio")
                .baseUrl("http://localhost:2000/v1")
                .modelName("TheBloke/nsql-llama-2-7B-GGUF/nsql-llama-2-7b.Q8_0.gguf")
                .temperature(0.7)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    public String generateResponse(String prompt) {
        // Gere a resposta da IA com base na entrada do usuário
        return model.generate(prompt);
    }
}
