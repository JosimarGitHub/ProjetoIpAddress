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
import java.io.InputStream;
import java.net.URISyntaxException;
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
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import bauducco.java.classes.IpAddress;

public class SetIPAddress extends JFrame {
	
	private static final long serialVersionUID = -2504562347407582961L;
	
	//#########################################################
	//***************** Criação de Objetos *******************
	//#########################################################
	
	//Objeto IPAdress para controle de endereços IP
		private IpAddress enderecoIp = new IpAddress();
		private IpAddress enderecoGateway = new IpAddress();
		private IpAddress enderecoDNS1 = new IpAddress();
		private IpAddress enderecoDNS2 = new IpAddress();
		private boolean dhcp;
		
		
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
		
		
		//Metodo para animar a tela quando esta em processamento
		private Runnable animacao = new Runnable() {

			@Override
			public void run() {
				
				// Gerenciador de Layout
				GridBagConstraints gridBagConstraints = new GridBagConstraints();

				// Posicionamento e configuraçoes
				gridBagConstraints.weightx = 550;
				gridBagConstraints.weighty = 550;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 7;
				gridBagConstraints.gridwidth = 3;
				gridBagConstraints.insets = new Insets(5, 10, 10, 5);
				gridBagConstraints.anchor = GridBagConstraints.CENTER;

				// A cor da String quando a barra de valor estiver sobre ela
				UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
				// A cor da String quando a barra de valor NÃO estiver sobre ela
				UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
				
				JProgressBar barra = new JProgressBar();
				barra.setBorderPainted(true);
				barra.setBackground(Color.GRAY);
				barra.setForeground(Color.GREEN);
				barra.setMaximum(1000);
				barra.setStringPainted(true);
				barra.setPreferredSize(new Dimension(300, 30));
				painel.add(barra, gridBagConstraints);
				barra.setVisible(true);
				
				resultado.setFont(new Font("Arial",Font.BOLD,24));
				resultado.setText("\n\n                        Processando");

				
				while (true) {
					
					barra.setVisible(true);
					barra.setEnabled(true);
					
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					barra.setValue(barra.getValue() + 10);
				}

			}
		};
		
		//start da Animaçao quando esta processando
		private Thread eventoAnimacao;
		
		//Metodo a ser executado quando o botao for acionado
		private Runnable eventoSet = new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
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
									
