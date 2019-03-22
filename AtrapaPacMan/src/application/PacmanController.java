package application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import hilo.HiloPacman;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Juego;
import modelo.Pacman;

public class PacmanController {
	@FXML
	private MenuItem iniciar;
	@FXML
	private MenuItem reiniciar;
	@FXML
	private MenuItem salir;
	@FXML
	private MenuItem cargar;
	@FXML
	private MenuItem guardar;
	@FXML
	private MenuItem puntajes;
	@FXML
	private MenuItem informacion;
	@FXML
	private Label rebotes;
	@FXML
	private Pane panel;
	@FXML
	private MenuBar menu;

	private ArrayList<Pacman> pacmans = new ArrayList<Pacman>();
	private Arc[] pms;

	private Juego pm = new Juego();

	public void Iniciar(ActionEvent e) {
//		FileChooser FC = new FileChooser();
//		FC.setInitialDirectory(new File("./data"));
//		FC.setInitialFileName("./data/nivel1.txt");
//		FC.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "nivel1.txt"));
//
//		File f = FC.showOpenDialog(null);

		File f = new File("./data/nivel1.txt");
		if (f != null) {
			try {
				pm.cargarJuego(f);
				crearPacman(pm.darPacmans());
				crearHilos();

			} catch (IOException io) {
				// TODO: handle exception

			}
		}

	}

	public double darAnchoPanel() {
		double ancho = 0;
		ancho = panel.getWidth();
		return ancho;
	}

	public double darAltoPanel() {

		double alto = 0;
		alto = panel.getHeight() - menu.getHeight();

		return alto;

	}

	public void organizar() {
//		pacman1.setLayoutX(20);
//		pacman1.setLayoutY(280);
//		pacman1.setVisible(true);
//		pacman1.setRadiusX(20);
//		pacman1.setRadiusY(20);
//		pacman1.setRotate(0);
//
//		pacman2.setLayoutX(250);
//		pacman2.setLayoutY(50);
//		pacman2.setVisible(true);
//		pacman2.setRadiusX(20);
//		pacman2.setRadiusY(20);
//		pacman2.setRotate(90);
//
//		pacman3.setLayoutX(480);
//		pacman3.setLayoutY(280);
//		pacman3.setVisible(true);
//		pacman3.setRadiusX(20);
//		pacman3.setRadiusY(20);
//		pacman3.setRotate(180);
//
//		pacman4.setLayoutX(250);
//		pacman4.setLayoutY(510);
//		pacman4.setVisible(true);
//		pacman4.setRadiusX(20);
//		pacman4.setRadiusY(20);
//		pacman4.setRotate(270);

	}

	public void Reiniciar(ActionEvent e) {
		Iniciar(e);

	}

	public void Salir(ActionEvent e) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Desea guardar el juego antes de salir?");
		if (opcion == JOptionPane.YES_OPTION) {
			Guardar(e);
			System.exit(0);

		} else if (opcion == JOptionPane.YES_NO_CANCEL_OPTION) {
			System.exit(0);
		} else {

		}

	}

	public void Cargar(ActionEvent e) {
		FileChooser FC = new FileChooser();
		FC.setInitialDirectory(new File("./data"));
		FC.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.txt"));

		File f = FC.showOpenDialog(null);
		if (f != null) {
			try {
				pm.cargarJuego(f);
				crearPacman(pm.darPacmans());
				crearHilos();
				JOptionPane.showMessageDialog(null, "Juego cargado con exito", null, JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException io) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null,
						"Problemas leyendo el archivo\\nEs probable que el formato no sea válido.",
						"No se puede cargar.", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void crearHilos() {
		for (int i = 0; i < pm.darPacmans().size(); i++) {
			HiloPacman hilo = new HiloPacman(pm.darPacmans().get(i), this);
			hilo.start();
		}

	}

	public void Guardar(ActionEvent e) {
		FileChooser FC = new FileChooser();
		FC.setInitialDirectory(new File("./data"));
//		FC.getExtensionFilters().addAll(new ExtensionFilter("TXT Files", "*.txt"));
		FC.setInitialFileName(
				"JuegoGuardado_" + (new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date()) + ".txt"));
		File file = FC.showSaveDialog(null);
		if (file != null) {
			try {
				pm.guardarJuego(file);

				JOptionPane.showMessageDialog(null, "Juego guardado con exito", null, JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception io) {

				JOptionPane.showMessageDialog(null,
						"Problemas guardando el archivo\nEs probable que no tenga permisos de escritura o\nel archivo puede estar bloqueado por otro programa.",
						"No se puede guardar.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void Puntajes(ActionEvent e) {
		String puntajes;
		puntajes = pm.darPuntajes();

		JOptionPane.showMessageDialog(null, puntajes, "Mejores puntajes:", JOptionPane.INFORMATION_MESSAGE);

	}

	public void Informacion(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Juego hecho por: Juan Pablo Sanchez.", "Informacion del juego.",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void Atrapado(MouseEvent e) {

	}

	public void crearPacman(ArrayList<Pacman> pc) {
		pacmans = pc;
		pms = new Arc[pacmans.size()];
		for (int i = 0; i < pacmans.size(); i++) {

			pms[i] = new Arc();
			pms[i].setLayoutX(pacmans.get(i).darPosX());

			pms[i].setLayoutY(pacmans.get(i).darPosY());

			pms[i].setRadiusX(pacmans.get(i).darDiametro());

			pms[i].setRadiusY(pacmans.get(i).darDiametro());
			pms[i].setVisible(true);
			panel.getChildren().add(pms[i]);

		}

	}

	public void mostrarRebotes() {
		rebotes.setText(pm.calcularTotalRebotes() + "");
	}

}
