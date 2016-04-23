package vista;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import modelo.AccesoDatos;

import java.util.*;
import java.awt.*;

public class ConsultaCatalogoMySQL extends JPanel {
	static JFrame frame;
	private static final long serialVersionUID = 1L;
	String bbdd, tabla;
	JList l_bd;
	JList l_tablas;
	JTable t_registros;
	String newline = "\n";
	DefaultListModel lmBD, lmT;
	// MiModeloTabla tm;
	DefaultTableModel tm;

	@SuppressWarnings("unchecked")
	public ConsultaCatalogoMySQL() {
		super(new BorderLayout());

		/* JList de bdatos */
		ArrayList<String> bdatos = new AccesoDatos().getSchemas();
		lmBD = new DefaultListModel();
		for (String bdato : bdatos)
			lmBD.addElement(bdato);
		l_bd = new JList(lmBD);

		l_bd.addListSelectionListener(new ListaBDatosHandler());

		/* JList de tablas */
		ArrayList<String> tablasSchema = new AccesoDatos().getTablesSchemas("paro");
		lmT = new DefaultListModel();
		for (String tabla : tablasSchema)
			l_tablas = new JList(lmT);

		l_tablas.addListSelectionListener(new ListaTablasHandler());

		tm = null;
		
		t_registros = new JTable();
		

		JScrollPane bdPane = new JScrollPane(l_bd);

		JScrollPane tablasPane = new JScrollPane(l_tablas, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
		JPanel bdContainer = new JPanel(new GridLayout(1, 1));
		bdContainer.setBorder(BorderFactory.createTitledBorder("Bases de datos"));
		bdContainer.add(bdPane);
		JPanel regsContainer = new JPanel(new GridLayout(1, 1));
		regsContainer.setBorder(BorderFactory.createTitledBorder("Registros"));
		regsContainer.add(t_registros);
		tablasPane.setPreferredSize(new Dimension(300, 100));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		panelSuperior.add(bdContainer);
		panelSuperior.add(regsContainer);

		panelSuperior.setMinimumSize(new Dimension(400, 50));
		panelSuperior.setPreferredSize(new Dimension(400, 110));
		splitPane.add(panelSuperior);

		JPanel panelinferior = new JPanel(new BorderLayout());
		JPanel tablasContainer = new JPanel(new GridLayout(1, 1));
		tablasContainer.setBorder(BorderFactory.createTitledBorder("Tablas"));
		panelinferior.add(tablasContainer, BorderLayout.CENTER);
		tablasContainer.add(tablasPane);
		panelinferior.setPreferredSize(new Dimension(450, 135));
		splitPane.add(panelinferior);
	}

	class ListaBDatosHandler implements ListSelectionListener {
		@SuppressWarnings({ "unchecked" })
		public void valueChanged(ListSelectionEvent evt) {
			if (!evt.getValueIsAdjusting()) {
				bbdd = l_bd.getSelectedValue().toString();
				ArrayList<String> tablasSchema = new AccesoDatos().getTablesSchemas(bbdd);

				lmT.removeAllElements();
				for (String tabla : tablasSchema)
					lmT.addElement(tabla);
			}
		}
	}

	class ListaTablasHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent evt) {
			if (!evt.getValueIsAdjusting() && (l_tablas.getSelectedValue() != null) ) {
				tabla = (String) l_tablas.getSelectedValue();
				System.out.println(bbdd + "," + tabla);
				tm = new AccesoDatos().getRegistrosTablaBD_DTM(tabla, bbdd);
				t_registros.setModel(tm);
				//frame.pack();
			}
		}
	}

		public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.

		frame = new JFrame("Consulta catálogo mySQL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ConsultaCatalogoMySQL();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
