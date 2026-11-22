package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexionBD {
	private static final String URL = "jdbc:mariadb://localhost:3307/gestion_notas_universidad?sslMode=disable";
	private static final String USUARIO = "root";
	private static final String CONTRASENA = "";
	
	public static Connection conectar() throws SQLException {
		return DriverManager.getConnection(URL,USUARIO,CONTRASENA);
	}
	

}
