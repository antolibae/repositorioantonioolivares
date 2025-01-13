import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private final static Scanner sc = new Scanner(System.in);
    /*
     * Método principal
     * Modificación método principal
     */
    public static void main(String[] args) throws Exception {
        Functions.UsarBD();
        int opc=-1;
        do {
            menu();
            opc = sc.nextInt();
            switch(opc){
                case 1:
                    menuCreacion();
                    opc = sc.nextInt();
                    switch (opc) {
                        case 1:
                            if(Functions.CrearPlayer()){
                                System.out.println("Tabla creada correctamente");
                            }else{
                                System.out.println("Error al crear la tabla");
                            }
                            break;
                        case 2:
                            if(Functions.CrearGames()){
                                System.out.println("Tabla creada correctamente");
                            }else{
                                System.out.println("Error al crear la tabla");
                            }
                            break;
                        case 3 :
                            if(Functions.CrearCompras()){
                                System.out.println("Tabla creada correctamente");
                            }else{
                                System.out.println("Error al crear la tabla, posiblemente falten las demas tablas");
                            }
                            break;
                        case 4 :
                            if (Functions.CrearPlayer() &&
                                Functions.CrearGames()&&
                                Functions.CrearCompras()){
                                System.out.println("Tablas creadas correctamente");
                            }else{
                                System.out.println("Error al crear las tablas");
                            }
                            break;
                    }
                    break;
                case 2:
                    menuTablas();
                    opc = sc.nextInt();
                    switch (opc) {
                        case 1:
                            sc.nextLine();
                            menuJugadores();
                            break;
                        case 2:
                            sc.nextLine();
                            menuJuegos();
                            break;
                        case 3:
                            sc.nextLine();
                            menuCompras();
                            break;
                    }
                    break;
                case 3:
                    menuTablas();
                    opc = sc.nextInt();
                    switch (opc) {
                        case 1:
                            if(Functions.EliminarTabla(opc)){
                                System.out.println("Datos insertados correctamente");
                            }else{
                                System.out.println("Error al insertar los datos");
                            }
                            break;
                        case 2:
                            if(Functions.EliminarTabla(opc)){
                                System.out.println("Datos insertados correctamente");
                            }else{
                                System.out.println("Error al insertar los datos");
                            }
                            break;
                        case 3:
                            if(Functions.EliminarTabla(opc)){
                                System.out.println("Datos insertados correctamente");
                            }else{
                                System.out.println("Error al insertar los datos");
                            }
                            break;
                    }
                    break;
                case 4:
                    menuTablas();
                    opc = sc.nextInt();
                    switch (opc) {
                        case 1:
                            sc.nextLine();
                            System.out.println("Inserte el id del jugador: ");
                            int jugador = sc.nextInt();
                            Functions.EliminarPlayers(jugador);
                            break;
                        case 2:
                            sc.nextLine();
                            System.out.println("Inserte el id del juego: ");
                            int juego = sc.nextInt();
                            Functions.EliminarGames(juego);
                            break;
                        case 3:
                            sc.nextLine();
                            System.out.println("Inserte el id de la compra: ");
                            int compra = sc.nextInt();
                            Functions.EliminarCompras(compra);
                            break;
                    }
                    break;
                case 5:
                    menuTablas();
                    opc = sc.nextInt();
                    
                    switch (opc) {
                        case 1:
                            sc.nextLine();
                            Functions.ListarPlayers();
                            break;
                        case 2:
                            sc.nextLine();
                            Functions.ListarJuegos();
                            break;
                        case 3:
                            sc.nextLine();
                            Functions.ListarCompras();
                            break;
                        default:
                            break;
                    }
                    break;
                case 6:
                    menuTablas();
                    opc = sc.nextInt();                 
                    switch (opc) {
                        case 1 :
                        menuEditarJugadores();
                            break;
                        case 2 : 
                        menuEditarJuegos();
                            break;
                        case 3 :
                        menuEditarCompras();
                            break;
                    }
                    break;
                case 0 :
                    System.out.println("Saliendo...");
                    break;
                default: 
                System.out.println("Opcion no valida"); 
                break;
            }
        } while (opc != 0);
    }

    private static void menu() {
        System.out.println("Menu");
        System.out.println("1. Agregar");
        System.out.println("2. Insertar");
        System.out.println("3. Eliminar tabla");
        System.out.println("4. Eliminar dato");
        System.out.println("5. Mostrar");
        System.out.println("6. Editar");
        System.out.println("0. Salir");
    }

    private static void menuTablas() {
        System.out.println("Elija:");
        System.out.println("1. Jugadores");
        System.out.println("2. Juegos");
        System.out.println("3. Compras");
        System.out.println("0. Salir");
    }

    private static void menuCreacion() {
        System.out.println("Elija:");
        System.out.println("1. Jugadores");
        System.out.println("2. Juegos");
        System.out.println("3. Compras");
        System.out.println("4. Todas");
        System.out.println("0. Salir");
    }

    private static void menuJuegos() {
        System.out.println("Inserte el nombre del videojuego: ");
        String nombre = sc.nextLine();
        System.out.println("Inserte el tiempo jugado (hh:mm): ");
        String tiempo = sc.nextLine();
        try {
            if(Functions.InsertGames(nombre,tiempo)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(IOException | SQLException e){
            System.out.println("Error en la base de datos");
        }
        
    }

    private static void menuJugadores() {
        System.out.println("Inserte el nombre del jugador: ");
        String nombre = sc.nextLine();
        System.out.println("Inserte la contraseña del jugador: ");
        String password = sc.nextLine();
        System.out.println("Inserte el email del jugador: ");
        String email = sc.nextLine();
        try {
            if(Functions.InsertPlayers(nombre,password,email)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void menuCompras() {
        System.out.println("Inserte el nickname del jugador: ");
        String nick = sc.nextLine();
        System.out.println("Inserte el nombre del juego: ");
        String nGame = sc.nextLine();
        System.out.println("Inserte la cosa comprada: ");
        String cosa = sc.nextLine();
        System.out.println("Inserte el precio de la compra: ");
        double precio = sc.nextDouble();
        System.out.println("Inserte la fecha de la compra: ");
        String fecha = sc.nextLine();
        try {
            if(Functions.InsertCompras(nick,nGame,cosa,precio,fecha)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void menuEditarCompras() {
        System.out.println("Inserte el id de la compra: ");
        int id = sc.nextInt();
        System.out.println("Inserte la cosa comprada: ");
        String cosa = sc.nextLine();
        System.out.println("Inserte el precio de la compra: ");
        double precio = sc.nextDouble();
        System.out.println("Inserte la fecha de la compra: ");
        String fecha = sc.nextLine();
        try {
            if(Functions.EditarCompra(id,cosa,fecha,precio)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void menuEditarJugadores() {
        System.out.println("Inserte el id del jugador: ");
        int id = sc.nextInt();
        System.out.println("Inserte el nombre del jugador: ");
        String nombre = sc.nextLine();
        System.out.println("Inserte la contraseña del jugador: ");
        String password = sc.nextLine();
        System.out.println("Inserte el email del jugador: ");
        String email = sc.nextLine();
        try {
            if(Functions.EditarPlayer(id,nombre,password,email)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void menuEditarJuegos() {
        System.out.println("Inserte el id del juego: ");
        int id = sc.nextInt();
        System.out.println("Inserte el nombre del videojuego: ");
        String nombre = sc.nextLine();
        System.out.println("Inserte el tiempo jugado (hh:mm): ");
        String tiempo = sc.nextLine();
        try {
            if(Functions.EditarGame(id,nombre,tiempo)){
                System.out.println("Realizado correctamente");
            }else{
                System.out.println("Error en la consulta");
            }
        }catch(Exception e){
            System.out.println("Error en la base de datos");
        }
    }
}