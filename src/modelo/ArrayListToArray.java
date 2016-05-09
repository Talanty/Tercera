package modelo;

import java.util.ArrayList;

public class ArrayListToArray {

	public ArrayListToArray() {
	}
	
	public String[][] creaString(ArrayList<ArrayList<String>> tabla) {
		String[][] arrayRegistros = new String[tabla.size() - 1][tabla.get(0).size()];
		for (int i = 0; i < arrayRegistros.length; i++) {
			for (int j = 0; j < arrayRegistros[i].length; j++) {
				arrayRegistros[i][j] = tabla.get(i + 1).get(j);
			}
		}
		return arrayRegistros;
	}
}
