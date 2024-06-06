package langchain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChatController {
    private final ChatLanguageModel model;

    public ChatController() {
		String MODEL_NAME = "duckdb-nsql";
        // Inicialize o modelo de idioma aqui
    	 String baseUrl = "http://localhost:11434/";

         // Crie o modelo de chat com a configuração desejada
    	 this.model = OllamaChatModel.builder()
                 .baseUrl(baseUrl)
                 .modelName(MODEL_NAME)
                 .build();
    }

    public String generateResponse(String prompt) {
        // Gere a resposta da IA com base na entrada do usuário
        return model.generate(prompt);
    }
}
