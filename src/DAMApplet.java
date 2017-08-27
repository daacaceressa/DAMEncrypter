import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import PipesAndFilter.Generator;
import PipesAndFilter.Sink;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class DAMApplet {

	private JFrame frame;
	public static JTextArea resultField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DAMApplet window = new DAMApplet();
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
	public DAMApplet() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("DAM cipher");
		frame.setBounds(100, 100, 782, 367);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultListModel filters = new DefaultListModel<>();
		filters.addElement(XORFilter.NAME);
		filters.addElement(ComplementFilter.NAME);
		filters.addElement(ReverseFilter.NAME);
		filters.addElement(SwapFilter.NAME);
		
		DefaultListModel architecture = new DefaultListModel<>();

		JList filtersList = new JList();
		filtersList.setBorder(new LineBorder(Color.GRAY));
		filtersList.setBounds(24, 23, 135, 139);
		filtersList.setModel(filters);
		frame.getContentPane().add(filtersList);
		
		JLabel lblNewLabel = new JLabel("Message:");
		lblNewLabel.setBounds(24, 203, 100, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Key:");
		lblNewLabel_1.setBounds(24, 288, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JScrollPane messageScrollPane = new JScrollPane();
		messageScrollPane.setBounds(107, 203, 257, 69);
		frame.getContentPane().add(messageScrollPane);
		
		JTextArea messageTextField = new JTextArea();
		messageScrollPane.setViewportView(messageTextField);
		messageTextField.setWrapStyleWord(true);
		messageTextField.setLineWrap(true);
		
		JTextField keyTextField = new JTextField();
		keyTextField.setBounds(107, 283, 257, 29);
		frame.getContentPane().add(keyTextField);
		keyTextField.setColumns(10);
		
		JScrollPane architectureScrollPane = new JScrollPane();
		architectureScrollPane.setBounds(268, 23, 135, 139);
		frame.getContentPane().add(architectureScrollPane);
		
		JList architectureList = new JList();
		architectureScrollPane.setViewportView(architectureList);
		
		JScrollPane resultScrollPane = new JScrollPane();
		resultScrollPane.setBounds(500, 23, 232, 294);
		frame.getContentPane().add(resultScrollPane);
		
		resultField = new JTextArea();
		resultScrollPane.setViewportView(resultField);
		resultField.setLineWrap(true);
		resultField.setWrapStyleWord(true);
		resultField.setText("Result");
		resultField.setEditable(false);
		
		JButton addFilterButton = new JButton("Add");
		addFilterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				architecture.addElement(filtersList.getSelectedValue());
				architectureList.setModel(architecture);
			}
		});
		addFilterButton.setBounds(169, 40, 89, 23);
		frame.getContentPane().add(addFilterButton);
		
		JButton removeFilterButton = new JButton("Remove");
		removeFilterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (architectureList.getSelectedIndex() != -1 ) {
					architecture.remove(architectureList.getSelectedIndex());
					architectureList.setModel(architecture);
				}	
			}
		});
		removeFilterButton.setBounds(169, 74, 89, 23);
		frame.getContentPane().add(removeFilterButton);
		
		JButton encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// validateAll();
				resultField.setText("");
				encrypt(architecture.toArray(), messageTextField.getText(), keyTextField.getText());
			}
		});
		encryptButton.setBounds(388, 214, 89, 23);
		frame.getContentPane().add(encryptButton);
		
		JButton decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// validateAll();
				resultField.setText("");
				decrypt(architecture.toArray(), messageTextField.getText(), keyTextField.getText());
			}
		});
		decryptButton.setBounds(388, 264, 89, 23);
		frame.getContentPane().add(decryptButton);
	}
	
	protected void decrypt(Object[] arch, String message, String key) {
		Collections.reverse(Arrays.asList(arch));
		encrypt(arch, message, key);
	}

	protected void encrypt(Object[] arch, String message, String key) {
		ArrayList<String> filters = new ArrayList<>();
		for (int i = 0; i < arch.length; i++) {
			filters.add((String)arch[i]);
		}
		Architecture encryptArchitecture = new Architecture(filters);
		Generator<Message> generator = new DAMGenerator(encryptArchitecture.getStartingPipe(), message, key);
		Sink<Message> sink = new DAMSink(encryptArchitecture.getEndingPipe());

    // start all components
    generator.start();
    encryptArchitecture.start();
    sink.start();
	}
}
