package hilo;

import application.PacmanController;
import modelo.Pacman;

public class HiloPacman extends Thread implements Runnable {
	private PacmanController pm;
	private Pacman pm1;

	public HiloPacman(Pacman pm1, PacmanController pm) {
		this.pm1 = pm1;
		this.pm = pm;
	}

	public void run() {
		while (!pm1.haSidoAtrapado()) {
			pm1.mover((int) pm.darAnchoPanel(), (int) pm.darAltoPanel());
			pm.mostrarRebotes();

			try {
				Thread.sleep(pm1.darEspera());
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
