package crud;
import java.sql.*;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ConnectionSingleton {
	private static Connection con;
	
	public static Connection getConnection() throws SQLException {
		String url="jdbc:mysql://127.0.0.1:3307/crud";
		String user="alumno";
		String password="alumno1234";
		
		if(con==null || con.isClosed()) {
			con=DriverManager.getConnection(url,user,password);
		}
		return con;
		
	}
}
public class VentanaPrincipal {

	private JFrame frame;
	private JTextField textFieldNombre;
	private JTextField textFieldNombre2;
	private JTextField textFieldDNI;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 675, 487);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(51, 40, 85, 15);
		frame.getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(51, 83, 85, 15);
		frame.getContentPane().add(lblLastName);
		
		JLabel lblRegNumber = new JLabel("Reg Number");
		lblRegNumber.setBounds(51, 122, 102, 15);
		frame.getContentPane().add(lblRegNumber);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(164, 38, 114, 19);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldNombre2 = new JTextField();
		textFieldNombre2.setColumns(10);
		textFieldNombre2.setBounds(164, 81, 114, 19);
		frame.getContentPane().add(textFieldNombre2);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(164, 120, 114, 19);
		frame.getContentPane().add(textFieldDNI);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con= ConnectionSingleton.getConnection();
					PreparedStatement smt2=con.prepareStatement("INSERT into personas(primerNombre,ultimoNombre,dni) VALUES(?,?,?)");
					smt2.setString(1, textFieldNombre.getText());
					smt2.setString(2, textFieldNombre2.getText());
					smt2.setString(3, textFieldDNI.getText());
					
					smt2.executeUpdate();
					smt2.close();
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
		
			}
		});
		btnSave.setBounds(51, 237, 85, 25);
		frame.getContentPane().add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Connection con= ConnectionSingleton.getConnection();
					TableModel model=table.getModel();
					int index=table.getSelectedRow();
					PreparedStatement smt2=con.prepareStatement("DELETE * FROM personas WHERE dni=?");
					
					textFieldNombre.setText(model.getValueAt(index, 0).toString());
					textFieldNombre2.setText(model.getValueAt(index, 1).toString());
					textFieldDNI.setText(model.getValueAt(index, 2).toString());
					
					String dni=(String) model.getValueAt(index, 0);
					System.out.println(dni);
					
				smt2.setString(1,model.getValueAt(index, 3).toString());
					System.out.println();
					smt2.executeUpdate();
					smt2.close();
					con.close();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
	
		btnUpdate.setBounds(161, 237, 90, 25);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(277, 237, 85, 25);
		frame.getContentPane().add(btnDelete);
		
		

		DefaultTableModel model=new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("FirstName");
		model.addColumn("LastName");
		model.addColumn("DNI");
		
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBounds(373,42,193,120);
		try {
			Connection con= ConnectionSingleton.getConnection();
			Statement smt = con.createStatement();
			ResultSet rs= smt.executeQuery("SELECT * from personas");
			
			while(rs.next()) {
				Object[] row =new Object[4];
				row[0] =rs.getString("id");
				row[1] = rs.getString("primerNombre");
				row[2] = rs.getString("ultimoNombre");				
				row[3] =rs.getString("dni");
				model.addRow(row);
			}
			table.setModel(model);
			rs.close();
			smt.close();
			con.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(370, 40, 293, 222);
		frame.getContentPane().add(scrollPane);
		
	}

}
