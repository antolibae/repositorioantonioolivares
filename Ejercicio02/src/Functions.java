import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Functions {
	//#region Conexiones
	public static Connection Conectar() throws ClassNotFoundException, SQLException{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_rromera?user=rromera&password=12345";
			Connection connect = DriverManager.getConnection(connectionUrl);
			return connect;
	}

	public static void UsarBD() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Statement stmt = null;
		
		conn = Conectar();
		stmt = conn.createStatement();
		String sql = "USE ad2425_rromera";
		stmt.execute(sql);
	}
//#endregion
	//#region Crear Tablas
	public static boolean CrearPlayer() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try {
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Players("
					+ "idPlayer INT NOT NULL AUTO_INCREMENT,"
					+ "nick VARCHAR(45),"
					+ "password VARCHAR(128),"
					+ "email VARCHAR(100),"
					+ "PRIMARY KEY (idPlayer)"
					+ ")";
			stmt.executeUpdate(sql);
			creado = true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
	public static boolean CrearGames() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try {
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS Games("
			+ "idGame INT NOT NULL AUTO_INCREMENT,"
			+ "nombre VARCHAR(45),"
			+ "tiempoJugado TIME,"
			+ "PRIMARY KEY (idGame)"
			+ ")";
			stmt.executeUpdate(sql);
			creado = true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
	public static boolean CrearCompras() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		boolean creado1 = false;
		boolean creado2 = false;
		try {
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "SELECT IF(EXISTS (" +
								"SELECT 1\n" +
								"FROM INFORMATION_SCHEMA.TABLES\n" +
								"WHERE TABLE_SCHEMA = 'ad2425_rromera'\n" +
								"AND TABLE_NAME = 'Games'" +
								"), true, false)";
			creado1 = stmt.execute(sql);

			sql = "SELECT IF(EXISTS (" +
			"SELECT 1\n" +
			"FROM INFORMATION_SCHEMA.TABLES\n" +
			"WHERE TABLE_SCHEMA = 'ad2425_rromera'\n" +
			"AND TABLE_NAME = 'Players'" +
			"), true, false)";
			creado2 = stmt.execute(sql);

			if(creado1 && creado2){
				sql = "CREATE TABLE IF NOT EXISTS Compras("
				+ "idCompra INT NOT NULL AUTO_INCREMENT,"
				+ "idPlayer INT,"
				+ "idGame INT,"
				+ "cosa VARCHAR(25),"
				+ "precio DECIMAL(6,2),"
				+ "fechaCompra DATE,"
				+ "PRIMARY KEY (idCompra),"
				+ "FOREIGN KEY (idPlayer) REFERENCES Players(idPlayer),"
				+ "FOREIGN KEY (idGame) REFERENCES Games(idGame)"
				+ ")";
				stmt.executeUpdate(sql);
				creado = true;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
//#endregion
	//#region Inserts
	public static boolean InsertCompras(String nick, String ngame, String cosa, double precio, String fecha) throws SQLException, IOException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			ResultSet rs = null;
			stmt = conn.createStatement();
			String sql = "SELECT idPlayer FROM Players WHERE nick = '"+nick+"';";
			rs = stmt.executeQuery(sql);
			rs.next();
			int idPlayer = rs.getInt(1);
			sql = "SELECT idGame FROM Games WHERE nombre = '"+ngame+"';";
			rs = stmt.executeQuery(sql);
			rs.next();
			int idGame = rs.getInt(1);
			sql = "INSERT INTO Compras (idCompra, idPlayer, idGame, cosa, precio, fechaCompra) VALUES (0, "+idPlayer+", "+idGame+", '"+cosa+"', "+precio+", '"+fecha+"');";
			stmt.executeUpdate(sql);

			creado = true;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}

	public static boolean InsertGames(String nombre, String tiempo) throws SQLException, IOException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO Games (idGame, nombre,tiempoJugado) VALUES (0, '"+nombre+"','"+tiempo+"');";
			stmt.executeUpdate(sql);
			
			creado = true;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
			
	}

	public static boolean InsertPlayers(String nick, String password, String email) throws SQLException, IOException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			stmt = conn.createStatement();

			String sql = "INSERT INTO Players (idPlayer, nick, password, email) VALUES (0, '"+nick+"','"+password+"','"+email+"');";
			stmt.executeUpdate(sql);

			creado = true;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
//#endregion
	//#region Eliminar Datos
	public static boolean EliminarCompras(int id) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		Scanner sc = new Scanner(System.in);
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "BEGIN TRANSACTION; DELETE FROM Compras WHERE idCompra = "+id+";";
			stmt.executeUpdate(sql);
			System.out.println("Deseas eliminar la compra?");
			String resp = sc.next();
			if(resp.equalsIgnoreCase("s")){
				sql = "COMMIT TRANSACTION;";
				stmt.executeUpdate(sql);
				creado = true;
			}else{
				sql = "ROLLBACK;";
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sc.close();
			stmt.close();
			conn.close();
		}
		return creado;
	}
	public static boolean EliminarPlayers(int id) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		Scanner sc = new Scanner(System.in);
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "BEGIN TRANSACTION; DELETE FROM Players WHERE idCompra = " + id +";";
			stmt.executeUpdate(sql);
			System.out.println("Deseas eliminar el jugador?");
			String resp = sc.next();
			if(resp.equalsIgnoreCase("s")){
				sql = "COMMIT TRANSACTION;";
				stmt.executeUpdate(sql);
				creado = true;
			}else{
				sql = "ROLLBACK;";
			}

		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sc.close();
			stmt.close();
			conn.close();
		}
		return creado;
	}

	public static boolean EliminarGames(int id) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		Scanner sc = new Scanner(System.in);
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "BEGIN TRANSACTION; DELETE FROM Games WHERE idCompra = "+ id +";";
			stmt.executeUpdate(sql);
			System.out.println("Deseas eliminar el juego?");
			String resp = sc.next();
			if(resp.equalsIgnoreCase("s")){
				sql = "COMMIT TRANSACTION;";
				stmt.executeUpdate(sql);
				creado = true;
			}else{
				sql = "ROLLBACK;";
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sc.close();
			stmt.close();
			conn.close();
		}
		return creado;
	}
