package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Juego {
	public final static String RUTA_PUNTAJE = "./data/top10.dat";

	private ArrayList<Pacman> pacmans;
	private Puntaje puntaje;
	private int nivel;
	private BufferedReader lector;

	public Juego() {
		pacmans = new ArrayList<>();
		puntaje = new Puntaje();
		nivel = 0;
		cargarPuntaje();
	}

	public void cargarPuntaje() {

		File archivo = new File(RUTA_PUNTAJE);
		try {
			//
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(archivo));
			puntaje = (Puntaje) os.readObject();
			os.close();

		} catch (Exception e) {

		}
	}

	public void guardarPuntaje() {

		try {
			ObjectOutputStream ios = new ObjectOutputStream(new FileOutputStream(RUTA_PUNTAJE));
			ios.writeObject(puntaje);
			ios.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void cargarJuego(File archivo) throws IOException {
		// Cargar el juego.

		pacmans.clear();
		String texto, direccion;
		int posX, posY, diametro, rebotes, espera;

		try {

			lector = new BufferedReader(new FileReader(archivo));
			texto = lector.readLine();

		} catch (IOException e) {
			throw new IOException("Error al cargar los datos del juego");
		}

		if (texto.startsWith("#nivel")) {
			nivel = Integer.parseInt(lector.readLine());
		}

		while (texto != null) {

			if (!texto.startsWith("#")) {

				String valores[] = texto.split("\t");
				boolean atrapado = false;

				if (valores[6].equals("true")) {
					atrapado = true;
				}

				diametro = Integer.parseInt(valores[0]);
				posX = Integer.parseInt(valores[1]);
				posY = Integer.parseInt(valores[2]);
				espera = Integer.parseInt(valores[3]);
				direccion = valores[4];
				rebotes = Integer.parseInt(valores[5]);

				Pacman nuevoBalon = new Pacman(diametro, posX, posY, espera, direccion, rebotes, atrapado);
				pacmans.add(nuevoBalon);
			}

			texto = lector.readLine();

		}
		System.out.println("Archivo cargado");

	}

	public void guardarJuego(File f) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		PrintWriter guardar = new PrintWriter(writer);

		guardar.print("#nivel");
		guardar.println();
		guardar.print(nivel);
		guardar.println();
		guardar.print("#radio posX posY espera direccion rebotes");
		guardar.println();
		for (int i = 0; i < pacmans.size(); i++) {
			Pacman balon = pacmans.get(i);
			guardar.print(
					balon.darDiametro() + "\t" + balon.darPosX() + "\t" + balon.darPosY() + "\t" + balon.darEspera()
							+ "\t" + balon.getDireccion() + "\t" + balon.darRebotes() + "\t" + balon.haSidoAtrapado());
			guardar.println();
		}

		guardar.close();

	}

	public ArrayList<Pacman> darPacmans() {
		return pacmans;
	}

	public Puntaje darPuntaje() {
		return puntaje;
	}

	public int calcularTotalRebotes() {
		int totalReb = 0;
		for (Pacman b : pacmans) {
			totalReb += b.darRebotes();
		}
		return totalReb;
	}

	public void atrapar(int x, int y) {
		for (int i = 0; i < pacmans.size(); i++) {
			Pacman b = pacmans.get(i);
			if (b.atrapado(x, y)) {
				b.cambiarAtrapado(true);
			}
		}
	}

	public boolean juegoTerminado() {
		boolean atrapados = true;
		for (int i = 0; i < pacmans.size() && atrapados; i++) {
			Pacman b = pacmans.get(i);
			if (!b.haSidoAtrapado()) {
				atrapados = false;
			}
		}
		return atrapados;
	}

	public boolean hayQueGuardarPuntaje() {
		int totalReb = calcularTotalRebotes();
		boolean hayQueGuardar = puntaje.entreLosMejores(nivel, totalReb);
		return hayQueGuardar;
	}

	public void guardarPuntajeActual(String nom) {
		puntaje.agregarPuntaje(nivel, nom, calcularTotalRebotes());
	}

	public String darPuntajes() {
		String reportePuntaje;
		reportePuntaje = puntaje.darPuntajes();
		return reportePuntaje;
	}

}
