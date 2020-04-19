package ohjelmaTaulut;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Luokka joka näyttää tietoja ohjelmasta
 * @version 24.2.2020
 * @author villeh
 * 
 */
public class TietojaTaulu extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TietojaTaulu dialog = new TietojaTaulu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TietojaTaulu() {
		setResizable(false);
		setBounds(100, 100, 300, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JTextArea textYksi = new JTextArea(2, 20);
			textYksi.setWrapStyleWord(true);
			textYksi.setLineWrap(true);
			textYksi.setEditable(false);
			textYksi.setBackground((Color) null);
			textYksi.setText("Tämä ohjelma on NHL Fantasy Liigan virallinen tulosrekisteri. ");
			contentPanel.add(textYksi);
		}
		{
			JTextArea textKaksi = new JTextArea(2, 20);
			textKaksi.setLineWrap(true);
			textKaksi.setWrapStyleWord(true);
			textKaksi.setEditable(false);
			textKaksi.setText("Tulosrekisteri on luotu vuosien 2019-20 aikana konsulttiyhtiö KKHC:n toimesta. Älä kysy hintaa...");
			textKaksi.setBackground((Color) null);
			contentPanel.add(textKaksi);
		}
		{
			JTextArea textKolme = new JTextArea(2, 20);
			textKolme.setLineWrap(true);
			textKolme.setEditable(false);
			textKolme.setWrapStyleWord(true);
			textKolme.setText("Ohjelma on kirjoitettu Javalla. Sen toimintalogiikka on ajaton eikä se tarvitse päivityksiä.");
			textKolme.setBackground((Color) null);
			contentPanel.add(textKolme);
		}
		{
			JTextArea textNelja = new JTextArea(1, 20);
			textNelja.setLineWrap(true);
			textNelja.setEditable(false);
			textNelja.setWrapStyleWord(true);
			textNelja.setText("KKHC:n puolesta:");
			textNelja.setBackground((Color) null);
			contentPanel.add(textNelja);
		}
		
		{
			JTextArea textViisi = new JTextArea(2, 17);
			textViisi.setLineWrap(true);
			textViisi.setEditable(false);
			textViisi.setWrapStyleWord(true);
			textViisi.setText("Kuikka Koponen,\nMadrid 25.2.2020");
			Font newTextAreaFont=new Font(textViisi.getFont().getName(),Font.ITALIC+Font.BOLD,textViisi.getFont().getSize()); 
			
			textViisi.setFont(newTextAreaFont);
			textViisi.setBackground((Color) null);
			contentPanel.add(textViisi);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Kiitos tiedosta");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
