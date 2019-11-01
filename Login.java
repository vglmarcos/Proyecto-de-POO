import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

	private JButton ingresar;
	private JLabel usuario, empresa, derechos, password, tira, tira2;
	private JTextField usuarioField;
	private JPasswordField passwordField;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Boolean existe = false;

	public Login(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 630, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
		this.addWindowListener(this);

		//iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}

		ImageIcon empresa_imagen = new ImageIcon("images/San-Roman-Logo.png");
		empresa = new JLabel(empresa_imagen);
		empresa.setBounds(50, 70, 530, 157);
		add(empresa);

		usuario = new JLabel("Usuario");
		usuario.setBounds(80, 250, 100, 30);
		usuario.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		usuario.setForeground(new Color(50, 203, 185));
		add(usuario);

		usuarioField = new JTextField();
		usuarioField.setBounds(200, 250, 250, 30);
		usuarioField.setBackground(new Color(224, 224, 224));
		usuarioField.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		usuarioField.setForeground(new Color(50, 203, 185));
		usuarioField.addKeyListener(this);
		usuarioField.addFocusListener(this);
		add(usuarioField);

		password = new JLabel("Contrase\u00f1a");
		password.setBounds(80, 300, 100, 30);
		password.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		password.setForeground(new Color(50, 203, 185));
		add(password);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 300, 250, 30);
		passwordField.setBackground(new Color(224, 224, 224));
		passwordField.setForeground(new Color(50, 203, 185));
		passwordField.addKeyListener(this);
		passwordField.addFocusListener(this);
		add(passwordField);

		derechos = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.");
		derechos.setBounds(40, 420, 500, 30);
		derechos.setFont(new Font("Microsoft New Tai Lue", 2, 14));
		derechos.setForeground(new Color(255, 255, 255));
		add(derechos);

		ingresar = new JButton("Ingresar");
		ingresar.setBounds(260, 350, 100, 30);
		ingresar.setBackground(new Color(50, 203, 185));
		ingresar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		ingresar.setForeground(new Color(255, 255, 255));
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
					rs = st.executeQuery("SELECT nom_usu, contra_usu FROM Usuario");
					while(rs.next()) {
						if(rs.getString("nom_usu").equals(usuario1) && rs.getString("contra_usu").equals(password1)) {
							existe = true;
							break;
						}
					}
				} catch(SQLException err) {
					JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(existe) {
					try {
						db.desconectar();
						JOptionPane.showMessageDialog(null, "Bienvenido " + usuario1 + ".", "Bienvenida", 
						JOptionPane.INFORMATION_MESSAGE);
						Menu m = new Menu("Men\u00FA");
						m.setVisible(true);
						this.setVisible(false);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
					}
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
					rs = st.executeQuery("SELECT nom_usu, contra_usu FROM Usuario WHERE nom_usu = '" + 
					usuario1 + "' AND contra_usu = '" + password1 + "'");
					while(rs.next()) {
						if(rs.getString("nom_usu").equals(usuario1) && rs.getString("contra_usu").equals(password1)) {
							existe = true;
							break;
						}
					}
				} catch(SQLException err) {
					JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(existe) {
					try {
						db.desconectar();
						JOptionPane.showMessageDialog(null, "Bienvenido " + usuario1 + ".", "Bienvenida", 
						JOptionPane.INFORMATION_MESSAGE);
						Menu m = new Menu("Men\u00FA");
						m.setVisible(true);
						this.setVisible(false);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
					}
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
			this.usuarioField.setBackground(new Color(50, 203, 185));
			this.usuarioField.setForeground(new Color(255, 255, 255));
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(new Color(50, 203, 185));
			this.passwordField.setForeground(new Color(255, 255, 255));
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(new Color(255, 255, 255));
			this.ingresar.setForeground(new Color(50, 203, 185));
		}
    }

    @Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.usuarioField) {
			this.usuarioField.setBackground(new Color(224, 224, 224));
			this.usuarioField.setForeground(new Color(50, 203, 185));
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(new Color(224, 224, 224));
			this.passwordField.setForeground(new Color(50, 203, 185));
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(new Color(50, 203, 185));
			this.ingresar.setForeground(new Color(255, 255, 255));
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
	
	//ventana
	@Override
	public void windowClosing(WindowEvent evt) {
		try {
			db.desconectar();
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {

	}

	@Override
	public void windowActivated(WindowEvent evt) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
		
	}

	@Override
	public void windowIconified(WindowEvent evt) {
		
	}

	@Override
	public void windowClosed(WindowEvent evt) {
		
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		
	}

	public static void main(String args[]) {
		Login login = new Login("Ingresar");
		login.setVisible(true);
	}
}