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

		usuario = new JLabel("USUARIO");
		usuario.setBounds(80, 250, 100, 30);
		usuario.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		usuario.setForeground(new Color(0, 153, 153));
		add(usuario);

		usuarioField = new JTextField();
		usuarioField.setBounds(210, 250, 280, 30);
		usuarioField.setBackground(new Color(224, 224, 224));
		usuarioField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		usuarioField.setForeground(new Color(0, 153, 153));
		usuarioField.addKeyListener(this);
		usuarioField.addFocusListener(this);
		add(usuarioField);

		password = new JLabel("CONTRASE\u00D1A");
		password.setBounds(80, 300, 120, 30);
		password.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		password.setForeground(new Color(0, 153, 153));
		add(password);

		passwordField = new JPasswordField();
		passwordField.setBounds(210, 300, 280, 30);
		passwordField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		passwordField.setBackground(new Color(224, 224, 224));
		passwordField.setForeground(new Color(0, 153, 153));
		passwordField.addKeyListener(this);
		passwordField.addFocusListener(this);
		add(passwordField);

		derechos = new JLabel("SILICA. \u00A9 Copyright 2019. Todos los derechos reservados.");
		derechos.setBounds(40, 435, 500, 30);
		derechos.setFont(new Font("Microsoft New Tai Lue", 0, 16));
		derechos.setForeground(new Color(255, 255, 255));
		add(derechos);

		ingresar = new JButton("Ingresar");
		ingresar.setBounds(390, 350, 100, 30);
		ingresar.setBackground(new Color(0, 153, 153));
		ingresar.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		ingresar.setForeground(new Color(255, 255, 255));
		ingresar.addActionListener(this);
		ingresar.addKeyListener(this);
		ingresar.addFocusListener(this);
		add(ingresar);

		tira = new JLabel("");
		tira.setOpaque(true);
		tira.setBounds(0, 425, 650, 50);
		tira.setBackground(new Color(0, 0, 0));
		add(tira);

		tira2 = new JLabel("");
		tira2.setOpaque(true);
		tira2.setBounds(0, 0, 630, 50);
		tira2.setBackground(new Color(0, 153, 153));
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
			this.usuarioField.setBackground(new Color(0, 153, 153));
			this.usuarioField.setForeground(new Color(255, 255, 255));
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(new Color(0, 153, 153));
			this.passwordField.setForeground(new Color(255, 255, 255));
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(new Color(255, 255, 255));
			this.ingresar.setForeground(new Color(0, 153, 153));
		}
    }

    @Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.usuarioField) {
			this.usuarioField.setBackground(new Color(224, 224, 224));
			this.usuarioField.setForeground(new Color(0, 153, 153));
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(new Color(224, 224, 224));
			this.passwordField.setForeground(new Color(0, 153, 153));
		} else if(evt.getSource() == this.ingresar) {
			this.ingresar.setBackground(new Color(0, 153, 153));
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