//#endregion
	//#region Eliminar Tablas
	public static boolean EliminarTabla(int num) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		Scanner sc = new Scanner(System.in);
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql="";
			switch (num) {
				case 1:
					sql="BEGIN TRANSACTION; DROP TABLE Players;";
					break;
				case 2:
					sql="BEGIN TRANSACTION; DROP TABLE Games;";
					break;
				case 3:
					sql="BEGIN TRANSACTION; DROP TABLE Compras;";
					break;
				default:
					break;
			}
			stmt.executeUpdate(sql);
			System.out.println("Deseas eliminar la tabla seleccionada?");
			String resp = sc.next();
			if(resp.equalsIgnoreCase("s")){
				sql = "COMMIT TRANSACTION;";
				stmt.executeUpdate(sql);
				creado = true;
			}else{
				sql = "ROLLBACK;";
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sc.close();
			stmt.close();
			conn.close();
		}
		return creado;
	}
//#endregion
	//#region Listar Datos
	public static void ListarPlayers() throws SQLException{
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n=== Listar Datos de Jugadores ===");
        System.out.print("¿Desea aplicar filtros? (s/n): ");
        String aplicarFiltro = scanner.nextLine();

        String query = "SELECT * FROM Players";
        if (aplicarFiltro.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el campo por el cual filtrar: ");
            String campo = scanner.nextLine();
            System.out.print("Ingrese el operador (=, <, >, LIKE): ");
            String operador = scanner.nextLine();
            System.out.print("Ingrese el valor para el filtro: ");
            String valor = scanner.nextLine();

            if (operador.equalsIgnoreCase("LIKE")) {
                valor = "%'" + valor + "'%";
            } else {
                valor = "'" + valor + "'";
            }

            query += " WHERE " + campo + " " + operador + " " + valor;
        }

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }finally{
			conn.close();
		}
	}

	public static void ListarJuegos() throws SQLException{
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n=== Listar Datos de Juegos ===");
        System.out.print("¿Desea aplicar filtros? (s/n): ");
        String aplicarFiltro = scanner.nextLine();

        String query = "SELECT * FROM Games";
        if (aplicarFiltro.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el campo por el cual filtrar: ");
            String campo = scanner.nextLine();
            System.out.print("Ingrese el operador (=, <, >, LIKE): ");
            String operador = scanner.nextLine();
            System.out.print("Ingrese el valor para el filtro: ");
            String valor = scanner.nextLine();

            if (operador.equalsIgnoreCase("LIKE")) {
                valor = "%'" + valor + "'%";
            } else {
                valor = "'" + valor + "'";
            }

            query += " WHERE " + campo + " " + operador + " " + valor;
        }

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }finally{
			conn.close();
		}
	}

	public static void ListarCompras() throws SQLException{
		Connection conn = null;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n=== Listar Datos de Compras ===");
        System.out.print("¿Desea aplicar filtros? (s/n): ");
        String aplicarFiltro = scanner.next();

        String query = "SELECT * FROM Compras";
        if (aplicarFiltro.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el campo por el cual filtrar: ");
            String campo = scanner.nextLine();
            System.out.print("Ingrese el operador (=, <, >, LIKE): ");
            String operador = scanner.nextLine();
            System.out.print("Ingrese el valor para el filtro: ");
            String valor = scanner.nextLine();

            if (operador.equalsIgnoreCase("LIKE")) {
                valor = "%'" + valor + "'%";
            } else {
                valor = "'" + valor + "'";
            }

            query += " WHERE " + campo + " " + operador + " " + valor;
        }

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error al listar datos: " + e.getMessage());
        }finally{
			conn.close();
		}
	}
//#endregion
	//#region Editar Datos
	public static boolean EditarPlayer(int id, String nick, String password, String email) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "UPDATE Players SET nick = '"+nick+"', password = '"+password+"', email = '"+email+"' WHERE idPlayer = "+id+";";
			stmt.executeUpdate(sql);
			creado = true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}

	public static boolean EditarGame(int id, String name, String tiempo) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "UPDATE Games SET nombre = '"+name+"', tiempoJugado = '"+tiempo+"' WHERE idGame = "+id+";";
			stmt.executeUpdate(sql);
			creado = true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
	public static boolean EditarCompra(int id, String cosa, String fecha, double precio) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		boolean creado = false;
		try{
			conn = Conectar();
			stmt = conn.createStatement();
			String sql = "UPDATE Compras SET cosa = '"+cosa+"', fechaCompra = '"+fecha+"', precio = '"+precio+"' WHERE idCompra = "+id+";";
			stmt.executeUpdate(sql);
			creado = true;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			stmt.close();
			conn.close();
		}
		return creado;
	}
//#endregion
}