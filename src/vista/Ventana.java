package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import modelo.AccesoDatos;


public class Ventana extends javax.swing.JFrame {

	private javax.swing.JButton jButton1;
	private javax.swing.JComboBox<String> jComboBox1;
	private javax.swing.JList<String> jList1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;


	public Ventana() {
		inicio();
	}


	private void inicio() {
		this.setTitle("Consulta Catalogo MySQL");
		
		//crear el contenedor de la ventana y aplicarle un borderlayout
		Container contenido = this.getContentPane();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(640,480));
		this.setLocation(50, 100);
		//this.setResizable(false);
		jScrollPane1 = new JScrollPane();
		
		jComboBox1 = new JComboBox<>();
		jButton1 = new JButton();
		jTextField1 = new JTextField();
		jTextField2 = new JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         ArrayList<String> lista_baseDatos = new AccesoDatos().getSchemas();
        String[] listaBD = lista_baseDatos.toArray(new String[lista_baseDatos.size()]);
        jList1 = new JList<>(listaBD);
         /*		jList1.setModel(new javax.swing.AbstractListModel<String>() {
			@Override // anotación
			public String getElementAt(int index) {
				// TODO Auto-generated method stub
				return lista_baseDatos.get(index);
			}
			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return lista_baseDatos.size();
			}
		});*/

		jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				jList1ValueChanged(evt);
			}
		});

		jScrollPane1.setViewportView(jList1);

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gran Canaria", "Lanzarote", "Fuerteventura", "Tenerife", "La Gomera", "La Palma", "El Hierro" }));
		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox1ActionPerformed(evt);
			}
		});

		jButton1.setText("¡PulsaMe!!!");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jTextField1.setText(null);

		//Ahora se añaden los elementos dentro a la ventana
		
		contenido.add(jScrollPane1, BorderLayout.WEST);
		contenido.add(jComboBox1, BorderLayout.CENTER);
		contenido.add(jTextField1, BorderLayout.SOUTH);
		contenido.add(jTextField2, BorderLayout.EAST);
		

		pack();
	}                        

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
		Date date = new Date();
		jTextField1.setText (date.toString());
	}                                        

	private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {                                    
		// TODO add your handling code here:
		JList objetoEvento = (JList)evt.getSource();
		jTextField1.setText(objetoEvento.getSelectedValue().toString());
	}                                   

	private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
		JComboBox objetoEvento2 = (JComboBox) evt.getSource();
		jTextField2.setText(objetoEvento2.getSelectedItem().toString());
	}                                          


	public static void main(String args[]) { 
		new Ventana().setVisible(true);
	}

}                  

