package bauducco.java.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import bauducco.java.classes.IpAddress;

public class SetIPAddress extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2504562347407582961L;
	//#########################################################
	//***************** Criação de Objetos *******************
	//#########################################################
	
	//Objeto IPAdress para controle de endereços IP
		IpAddress enderecoIp = new IpAddress();
		IpAddress enderecoGateway = new IpAddress();
		IpAddress enderecoDNS1 = new IpAddress();
		IpAddress enderecoDNS2 = new IpAddress();
		boolean dhcp;
		
		//Objetos Javax componentes da Tela
	 
		private JPanel painel = new JPanel(new GridBagLayout());
		private JLabel label1 = new JLabel("Endereço IP:");
		private JLabel label2 = new JLabel("Mascara de Rede:");
		private JLabel label3 = new JLabel("Endereço Gateway:");
		private JLabel label4 = new JLabel("DNS Primario:");
		private JLabel label5 = new JLabel("DNS Secundario:");
		private JLabel label6 = new JLabel("DHCP IP AUTOMATICO");
		private JTextArea resultado = new JTextArea();
		private JButton set = new JButton("Set");
		private JTextField entradaIP = new JTextField();
		private JTextField entradaMascara = new JTextField();
		private JTextField entradaGateway = new JTextField();
		private JTextField entradaDNS1 = new JTextField();
		private JTextField entradaDNS2 = new JTextField();
		private JComboBox<String> box = new JComboBox<String>();
		private JCheckBox dhcpFalse = new JCheckBox("Desabilita");
		private JCheckBox dhcpTrue = new JCheckBox("Habilita");
		
		//#########################################################
		//**** Criação do Painel com Componetes e suas Funçoes ****
		//#########################################################
		
		/************************Metodo Construtor da Tela***********************/
	
		public SetIPAddress()throws IOException {
			
			//Criando pasta paraArquivos
			criarDiretorios();
			
			List<String> boxItens = new ArrayList<String>();
			
			//Obtendo placas de rede do PC para adicionar no combo Box
			criarShowInterfaces(boxItens);
			
			//Iniciando o DHCP desabilitado
			dhcpFalse.setSelected(true);
			
			/*********Distribuicao,dimensionamento e funçoes dos Componentes***************/
			
			//Painel
			setSize(new Dimension(500, 550));
			setTitle("SET IP - Interfaces de Rede");
			setResizable(false);
			setLocationRelativeTo(null);
			
			//Gerenciador de Layout
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			
			//Posicionamento e configuraçoes
			gridBagConstraints.weightx=500;
			gridBagConstraints.weighty=500;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridwidth =3;
			gridBagConstraints.insets = new Insets(5, 35, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			
			//Combo Box
			box.setPreferredSize(new Dimension(350, 25));
			for (String string : boxItens) {
				box.addItem(string);
			}
			painel.add(box,gridBagConstraints);
			
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridy=1;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//Label de descriçao
			painel.add(label1,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			entradaIP.setPreferredSize(new Dimension(120, 25));
			entradaIP.setFont(new Font("Times",Font.BOLD,14));
			entradaIP.setForeground(Color.DARK_GRAY);
			painel.add(entradaIP,gridBagConstraints);
			
			gridBagConstraints.gridx=2;
			
			//Label de descriçao
			painel.add(label6,gridBagConstraints);
			
			gridBagConstraints.gridy=2;
			
			painel.add(dhcpFalse,gridBagConstraints);
			
			gridBagConstraints.gridy=3;
			
			painel.add(dhcpTrue,gridBagConstraints);
	
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=2;
			gridBagConstraints.gridwidth = 1;
			
			//Label de descriçao
			painel.add(label2,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			entradaMascara.setPreferredSize(new Dimension(120, 25));
			entradaMascara.setFont(new Font("Times",Font.BOLD,14));
			entradaMascara.setForeground(Color.DARK_GRAY);
			painel.add(entradaMascara,gridBagConstraints);
			
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=3;
			
			//Label de descriçao
			painel.add(label3,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			entradaGateway.setPreferredSize(new Dimension(120, 25));
			entradaGateway.setFont(new Font("Times",Font.BOLD,14));
			entradaGateway.setForeground(Color.DARK_GRAY);
			painel.add(entradaGateway,gridBagConstraints);

			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=4;
			
			//Label de descriçao
			painel.add(label4,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			entradaDNS1.setPreferredSize(new Dimension(120, 25));
			entradaDNS1.setFont(new Font("Times",Font.BOLD,14));
			entradaDNS1.setForeground(Color.DARK_GRAY);
			painel.add(entradaDNS1,gridBagConstraints);
			
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=5;
			
			//Label de descriçao
			painel.add(label5,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			entradaDNS2.setPreferredSize(new Dimension(120, 25));
			entradaDNS2.setFont(new Font("Times",Font.BOLD,14));
			entradaDNS2.setForeground(Color.DARK_GRAY);
			painel.add(entradaDNS2,gridBagConstraints);
			
			gridBagConstraints.insets = new Insets(5, 10, 10, 5);
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=6;
			gridBagConstraints.gridwidth = 3;
			
			set.setPreferredSize(new Dimension(120, 25));
			painel.add(set,gridBagConstraints);
			if(entradaIP.getText()== "" ) {
				set.setEnabled(false);
			}else {
				set.setEnabled(true);
			}
			set.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					enderecoIp.setIpAddress(entradaIP.getText());
					enderecoIp.getMascAdress().setMascAddress(entradaMascara.getText());
					enderecoGateway.setIpAddress(entradaGateway.getText());
					enderecoDNS1.setIpAddress(entradaDNS1.getText());
					enderecoDNS2.setIpAddress(entradaDNS2.getText());
					dhcp = dhcpTrue.isSelected();
					
					if (enderecoIp.isValidAddress()||dhcp) {
						if (enderecoIp.getMascAdress().isValidMasc()||dhcp) {
							if ((enderecoGateway.isValidAddress() || enderecoGateway.getIpAddress().isEmpty()||dhcp)) {
								if ((enderecoDNS1.isValidAddress() || enderecoDNS1.getIpAddress().isEmpty()||dhcp)) {
									if ((enderecoDNS2.isValidAddress() || enderecoDNS2.getIpAddress().isEmpty()||dhcp)) {
										try {
											setIPV4(enderecoIp, enderecoGateway, enderecoDNS1, enderecoDNS2,
													box.getSelectedItem().toString(),dhcp);

										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

										try {
											Thread.sleep(15000);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										List<String> texto = new ArrayList<String>();
										try {
											showInterface(box.getSelectedItem().toString(), texto);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										resultado.setText("");
										for (String string : texto) {
											resultado.setText(resultado.getText() + string + "\n");
										}
									} else {
										JOptionPane.showMessageDialog(null,
												"DNS Secundario inválido\nCaso nao utilizado deixar em branco");
									}

								} else {
									JOptionPane.showMessageDialog(null,
											"DNS Primario inválido\nCaso nao utilizado deixar em branco");
								}

							} else {
								JOptionPane.showMessageDialog(null,
										"Gateway inválido\nCaso nao utilizado deixar em branco");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Mascara inválida");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Endereço Invalido");
					}

				}
			});
			
			gridBagConstraints.gridy=7;
			
			resultado.setPreferredSize(new Dimension(450, 230));
			resultado.setSelectedTextColor(Color.RED);
			resultado.setFont(new Font("Arial",Font.BOLD,14));
			resultado.setForeground(Color.BLACK);
			resultado.setBackground(Color.lightGray);
			resultado.setEditable(false);
			painel.add(resultado,gridBagConstraints);
			
			add(painel,BorderLayout.WEST);
			setVisible(true);
			
			dhcpFalse.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					dhcpTrue.setSelected(false);
					dhcpFalse.setSelected(true);
					entradaIP.setEditable(true);
					entradaMascara.setEditable(true);
					entradaGateway.setEditable(true);
					entradaDNS1.setEditable(true);
					entradaDNS2.setEditable(true);	

					
				}
			});
			
			dhcpTrue.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					dhcpFalse.setSelected(false);
					entradaIP.setEditable(false);
					entradaIP.setText("");
					entradaMascara.setEditable(false);
					entradaMascara.setText("");
					entradaGateway.setEditable(false);
					entradaGateway.setText("");
					entradaDNS1.setEditable(false);
					entradaDNS1.setText("");
					entradaDNS2.setEditable(false);	
					entradaDNS2.setText("");

				}
			});
			
		
			
			addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent evt) {
				
				deletarPasta();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.exit(0);
			}
			
			});
		}
		
		public static void criarDiretorios() {
			
			File criarPasta = new File(System.getProperty("user.home"));
			criarPasta = new File(criarPasta,"SetIPV4");
			boolean criarPastaOk = false;
			
			if(!criarPasta.exists()) {
				criarPastaOk= criarPasta.mkdir();
			}
			if(!criarPastaOk) {
				JOptionPane.showMessageDialog(null,"Não foi possivel criar a pasta\n"
						+ "no caminho especificado");
			}
			
		}
		
		public static List<String> criarShowInterfaces(List<String> string) throws IOException {
			
			File showIntefaces = new File(System.getProperty("user.home")+"\\SetIPV4\\showInterfaces.cmd");
			FileWriter criaShowInterfaces = new FileWriter(showIntefaces);

			if (!showIntefaces.exists()) {

				showIntefaces.createNewFile();
			}

			criaShowInterfaces.write("chcp 1252\n netsh interface ipv4 show config >"+System.getProperty("user.home")+"\\SetIPV4\\interfaces.txt");
			criaShowInterfaces.flush();
			criaShowInterfaces.close();
			Desktop.getDesktop().open(showIntefaces);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			FileInputStream showInterfacesOut = new FileInputStream(System.getProperty("user.home")+"\\SetIPV4\\interfaces.txt");

			Scanner lerInterfaces = new Scanner(showInterfacesOut);

			while (lerInterfaces.hasNext()) {

				String linhaInterfaces = lerInterfaces.nextLine();
				if (linhaInterfaces != null && !linhaInterfaces.isEmpty()) {

					int posInicial = linhaInterfaces.indexOf("\"") + 1;
					String Aux = linhaInterfaces.substring(posInicial, linhaInterfaces.length());

					int posFinal = Aux.indexOf("\"");
					if (posFinal > 0) {
						String interfaceRede = Aux.substring(0, Aux.indexOf("\""));
						string.add(interfaceRede);
					}
				}
			}
			lerInterfaces.close();
			return string;
		}
		
		public static void setIPV4(IpAddress enderecoIp,IpAddress enderecoGateway,
				IpAddress enderecoDNS1,IpAddress enderecoDNS2,String interfaceRede,boolean dhcpTrue) throws IOException {

			File setIPV4 = new File(System.getProperty("user.home")+"\\SetIPV4\\SetIPV4.cmd");
			FileWriter criaSetIPV4 = new FileWriter(setIPV4);
			
			if(dhcpTrue) {
				
				criaSetIPV4.write("netsh interface ipv4 set address \"" + interfaceRede + "\" dhcp");
				
				criaSetIPV4.write("\n netsh interface ip set dns \"" + interfaceRede + "\" dhcp");
				criaSetIPV4.flush();
				criaSetIPV4.close();
				Desktop.getDesktop().open(setIPV4);
				
			}else {

			criaSetIPV4.write("netsh interface ipv4 set address \"" + interfaceRede + "\" static "
					+ enderecoIp.getIpAddress() + " " + enderecoIp.getMascAdress().getMascAddress() + " "
					+ enderecoGateway.getIpAddress());
			 if(!enderecoDNS1.getIpAddress().isEmpty()) {
			criaSetIPV4.write("\n netsh interface ip set dns \"" + interfaceRede + "\" static "+enderecoDNS1.getIpAddress());
			 }else {
				 criaSetIPV4.write("\n netsh interface ip set dns \"" + interfaceRede + "\" static none");
			 }
			 if(!enderecoDNS2.getIpAddress().isEmpty()) {
			criaSetIPV4.write("\n netsh interface ip add dnsservers \"" + interfaceRede + "\" "+enderecoDNS2.getIpAddress()+" index=2");
			 }else {
				 criaSetIPV4.write("\n netsh interface ip add dnsservers \"" + interfaceRede + "\" none index=2"); 
			 }
			criaSetIPV4.flush();
			criaSetIPV4.close();
			Desktop.getDesktop().open(setIPV4);
			}
		}
		
		public static List<String> showInterface(String nomeInterface,List<String> listString) throws IOException {
			
			
			File showInteface = new File(System.getProperty("user.home") + "\\SetIPV4\\showInterface.cmd");
			FileWriter criaShowInterface = new FileWriter(showInteface);

			if (!showInteface.exists()) {

				showInteface.createNewFile();
			}

			criaShowInterface.write("chcp 1252\n netsh interface ipv4 show config \""+nomeInterface+"\" >"
					+ System.getProperty("user.home") + "\\SetIPV4\\interface.txt");
			criaShowInterface.flush();
			criaShowInterface.close();
			Desktop.getDesktop().open(showInteface);
			
			File showIntefaceTxt = new File(System.getProperty("user.home") + "\\SetIPV4\\interface.txt");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			FileInputStream showInterfaceOut = new FileInputStream(
					System.getProperty("user.home") + "\\SetIPV4\\interface.txt");

			Scanner lerInterface = new Scanner(showInterfaceOut);

			while (lerInterface.hasNext()) {
				String Aux = lerInterface.nextLine();
				listString.add(Aux.replaceAll("\\s+ "," "));

			}
			lerInterface.close();
			showIntefaceTxt.delete();
			return listString;
		}
		
		public static void deletarPasta() {
			
			File deletarPasta = new File(System.getProperty("user.home")+"\\SetIPV4");
			File[] arquivos = deletarPasta.listFiles();
			
			for (File file : arquivos) {
				
				file.delete();
			}
			
			deletarPasta.delete();
			
		}
		
}
