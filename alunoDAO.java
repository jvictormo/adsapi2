package alunoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import factory.ConnectionFactory;
import Modelo.bancodeDados;

public class AlunoDAO {
	private Connection connection;
	
	public AlunoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}


    public void lerAtributosAlunos() {
        String sql = "SELECT * FROM Alunos";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String dataNascimento = rs.getString("data_nascimento");
                System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email + ", Data de Nascimento: " + dataNascimento);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void lerAtributosCursos() {
        String sql = "SELECT * FROM Cursos";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                System.out.println("ID: " + id + ", Nome do Curso: " + nome + ", Descrição: " + descricao);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void lerAtributosTurmas() {
        String sql = "SELECT * FROM Turmas";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int cursoId = rs.getInt("curso_id");
                String semestre = rs.getString("semestre");
                System.out.println("ID: " + id + ", Curso ID: " + cursoId + ", Semestre: " + semestre);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
