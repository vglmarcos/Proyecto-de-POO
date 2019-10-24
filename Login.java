import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener {

	private JButton ingresar;
	private JLabel usuario, empresa, derechos, password, tira, tira2;
	private JTextField usuarioField;
	private JPasswordField passwordField;
	private Color color = new Color(50, 203, 185);
	private Color blanco = new Color(255, 255, 255);
	private Color label = new Color(224, 224, 224);
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Boolean existe;

	public Login(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 630, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(blanco);
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());

		//iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			System.out.println("Se ha establecido conexion a la base de datos.");
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		ImageIcon empresa_imagen = new ImageIcon("images/San-Roman-Logo.png");
		empresa = new JLabel(empresa_imagen);
		empresa.setBounds(50, 70, 530, 157);
		add(empresa);

		usuario = new JLabel("Usuario");
		usuario.setBounds(80, 250, 100, 30);
		usuario.setFont(new Font("Tahoma", 1, 16));
		usuario.setForeground(color);
		add(usuario);

		usuarioField = new JTextField();
		usuarioField.setBounds(200, 250, 250, 30);
		usuarioField.setBackground(label);
		usuarioField.setFont(new Font("Tahoma", 1, 14));
		usuarioField.setForeground(color);
		usuarioField.addKeyListener(this);
		usuarioField.addFocusListener(this);
		add(usuarioField);

		password = new JLabel("Contrase\u00f1a");
		password.setBounds(80, 300, 100, 30);
		password.setFont(new Font("Tahoma", 1, 16));
		password.setForeground(color);
		add(password);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 300, 250, 30);
		passwordField.setBackground(label);
		passwordField.setForeground(color);
		passwordField.addKeyListener(this);
		passwordField.addFocusListener(this);
		add(passwordField);

		derechos = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.");
		derechos.setBounds(40, 420, 500, 30);
		derechos.setFont(new Font("Tahoma", 2, 14));
		derechos.setForeground(blanco);
		add(derechos);

		ingresar = new JButton("Ingresar");
		ingresar.setBounds(260, 350, 100, 30);
		ingresar.setBackground(color);
		ingresar.setFont(new Font("Tahoma", 1, 14));
		ingresar.setForeground(blanco);
		ingresar.addActionListener(this);
		ingresar.addKeyListener(this);
		ingresar.addFocusListener(this);
		add(ingresar);

		ImageIcon tira_imagen = new ImageIcon("images/tira.png");
		tira = new JLabel(tira_imagen);
		tira.setBounds(0, 400, 630, 100);
		add(tira);

		ImageIcon tira2_imagen = new ImageIcon("images/tira2.png");
		tira2 = new JLabel(tira2_imagen);
		tira2.setBounds(0, 0, 630, 60);
		add(tira2);
	}

	//Botones

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == ingresar) {
			String usuario1 = new String(usuarioField.getText().trim());
			String password1 = new String(passwordField.getPassword());
			if(usuario1.isEmpty() || password1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", 
				JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					st = db.getConexion().createStatement();
					rs = st.executeQuery("SELECT nom_usu, contra_usu FROM Usuario WHERE nom_usu = '" + usuario1 + 
					"' AND contra_usu = '" + password1 + "'");
					existe = rs.next();
				} catch(SQLException err) {
					JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(existe) {
					try {
						db.desconectar();
						System.out.println("Se ha desconectado de la base de datos.");
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Bienvenido " + usuario1 + ".", "Bienvenida", 
					JOptionPane.INFORMATION_MESSAGE);
					Menu m = new Menu("Men\u00FA");
					m.setVisible(true);
					this.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contrase\u00f1a incorrectos.",
					"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	//Teclado

	@Override
	public void keyTyped(KeyEvent evt) {
        
    }

	@Override
	public void keyReleased(KeyEvent evt) {
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyCode() == 10) {
			String usuario1 = new String(usuarioField.getText().trim());
			String password1 = new String(passwordField.getPassword());
			if(usuario1.isEmpty() || password1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", 
				JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					st = db.getConexion().createStatement();
					rs = st.executeQuery("SELECT nom_usu, contra_usu FROM Usuario WHERE nom_usu = '" + usuario1 + 
					"' AND contra_usu = '" + password1 + "'");
					existe = rs.next();
				} catch(SQLException err) {
					JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(existe) {
					try {
						db.desconectar();
						System.out.println("Se ha desconectado de la base de datos.");
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "Bienvenido " + usuario1 + ".", "Bienvenida", 
					JOptionPane.INFORMATION_MESSAGE);
					Menu m = new Menu("Men\u00FA");
					m.setVisible(true);
					this.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contrase\u00f1a incorrectos.",
					"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
		if(evt.getSource() == this.usuarioField) {
			this.usuarioField.setBackground(color);
			this.usuarioField.setForeground(blanco);
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(color);
			this.passwordField.setForeground(blanco);
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(blanco);
			this.ingresar.setForeground(color);
		}
    }

    @Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.usuarioField) {
			this.usuarioField.setBackground(label);
			this.usuarioField.setForeground(color);
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(label);
			this.passwordField.setForeground(color);
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(color);
			this.ingresar.setForeground(blanco);
		}
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

	public static void main(String args[]) {
		Login login = new Login("Ingresar");
		login.setVisible(true);
	}
}