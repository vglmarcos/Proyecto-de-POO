import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    private Connection con;
    private String url;

    public Conexion() {
        url = "jdbc:ucanaccess://database/Silica.accdb";
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection(url);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Connection getConexion() {
        return this.con;
    }

    public void desconectar() {
        try {
            con.close();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}