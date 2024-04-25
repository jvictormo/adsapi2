package langchain;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.Scanner;
import static java.time.Duration.ofSeconds;

public class ModelParameters {

    public static void main(String[] args) {

        // OpenAI parameters are explained here: https://platform.openai.com/docs/api-reference/chat/create

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey("lm-studio")
                .baseUrl("http://localhost:2000/v1")
                .modelName("TheBloke/nsql-llama-2-7B-GGUF/nsql-llama-2-7b.Q8_0.gguf")
                .temperature(0.7)
                .timeout(ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Solicitar entrada do usuário
            System.out.print("Digite uma mensagem para a IA: ");
            String prompt = scanner.nextLine();

            // Gerar resposta da IA com base na entrada do usuário
            String response = model.generate(prompt);

            // Imprimir a resposta da IA
            System.out.println("Resposta da IA: " + response);
        }
    }
}
