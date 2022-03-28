import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class SPIEL extends JFrame {
	static JFrame Spiel;
	PANE pane;

	public SPIEL() {
		super("Brandoubh");
 
		createMenu();
		setContentPane(pane = new PANE(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public static void main(String[] args) {
		//Windows Design
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {

		}
		new SPIEL();
	}

	private void createMenu() {

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("Spiel");
		menuBar.add(fileMenu);
		JMenuItem AnleitungItem = new JMenuItem("Anleitung");
		fileMenu.add(AnleitungItem);
		JMenuItem helpItem = new JMenuItem("Hilfssystem");
		fileMenu.add(helpItem);
		JMenuItem RestartItem = new JMenuItem("Neustarten");
		fileMenu.add(RestartItem);
		JMenuItem quitItem = new JMenuItem("Beenden");
		fileMenu.add(quitItem);

		AnleitungItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Brandoubh war im 6. und 7. Jahrhundert in Skandinavien populär. \nDie Regeln dafür lauten:\n- Es gibt 2 Teams, Schwarz und Weiß, jedes Team darf abwechselnd ziehen\n- Auf jedem Feld darf nur eine Figur sein\n- es darf in waagrechter und senkrechter Richtung gezogen werden, jedoch darf keine Figur übersprungen werden\n- Das Feld genau in der Mitte ist der Thron. Dieser darf nur vom König besetzt werden!\n- Ist der Thron nicht besetzt, so darf dieser übersprungen werden!\n- Gerät eine Figur durch einen gegnerischen Zug zwischen zwei Gegner, so wird sie geschlagen! Der König kann so nicht geschlagen werden.\n- Schwarz gewinnt, wenn der König gefangen ist. Das ist der Fall, wenn:\n                       - auf vier unmittelbaren Nachbarfeldern gegnerische Figuren stehen\n                       - auf drei Nachbarfeldern gegnerische Figuren stehen und der König unmittelbar neben dem Thron steht\n- Weiß hat gewonnen, wenn der König ein Randfeld erreicht\nDas Spiel wurde hiermit in Java umgesetzt. Die Steuerung funktioniert wie folgt:\n- Steine werden mit der linken Maustaste ausgewählt,\n- mit der linken Maustaste werden diese auch platziert.\n- Mit der rechten Maustaste kann die Markierung eines Steines rückgängig gemacht werden\nNun wünsche ich viel Spaß bei Brandoubh!\nEine Spieleumsetzung von Alexander Fink 2018.",
						"Anleitung", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		RestartItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.restart();
			}
		});

		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.inverthelp();
			}
		});

	}
}
