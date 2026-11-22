package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import baseDatos.conexionBD;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class VentanaProfesor extends JFrame {
	private int idProfesor;
	private JComboBox comboModulo;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaNotas;
	private JTextField textoNota;
	
	private void cargarNotas() {
		DefaultTableModel modelo = (DefaultTableModel) tablaNotas.getModel();
		modelo.setRowCount(0);
		try {
			Connection conexion = conexionBD.conectar();
			String sql = "SELECT n.id_nota, m.nombre_modulo, u.nombre AS alumno, n.calificacion "
			           + "FROM notas n "
			           + "INNER JOIN matriculas ma ON n.id_matricula = ma.id_matricula "
			           + "INNER JOIN usuarios u ON ma.id_alumno = u.id_usuario "
			           + "INNER JOIN modulos m ON ma.id_modulo = m.id_modulo "
			           + "WHERE m.nombre_modulo = ?";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, comboModulo.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			    modelo.addRow(new Object[] {
			        rs.getInt("id_nota"),
			        rs.getString("nombre_modulo"),
			        rs.getString("alumno"),
			        rs.getDouble("calificacion")
			    });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	 
	public VentanaProfesor(int idProfesor) {
		this.idProfesor = idProfesor;
		setTitle("Panel Profesores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("GESTION DE NOTAS-PROFESOR");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setBounds(137, 11, 307, 29);
		contentPane.add(lblTitulo);
		
		JLabel lblModulo = new JLabel("Modulo");
		lblModulo.setFont(new Font("Arial", Font.BOLD, 14));
		lblModulo.setBounds(65, 37, 82, 29);
		contentPane.add(lblModulo);
		
		comboModulo = new JComboBox();
		
		comboModulo.setBounds(10, 71, 200, 22);
		contentPane.add(comboModulo);
		String sqlModulos = "SELECT m.nombre_modulo "
		        + "FROM modulos m "
		        + "INNER JOIN profesores_modulos pm ON m.id_modulo = pm.id_modulo "
		        + "WHERE pm.id_profesor = ?";
		try {
			Connection conexion = conexionBD.conectar();
			PreparedStatement ps = conexion.prepareStatement(sqlModulos);
			ps.setInt(1, idProfesor);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				comboModulo.addItem(rs.getString("Nombre_modulo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(214, 51, 360, 287);
		contentPane.add(scrollPane);
		
		tablaNotas = new JTable();
		tablaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaNotas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "MODULO", "ALUMNO", "NOTA"
				
			}
		));
		scrollPane.setViewportView(tablaNotas);
		comboModulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarNotas();
			}
		});
		DefaultTableModel modelo = (DefaultTableModel) tablaNotas.getModel();
		modelo.setRowCount(0);
		
		JButton btnGuardarNota = new JButton("Guardar Nota");
		btnGuardarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaNotas.getSelectedRow();
				if(fila== -1) {
					JOptionPane.showMessageDialog(null, "Selecciona un alumno de la tabla");
					return;
				}
				String nuevaNota = textoNota.getText().trim();
				if(nuevaNota.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce una nota");
					return;
				}
				double calificacion;
				try {
					calificacion = Double.parseDouble(nuevaNota);
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Introduce una nota valida");
					return;
				}
				if(calificacion <0 || calificacion > 10) {
					JOptionPane.showMessageDialog(null, "La nota debe estar entre 0 y 10");
					return;
				}
				int idNota = (int) tablaNotas.getValueAt(fila, 0);
				String sql = "UPDATE notas SET calificacion = ? where id_nota = ?";
				try {
					Connection conexion = conexionBD.conectar();
					PreparedStatement ps = conexion.prepareStatement(sql);
					ps.setDouble(1, calificacion);
					ps.setInt(2, idNota);
					int filasActualizadas = ps.executeUpdate();
					if(filasActualizadas > 0) {
						JOptionPane.showMessageDialog(null, "Nota actualizada correctamente");	
						tablaNotas.setValueAt(calificacion,fila,3);
					}else {
						JOptionPane.showMessageDialog(null, "No se encontros la nota");
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al guardar la nota: " + ex.getMessage());
				}
			}
		});
		btnGuardarNota.setBounds(10, 366, 110, 23);
		contentPane.add(btnGuardarNota);
		
		textoNota = new JTextField();
		textoNota.setBounds(56, 241, 86, 29);
		contentPane.add(textoNota);
		textoNota.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("NUEVA NOTA");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(56, 211, 110, 19);
		contentPane.add(lblNewLabel);
		
		if (comboModulo.getItemCount() > 0) cargarNotas();

	}
}
