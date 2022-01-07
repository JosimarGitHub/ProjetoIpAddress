package bauducco.java.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bauducco.java.classes.IpAddress;

public class CalculoSubRedesIPV4 extends JDialog {
	
	// Criação do Objeto ipAddress
	IpAddress enderecoIp = new IpAddress();
		
	private JPanel painel = new JPanel(new GridBagLayout());
	
	private JLabel label1 = new JLabel("Endereço IP:");
	private JLabel label2 = new JLabel("Sub-Rede:");
	private JLabel label3 = new JLabel("Mascara de Rede:");

	
	private JTextField entradaIP = new JTextField();
	private JTextField entradaMascara = new JTextField();
	private JTextArea saidaSubrede = new JTextArea();
	
	private JButton calcular = new JButton("Calcular");
	
	public CalculoSubRedesIPV4() {

		setSize(new Dimension(500, 500));
		setTitle("Calculadora de SubRedes-IPV4");
		setLocationRelativeTo(null);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(5, 70, 5, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		
		label1.setPreferredSize(new Dimension(100, 25));
		painel.add(label1,gridBagConstraints);
		
		gridBagConstraints.gridx++;
	
		
		entradaIP.setPreferredSize(new Dimension(200, 25));
		painel.add(entradaIP,gridBagConstraints);
		

		gridBagConstraints.gridx--;
		gridBagConstraints.gridy++;
		
		label3.setPreferredSize(new Dimension(120, 25));
		painel.add(label3,gridBagConstraints);
		
		gridBagConstraints.gridx++;
	
		
		entradaMascara.setPreferredSize(new Dimension(200, 25));
		painel.add(entradaMascara,gridBagConstraints);
		

		gridBagConstraints.gridx--;
		gridBagConstraints.gridy++;
		
		
		calcular.setPreferredSize(new Dimension(100, 25));
		painel.add(calcular,gridBagConstraints);
		if(entradaIP.getText()== "" ) {
			calcular.setEnabled(false);
		}else {
			calcular.setEnabled(true);
		}
		calcular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enderecoIp.setIpAddress(entradaIP.getText());
				enderecoIp.getMascAdress().setMascAddress(entradaMascara.getText());
				saidaSubrede.setText("Endereço IP: " + enderecoIp.getIpAddress() 
						+ "\nEndereço Rede: "+enderecoIp.getNetworkAddress()
						+"\nIP's utilizáveis: "+ enderecoIp.getSubNetRangeFirst()+" - "+enderecoIp.getSubNetRangeLast()
						+"\nEndereço Broadcast: "+enderecoIp.getBroadcast()
						+"\nClasse de IP: "+enderecoIp.getIpClass()
						+"\nTipo de IP: "+ enderecoIp.getIpType()
						+"\nEndereco IP short: "+enderecoIp.getIpAddress()+"/"+enderecoIp.getMascAdress().getCIDRNotation() 
						+ "\nMascara de Rede: "+ enderecoIp.getMascAdress().getMascAddress()
						+"\nNotação CIDR: /"+ enderecoIp.getMascAdress().getCIDRNotation()
						+"\nNumero de hosts utilizáveis: " + enderecoIp.getMascAdress().getUsebleHosts());
				
			}
		});
		
		gridBagConstraints.gridy++;
		
		label2.setPreferredSize(new Dimension(100, 25));
		painel.add(label2,gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.gridwidth = 2;
		
		saidaSubrede.setPreferredSize(new Dimension(350, 200));
		saidaSubrede.setEditable(false);
		painel.add(saidaSubrede,gridBagConstraints);
		
		add(painel,BorderLayout.WEST);
		setVisible(true);
	
		
		addWindowListener(new WindowAdapter() {
		
		public void windowClosing(WindowEvent evt) {
			
			System.exit(0);
		}
		
		});
	}


}
