package useful;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLine {
	public void servidor(String command) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cmd", "/c", String.format("lms server %s", command));
		try {
			Process process = builder.start();
			int exitCode = process.waitFor();
		} catch (IOException | InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void model(String command, String model) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("cmd", "/c", String.format("lms %s %s", command, model));
		try {
            // Iniciar o processo
            Process process = builder.start();

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