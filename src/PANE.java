
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class PANE extends JPanel implements MouseListener {
	// Schrittweiten
	// varX = 57 + 40*3; 
	// varY = 37 + 37;
	BufferedImage image;
	boolean zug;
	SPIEL spiel;
	int[] deltaX = { 177, 177, 96, 136, 177, 177, 216, 256, 176 };
	int[] deltaY = { 74, 114, 155, 155, 195, 236, 155, 155, 157 };
	int[] zeichnen = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
	int[] SdeltaX = { 57, 57, 57, 57, 97, 97, 137, 137, 217, 217, 257, 257, 297, 297, 297, 297 };
	int[] SdeltaY = { 75, 115, 195, 236, 35, 275, 35, 275, 35, 275, 35, 275, 75, 114, 194, 235 };
	boolean[] Szeichnen = { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true };
	boolean wartemodus;
	int globalZ;
	boolean w;
	boolean gameover;
	boolean hilfe = false;
	int save2;

	public PANE(SPIEL spiel) {
		this.spiel = spiel;
		try {
			image = ImageIO.read(this.getClass().getResource("images/" + "Krone.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		addMouseListener(this);

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(48, 27, 284, 280);
		g.setColor(Color.black);

		//waagrechte Linien
		int varX = 26;
		for (int x = 0; x < 8; x++) {
			g.fillRect(22 + varX, 22 + 8, 5, 280);
			varX += 40;
		}

		//senkrechte Linien
		int varY = 4;
		for (int x = 0; x < 8; x++) {
			g.fillRect(getX() + 48, getY() + varY, 285, 5);
			varY += 40;
		}

		//malen der weißen Spielfiguren
		for (int x = 0; x < 8; x++) {
			if (zeichnen[x] == 1) {
				g.setColor(Color.white);
				g.fillOval(deltaX[x], deltaY[x], 28, 28);
				g.setColor(Color.black);
				g.drawOval(deltaX[x], deltaY[x], 28, 28);
			}
		}
		
		//malen der schwarzen Spielfiguren
		for (int x = 0; x < 16; x++) {
			if (Szeichnen[x] == true) {
				g.fillOval(SdeltaX[x], SdeltaY[x], 28, 28);

			}
		}
		//König zeichnen
		if (zeichnen[8] == 2) {
			g.drawImage(image, deltaX[8], deltaY[8], null);
		}

		//Bei Spielende GameOverScreen
		if (gameover) {
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 65));
			g.setColor(Color.blue);
			g.drawString("GAME OVER!", spiel.getWidth() / 2 - 194, spiel.getHeight() / 5);

			g.setColor(Color.green);
			if (w) {
				g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
				g.drawString("Weiß hat gewonnen!", spiel.getWidth() / 2 - 194, spiel.getHeight() / 3);
			} else {
				g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
				g.drawString("Schwarz hat gewonnen!", spiel.getWidth() / 2 - 194, spiel.getHeight() / 3);
			}
		}
		
//		Hilfsmodus
		if (hilfe) {
			if (wartemodus) {
				//
				//// g.setColor(Color.GREEN);
				//// g.fillOval(0, 0, 28, 28);
				//
				if (zug == false) {
					boolean drw = false;
					boolean xneg = false;
					boolean wag = false;
					boolean weg = false;
					for (int mo = 0; mo < 7; mo++) {
						for (int i = 0; i < 9; i++) {
							if (deltaX[globalZ] + 40 * mo < deltaX[i] && deltaX[globalZ] + 50 * (mo + 1) > deltaX[i]
									&& deltaY[globalZ] + 20 > deltaY[i] && deltaY[globalZ] - 20 < deltaY[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								drw = true;
							}
							if (deltaX[globalZ] - 40 * mo > deltaX[i] && deltaX[globalZ] - 50 * (mo + 1) < deltaX[i]
									&& deltaY[globalZ] + 20 > deltaY[i] && deltaY[globalZ] - 20 < deltaY[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								xneg = true;
							}
							if (deltaY[globalZ] + 40 * mo < deltaY[i] && deltaY[globalZ] + 50 * (mo + 1) > deltaY[i]
									&& deltaX[globalZ] + 20 > deltaX[i] && deltaX[globalZ] - 20 < deltaX[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								wag = true;
							}
							if (deltaY[globalZ] - 40 * mo > deltaY[i] && deltaY[globalZ] - 50 * (mo + 1) < deltaY[i]
									&& deltaX[globalZ] + 20 > deltaX[i] && deltaX[globalZ] - 20 < deltaX[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								weg = true;
							}
						}
						for (int i = 0; i < 16; i++) {
							if (drw == false && deltaX[globalZ] + 40 * mo < SdeltaX[i]
									&& deltaX[globalZ] + 46 * (mo + 1) > SdeltaX[i] && deltaY[globalZ] + 20 > SdeltaY[i]
									&& deltaY[globalZ] - 20 < SdeltaY[i] && Szeichnen[i]) {
								drw = true;
							}
							if (deltaX[globalZ] - 40 * mo > SdeltaX[i] && deltaX[globalZ] - 50 * (mo + 1) < SdeltaX[i]
									&& deltaY[globalZ] + 20 > SdeltaY[i] && deltaY[globalZ] - 20 < SdeltaY[i]
									&& Szeichnen[i]) {
								xneg = true;
							}
							if (wag == false && deltaY[globalZ] + 40 * mo < SdeltaY[i]
									&& deltaY[globalZ] + 46 * (mo + 1) > SdeltaY[i] && deltaX[globalZ] + 20 > SdeltaX[i]
									&& deltaX[globalZ] - 20 < SdeltaX[i] && Szeichnen[i]) {
								wag = true;
							}
							if (deltaY[globalZ] - 40 * mo > SdeltaY[i] && deltaY[globalZ] - 50 * (mo + 1) < SdeltaY[i]
									&& deltaX[globalZ] + 20 > SdeltaX[i] && deltaX[globalZ] - 20 < SdeltaX[i]
									&& Szeichnen[i]) {
								weg = true;
							}
						}

						if (drw == false
								&& (deltaX[globalZ] + 40 * (mo + 1) > 48 && deltaX[globalZ] + 40 * (mo + 1) < 330)) {
							g.setColor(Color.GREEN);
							g.fillOval(deltaX[globalZ] + 40 * (mo + 1), deltaY[globalZ], 28, 28);
						}
						if (xneg == false
								&& (deltaX[globalZ] - 40 * (mo + 1) > 48 && deltaX[globalZ] - 40 * (mo + 1) < 330)) {
							g.setColor(Color.GREEN);
							g.fillOval(deltaX[globalZ] - 40 * (mo + 1), deltaY[globalZ], 28, 28);
						}
						if (wag == false
								&& (deltaY[globalZ] + 40 * (mo + 1) > 27 && deltaY[globalZ] + 40 * (mo + 1) < 308)) {
							g.setColor(Color.GREEN);
							g.fillOval(deltaX[globalZ], deltaY[globalZ] + 40 * (mo + 1), 28, 28);
						}
						if (weg == false
								&& (deltaY[globalZ] - 40 * (mo + 1) > 27 && deltaY[globalZ] - 40 * (mo + 1) < 308)) {
							g.setColor(Color.GREEN);
							g.fillOval(deltaX[globalZ], deltaY[globalZ] - 40 * (mo + 1), 28, 28);
						}

						if (xneg && drw && wag && weg) {

							break;
						}
					}

				}

				else {
					//
					// for(int z = 0; z < 16; z++) {
					//
					//// }
					// }
					//

					boolean drw = false;
					boolean xneg = false;
					boolean wag = false;
					boolean weg = false;
					for (int mo = 0; mo < 7; mo++) {
						for (int i = 0; i < 9; i++) {
							if (SdeltaX[globalZ] + 40 * mo < deltaX[i] && SdeltaX[globalZ] + 50 * (mo + 1) > deltaX[i]
									&& SdeltaY[globalZ] + 20 > deltaY[i] && SdeltaY[globalZ] - 20 < deltaY[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								drw = true;
							}
							if (SdeltaX[globalZ] - 40 * mo > deltaX[i] && SdeltaX[globalZ] - 50 * (mo + 1) < deltaX[i]
									&& SdeltaY[globalZ] + 20 > deltaY[i] && SdeltaY[globalZ] - 20 < deltaY[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								xneg = true;
							}
							if (SdeltaY[globalZ] + 40 * mo < deltaY[i] && SdeltaY[globalZ] + 50 * (mo + 1) > deltaY[i]
									&& SdeltaX[globalZ] + 20 > deltaX[i] && SdeltaX[globalZ] - 20 < deltaX[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								wag = true;
							}
							if (SdeltaY[globalZ] - 40 * mo > deltaY[i] && SdeltaY[globalZ] - 50 * (mo + 1) < deltaY[i]
									&& SdeltaX[globalZ] + 20 > deltaX[i] && SdeltaX[globalZ] - 20 < deltaX[i]
									&& (zeichnen[i] == 1 || zeichnen[i] == 2)) {

								weg = true;
							}
						}
						for (int i = 0; i < 16; i++) {
							if (drw == false && SdeltaX[globalZ] + 40 * mo < SdeltaX[i]
									&& SdeltaX[globalZ] + 46 * (mo + 1) > SdeltaX[i]
									&& SdeltaY[globalZ] + 20 > SdeltaY[i] && SdeltaY[globalZ] - 20 < SdeltaY[i]
									&& Szeichnen[i]) {
								drw = true;
							}
							if (SdeltaX[globalZ] - 40 * mo > SdeltaX[i] && SdeltaX[globalZ] - 50 * (mo + 1) < SdeltaX[i]
									&& SdeltaY[globalZ] + 20 > SdeltaY[i] && SdeltaY[globalZ] - 20 < SdeltaY[i]
									&& Szeichnen[i]) {
								xneg = true;
							}
							if (wag == false && SdeltaY[globalZ] + 40 * mo < SdeltaY[i]
									&& SdeltaY[globalZ] + 46 * (mo + 1) > SdeltaY[i]
									&& SdeltaX[globalZ] + 20 > SdeltaX[i] && SdeltaX[globalZ] - 20 < SdeltaX[i]
									&& Szeichnen[i]) {
								wag = true;
							}
							if (SdeltaY[globalZ] - 40 * mo > SdeltaY[i] && SdeltaY[globalZ] - 50 * (mo + 1) < SdeltaY[i]
									&& SdeltaX[globalZ] + 20 > SdeltaX[i] && SdeltaX[globalZ] - 20 < SdeltaX[i]
									&& Szeichnen[i]) {
								weg = true;
							}
						}
						// for(int i = 0; i< 16; i++) {
						// if(deltaX[globalZ] + 40 * mo < SdeltaX[i] && deltaX[globalZ] + 65 * (mo + 1)
						// > SdeltaX[i] && deltaY[globalZ] + 20 > SdeltaY[i] && deltaY[globalZ] - 20 <
						// SdeltaY[i]) {
						//
						// drw = true;
						// }
						//
						// }
						if (drw == false
								&& (SdeltaX[globalZ] + 40 * (mo + 1) > 48 && SdeltaX[globalZ] + 40 * (mo + 1) < 330)) {
							g.setColor(Color.GREEN);
							g.fillOval(SdeltaX[globalZ] + 40 * (mo + 1), SdeltaY[globalZ], 28, 28);
						}
						if (xneg == false
								&& (SdeltaX[globalZ] - 40 * (mo + 1) > 48 && SdeltaX[globalZ] - 40 * (mo + 1) < 330)) {
							g.setColor(Color.GREEN);
							g.fillOval(SdeltaX[globalZ] - 40 * (mo + 1), SdeltaY[globalZ], 28, 28);
						}
						if (wag == false
								&& (SdeltaY[globalZ] + 40 * (mo + 1) > 27 && SdeltaY[globalZ] + 40 * (mo + 1) < 308)) {
							g.setColor(Color.GREEN);
							g.fillOval(SdeltaX[globalZ], SdeltaY[globalZ] + 40 * (mo + 1), 28, 28);
						}
						if (weg == false
								&& (SdeltaY[globalZ] - 40 * (mo + 1) > 27 && SdeltaY[globalZ] - 40 * (mo + 1) < 308)) {
							g.setColor(Color.GREEN);
							g.fillOval(SdeltaX[globalZ], SdeltaY[globalZ] - 40 * (mo + 1), 28, 28);
						}

						if (xneg && drw && wag && weg) {

							break;
						}
					}

				}

			}
		}
	}

	
	//wird bei Mausklick ausgeführt
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (gameover == false) {
			int x = arg0.getX();
			int y = arg0.getY();
			int vac = arg0.getButton();
			if (vac == 1) {
				if (wartemodus == false) {
					if (zug) {
						for (int z = 0; z < 9; z++) {
							if (deltaX[z] - 10 < x && deltaX[z] + 35 > x && deltaY[z] - 10 < y && deltaY[z] + 35 > y
									&& (zeichnen[z] > 0)) {
								zug = false;
								if (zeichnen[z] == 1) {
									zeichnen[z] = 0;
								} else {
									zeichnen[z] = 3;
								}
								wartemodus = true;
								globalZ = z;
								break;
							}

						}

					} else {

						for (int z = 0; z < 16; z++) {
							if (SdeltaX[z] - 10 < x && SdeltaX[z] + 35 > x && SdeltaY[z] - 10 < y && SdeltaY[z] + 35 > y
									&& Szeichnen[z] == true) {
								zug = true;
								Szeichnen[z] = false;
								wartemodus = true;
								globalZ = z;
								break;
							}
						}

					}
				}
				//Wenn schwarz dran ist
				else if (zug == false) {
					if (Math.abs(x - deltaX[globalZ]) < +Math.abs(y - deltaY[globalZ])) {
						int save = ((y - deltaY[globalZ]) / 40);
						if (y < deltaY[globalZ]) {
							save -= 1;
						}
						boolean works = true;
						if (y < 20 || y > 307) {
							save = 0;
							JOptionPane.showMessageDialog(null,
									"Der Stein muss innerhalb des Spielfeldes platziert werden!", "Fehler!",
									JOptionPane.ERROR_MESSAGE);

							works = false;
						} else if (x - 40 < 170 && y - 38 < 148 && x + 40 > 211 && y + 38 > 186
								&& zeichnen[globalZ] == 0) {
							save = 0;
							JOptionPane.showMessageDialog(null, "Dieses Feld darf nur vom König betreten werden!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						save2 = save;
						boolean[] check = new boolean[Math.abs(save)];
						int durchlauf = 0;
						
						//überprüfen, ob Steine übersprungen werden
						while (save != 0) {
							check[durchlauf] = true;
							if (x - 20 < deltaX[globalZ] && x + 20 > deltaX[globalZ]) {

								for (int val = 0; val < 9; val++) {
									if ((deltaY[val] - 30 < deltaY[globalZ] + 40 * save
											&& deltaY[val] + 30 > deltaY[globalZ] + 40 * save
											&& deltaX[val] - 30 < deltaX[globalZ] && deltaX[val] + 30 > deltaX[globalZ])
											|| (SdeltaY[val] - 30 < deltaY[globalZ] + 40 * save
													&& SdeltaY[val] + 30 > deltaY[globalZ] + 40 * save
													&& SdeltaX[val] - 30 < deltaX[globalZ]
													&& SdeltaX[val] + 30 > deltaX[globalZ])
													&& (zeichnen[val] != 0 && zeichnen[val] != 3)) {
										check[durchlauf] = false;
									}
								}
								if (check[durchlauf]) {
									for (int val = 9; val < 16; val++) {
										if (SdeltaY[val] - 30 < deltaY[globalZ] + 40 * save
												&& SdeltaY[val] + 30 > deltaY[globalZ] + 40 * save
												&& SdeltaX[val] - 30 < deltaX[globalZ]
												&& SdeltaX[val] + 30 > deltaX[globalZ] && (Szeichnen[val] == true)) {
											check[durchlauf] = false;
										}
									}

								}

								durchlauf++;
							}
							if (save < 0) {
								save++;
							} else if (save > 0) {
								save--;
							}

						}

						for (int m = 0; m < check.length; m++) {
							if (check[m] == false) {
								JOptionPane.showMessageDialog(null,
										"Du darfst keine Steine überspringen und nicht selben Platz mit einem anderen Teilen!",
										"Information!", JOptionPane.ERROR_MESSAGE);
								works = false;
								break;
							}

						}

						if (deltaY[globalZ] == deltaY[globalZ] + 40 * save2 && works) {
							JOptionPane.showMessageDialog(null,
									"Die Figur darf nicht auf das selbe Feld gehen, auf dem sie sich gerade befindet!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						if (works) {

							deltaY[globalZ] = deltaY[globalZ] + 40 * save2;
							if (zeichnen[globalZ] == 0) {
								zeichnen[globalZ] = 1;
							} else {
								zeichnen[globalZ] = 2;

								if (deltaY[globalZ] < 67 || deltaY[globalZ] > 268) {
									w = true;
									gameover = true;
								}
							}
							wartemodus = false;
						}

						for (int a = 0; a < 16; a++) {
							if (deltaX[globalZ] < SdeltaX[a] && deltaX[globalZ] + 70 > SdeltaX[a]
									&& deltaY[globalZ] - 20 < SdeltaY[a] && deltaY[globalZ] + 20 > SdeltaY[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaX[a] < deltaX[h] && SdeltaX[a] + 70 > deltaX[h]
											&& SdeltaY[a] - 20 < deltaY[h] && SdeltaY[a] + 20 > deltaY[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaX[globalZ] > SdeltaX[a] && deltaX[globalZ] - 70 < SdeltaX[a]
									&& deltaY[globalZ] - 20 < SdeltaY[a] && deltaY[globalZ] + 20 > SdeltaY[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaX[a] > deltaX[h] && SdeltaX[a] - 70 < deltaX[h]
											&& SdeltaY[a] - 20 < deltaY[h] && SdeltaY[a] + 20 > deltaY[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaY[globalZ] < SdeltaY[a] && deltaY[globalZ] + 70 > SdeltaY[a]
									&& deltaX[globalZ] - 20 < SdeltaX[a] && deltaX[globalZ] + 20 > SdeltaX[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaY[a] < deltaY[h] && SdeltaY[a] + 70 > deltaY[h]
											&& SdeltaX[a] - 20 < deltaX[h] && SdeltaX[a] + 20 > deltaX[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaY[globalZ] > SdeltaY[a] && deltaY[globalZ] - 70 < SdeltaY[a]
									&& deltaX[globalZ] - 20 < SdeltaX[a] && deltaX[globalZ] + 20 > SdeltaX[a]) {

								for (int h = 0; h < 8; h++) {
									if (SdeltaY[a] > deltaY[h] && SdeltaY[a] - 70 < deltaY[h]
											&& SdeltaX[a] - 20 < deltaX[h] && SdeltaX[a] + 20 > deltaX[h]) {
										Szeichnen[a] = false;

									}
								}
							}

						}

					}

					else {
						int save = ((x - deltaX[globalZ]) / 40);

						if (x < deltaX[globalZ]) {
							save -= 1;
						}
						// abfrage ob trifft auf anderen punkt
						boolean works = true;
						if (x < 48 || x > 330) {
							save = 0;
							JOptionPane.showMessageDialog(null,
									"Der Stein muss innerhalb des Spielfeldes platziert werden!", "Fehler!",
									JOptionPane.ERROR_MESSAGE);

							works = false;
						} else if (x - 40 < 170 && y - 38 < 148 && x + 40 > 211 && y + 38 > 186
								&& zeichnen[globalZ] == 0) {
							save = 0;
							JOptionPane.showMessageDialog(null, "Dieses Feld darf nur vom König betreten werden!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						int save2 = save;
						boolean[] check = new boolean[Math.abs(save)];
						int durchlauf = 0;
						while (save != 0) {
							check[durchlauf] = true;
							if (y - 20 < deltaY[globalZ] && y + 20 > deltaY[globalZ]) {

								for (int val = 0; val < 9; val++) {
									if (deltaX[val] - 30 < deltaX[globalZ] + 40 * save
											&& deltaX[val] + 30 > deltaX[globalZ] + 40 * save
											&& deltaY[val] - 30 < deltaY[globalZ] && deltaY[val] + 30 > deltaY[globalZ]
											&& zeichnen[val] != 0 && zeichnen[val] != 3) {
										check[durchlauf] = false;
									}
								}
								if (check[durchlauf]) {
									for (int val = 0; val < 16; val++) {
										if (SdeltaX[val] - 30 < deltaX[globalZ] + 40 * save
												&& SdeltaX[val] + 30 > deltaX[globalZ] + 40 * save
												&& SdeltaY[val] - 30 < deltaY[globalZ]
												&& SdeltaY[val] + 30 > deltaY[globalZ] && Szeichnen[val] == true) {

											check[durchlauf] = false;
										}
									}
								}
								durchlauf++;
							}
							if (save < 0) {
								save++;
							} else if (save > 0) {
								save--;
							}

						}

						for (int m = 0; m < check.length; m++) {
							if (check[m] == false) {
								JOptionPane.showMessageDialog(null,
										"Du darfst keine Steine überspringen und nicht selben Platz mit einem anderen Teilen!",
										"Information!", JOptionPane.ERROR_MESSAGE);
								works = false;
								break;
							}

						}
						if (deltaX[globalZ] == deltaX[globalZ] + 40 * save2 && works) {
							JOptionPane.showMessageDialog(null,
									"Die Figur darf nicht auf das selbe Feld gehen, auf dem sie sich gerade befindet!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						if (works) {
							deltaX[globalZ] = deltaX[globalZ] + 40 * save2;
							if (zeichnen[globalZ] == 0) {
								zeichnen[globalZ] = 1;
							} else {

								zeichnen[globalZ] = 2;
								if (deltaX[globalZ] < 90 || deltaX[globalZ] > 290) {
									w = true;
									gameover = true;
								}
							}
							wartemodus = false;
						}
						for (int a = 0; a < 16; a++) {
							if (deltaX[globalZ] < SdeltaX[a] && deltaX[globalZ] + 70 > SdeltaX[a]
									&& deltaY[globalZ] - 20 < SdeltaY[a] && deltaY[globalZ] + 20 > SdeltaY[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaX[a] < deltaX[h] && SdeltaX[a] + 70 > deltaX[h]
											&& SdeltaY[a] - 20 < deltaY[h] && SdeltaY[a] + 20 > deltaY[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaX[globalZ] > SdeltaX[a] && deltaX[globalZ] - 70 < SdeltaX[a]
									&& deltaY[globalZ] - 20 < SdeltaY[a] && deltaY[globalZ] + 20 > SdeltaY[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaX[a] > deltaX[h] && SdeltaX[a] - 70 < deltaX[h]
											&& SdeltaY[a] - 20 < deltaY[h] && SdeltaY[a] + 20 > deltaY[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaY[globalZ] < SdeltaY[a] && deltaY[globalZ] + 70 > SdeltaY[a]
									&& deltaX[globalZ] - 20 < SdeltaX[a] && deltaX[globalZ] + 20 > SdeltaX[a]) {
								for (int h = 0; h < 8; h++) {
									if (SdeltaY[a] < deltaY[h] && SdeltaY[a] + 70 > deltaY[h]
											&& SdeltaX[a] - 20 < deltaX[h] && SdeltaX[a] + 20 > deltaX[h]) {
										Szeichnen[a] = false;

									}
								}
							}

							if (deltaY[globalZ] > SdeltaY[a] && deltaY[globalZ] - 70 < SdeltaY[a]
									&& deltaX[globalZ] - 20 < SdeltaX[a] && deltaX[globalZ] + 20 > SdeltaX[a]) {

								for (int h = 0; h < 8; h++) {
									if (SdeltaY[a] > deltaY[h] && SdeltaY[a] - 70 < deltaY[h]
											&& SdeltaX[a] - 20 < deltaX[h] && SdeltaX[a] + 20 > deltaX[h]) {
										Szeichnen[a] = false;

									}
								}
							}

						}

					}
// Wenn weiß am zug ist
				} else if (zug == true) {
					if (Math.abs(x - SdeltaX[globalZ]) < +Math.abs(y - SdeltaY[globalZ])) {

						int save = ((y - SdeltaY[globalZ]) / 40);
						if (y < SdeltaY[globalZ]) {
							save -= 1;
						}
						int save2 = save;
						boolean works = true;
						if (y < 20 || y > 307) {
							save = 0;
							JOptionPane.showMessageDialog(null,
									"Der Stein muss innerhalb des Spielfeldes platziert werden!", "Fehler!",
									JOptionPane.ERROR_MESSAGE);

							works = false;
						} else if (x - 40 < 170 && y - 38 < 148 && x + 40 > 211 && y + 38 > 186) {
							save = 0;
							JOptionPane.showMessageDialog(null, "Dieses Feld darf nur vom König betreten werden!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						boolean[] check = new boolean[Math.abs(save)];
						int durchlauf = 0;
						while (save != 0) {
							check[durchlauf] = true;

							for (int val = 0; val < 16; val++) {
								if ((SdeltaY[val] - 30 < SdeltaY[globalZ] + 40 * save
										&& SdeltaY[val] + 30 > SdeltaY[globalZ] + 40 * save
										&& SdeltaX[val] - 30 < SdeltaX[globalZ] && SdeltaX[val] + 30 > SdeltaX[globalZ]
										&& (Szeichnen[val] == true))) {

									check[durchlauf] = false;
								}
							}
							if (check[durchlauf]) {
								for (int val = 0; val < 9; val++) {
									if ((deltaY[val] - 30 < SdeltaY[globalZ] + 40 * save
											&& deltaY[val] + 30 > SdeltaY[globalZ] + 40 * save
											&& deltaX[val] - 30 < SdeltaX[globalZ]
											&& deltaX[val] + 30 > SdeltaX[globalZ]
											&& (zeichnen[val] != 0 && zeichnen[val] != 3))) {

										check[durchlauf] = false;
									}

								}
							}
							durchlauf++;
							if (save < 0) {
								save++;
							} else if (save > 0) {
								save--;
							}
						}

						for (int m = 0; m < check.length; m++) {
							if (check[m] == false) {
								works = false;
								JOptionPane.showMessageDialog(null,
										"Du darfst keine Steine überspringen und nicht selben Platz mit einem anderen Teilen!",
										"Information!", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
						if (SdeltaY[globalZ] == SdeltaY[globalZ] + 40 * save2 && works) {
							JOptionPane.showMessageDialog(null,
									"Die Figur darf nicht auf das selbe Feld gehen, auf dem sie sich gerade befindet!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);
							works = false;
						}
						if (works) {
							SdeltaY[globalZ] = SdeltaY[globalZ] + 40 * save2;
							Szeichnen[globalZ] = true;
							wartemodus = false;
						}
						boolean xuhit = false;
						boolean yuhit = false;
						boolean xohit = false;
						boolean yohit = false;
						for (int c = 0; c < 16; c++) {

							if (((deltaX[8] < SdeltaX[c] && deltaX[8] + 80 > SdeltaX[c])
									&& (deltaY[8] - 20 < SdeltaY[c] && deltaY[8] + 20 > SdeltaY[c]))
									|| (deltaX[8] < 170 && deltaX[8] + 70 > 180 && deltaY[8] - 80 < 147
											&& deltaY[8] + 80 > 188)) {

								xuhit = true;
							}
							if (((deltaY[8] + 0 < SdeltaY[c] && deltaY[8] + 70 > SdeltaY[c])
									&& (deltaX[8] + 20 > SdeltaX[c] && deltaX[8] - 20 < SdeltaX[c]))
									|| (deltaY[8] < 147 && deltaY[8] + 70 > 157 && deltaX[8] - 30 < 170
											&& deltaX[8] + 70 > 180)) {

								yuhit = true;
							}
							if (((deltaX[8] + 0 > SdeltaX[c] && deltaX[8] - 70 < SdeltaX[c])
									&& (deltaY[8] - 20 < SdeltaY[c] && deltaY[8] + 20 > SdeltaY[c]))
									|| (deltaX[8] > 210 && deltaX[8] - 34 < 210 && deltaY[8] - 70 < 188
											&& deltaY[8] + 70 > 188)) {

								xohit = true;
							}
							if ((deltaY[8] + 0 > SdeltaY[c] && deltaY[8] - 70 < SdeltaY[c]
									&& (deltaX[8] + 20 > SdeltaX[c] && deltaX[8] - 20 < SdeltaX[c]))
									|| (deltaY[8] > 188 && deltaY[8] - 48 < 188 && deltaX[8] - 30 < 170
											&& deltaX[8] + 70 > 180)) {

								yohit = true;
							}

						}

						for (int a = 0; a < 8; a++) {
							if ((SdeltaY[globalZ] < deltaY[a] && SdeltaY[globalZ] + 70 > deltaY[a])
									&& (SdeltaX[globalZ] - 20 < deltaX[a] && SdeltaX[globalZ] + 20 > deltaX[a])) {
								for (int h = 0; h < 16; h++) {
									if ((deltaY[a] < SdeltaY[h] && deltaY[a] + 70 > SdeltaY[h])
											&& (deltaX[a] - 20 < SdeltaX[h] && deltaX[a] + 20 > SdeltaX[h])) {

										zeichnen[a] = 0;
									}
								}
							}

							if ((SdeltaY[globalZ] > deltaY[a] && SdeltaY[globalZ] - 70 < deltaY[a])
									&& (SdeltaX[globalZ] + 20 > deltaX[a] && SdeltaX[globalZ] - 20 < deltaX[a])) {
								for (int h = 0; h < 16; h++) {
									if ((deltaY[a] > SdeltaY[h] && deltaY[a] - 70 < SdeltaY[h])
											&& (deltaX[a] + 20 > SdeltaX[h] && deltaX[a] - 20 < SdeltaX[h])) {

										zeichnen[a] = 0;
									}
								}
							}

							if ((SdeltaX[globalZ] < deltaX[a] && SdeltaX[globalZ] + 70 > deltaX[a])
									&& (SdeltaY[globalZ] - 20 < deltaY[a] && SdeltaY[globalZ] + 20 > deltaY[a])) {
								for (int h = 0; h < 16; h++) {
									if ((deltaX[a] < SdeltaX[h] && deltaX[a] + 70 > SdeltaX[h])
											&& (deltaY[a] - 20 < SdeltaY[h] && deltaY[a] + 20 > SdeltaY[h])) {

										zeichnen[a] = 0;
									}
								}
							}

							if ((SdeltaX[globalZ] > deltaX[a] && SdeltaX[globalZ] - 70 < deltaX[a])
									&& (SdeltaY[globalZ] + 20 > deltaY[a] && SdeltaY[globalZ] - 20 < deltaY[a])) {
								for (int h = 0; h < 16; h++) {
									if ((deltaX[a] > SdeltaX[h] && deltaX[a] - 70 < SdeltaX[h])
											&& (deltaY[a] + 20 > SdeltaY[h] && deltaY[a] - 20 < SdeltaY[h])) {

										zeichnen[a] = 0;
									}
								}
							}

						}

						if (xuhit && xohit && yuhit && yohit) {
							gameover = true;
						}

					}

					else {

						int save = ((x - SdeltaX[globalZ]) / 40);
						if (x < SdeltaX[globalZ]) {
							save -= 1;
						}
						int save2 = save;
						boolean works = true;
						if (x < 48 || x > 330) {
							save = 0;
							JOptionPane.showMessageDialog(null,
									"Der Stein muss innerhalb des Spielfeldes platziert werden!", "Fehler!",
									JOptionPane.ERROR_MESSAGE);

							works = false;
						} else if (x - 40 < 170 && y - 38 < 148 && x + 40 > 211 && y + 38 > 186) {
							save = 0;
							JOptionPane.showMessageDialog(null, "Dieses Feld darf nur vom König betreten werden!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						boolean[] check = new boolean[Math.abs(save)];
						int durchlauf = 0;
						while (save != 0) {
							check[durchlauf] = true;
							if (y - 20 < SdeltaY[globalZ] && y + 20 > SdeltaY[globalZ]) {

								for (int val = 0; val < 16; val++) {
									if ((SdeltaX[val] - 30 < SdeltaX[globalZ] + 40 * save
											&& SdeltaX[val] + 30 > SdeltaX[globalZ] + 40 * save
											&& SdeltaY[val] - 30 < SdeltaY[globalZ]
											&& SdeltaY[val] + 30 > SdeltaY[globalZ]) && (Szeichnen[val] == true)) {

										check[durchlauf] = false;
									}
								}
								if (check[durchlauf]) {
									for (int val = 0; val < 9; val++) {
										if ((deltaX[val] - 30 < SdeltaX[globalZ] + 40 * save
												&& deltaX[val] + 30 > SdeltaX[globalZ] + 40 * save
												&& deltaY[val] - 30 < SdeltaY[globalZ]
												&& deltaY[val] + 30 > SdeltaY[globalZ])
												&& (zeichnen[val] != 0 && zeichnen[val] != 3)) {

											check[durchlauf] = false;
										}

									}
								}
								durchlauf++;
							}
							if (save < 0) {
								save++;
							} else if (save > 0) {
								save--;
							}

						}

						for (int m = 0; m < check.length; m++) {
							if (check[m] == false) {
								JOptionPane.showMessageDialog(null,
										"Du darfst keine Steine überspringen und nicht selben Platz mit einem anderen Teilen!",
										"Information!", JOptionPane.ERROR_MESSAGE);
								works = false;
								break;
							}

						}
						if (SdeltaX[globalZ] == SdeltaX[globalZ] + 40 * save2 && works) {
							JOptionPane.showMessageDialog(null,
									"Die Figur darf nicht auf das selbe Feld gehen, auf dem sie sich gerade befindet!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);

							works = false;
						}
						if (works) {
							SdeltaX[globalZ] = SdeltaX[globalZ] + (40 * save2);
							Szeichnen[globalZ] = true;
							wartemodus = false;

						}
						boolean xuhit = false;
						boolean yuhit = false;
						boolean xohit = false;
						boolean yohit = false;
						for (int c = 0; c < 16; c++) {
							if (((deltaX[8] + 0 < SdeltaX[c] && deltaX[8] + 80 > SdeltaX[c])
									&& (deltaY[8] - 20 < SdeltaY[c] && deltaY[8] + 20 > SdeltaY[c]))
									|| (deltaX[8] < 170 && deltaX[8] + 70 > 180 && deltaY[8] - 80 < 147
											&& deltaY[8] + 80 > 188)) {
								xuhit = true;

							}
							if (((deltaY[8] + 0 < SdeltaY[c] && deltaY[8] + 70 > SdeltaY[c])
									&& (deltaX[8] + 20 > SdeltaX[c] && deltaX[8] - 20 < SdeltaX[c]))
									|| (deltaY[8] < 147 && deltaY[8] + 70 > 157 && deltaX[8] - 30 < 170
											&& deltaX[8] + 70 > 180)) {
								yuhit = true;

							}
							if (((deltaX[8] + 0 > SdeltaX[c] && deltaX[8] - 70 < SdeltaX[c])
									&& (deltaY[8] - 20 < SdeltaY[c] && deltaY[8] + 20 > SdeltaY[c]))
									|| (deltaX[8] > 210 && deltaX[8] - 34 < 210 && deltaY[8] - 70 < 188
											&& deltaY[8] + 70 > 188)) {
								xohit = true;

							}
							if (((deltaY[8] + 0 > SdeltaY[c] && deltaY[8] - 70 < SdeltaY[c])
									&& (deltaX[8] + 20 > SdeltaX[c] && deltaX[8] - 20 < SdeltaX[c]))
									|| (deltaY[8] > 188 && deltaY[8] - 48 < 188 && deltaX[8] - 30 < 170
											&& deltaX[8] + 70 > 180)) {
								yohit = true;

							}
							for (int a = 0; a < 8; a++) {
								if ((SdeltaX[globalZ] < deltaX[a] && SdeltaX[globalZ] + 70 > deltaX[a])
										&& (SdeltaY[globalZ] - 20 < deltaY[a] && SdeltaY[globalZ] + 20 > deltaY[a])) {
									for (int h = 0; h < 16; h++) {
										if ((deltaX[a] < SdeltaX[h] && deltaX[a] + 70 > SdeltaX[h])
												&& (deltaY[a] - 20 < SdeltaY[h] && deltaY[a] + 20 > SdeltaY[h])) {

											zeichnen[a] = 0;
										}
									}
								}

								if ((SdeltaX[globalZ] > deltaX[a] && SdeltaX[globalZ] - 70 < deltaX[a])
										&& (SdeltaY[globalZ] + 20 > deltaY[a] && SdeltaY[globalZ] - 20 < deltaY[a])) {
									for (int h = 0; h < 16; h++) {
										if ((deltaX[a] > SdeltaX[h] && deltaX[a] - 70 < SdeltaX[h])
												&& (deltaY[a] + 20 > SdeltaY[h] && deltaY[a] - 20 < SdeltaY[h])) {

											zeichnen[a] = 0;
										}
									}

								}

								if ((SdeltaY[globalZ] < deltaY[a] && SdeltaY[globalZ] + 70 > deltaY[a])
										&& (SdeltaX[globalZ] - 20 < deltaX[a] && SdeltaX[globalZ] + 20 > deltaX[a])) {
									for (int h = 0; h < 16; h++) {
										if ((deltaY[a] < SdeltaY[h] && deltaY[a] + 70 > SdeltaY[h])
												&& (deltaX[a] - 20 < SdeltaX[h] && deltaX[a] + 20 > SdeltaX[h])) {

											zeichnen[a] = 0;
										}
									}
								}

								if ((SdeltaY[globalZ] > deltaY[a] && SdeltaY[globalZ] - 70 < deltaY[a])
										&& (SdeltaX[globalZ] + 20 > deltaX[a] && SdeltaX[globalZ] - 20 < deltaX[a])) {
									for (int h = 0; h < 16; h++) {
										if ((deltaY[a] > SdeltaY[h] && deltaY[a] - 70 < SdeltaY[h])
												&& (deltaX[a] + 20 > SdeltaX[h] && deltaX[a] - 20 < SdeltaX[h])) {

											zeichnen[a] = 0;
										}
									}
								}

							}

							if (xuhit && xohit && yuhit && yohit) {
								gameover = true;
							}

						}

					}

				}

			}

			if (vac == 3) {
				if (wartemodus == true) {

					if (zug == true) {
						Szeichnen[globalZ] = true;
						zug = false;
					} else {
						if (zeichnen[globalZ] == 0) {
							zeichnen[globalZ] = 1;
						} else {
							zeichnen[globalZ] = 2;
						}
						zug = true;
					}
					wartemodus = false;
				}
			}

			spiel.repaint();
		}
	}

	public void restart() {

		int[] deltaX2 = { 177, 177, 96, 136, 177, 177, 216, 256, 176 };
		int[] deltaY2 = { 74, 114, 155, 155, 195, 236, 155, 155, 157 };
		int[] zeichnen2 = { 1, 1, 1, 1, 1, 1, 1, 1, 2 };
		int[] SdeltaX2 = { 57, 57, 57, 57, 97, 97, 137, 137, 217, 217, 257, 257, 297, 297, 297, 297 };
		int[] SdeltaY2 = { 75, 115, 195, 236, 35, 275, 35, 275, 35, 275, 35, 275, 75, 114, 194, 235 };
		boolean[] Szeichnen2 = { true, true, true, true, true, true, true, true, true, true, true, true, true, true,
				true, true };
		for (int z = 0; z < 9; z++) {
			deltaX[z] = deltaX2[z];
			deltaY[z] = deltaY2[z];
			zeichnen[z] = zeichnen2[z];
		}
		for (int z = 0; z < 16; z++) {
			SdeltaX[z] = SdeltaX2[z];
			SdeltaY[z] = SdeltaY2[z];
			Szeichnen[z] = Szeichnen2[z];
		}
		wartemodus = false;
		gameover = false;
		w = false;
		zug = false;
		spiel.repaint();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	public void inverthelp() {
		if (hilfe == false) {
			hilfe = true;
		} else {
			hilfe = false;
		}
	}

}
