package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import baseDatos.conexionBD;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaAlumno extends JFrame {
	
	private int idAlumno;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaNotas;
	private JComboBox comboModulo;
	
	private void cargarNotas() {
		DefaultTableModel modelo = (DefaultTableModel) tablaNotas.getModel();
		modelo.setRowCount(0);
		String sql = "SELECT m.nombre_modulo, n.calificacion "
		        + "FROM notas n "
		        + "INNER JOIN matriculas ma ON n.id_matricula = ma.id_matricula "
		        + "INNER JOIN modulos m ON ma.id_modulo = m.id_modulo "
		        + "WHERE ma.id_alumno = ? "
		        + "AND (? = 'Todos los módulos' OR m.nombre_modulo = ?)";
		try {
		    Connection conexion = conexionBD.conectar();
		    PreparedStatement ps = conexion.prepareStatement(sql);
		    ps.setInt(1, idAlumno);
		    String moduloSeleccionado = comboModulo.getSelectedItem().toString();
		    ps.setString(2, moduloSeleccionado);
		    ps.setString(3, moduloSeleccionado);	    
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		    	modelo.addRow(new Object[] {
		    			rs.getString("nombre_modulo"),
		    			rs.getDouble("calificacion")
		    	});
		    }
		}
		catch (Exception e) {
		    e.printStackTrace();
		}

	}

	
	public VentanaAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
		setTitle("Ventana Alumno");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTituloNotas = new JLabel("MIS NOTAS");
		lblTituloNotas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloNotas.setFont(new Font("Arial", Font.BOLD, 20));
		lblTituloNotas.setBounds(222, 11, 114, 24);
		contentPane.add(lblTituloNotas);
		
		JLabel lblModulo = new JLabel("Modulo");
		lblModulo.setFont(new Font("Arial", Font.BOLD, 14));
		lblModulo.setBounds(20, 47, 71, 24);
		contentPane.add(lblModulo);
		
		comboModulo = new JComboBox();
		comboModulo.addItem("Todos los módulos");
		comboModulo.setBounds(10, 106, 152, 22);
		contentPane.add(comboModulo);
		String sqlModulos = "SELECT m.nombre_modulo "
		        + "FROM modulos m "
		        + "INNER JOIN matriculas ma ON m.id_modulo = ma.id_modulo "
		        + "WHERE ma.id_alumno = ?";

		try {
		    Connection conexion = conexionBD.conectar();
		    PreparedStatement ps = conexion.prepareStatement(sqlModulos);
		    ps.setInt(1, idAlumno);
		    ResultSet rs = ps.executeQuery();

		    while (rs.next()) {
		        comboModulo.addItem(rs.getString("nombre_modulo"));
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(176, 46, 398, 297);
		contentPane.add(scrollPane);
		
		tablaNotas = new JTable();
		tablaNotas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Modulo", "Nota"
			}
		));
		scrollPane.setViewportView(tablaNotas);
		comboModulo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        cargarNotas();
		    }
		});
		DefaultTableModel modelo = (DefaultTableModel) tablaNotas.getModel();
		tablaNotas.setAutoCreateRowSorter(true);
		
		cargarNotas();
		    
		
		
	}
}
