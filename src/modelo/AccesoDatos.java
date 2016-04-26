package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class AccesoDatos {
	private Connection con;

	private String host = "localhost";
	private String bd = "information_schema";
	private String usuario = "root";
	private String clave = "";

	public void getConexion() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_schema", usuario, clave);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// System.out.println("Conexion establecida con la bd " + bd);
	}

	public ArrayList<String> getSchemas() {
		ArrayList<String> listaBD = new ArrayList<String>();
		this.getConexion();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select schema_name from information_schema.schemata where schema_name not in ('mysql', 'information_schema', ' performance_schema')");
			String registro;
			while (rs.next()) {
				registro = rs.getString(1);
				// System.out.println(registro);
				listaBD.add(registro);
			}
		} catch (SQLException e) {
			System.out.println("Error SQL" + e.getMessage());
		}
		return listaBD;
	}

	public ArrayList<String> getTablesSchemas(String db) {
		ArrayList<String> listaTablas = new ArrayList<String>();
		this.getConexion();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select table_name from tables where table_schema = '" + db + "'");
			String registro;
			while (rs.next()) {
				registro = rs.getString("table_name");
				// System.out.println(registro);
				listaTablas.add(registro);
			}
		} catch (SQLException e) {
			System.out.println("Error SQL" + e.getMessage());
		}
		return listaTablas;
	}

	public String[][] getRegistrosTablaBD(String db, String tabla) {

		this.getConexion();
		String[][] registros = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + db + "." + tabla);
			// obtenemos numFilas y numColumnas de rs

			ResultSetMetaData md = rs.getMetaData();
			int numColumnas = md.getColumnCount();
			rs.last(); // ponemos el puntero del cursor al final
			int numFilas = rs.getRow();
			registros = new String[numFilas + 1][numColumnas];
			// rs.first(); // volvemos a poner el cursor en el principio del
			// ResultSet.
			// Ponemos los nombres de las columnas
			for (int j = 1; j <= numColumnas; j++) {
				registros[0][j - 1] = md.getColumnName(j);
				// System.out.print(md.getColumnName(j) + ",");
			}
			// System.out.println("");
			// iteramos en los datos
			int i = 1;
			rs.beforeFirst();
			while (rs.next()) {
				for (int j = 1; j <= numColumnas; j++) {
					registros[i][j - 1] = rs.getString(j);
					// System.out.print(rs.getString(j) + ",");
				}
				// System.out.println("");
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error SQL" + e.getMessage());
		}
		return registros;
	}

	// Método alternativo
	// Obtiene los datos del ResultSet (Jose David)
	// Usa además el método creaString de la claseArrayListToArray
	// para convertirlo a String [][]
	public ArrayList<ArrayList<String>> getRegistrosTablaBD1(String tabla, String bd) {
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		this.getConexion();
		try {
			Statement sentencia = con.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + bd + "." + tabla);
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			while (rs.next()) {
				ArrayList<String> registro = new ArrayList<>();
				ArrayList<String> nombreCol = new ArrayList<>();
				for (int i = 1; i <= col; i++) {
					if (lista.size() == 0) {
						nombreCol.add((rsmd.getColumnName(i)));
					}
					registro.add(rs.getString(i));
				}
				if (lista.size() == 0) {
					lista.add(nombreCol);
				}
				lista.add(registro);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	public DefaultTableModel getRegistrosTablaBD_DTM(String tabla, String bd) {
		this.getConexion();

		try {
			Statement sentencia = con.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM " + bd + "." + tabla);
			ResultSetMetaData rsmd = rs.getMetaData();

			// nombres de las columnas
			Vector<String> nombreColumnas = new Vector<String>();
			int numCols = rsmd.getColumnCount();
			for (int col = 1; col <= numCols; col++) {
				nombreColumnas.add(rsmd.getColumnName(col));
			}
			// datos de la tabla
			Vector<Vector<Object>> datos = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int col = 1; col <= numCols; col++) {
					vector.add(rs.getObject(col));
				}
				datos.add(vector);
			}
			return new DefaultTableModel(datos, nombreColumnas);
		} catch (SQLException e) {
				System.out.println("Ooops, sql!");
		}
		return null;
	}
}
