package vista;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modelo.Interfaz1;

public class ManejoEventos extends JFrame
// implements ActionListener, Interfaz1
{
	private Interfaz1 intf1; // se puede definir un objeto con tipo interfaz
	private JButton boton;
	private JLabel etiqueta;
	private JTextField texto;
	private JComboBox combo;

	public ManejoEventos() throws HeadlessException {

		super();

		// this.intf1.metodo1("123", 3);
		this.boton = new JButton();
		this.etiqueta = new JLabel("Mensaje");
		this.setTitle("MANEJO DE EVENTOS");
		Container contenido = this.getContentPane();
		boton.setText("Boton 1");
		String[] numeros = { "un", "dos", "tres" };
		combo = new JComboBox(numeros);
		this.texto = new JTextField("abcdefgh");

		this.setLayout(new FlowLayout());
		// gestión centralizada del evento
		/*
		 * boton.addActionListener(this); combo.addActionListener(this);
		 */
		ComboListener cL = new ComboListener();
		// boton.addActionListener(new BotonListener());
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				JComboBox combo = (JComboBox) evt.getSource();
				texto.setText(combo.getSelectedItem().toString());
			}
		});
		boton.addActionListener(new ActionListener() {
			/* Clase interna anónima */
			@Override
			public void actionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				JButton boton = (JButton) evt.getSource();
				texto.setText(boton.getText());
			}
		});
		// combo.addActionListener(cL);
		contenido.add(boton);
		contenido.add(combo);
		contenido.add(texto);
		this.pack();
		this.setVisible(true);
	}

	class BotonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			JButton boton = (JButton) evt.getSource();
			// Class<? extends Object> clase = objeto.getClass();

			// texto.setText(evt.getActionCommand());
			texto.setText(boton.getText());

		}

	}

	class ComboListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evt) {
			JComboBox combo = (JComboBox) evt.getSource();
			// Class<? extends Object> clase = objeto.getClass();

			// texto.setText(evt.getActionCommand());
			texto.setText(combo.getSelectedItem().toString());

		}

	}

	/*
	 * @Override public void actionPerformed(ActionEvent evt) {
	 * 
	 * //JButton bt = (JButton) evt.getSource(); Object objeto =
	 * evt.getSource(); Class<? extends Object> clase = objeto.getClass();
	 * 
	 * //texto.setText(evt.getActionCommand()); texto.setText(clase.getName());
	 * this.pack(); }
	 */
	public static void main(String[] args) {
		new ManejoEventos();
	}
	/*
	 * @Override public int metodo1(String param1, int param2) { // TODO
	 * Auto-generated method stub return 0; }
	 * 
	 * @Override public void metodo2(Float param1) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 * 
	 * @Override public Integer[] metodo3(float param1) { // TODO Auto-generated
	 * method stub return null; } Interna interna = new Interna(); public class
	 * Interna{
	 * 
	 * }
	 */
}
