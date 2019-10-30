import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Cotizacion extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener {

	private Color blanco = new Color(255, 255, 255);
	private Color label = new Color(224, 224, 224);
	private JButton regresar, agregar, guardar;
	private JLabel tira, no_cot, fechaLabel, total, iva, sbt;
	private JTextField no_cotField;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Integer id;
	private Calendar fecha;
	private String dia, mes, anio;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll;

	public Cotizacion(String title) {
		this.setLayout(null);;
		this.setResizable(false);
		this.setBounds(0, 0, 810, 650);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(blanco);
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());

		//obtenemos fecha actual
		fecha = Calendar.getInstance();
		dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
		anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

		//iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			System.out.println("Se ha establecido conexion a la base de datos.");
			st = db.getConexion().createStatement();
			rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
			rs.next();
			id = rs.getInt(1); //maxima id de cotizaciones
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		no_cot = new JLabel("No. Cotizaci\u00F3n");
		no_cot.setBounds(30, 70, 150, 30);
		no_cot.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		no_cot.setForeground(new Color(0, 153, 153));
		add(no_cot);

		no_cotField = new JTextField();
		no_cotField.setBounds(170, 70, 50, 30);
		no_cotField.setBackground(label);
		no_cotField.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		no_cotField.setText(Integer.valueOf(id).toString());
		no_cotField.setHorizontalAlignment(JTextField.CENTER); 
		no_cotField.setEditable(false);
		no_cotField.setForeground(new Color(0, 153, 153));
		no_cotField.addKeyListener(this);
		add(no_cotField);

		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(620, 70, 200, 30);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		fechaLabel.setForeground(new Color(0, 0, 0));
		add(fechaLabel);

		String[] campos = new String[]{"Id", "Nombre", "Tipo", "Precio unitario", "Largo", "Ancho", 
									   "Cantidad", "Precio total"};

		modelo = new DefaultTableModel(null, campos);
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 120, 750, 250);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(30); //Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(160); //Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(80); //Tipo
		tabla.getColumnModel().getColumn(3).setPreferredWidth(120); //Precio unitario
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80); //Largo
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80); //Ancho
		tabla.getColumnModel().getColumn(6).setPreferredWidth(100); //Cantidad
		tabla.getColumnModel().getColumn(7).setPreferredWidth(100); //Precio total
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 14)); 
		tabla.getTableHeader().setForeground(new Color(255, 255, 255)); 
		tabla.getTableHeader().setBackground(new Color(0, 153, 153)); 
		tabla.setBackground(new Color(255, 255, 255));
		tabla.setForeground(new Color(0, 0, 0));
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 14));
		this.add(scroll);

		modelo.addRow(new String[]{"1", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		modelo.addRow(new String[]{"2", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		
		sbt = new JLabel("Subtotal: 0");
		sbt.setBounds(690, 390, 100, 30);
		sbt.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		sbt.setForeground(new Color(0, 0, 0));
		add(sbt);

		iva = new JLabel("IVA: 16 \u0025");
		iva.setBounds(690, 420, 100, 30);
		iva.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		iva.setForeground(new Color(0, 0, 0));
		add(iva);
		
		total = new JLabel("Total: \u0024 0");
		total.setBounds(690, 450, 100, 30);
		total.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		total.setForeground(new Color(0, 0, 0));
		add(total);

		regresar = new JButton("Regresar");
		regresar.setBounds(120, 480, 100, 30);
		regresar.setBackground(new Color(0, 153, 153));
		regresar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		regresar.setForeground(blanco);
		regresar.addActionListener(this);
		regresar.addKeyListener(this);
		add(regresar);

		agregar = new JButton("Agregar");
		agregar.setBounds(320, 480, 100, 30);
		agregar.setBackground(new Color(0, 153, 153));
		agregar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		agregar.setForeground(blanco);
		agregar.addActionListener(this);
		agregar.addKeyListener(this);
		add(agregar);

		guardar = new JButton("Guardar");
		guardar.setBounds(520, 480, 100, 30);
		guardar.setBackground(new Color(0, 153, 153));
		guardar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		guardar.setForeground(blanco);
		guardar.addActionListener(this);
		guardar.addKeyListener(this);
		add(guardar);

		ImageIcon tira_imagen = new ImageIcon("images/tira.png");
		tira = new JLabel(tira_imagen);
		tira.setBounds(0, 550, 810, 100);
		add(tira);
	}

	//botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == regresar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
				System.out.println("Se ha desconectado de la base de datos.");
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	//teclado
	@Override
	public void keyTyped(KeyEvent evt) {
        if(evt.getSource() == this.no_cotField) {
			if(this.no_cotField.getText().length() >= 4) {
				evt.consume();
			}
		}
    }

	@Override
	public void keyReleased(KeyEvent evt) {
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getSource() == this.regresar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
				System.out.println("Se ha desconectado de la base de datos.");
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
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