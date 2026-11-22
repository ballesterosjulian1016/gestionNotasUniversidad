package baseDatos;

public class pruebaConexion {

	public static void main(String[] args) {

		try {
			conexionBD.conectar();
			System.out.println("conecion correcta");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
