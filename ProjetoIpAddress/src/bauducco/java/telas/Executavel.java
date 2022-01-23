package bauducco.java.telas;

import java.io.IOException;

import javax.swing.JOptionPane;

public class Executavel {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		try {
		SetIPAddress telaSetIp = new SetIPAddress();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro ao Processar!!!\n"+e.getMessage());
			e.printStackTrace();
		}
	}

}
