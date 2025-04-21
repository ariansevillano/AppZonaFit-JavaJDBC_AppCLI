package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConnexion;

public class ClienteDAO implements IClienteDAO {

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnexion();
        String sql = "SELECT * FROM cliente ORDER BY id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        } catch (Exception e){
            System.out.println("Error al listar clientes: "+ e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnexion();
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId()); // esto lo que hace es indicar que cliente.getid va a llenarse en el "?", se pone 1 pq puede adminitr mas parametros
            rs = ps.executeQuery();

            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        } catch (Exception e){
            System.out.println("Error al recuperar cliente por id: " +e.getMessage());
        } finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar la conexión: " +e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        //considerar que result set csolo se usa cuando se RECUPERA informaición y aquí se va a ingresar
        PreparedStatement ps;
        Connection con = getConnexion();
        String sql = "INSERT INTO cliente (nombre, apellido, membresia) "
                + "VALUES (?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;
        } catch (Exception e){
            System.out.println("Error al ingresar el cliente: " +e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: ");
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConnexion();
        String sql = "UPDATE cliente SET nombre=? , apellido=?, membresia=? "
                + " WHERE id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.setInt(4,cliente.getId());
            ps.execute();
            return  true;
        } catch (Exception e) {
            System.out.println("Error al modificar al cliente: " +e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar la conexión: ");
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {

        PreparedStatement ps;
        Connection con = getConnexion();
        String sql = "DELETE FROM cliente " +
                "WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("No se logró borrar al cliente: " +e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Erro al cerrar la conexión: "+e.getMessage());
            }
        }

        return false;
    }

    public static void main(String[] args) {
        /*System.out.println("Listar clientes: ");
        IClienteDAO clienteDao = new ClienteDAO();

        List<Cliente> clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);
        */

        //Buscar por id
        /*IClienteDAO clienteDao = new ClienteDAO();
        Cliente cliente1 = new Cliente(6);
        System.out.println("Cliente antes de la búsqueda: "+ cliente1);
        boolean encontrado = clienteDao.buscarClientePorId(cliente1);
        if (encontrado){
            System.out.println("Cliente encontrado: " + cliente1);
        } else {
            System.out.println("No se encontró cliente: " + cliente1.getId());
        }*/

        IClienteDAO clienteDao = new ClienteDAO();

        /*Cliente nuevoCliente = new Cliente("Arian", "Colina", 500);
        System.out.println("Cliente antes de insertar: " + nuevoCliente);
        boolean agregado = clienteDao.agregarCliente(nuevoCliente);
        if (agregado) {
            System.out.println("Cliente agregado: " + nuevoCliente);
        } else {
            System.out.println("No se agregó el cliente: "+ nuevoCliente);
        }*/

        /*Cliente clienteModificado = new Cliente(3,"Julio", "Saca", 900);
        System.out.println("Cliente antes de mofiicar: " + clienteModificado);
        boolean modificado = clienteDao.modificarCliente(clienteModificado);
        if (modificado) {
            System.out.println("Cliente Modificado: " +clienteModificado);
        } else {
            System.out.println("No se modificó al cliente: "+clienteModificado);
        }*/

        Cliente clienteEliminado = new Cliente(3);
        System.out.println("Cliente antes de ser eliminado: "+clienteEliminado);
        boolean eliminado = clienteDao.eliminarCliente(clienteEliminado);
        if (eliminado){
            System.out.println("Cliente eliminado: " +clienteEliminado);
        } else {
            System.out.println("No se pudo eliminar al cliente: "+clienteEliminado);
        }
        System.out.println("Listar clientes");
        List<Cliente> clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);
    }
}
