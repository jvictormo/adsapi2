package langchain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChatController {
    private ChatLanguageModel model;
    private String modelName;

    public ChatController(String initialModelName) {
        this.modelName = initialModelName;
        initializeModel();
    }

    private void initializeModel() {
        String baseUrl = "http://localhost:11434/";
        this.model = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
        initializeModel();
    }

    public String generateResponse(String prompt) {
        return model.generate(prompt);
    }
}