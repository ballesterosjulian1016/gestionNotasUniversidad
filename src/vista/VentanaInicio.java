package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import baseDatos.conexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vista.VentanaProfesor;
import vista.VentanaAlumno;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField txtContraseña;

	
	public VentanaInicio() {
		setTitle("Ventana de Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IblUsuario = new JLabel("Usuario");
		IblUsuario.setBackground(new Color(0, 0, 0));
		IblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		IblUsuario.setBounds(21, 100, 63, 26);
		contentPane.add(IblUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(140, 104, 148, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setFont(new Font("Arial", Font.BOLD, 14));
		lblContraseña.setBounds(21, 176, 98, 26);
		contentPane.add(lblContraseña);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(140, 180, 148, 20);
		contentPane.add(txtContraseña);
		
		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setFont(new Font("Arial", Font.BOLD, 14));
		lblCargo.setBounds(21, 250, 63, 26);
		contentPane.add(lblCargo);
		
		JComboBox cmbCargo = new JComboBox();
		cmbCargo.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar cargo", "Profesor", "Alumno"}));
		cmbCargo.setBounds(140, 252, 148, 22);
		contentPane.add(cmbCargo);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = textUsuario.getText();
				String contrasena = new String(txtContraseña.getPassword());
				String cargo = cmbCargo.getSelectedItem().toString();
				if(cargo.equals("Seleccionar cargo")) {
					JOptionPane.showMessageDialog(null,"por favor, selecciona un cargo");
					return;
				}
				Connection conexion = null;
				try {
					conexion = conexionBD.conectar();
					String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
					PreparedStatement ps = conexion.prepareStatement(sql);
					ps.setString(1, usuario);
					ps.setString(2, contrasena);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						int idAlumno = rs.getInt("id_usuario");
						String cargoBD = rs.getString("cargo");
					if(cargo.equalsIgnoreCase(cargoBD)) {
						JOptionPane.showMessageDialog(null, "Bienvenido " + usuario );
						if(cargo.equalsIgnoreCase("profesor")) {
							VentanaProfesor frame = new VentanaProfesor(idAlumno);
							frame.setVisible(true);	
							dispose();
						}else if(cargo.equalsIgnoreCase("Alumno")) {
							VentanaAlumno frame = new VentanaAlumno(idAlumno);
							frame.setVisible(true);
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "El cargo seleccionado no corresponde al usuario");
					}
						
					}else {
						JOptionPane.showMessageDialog(null, "usuario o contraseña incorrecta" );
					}
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos:  " + ex.getMessage() );
					return;
				} finally {
					if (conexion != null) {
						try {
							conexion.close();
						} catch (Exception error){
							error.printStackTrace();
							
						}
					}
				}
				
			}
		});
		btnEntrar.setBounds(353, 344, 89, 23);
		contentPane.add(btnEntrar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textUsuario.setText("");
				txtContraseña.setText("");
			}
		});
		btnLimpiar.setBounds(102, 344, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(474, 344, 89, 23);
		contentPane.add(btnSalir);
		
		JLabel lblLogo = new JLabel("");
		ImageIcon logo = new ImageIcon(VentanaInicio.class.getResource("/imagenes/logo.png"));
		Image imagen = logo.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(imagen));
		lblLogo.setBounds(335, 113, 221, 89);
		contentPane.add(lblLogo);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Mostrar contraseña");
		chckbxNewCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
				    txtContraseña.setEchoChar((char) 0);
				} else {
				    txtContraseña.setEchoChar('•');
				}
			}
		});
		chckbxNewCheckBox.setBounds(299, 253, 143, 23);
		contentPane.add(chckbxNewCheckBox);

	}
}
