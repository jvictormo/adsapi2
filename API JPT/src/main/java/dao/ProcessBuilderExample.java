package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderExample {

    public static void main(String[] args) {
        // Comando que gera a barra de progresso
        String command = "lms load TheBloke/sqlcoder2-GGUF/sqlcoder2.Q2_K.gguf";

        // Criar o ProcessBuilder com o comando
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

        try {
            // Iniciar o processo
            Process process = processBuilder.start();

            // Lidar com a saída padrão (stdout)
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // Lidar com a saída de erro (stderr), onde a barra de progresso é geralmente escrita
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Thread para ler a saída padrão
            new Thread(() -> {
                String line;
                try {
                    while ((line = stdOutput.readLine()) != null) {
                        System.out.println("Output: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Thread para ler a saída de erro
            new Thread(() -> {
                String line;
                try {
                    while ((line = stdError.readLine()) != null) {
                        System.out.println("Error: " + line); // Pode substituir por System.err.println se preferir
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Aguardar a conclusão do processo
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

