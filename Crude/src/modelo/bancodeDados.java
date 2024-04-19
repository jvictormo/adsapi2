package modelo;


//Declara quais os valores do banco de dados
public class bancodeDados {
	int rm;
	String nome;
	char sexo;
	
	//Faz os métodos getters e setters, único obrigatorio são aqueles que vão ser usados pelo banco de dados para buscar dados, no caso o rm
	
	public int getRm() {
		return rm;
	}
	public void setRm(int rm) {
		this.rm = rm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	

}
