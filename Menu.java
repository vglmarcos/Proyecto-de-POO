import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener {

	private Color blanco = new Color(255, 255, 255);
	private Color color = new Color(50, 203, 185);
	private JLabel tira;
	private JButton cot;
	private JMenuBar mb;
	private JMenu opciones;
	private JMenuItem cerrar;

	public Menu(String title) {
		this.setLayout(null);
		this.setResizable(false);
		this.setBounds(0, 0, 700, 650);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(blanco);
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());

		mb = new JMenuBar();
		mb.setBackground(color);
		this.setJMenuBar(mb);

		opciones = new JMenu("Opciones");
		opciones.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		opciones.setForeground(blanco);
		opciones.setBackground(color);
		mb.add(opciones);

		cerrar = new JMenuItem("Cerrar sesi\u00F3n");
		cerrar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		cerrar.setForeground(color);
		opciones.add(cerrar);
		cerrar.addActionListener(this);

		cot = new JButton("Generar cotizaci\u00F3n");
		cot.setBounds(240, 350, 180, 30);
		cot.setBackground(color);
		cot.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		cot.setForeground(blanco);
		cot.addActionListener(this);
		cot.addKeyListener(this);
		add(cot);

		ImageIcon tira_imagen = new ImageIcon("images/tira.png");
		tira = new JLabel(tira_imagen);
		tira.setBounds(0, 525, 700, 100);
		add(tira);
	}

	//botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == this.cerrar) {
			Login login = new Login("Ingresar");
			login.setVisible(true);
			this.setVisible(false);
		} else if(evt.getSource() == this.cot) {
			Cotizacion ct = new Cotizacion("Realizar cotizaci\u00F3n");
			ct.setVisible(true);
			this.setVisible(false);
		}
	}

	//teclado
	@Override
	public void keyTyped(KeyEvent evt) {
        
    }

	@Override
	public void keyReleased(KeyEvent evt) {
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getSource() == cot) {
			Cotizacion ct = new Cotizacion("Realizar cotizaci\u00F3n");
			ct.setVisible(true);
			this.setVisible(false);
		}
	}

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {

    }

    @Override
    public void focusLost(FocusEvent evt) {

	}

	//mouse
	@Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        
    }
}