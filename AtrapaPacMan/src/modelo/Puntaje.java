package modelo;

import java.io.Serializable;

public class Puntaje implements Serializable {

	public final static int NIVELES = 3;
	public final static int LISTADO = 10;
	private String[][] nombres;
	private int[][] puntajes;

	public Puntaje() {
		nombres = new String[NIVELES][LISTADO];
		puntajes = new int[NIVELES][LISTADO];
		for (int j = 0; j < puntajes.length; j++) {
			for (int i = 0; i < puntajes[j].length; i++) {
				puntajes[j][i] = Integer.MAX_VALUE;
				nombres[j][i] = "";
			}
		}
	}

	public boolean agregarPuntaje(int niv, String n, int p) {
		boolean agrego = false;
		String nTemp = "";
		int pTemp = 0;
		int i = 0;
		while (i < LISTADO && !agrego) {
			if (puntajes[niv][i] > p) {
				nTemp = nombres[niv][i];
				pTemp = puntajes[niv][i];
				nombres[niv][i] = n;
				puntajes[niv][i] = p;
				agrego = true;
			}
			i++;
		}

		if (agrego) {
			for (int j = LISTADO - 1; j > i; j--) {
				nombres[niv][j] = nombres[niv][j - 1];
				puntajes[niv][j] = puntajes[niv][j - 1];
			}
			nombres[niv][i] = nTemp;
			puntajes[niv][i] = pTemp;
		}
		return agrego;
	}

	public String darPuntajes() {
		String reporte = "";
		for (int nivel = 0; nivel < nombres.length; nivel++) {
			String titulo = String.format("%-23s", "Nivel " + nivel);
			reporte += titulo;
		}
		reporte += "\n";
		for (int i = 0; i < LISTADO; i++) {
			for (int nivel = 0; nivel < nombres.length; nivel++) {
				if (!nombres[nivel][i].equals("")) {
					String nombre = String.format("%-10s", nombres[nivel][i]);
					String puntaje = String.format("%-10s", puntajes[nivel][i]);
					reporte += nombre + " " + puntaje + "";
				}
			}
			reporte += "\n";
		}
		return reporte;
	}

	public boolean entreLosMejores(int niv, int p) {
		boolean entreMejores = false;
		if (p < puntajes[niv][LISTADO - 1]) {
			entreMejores = true;
		}
		return entreMejores;
	}
}
