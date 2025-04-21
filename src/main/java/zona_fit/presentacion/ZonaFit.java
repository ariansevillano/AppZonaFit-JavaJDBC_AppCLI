package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.List;
import java.util.Scanner;

public class ZonaFit {
    public static void main(String[] args) {
        zonaFit();
    }

    private static void zonaFit() {
        boolean salir = false;
        Scanner consola = new Scanner(System.in);

        //creamos un objeto de la clase ClienteDao

        IClienteDAO clienteDao = new ClienteDAO();

        while (!salir) {
            try {
                int opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(opcion, consola, clienteDao);
            }catch (Exception e) {
                System.out.println();
            }
            System.out.println();
        }

    }
    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                ***Zona Fit (GYM)***
                1. Listar Clientes
                2. Buscar Clientes
                3. Agregar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Elige una opción: """);
            int opcion = Integer.parseInt(consola.nextLine());
            return  opcion;
    }


    private static  boolean ejecutarOpciones(int opcion, Scanner consola, IClienteDAO clienteDao){
        boolean salir = false;

        switch (opcion) {
            case 1 -> { // 1. Listar clientes
                System.out.println("---Listado de clientes---");
                List clientes = clienteDao.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> { //2. Buscar cliente por id
                System.out.print("Introduce el id del cliente a buscar: ");
                int idCliente = Integer.parseInt(consola.nextLine());
                Cliente cliente = new Cliente(idCliente);

                boolean encontrado = clienteDao.buscarClientePorId(cliente);

                if (encontrado) {
                    System.out.println("Cliente encontrado: "+cliente);
                } else {
                    System.out.println("Cliente no encontrado: "+cliente);
                }
            }
            case 3 -> {//3. Agregar cliente:
                System.out.println("---Agregar cliente---");
                System.out.print("Nombre: ");
                String nombre = consola.nextLine();
                System.out.println("\nApellido: ");
                String apellido = consola.nextLine();
                System.out.println("\nMembresía: ");
                int membresia = Integer.parseInt(consola.nextLine());

                //creamos el objeto cliente pero sin el id

                Cliente cliente = new Cliente(nombre, apellido, membresia);
                boolean agregado = clienteDao.agregarCliente(cliente);

                if (agregado) {
                    System.out.println("Cliente agregado: "+cliente);
                } else {
                    System.out.println("Error al agregar al cliente: "+cliente);
                }
            }
            case 4 -> { //4. Modificar cliente
                System.out.println("---Modificar cliente---");
                System.out.print("Id del cliente a modificar: ");
                int id = Integer.parseInt(consola.nextLine());
                System.out.print("Nuevo Nombre del cliente: ");
                String nombre = consola.nextLine();
                System.out.print("\nNuevo Apellido del cliente: ");
                String apellido = consola.nextLine();
                System.out.print("\nNueva Membresía del cliente: ");
                int membresia = Integer.parseInt(consola.nextLine());

                //creamos el objeto a modificar
                Cliente cliente = new Cliente(id, nombre, apellido, membresia);
                boolean modificado = clienteDao.modificarCliente(cliente);
                if (modificado) {
                    System.out.println("Cliente mofidicado: "+cliente);
                } else {
                    System.out.println("Error al modificar al cliente: "+cliente);
                }
            }

            case 5 -> {//5. Eliminar cliente
                System.out.println("Eliminar cliente");
                System.out.print("Ingrese el id del cliente que desea eliminar: ");
                int id = Integer.parseInt(consola.nextLine());

                //Creamos el objeto a eliminar
                Cliente cliente = new Cliente(id);
                boolean eliminado = clienteDao.eliminarCliente(cliente);
                if (eliminado) {
                    System.out.println("Cliente eliminado: "+cliente);
                } else {
                    System.out.println("Error al eliminar al cliente: "+cliente);
                }
            }

            case 6 -> {
                System.out.println("Vuelve pronto a ZONA FIT GYM!!!");
                salir = true;
            }

            default -> System.out.println("Opción inválida: "+opcion +", intente nuevamente\n");
        }

        return salir    ;
    }
}
