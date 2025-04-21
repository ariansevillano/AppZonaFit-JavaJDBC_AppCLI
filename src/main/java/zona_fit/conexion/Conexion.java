package zona_fit.conexion;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class Conexion {

    public static Connection getConnexion() {
        Connection conexion = null;
        String baseDatos = "zona_fit_bd";
        String url = "jdbc:mysql://localhost:3306/"+ baseDatos; //esto se llama cadena de conexión
        String usuario = "root";
        String password = "12345678";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //clase de conexión a la base de datos (Driver)
            conexion = DriverManager.getConnection(url,usuario,password);
        } catch (Exception e){
            System.out.println("Error al conectarse a la BD: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        Connection conexion = Conexion.getConnexion();

        if (conexion != null){
            System.out.println("Conexión exitososa " + conexion);
        } else {
            System.out.println("Error al conectarse");
        }
    }


}
