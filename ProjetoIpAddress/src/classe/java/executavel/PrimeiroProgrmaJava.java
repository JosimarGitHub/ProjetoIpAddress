package classe.java.executavel;

import java.util.Scanner;

import bauducco.java.classes.IpAddress;

public class PrimeiroProgrmaJava {

	public static void main(String[] args) {
		
		
        /*=======================================================================================
		                                  Declara��o de Variaveis
		=========================================================================================*/
		// Cria��o do Objeto ipAddress
		IpAddress enderecoIp = new IpAddress();
		
		// Declara��o de Variavel ler teclado
		Scanner entrada = new Scanner(System.in);
		
		/*===========================================================================================================
		 *                                               ENTRADA DE DADOS                                            *
		 ============================================================================================================*/
		
		/*Recebe endereco de rede*/
		do {
			
			/*Imprime mensagem para usuario*/
			System.out.print("Insira o Endere�o: ");
			
			 /*Recebe valor digitado*/
			enderecoIp.setIpAddress(entrada.next());
			
			/*Verifica se � valido*/
			if (!enderecoIp.isValidAddress()) {
				System.out.println("Endere�o Invalido");
			}

		} while (!enderecoIp.isValidAddress());
		

		 /*Recebe a mascara de rede*/
		do {
			
			 /*Imprime mensagem para usuario*/
			System.out.print("Insira a Mascara de Rede: ");
			
			/*Recebe valor digitado*/
			enderecoIp.getMascAdress().setMascAddress(entrada.next());
			/*Verifica se � valido*/
			if (!enderecoIp.getMascAdress().isValidMasc()) {
				System.out.println("Mascara inv�lida");
			}
		} while (!enderecoIp.getMascAdress().isValidMasc());
		
		
        /*Espa�o para Impress�o do Resultado*/
		System.out.printf("\n\n");

		 /*Metodo saida de Resultado para usuario caso IP e MASCARA validos*/
		System.out.println("Endere�o IP: "+enderecoIp.getIpAddress());
		System.out.println("Endere�o Rede: "+enderecoIp.getNetworkAddress());
		System.out.println("IP's utiliz�veis: "+ enderecoIp.getSubNetRangeFirst()+" - "+enderecoIp.getSubNetRangeLast());
		System.out.println("Endere�o Broadcast: "+enderecoIp.getBroadcast());
		System.out.println("Classe de IP: "+enderecoIp.getIpClass());
		System.out.println("Tipo de IP: "+ enderecoIp.getIpType());
		System.out.println("Endereco IP short: "+enderecoIp.getIpAddress()+"/"+enderecoIp.getMascAdress().getCIDRNotation());
		System.out.println("Mascara de Rede: "+ enderecoIp.getMascAdress().getMascAddress());
		System.out.println("Nota��o CIDR: /"+ enderecoIp.getMascAdress().getCIDRNotation());
		System.out.println("Numero de hosts utiliz�veis: " + enderecoIp.getMascAdress().getUsebleHosts());
		System.out.printf("\n\n");

		System.out.printf("Outras sub-Redes:\n\n");
		IpAddress enderecoIpGet = new IpAddress();
		IpAddress enderecoIpSet = new IpAddress();
		int[] octetoIpGet = new int[4];
		int	octetoAux = 0;
		
		
	for(int i = 0;i<255;i+= octetoAux) {
		
		enderecoIpGet.setIpAddress(enderecoIp.getNetworkAddress());
		enderecoIpGet.getMascAdress().setMascAddress(enderecoIp.getMascAdress().getMascAddress());
		octetoIpGet = enderecoIpGet.getOctet();
		
		if ((enderecoIpGet.getMascAdress().getCIDRNotation() > 24)
				&& (enderecoIpGet.getMascAdress().getCIDRNotation() < 31)) {
			
             octetoAux = (int) (Math.pow(2,32-enderecoIpGet.getMascAdress().getCIDRNotation()));
			if (i == octetoIpGet[3]) {
				continue;
			}

			enderecoIpSet.setIpAddress(String.valueOf(octetoIpGet[0]) + "." + String.valueOf(octetoIpGet[1]) + "."
					+ String.valueOf(octetoIpGet[2]) + "." + String.valueOf(i));
			enderecoIpSet.getMascAdress().setMascAddress(enderecoIpGet.getMascAdress().getMascAddress());
		}
		
		if ((enderecoIpGet.getMascAdress().getCIDRNotation() > 16)
				&& (enderecoIpGet.getMascAdress().getCIDRNotation() < 24)) {
			octetoAux = (int) (Math.pow(2,24-enderecoIpGet.getMascAdress().getCIDRNotation()));
			if (i == octetoIpGet[2]) {
				continue;
			}

			enderecoIpSet.setIpAddress(String.valueOf(octetoIpGet[0]) + "." + String.valueOf(octetoIpGet[1]) + "."
					+ String.valueOf(i) + "." + String.valueOf(0));
			enderecoIpSet.getMascAdress().setMascAddress(enderecoIpGet.getMascAdress().getMascAddress());
		}
		
		if ((enderecoIpGet.getMascAdress().getCIDRNotation() > 8)
				&& (enderecoIpGet.getMascAdress().getCIDRNotation() < 16)) {
			
			octetoAux = (int) (Math.pow(2,16-enderecoIpGet.getMascAdress().getCIDRNotation()));
			if (i == octetoIpGet[1]) {
				continue;
			}

			enderecoIpSet.setIpAddress(String.valueOf(octetoIpGet[0]) + "." + String.valueOf(i) + "."
					+ String.valueOf(0) + "." + String.valueOf(0));
			enderecoIpSet.getMascAdress().setMascAddress(enderecoIpGet.getMascAdress().getMascAddress());
		}
		
		if ((enderecoIpGet.getMascAdress().getCIDRNotation() > 0)
				&& (enderecoIpGet.getMascAdress().getCIDRNotation() <= 8)) {

			System.out.printf("N�o h� outras sub-Redes.");
				break;
		}
		
		System.out.println("Endere�o Rede: "+enderecoIpSet.getNetworkAddress());
		System.out.println("IP's utiliz�veis: "+ enderecoIpSet.getSubNetRangeFirst()+" - "+enderecoIpSet.getSubNetRangeLast());
		System.out.println("Endere�o Broadcast: "+enderecoIpSet.getBroadcast());
		System.out.println("Mascara de Rede: "+ enderecoIpSet.getMascAdress().getMascAddress());
		System.out.println("Numero de hosts utiliz�veis: " + enderecoIpSet.getMascAdress().getUsebleHosts());
		System.out.printf("\n##################################################\n");
	}
	}
}
