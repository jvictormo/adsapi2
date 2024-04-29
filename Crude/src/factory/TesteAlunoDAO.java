package factory;

import dao.AlunoDAO;
import modelo.bancodeDados;

public class TesteAlunoDAO {
	public static void main(String[] args) {
		//Cria objeto na classe banco de dados
		bancodeDados aluno = new bancodeDados();
		
		//Seleciona o rm que sera usado na busca
		aluno.setRm(2);
		
		AlunoDAO alunoDAO = new AlunoDAO();
		
		//Executa a parte do código le da classe AlunoDAO
		alunoDAO.le(aluno);
	}

}