									eventoAnimacao = new Thread(animacao);
									eventoAnimacao.start();
									
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
									resultado.setFont(new Font("Arial",Font.BOLD,16));
									resultado.setText("");
									for (String string : texto) {
										resultado.setText(resultado.getText() + string + "\n");
									}
									eventoAnimacao.stop();
									
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
				set.setVisible(true);
			};
		};
		
		//Start do Metodo quando aciona o botao
		private Thread eventoBotao;
		
		//#########################################################
		//**** Criação do Painel com Componetes e suas Funçoes ****
		//#########################################################
		
		/************************Metodo Construtor da Tela***********************/
	
		public SetIPAddress()throws IOException, URISyntaxException {
			
			
			//Criando pasta paraArquivos
			criarDiretorios();
			
			List<String> boxItens = new ArrayList<String>();
			
			//Obtendo placas de rede do PC para adicionar no combo Box
			criarShowInterfaces(boxItens);
			
			//Iniciando o DHCP desabilitado
			dhcpFalse.setSelected(true);
			//barra.setVisible(false);
			
			
			/*********Distribuicao,dimensionamento e funçoes dos Componentes***************/
			
			//Painel
			setSize(new Dimension(550, 550));
			setTitle("SET IP - Interfaces de Rede");
			setResizable(false);
			setLocationRelativeTo(null);
			
			//Gerenciador de Layout
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			
			//Posicionamento e configuraçoes
			gridBagConstraints.weightx=550;
			gridBagConstraints.weighty=550;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridwidth =3;
			gridBagConstraints.insets = new Insets(5, 30, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			
			//Combo Box
			box.setPreferredSize(new Dimension(350, 25));
			box.setFont(new Font("Arial",Font.PLAIN,16));
			//Adicionado itens na combo Box
			for (String string : boxItens) {
				box.addItem(string);
			}
			painel.add(box,gridBagConstraints);
			
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridy=1;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			
			//Label de descriçao
			label1.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label1,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			//JTextFild escreve IP Address
			entradaIP.setPreferredSize(new Dimension(120, 25));
			entradaIP.setFont(new Font("Times",Font.BOLD,14));
			entradaIP.setForeground(Color.DARK_GRAY);
			painel.add(entradaIP,gridBagConstraints);
			
			gridBagConstraints.gridx=2;
			
			//Label de descriçao
			label6.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label6,gridBagConstraints);
			
			gridBagConstraints.gridy=2;
			
			dhcpFalse.setFont(new Font("Times",Font.PLAIN,14));
			painel.add(dhcpFalse,gridBagConstraints);
			
			gridBagConstraints.gridy=3;
			
			dhcpTrue.setFont(new Font("Times",Font.PLAIN,14));
			painel.add(dhcpTrue,gridBagConstraints);
	
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=2;
			gridBagConstraints.gridwidth = 1;
			
			//Label de descriçao
			label2.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label2,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			//JTextFild escreve Mascara de Rede
			entradaMascara.setPreferredSize(new Dimension(120, 25));
			entradaMascara.setFont(new Font("Times",Font.BOLD,14));
			entradaMascara.setForeground(Color.DARK_GRAY);
			painel.add(entradaMascara,gridBagConstraints);
			
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=3;
			
			//Label de descriçao
			label3.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label3,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			//JTextFild escreve Gateway
			entradaGateway.setPreferredSize(new Dimension(120, 25));
			entradaGateway.setFont(new Font("Times",Font.BOLD,14));
			entradaGateway.setForeground(Color.DARK_GRAY);
			painel.add(entradaGateway,gridBagConstraints);

			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=4;
			
			//Label de descriçao
			label4.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label4,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			//JTextFild escreve DNS Primario
			entradaDNS1.setPreferredSize(new Dimension(120, 25));
			entradaDNS1.setFont(new Font("Times",Font.BOLD,14));
			entradaDNS1.setForeground(Color.DARK_GRAY);
			painel.add(entradaDNS1,gridBagConstraints);
			
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=5;
			
			//Label de descriçao
			label5.setFont(new Font("Times",Font.BOLD,14));
			painel.add(label5,gridBagConstraints);
			
			gridBagConstraints.gridx=1;
			
			//JTextFild escreve DNS Secundario
			entradaDNS2.setPreferredSize(new Dimension(120, 25));
			entradaDNS2.setFont(new Font("Times",Font.BOLD,14));
			entradaDNS2.setForeground(Color.DARK_GRAY);
			painel.add(entradaDNS2,gridBagConstraints);
			
			gridBagConstraints.insets = new Insets(5, 10, 10, 5);
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.gridx=0;
			gridBagConstraints.gridy=6;
			gridBagConstraints.gridwidth = 3;
			
			//JButton inicia o processo set IP na interface selecionada
			set.setPreferredSize(new Dimension(120, 25));
			painel.add(set,gridBagConstraints);
			
			//Monitora evento do botao ser apertado
			set.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					eventoBotao = new Thread(eventoSet);
					eventoBotao.start();
					set.setVisible(false);
				}
			});
			
			 gridBagConstraints.gridy=7;
			 
			//Imprime a configuraçao da Interface selecionada apos o set
			gridBagConstraints.insets = new Insets(5, 17, 10, 5);
			resultado.setPreferredSize(new Dimension(500, 230));
			resultado.setSelectedTextColor(Color.RED);
			resultado.setFont(new Font("Arial",Font.BOLD,14));
			resultado.setForeground(Color.BLACK);
			resultado.setBackground(Color.lightGray);
			resultado.setEditable(false);
			painel.add(resultado,gridBagConstraints);
		     
			add(painel,BorderLayout.WEST);
			setVisible(true);
			
			//Monitora se o check box foi selecionado
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
			
			//Monitora se o check box foi selecionado
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
			
			//Monitora se a aplicaçao foi fechada
			addWindowListener(new WindowAdapter() {
			
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent evt) {
				
				deletarPasta();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(eventoAnimacao != null){
					eventoAnimacao.stop();
				}
				if(eventoBotao != null) {
				eventoBotao.stop();
				}
				System.exit(0);
			}
			
			});
		}
		
		//Metodo para criar pasta de arquivos
		@SuppressWarnings("unused")
		public void criarDiretorios() throws IOException, URISyntaxException {
			
			File criarPasta = new File(System.getProperty("user.home"));
			criarPasta = new File(criarPasta,"SetIPV4");
			boolean criarPastaOk = false;
			criarPastaOk= criarPasta.mkdir();
			
			if(criarPastaOk) {	
				
				InputStream arquivo = (this.getClass().getClassLoader().getResourceAsStream("Administrador.txt"));
				
				File startCmd = new File(System.getProperty("user.home")+"\\SetIPV4\\start.cmd");
				
				FileWriter criastartCmd = new FileWriter(startCmd);
		
				Scanner lerStart = new Scanner(arquivo);
				
				String Aux = "";

				while (lerStart.hasNext()) {
					
					Aux = lerStart.nextLine()+"\n";
					criastartCmd.write(Aux);
					
				}
				
				criastartCmd.write("cmd /k SetIPV4.cmd");
				criastartCmd.flush();
				criastartCmd.close();
				lerStart.close();
				
			}
		}
		
		//Metodo criar arquivo.cmd para criar um txt com as interfaces de rede disponiveis 
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
				Thread.sleep(2000);
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
		
		//Motodo cria e executa arquivo.cmd para setar o IP na placa de rede 
		public static void setIPV4(IpAddress enderecoIp,IpAddress enderecoGateway,
				IpAddress enderecoDNS1,IpAddress enderecoDNS2,String interfaceRede,boolean dhcpTrue) throws IOException {

			File setIPV4 = new File(System.getProperty("user.home")+"\\SetIPV4\\SetIPV4.cmd");
			File startCmd = new File(System.getProperty("user.home")+"\\SetIPV4\\start.cmd");
			FileWriter criaSetIPV4 = new FileWriter(setIPV4);
			
			if(dhcpTrue) {
				
				criaSetIPV4.write("chcp 1252\nnetsh interface ipv4 set address \"" + interfaceRede + "\" dhcp");
				criaSetIPV4.write("\n netsh interface ipv4 set dns \"" + interfaceRede + "\" dhcp");
				criaSetIPV4.write("\ntimeout 1");
				criaSetIPV4.write("\nexit");
				criaSetIPV4.flush();
				criaSetIPV4.close();
				Desktop.getDesktop().open(startCmd);
				
			}else {

			criaSetIPV4.write("chcp 1252\nnetsh interface ipv4 set address \"" + interfaceRede + "\" static "
					+ enderecoIp.getIpAddress() + " " + enderecoIp.getMascAdress().getMascAddress() + " "
					+ enderecoGateway.getIpAddress());
			 if(!enderecoDNS1.getIpAddress().isEmpty()) {
			criaSetIPV4.write("\n netsh interface ipv4 set dns \"" + interfaceRede + "\" static "+enderecoDNS1.getIpAddress());
			 }else {
				 criaSetIPV4.write("\n netsh interface ipv4 set dns \"" + interfaceRede + "\" static none");
			 }
			 if(!enderecoDNS2.getIpAddress().isEmpty()) {
				 criaSetIPV4.write("\n netsh interface ipv4 add dnsservers \"" + interfaceRede + "\" "+enderecoDNS2.getIpAddress()+" index=2");
				 criaSetIPV4.write("\ntimeout 1");
				 criaSetIPV4.write("\nexit");
			 }else {
				 criaSetIPV4.write("\n netsh interface ipv4 add dnsservers \"" + interfaceRede + "\" none index=2");
				 criaSetIPV4.write("\ntimeout 1");
				 criaSetIPV4.write("\nexit");
			 }
			criaSetIPV4.flush();
			criaSetIPV4.close();
			Desktop.getDesktop().open(startCmd);
			}
		}
		
		//Metodo cria e executa arquivo.cmd para criacao de um txt com a configuraçao da placa de rede onde foi feito o set de endereço
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
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			File showIntefaceTxt = new File(System.getProperty("user.home") + "\\SetIPV4\\interface.txt");
			
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
		
		//Metodo para apagar arquivos e pasta criados quando o aplicativo for fechado
		public static void deletarPasta() {
			
			File deletarPasta = new File(System.getProperty("user.home")+"\\SetIPV4");
			File[] arquivos = deletarPasta.listFiles();
			
			for (File file : arquivos) {
				
				file.delete();
			}
			
			deletarPasta.delete();
			
		}
		
}
