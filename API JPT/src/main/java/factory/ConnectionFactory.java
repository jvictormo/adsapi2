 package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import useful.ConfigLoader;


//Realiza a conexão com o banco de dados
public class ConnectionFactory {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public ConnectionFactory() {
    	ConfigLoader configLoader = new ConfigLoader();
        this.dbUrl = configLoader.getProperty("db.url");
        this.dbUser = configLoader.getProperty("db.user");
        this.dbPassword = configLoader.getProperty("db.password");
    }
    
 public Connection getConnection() {
	 try {
		 //Indica qual banco de dados conectar, trocar SEUBANCODEDADOS pelo nome do banco, USUÁRIO trocar pelo usuário do MySQL (padrão root),
		 //senha trocar por senha Mysql
		 return DriverManager.getConnection(dbUrl,dbUser,dbPassword);
	 }
	 catch(SQLException excecao) {
		 throw new RuntimeException(excecao);
	 }
 }
 
 //metodo adicional para realizar a conexão a um banco específico
 public Connection conexao(String banco) {
	 try {
		 String link = String.format("jdbc:mysql://localhost/%s", banco);
		 return DriverManager.getConnection(link,dbUser,dbPassword);
	 }
	 catch(SQLException excecao) {
		 throw new RuntimeException(excecao);
	 }
 }
}