import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Cotizacion extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

	private JButton cancelar, agregar, guardar, borrar, agregarcot, detalle;
	private JLabel tira, no_cot, fechaLabel, sbt, total, iva, id_cliente, nom_cliente, tel, dir, corr, emp;
	private JLabel prod, pre_uni, dim, x, cant, sbtotal, mas, menos, desc, tipo, id_prod;
	private JTextField no_cotField, pre_uni_txt, dim_largo, dim_ancho, cant_txt, sbtotal_txt, id_prod_txt;
	private JTextField id_cliente_txt, nom_cliente_txt, tel_txt, dir_txt, corr_txt;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Integer id;
	private Calendar fecha;
	private String dia, mes, anio;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll, scroll_desc;
	private JPanel agregarCot, mostrarCot;
	private JComboBox<String> prod_com, tipo_prod, emp_txt;
	private JTextArea desc_text;

	public Cotizacion(String title) {
		this.setLayout(null);;
		this.setResizable(false);
		this.setBounds(0, 0, 810, 650);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
		this.addWindowListener(this);

		//Panel para agregar cotizacion
		agregarCot = new JPanel();
		agregarCot.setBackground(new Color(255, 255, 255));
		agregarCot.setBounds(0, 0, 810, 650);
		agregarCot.setLayout(null);
		agregarCot.setVisible(false);
		
		//Panel para mostrar detalles de cotizacion
		mostrarCot = new JPanel();
		mostrarCot.setBackground(new Color(255, 255, 255));
		mostrarCot.setBounds(0, 0, 810, 650);
		mostrarCot.setLayout(null);
		mostrarCot.setVisible(true);

		//obtenemos fecha actual
		fecha = Calendar.getInstance();
		dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
		anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

		//iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConexion().createStatement();
			rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
			rs.next();
			id = rs.getInt(1); //maxima id de cotizaciones
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		panelMostrarCot();
		panelAgregarCot();

		this.add(mostrarCot);
		this.add(agregarCot);
	}

	public void panelMostrarCot() {
		no_cot = new JLabel("Id Cotizaci\u00F3n");
		no_cot.setBounds(30, 36, 110, 25);
		no_cot.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		no_cot.setForeground(new Color(0, 153, 153));
		mostrarCot.add(no_cot);

		no_cotField = new JTextField();
		no_cotField.setBounds(159, 34, 50, 30);
		no_cotField.setBackground(new Color(224, 224, 224));
		no_cotField.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		no_cotField.setText(Integer.valueOf(id).toString());
		no_cotField.setHorizontalAlignment(JTextField.CENTER); 
		no_cotField.setEditable(false);
		no_cotField.setForeground(new Color(0, 153, 153));
		no_cotField.addKeyListener(this);
		mostrarCot.add(no_cotField);

		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 36, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(new Color(0, 0, 0));
		mostrarCot.add(fechaLabel);

		id_cliente = new JLabel("Id Cliente");
		id_cliente.setBounds(57, 87, 80, 25);
		id_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente.setForeground(new Color(0, 0, 0));
		mostrarCot.add(id_cliente);

		id_cliente_txt = new JTextField();
		id_cliente_txt.setBounds(159, 85, 50, 30);
		id_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(id_cliente_txt);

		nom_cliente = new JLabel("Nombre");
		nom_cliente.setBounds(228, 87, 70, 25);
		nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente.setForeground(new Color(0, 0, 0));
		mostrarCot.add(nom_cliente);

		nom_cliente_txt = new JTextField();
		nom_cliente_txt.setBounds(310, 85, 461, 30);
		nom_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(nom_cliente_txt);

		tel = new JLabel("T\u00E9lefono");
		tel.setBounds(57, 131, 75, 25);
		tel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel.setForeground(new Color(0, 0, 0));
		mostrarCot.add(tel);

		tel_txt = new JTextField();
		tel_txt.setBounds(159, 129, 171, 30);
		tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(tel_txt);

		dir = new JLabel("Direcci\u00F3n");
		dir.setBounds(352, 131, 80, 25);
		dir.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir.setForeground(new Color(0, 0, 0));
		mostrarCot.add(dir);

		dir_txt = new JTextField();
		dir_txt.setBounds(444, 129, 326, 30);
		dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(dir_txt);

		corr = new JLabel("Correo");
		corr.setBounds(57, 174, 58, 25);
		corr.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr.setForeground(new Color(0, 0, 0));
		mostrarCot.add(corr);

		corr_txt = new JTextField();
		corr_txt.setBounds(159, 172, 279, 30);
		corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(corr_txt);

		emp = new JLabel("Empleado");
		emp.setBounds(57, 215, 84, 25);
		emp.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		emp.setForeground(new Color(0, 0, 0));
		mostrarCot.add(emp);

		emp_txt = new JComboBox<>();
		emp_txt.addItem("");
		emp_txt.setBounds(159, 213, 461, 30);
        emp_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        emp_txt.setBackground(new Color(224, 224, 224));
        emp_txt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(emp_txt);

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
		scroll.setBounds(30, 265, 750, 180);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(30); //Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(160); //Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(80); //Tipo
		tabla.getColumnModel().getColumn(3).setPreferredWidth(120); //Precio unitario
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80); //Largo
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80); //Ancho
		tabla.getColumnModel().getColumn(6).setPreferredWidth(100); //Cantidad
		tabla.getColumnModel().getColumn(7).setPreferredWidth(100); //Precio total
		tabla.setRowHeight(25);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 0, 18)); 
		tabla.getTableHeader().setForeground(new Color(255, 255, 255)); 
		tabla.getTableHeader().setBackground(new Color(0, 153, 153)); 
		tabla.setBackground(new Color(255, 255, 255));
		tabla.setForeground(new Color(0, 0, 0));
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		mostrarCot.add(scroll);

		modelo.addRow(new String[]{"1", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		modelo.addRow(new String[]{"2", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		
		sbt = new JLabel("Subtotal: \u0024 0");
		sbt.setBounds(609, 468, 150, 25);
		sbt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbt.setForeground(new Color(0, 0, 0));
		mostrarCot.add(sbt);

		iva = new JLabel("IVA: 16 \u0025");
		iva.setBounds(650, 498, 80, 25);
		iva.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		iva.setForeground(new Color(0, 0, 0));
		mostrarCot.add(iva);
		
		total = new JLabel("Total: \u0024 0");
		total.setBounds(637, 528, 150, 25);
		total.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		total.setForeground(new Color(0, 0, 0));
		mostrarCot.add(total);

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(30, 523, 100, 30);
		cancelar.setBackground(new Color(0, 153, 153));
		cancelar.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cancelar.setForeground(new Color(255, 255, 255));
		cancelar.addActionListener(this);
		cancelar.addKeyListener(this);
		mostrarCot.add(cancelar);

		borrar = new JButton("Borrar");
		borrar.setBounds(170, 523, 100, 30);
		borrar.setBackground(new Color(0, 153, 153));
		borrar.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		borrar.setForeground(new Color(255, 255, 255));
		borrar.addActionListener(this);
		borrar.addKeyListener(this);
		mostrarCot.add(borrar);

		agregar = new JButton("Agregar");
		agregar.setBounds(320, 523, 100, 30);
		agregar.setBackground(new Color(0, 153, 153));
		agregar.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		agregar.setForeground(new Color(255, 255, 255));
		agregar.addActionListener(this);
		agregar.addKeyListener(this);
		mostrarCot.add(agregar);

		guardar = new JButton("Guardar");
		guardar.setBounds(470, 523, 100, 30);
		guardar.setBackground(new Color(0, 153, 153));
		guardar.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		guardar.setForeground(new Color(255, 255, 255));
		guardar.addActionListener(this);
		guardar.addKeyListener(this);
		mostrarCot.add(guardar);

		tira = new JLabel("");
		tira.setOpaque(true);
		tira.setBounds(0, 575, 810, 50);
		tira.setBackground(new Color(0, 0, 0));
		mostrarCot.add(tira);
	}

	public void panelAgregarCot() {
		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 61, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(new Color(0, 0, 0));
		agregarCot.add(fechaLabel);

		id_prod = new JLabel("Id Producto");
		id_prod.setBounds(30, 94, 98, 25);
		id_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_prod.setForeground(new Color(0, 153, 153));
		agregarCot.add(id_prod);

		id_prod_txt = new JTextField();
		id_prod_txt.setText("");
		id_prod_txt.setBounds(159, 92, 50, 30);
		id_prod_txt.setBackground(new Color(224, 224, 224));
		id_prod_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_prod_txt.setForeground(new Color(0, 153, 153));
		id_prod_txt.setHorizontalAlignment(JTextField.CENTER);
		id_prod_txt.setEditable(false);
		agregarCot.add(id_prod_txt);

		prod = new JLabel("Producto");
		prod.setBounds(57, 183, 77, 25);
		prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		prod.setForeground(new Color(0, 0, 0));
		agregarCot.add(prod);

		prod_com = new JComboBox<>();
		prod_com.addItem("");
		prod_com.setBounds(163, 181, 248, 30);
        prod_com.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        prod_com.setBackground(new Color(224, 224, 224));
        prod_com.setForeground(new Color(0, 0, 0));
		agregarCot.add(prod_com);
		
		pre_uni = new JLabel("Precio unitario");
		pre_uni.setBounds(438, 183, 124, 25);
		pre_uni.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni.setForeground(new Color(0, 0, 0));
		agregarCot.add(pre_uni);

		pre_uni_txt = new JTextField();
		pre_uni_txt.setText("\u0024 0");
		pre_uni_txt.setBounds(581, 181, 159, 30);
		pre_uni_txt.setBackground(new Color(224, 224, 224));
		pre_uni_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni_txt.setForeground(new Color(0, 0, 0));
		pre_uni_txt.setEditable(false);
		agregarCot.add(pre_uni_txt);

		dim = new JLabel("Dimensiones");
		dim.setBounds(57, 250, 113, 25);
		dim.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim.setForeground(new Color(0, 0, 0));
		agregarCot.add(dim);

		dim_largo = new JTextField();
		dim_largo.setBounds(188, 248, 107, 30);
		dim_largo.setBackground(new Color(224, 224, 224));
		dim_largo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_largo.setForeground(new Color(0, 0, 0));
		agregarCot.add(dim_largo);

		x = new JLabel("X");
		x.setBounds(308, 250, 11, 25);
		x.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		x.setForeground(new Color(0, 0, 0));
		agregarCot.add(x);

		dim_ancho = new JTextField();
		dim_ancho.setBounds(332, 248, 107, 30);
		dim_ancho.setBackground(new Color(224, 224, 224));
		dim_ancho.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_ancho.setForeground(new Color(0, 0, 0));
		agregarCot.add(dim_ancho);

		tipo = new JLabel("Tipo");
		tipo.setBounds(462, 250, 37, 25);
		tipo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tipo.setForeground(new Color(0, 0, 0));
		agregarCot.add(tipo);

		tipo_prod = new JComboBox<>();
		tipo_prod.addItem("");
		tipo_prod.setBounds(517, 248, 223, 30);
        tipo_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        tipo_prod.setBackground(new Color(224, 224, 224));
        tipo_prod.setForeground(new Color(0, 0, 0));
		agregarCot.add(tipo_prod);

		cant = new JLabel("Cantidad");
		cant.setBounds(58, 318, 76, 25);
		cant.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant.setForeground(new Color(0, 0, 0));
		agregarCot.add(cant);

		cant_txt = new JTextField();
		cant_txt.setBounds(153, 316, 90, 30);
		cant_txt.setBackground(new Color(224, 224, 224));
		cant_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant_txt.setForeground(new Color(0, 0, 0));
		cant_txt.setText("0");
		cant_txt.setHorizontalAlignment(JTextField.CENTER);
		cant_txt.setEditable(false);
		agregarCot.add(cant_txt);

		ImageIcon mas_imagen = new ImageIcon("./images/mas.png");
		mas = new JLabel(mas_imagen);
		mas.setBounds(306, 316, 30, 30);
		mas.addMouseListener(this);
		agregarCot.add(mas);

		ImageIcon menos_imagen = new ImageIcon("./images/menos.png");
		menos = new JLabel(menos_imagen);
		menos.setBounds(258, 316, 30, 30);
		menos.addMouseListener(this);
		agregarCot.add(menos);

		sbtotal = new JLabel("Subtotal");
		sbtotal.setBounds(370, 318, 70, 25);
		sbtotal.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal.setForeground(new Color(0, 0, 0));
		agregarCot.add(sbtotal);

		sbtotal_txt = new JTextField();
		sbtotal_txt.setBounds(462, 316, 133, 30);
		sbtotal_txt.setBackground(new Color(224, 224, 224));
		sbtotal_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal_txt.setForeground(new Color(0, 0, 0));
		sbtotal_txt.setText("\u0024 0");
		sbtotal_txt.setEditable(false);
		agregarCot.add(sbtotal_txt);

		desc = new JLabel("Descripci\u00F3n");
		desc.setBounds(57, 369, 99, 25);
		desc.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		desc.setForeground(new Color(0, 0, 0));
		agregarCot.add(desc);

		desc_text = new JTextArea();
		desc_text.setLineWrap(true);
		desc_text.setBackground(new Color(224, 224, 224));
		desc_text.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		desc_text.setForeground(new Color(0, 0, 0));
		scroll_desc = new JScrollPane(desc_text);
		scroll_desc.setBounds(177, 369, 567, 130);
		agregarCot.add(scroll_desc);

		detalle = new JButton("Detalles");
		detalle.setBounds(494, 523, 100, 30);
		detalle.setBackground(new Color(0, 153, 153));
		detalle.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		detalle.setForeground(new Color(255, 255, 255));
		detalle.addActionListener(this);
		agregarCot.add(detalle);

		agregarcot = new JButton("Agregar");
		agregarcot.setBounds(644, 523, 100, 30);
		agregarcot.setBackground(new Color(0, 153, 153));
		agregarcot.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		agregarcot.setForeground(new Color(255, 255, 255));
		agregarCot.add(agregarcot);

		tira = new JLabel("");
		tira.setOpaque(true);
		tira.setBounds(0, 575, 810, 50);
		tira.setBackground(new Color(0, 0, 0));
		agregarCot.add(tira);
	}

	//botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == this.cancelar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(evt.getSource() == this.agregar) {
			mostrarCot.setVisible(false);
			agregarCot.setVisible(true);
		} else if(evt.getSource() == this.detalle) {
			mostrarCot.setVisible(true);
			agregarCot.setVisible(false);
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
		if(evt.getSource() == this.cancelar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
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
        if(evt.getSource() == this.menos) {
			if(Integer.parseInt(this.cant_txt.getText()) > 0) {
				this.cant_txt.setEditable(true);
				Integer valor = Integer.parseInt(this.cant_txt.getText()) - 1;
				this.cant_txt.setText(valor.toString());
				this.cant_txt.setEditable(false);
			}
		} else if(evt.getSource() == this.mas) {
			this.cant_txt.setEditable(true);
			Integer valor = Integer.parseInt(this.cant_txt.getText()) + 1;
			this.cant_txt.setText(valor.toString());
			this.cant_txt.setEditable(false);
		}
		this.sbtotal_txt.setEditable(true);
		Integer precio = Integer.parseInt(this.cant_txt.getText()) * 100;
		this.sbtotal_txt.setText("\u0024 " + precio.toString());
		this.sbtotal_txt.setEditable(false);
	}
	
	//ventana
	@Override
	public void windowClosing(WindowEvent evt) {
		try {
			db.desconectar();
			System.out.println("Se ha desconectado de la base de datos.");
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
}