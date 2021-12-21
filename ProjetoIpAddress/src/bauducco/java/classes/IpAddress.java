package bauducco.java.classes;

import java.util.Objects;

/*Criação da Classe Endereco IP*/
public class IpAddress {

	/*######################################################################################
	  #                               Atributos do Objeto                                  #
	  ######################################################################################*/

	private String ipAddress;
	private boolean validAddress;
	private MascAddress mascAdress = new MascAddress();
	
	/*######################################################################################
	  #                               Metodos Get's and Set's                              #
	  ######################################################################################*/
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String IpAddress) {
		this.ipAddress = IpAddress;
	}
	
	public MascAddress getMascAdress() {
		MascAddress mascAddress = new MascAddress();
		mascAddress = this.mascAdress;
		if (mascAddress.getMascAddress() == null) {
			if(this.validAddress == true) {
				String ipType = this.getIpClass();
				if(ipType =="A") {
					mascAddress.setMascAddress("255.0.0.0");
				}else if(ipType =="B") {
					mascAddress.setMascAddress("255.255.0.0");
				}else if(ipType =="C") {
					mascAddress.setMascAddress("255.255.255.0");
				}
				
			}
			this.mascAdress = mascAddress;
		}
		return this.mascAdress;
	}

	public void setMascAdress(MascAddress mascAdress) {
		this.mascAdress = mascAdress;
	}

	public boolean isValidAddress() {
		this.getOctet();
		return validAddress;
	}
	

	public int[] getOctet() {

		int[] octet = new int[4];
		int[] octetAux = new int[4];
		int i = 0;

		if (this.ipAddress != null) {

			int stringLen = this.ipAddress.length();
			int a = 0, b = 0, c = 0;

			for (i = 0; i <= 3; i++) {

				for (a = c; a < stringLen; a++) {

					if (((this.ipAddress.charAt(a)) == '.') && (i < 3)) {
						octetAux[i] = Integer.parseInt(ipAddress.substring(b, a));
						if (i < 2) {
							b = a + 1;
							c = a + 1;
							break;
						} else {
							b = a + 1;
							c = a;
							break;
						}

					} else if (((this.ipAddress.charAt(a)) == '.') && (i == 3)) {
						octetAux[i] = Integer.parseInt(ipAddress.substring(b, stringLen));
						break;
					}

				}

				/* Metodo verifica se IP é valido */
				if ((octetAux[i] < 0) || (octetAux[i] > 255)) {
					this.validAddress = false;
					octet[0] = 0;
					octet[1] = 0;
					octet[2] = 0;
					octet[3] = 0;
					break;

				} else if (i == 3) {
					this.validAddress = true;
				}

			}

		} else {
			this.validAddress = false;
			return null;
		}
		if (this.validAddress) {
			octet = octetAux;
			return octet;
		} else {
			return null;
		}

	}

	public String getIpClass() {

		int[] ipAddress = new int[4];
		ipAddress = this.getOctet();
		String ipClass = "IP Invalido";
		
		if ((ipAddress[0] >= 0) && (ipAddress[0] <= 126)) {
			ipClass = "A";

		} else if ((ipAddress[0] >= 128) && (ipAddress[0] <= 191)) {
			ipClass = "B";

		} else if ((ipAddress[0] >= 192) && (ipAddress[0] <= 223)) {
			ipClass = "C";

		} else if ((ipAddress[0] >= 224) && (ipAddress[0] <= 239)) {
			ipClass = "D";

		} else if ((ipAddress[0] >= 240) && (ipAddress[0] <= 255)) {
			ipClass = "E";
		}
		return ipClass;
	}

	public String getIpType() {

		int[] ipAddress = new int[4];
		ipAddress = this.getOctet();
		String ipType = "IP Invalido";

		if ((ipAddress[0] >= 0) && (ipAddress[0] <= 126)) {
			if (ipAddress[0] == 10) {
				ipType = "private";
			} else {
				ipType = "public";
			}

		} else if ((ipAddress[0] >= 128) && (ipAddress[0] <= 191)) {
			if ((ipAddress[0] == 172) && ((ipAddress[1] >= 16) && (ipAddress[1] <= 31))) {
				ipType = "private";
			} else {
				ipType = "public";
			}

		} else if ((ipAddress[0] >= 192) && (ipAddress[0] <= 223)) {
			if ((ipAddress[0] == 192) && (ipAddress[1]==168)) {
				ipType = "private";
			} else {
				ipType = "public";
			}

		} else if ((ipAddress[0] >= 224) && (ipAddress[0] <= 239)) {
			ipType = "Multicast";

		} else if ((ipAddress[0] >= 240) && (ipAddress[0] <= 255)) {
			ipType = "Reserved";
		}
		return ipType;
	}
	
	public String getNetworkAddress() {

		String networkAddress;
		String networkAddressAux = "";

		int[] ipAddress = new int[4];
		ipAddress = this.getOctet();

		MascAddress mascAddress = new MascAddress();
		mascAddress = this.getMascAdress();

		int[] mascAddressAux = new int[4];
		mascAddressAux = mascAddress.getOctet();

		int interval = 0;
		int a = 0, b = 0, d = 0;

		if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] == 255)
				&& (mascAddressAux[3] >= 0)) {

			interval = 256 - mascAddressAux[3];

			for (d = 1; d < 255; d++) {

				a = interval * d;
				b = a - interval;

				if ((ipAddress[3] >= b) && (ipAddress[3] < a)) {
					networkAddressAux = String.valueOf(ipAddress[0]) + "." + String.valueOf(ipAddress[1]) + "."
							+ String.valueOf(ipAddress[2]) + "." + String.valueOf(b);
					break;
				}

			}
		} else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] >= 0)
				&& (mascAddressAux[3] == 0)) {

			interval = 256 - mascAddressAux[2];

			for (d = 1; d < 255; d++) {

				a = interval * d;
				b = a - interval;

				if ((ipAddress[2] >= b) && (ipAddress[2] < a)) {
					networkAddressAux = String.valueOf(ipAddress[0]) + "." + String.valueOf(ipAddress[1]) + "."
							+ String.valueOf(b) + "." + String.valueOf(0);
					break;
				}

			}
		} else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] >= 0) && (mascAddressAux[2] == 0)
				&& (mascAddressAux[3] == 0)) {

			interval = 256 - mascAddressAux[1];

			for (d = 1; d < 255; d++) {

				a = interval * d;
				b = a - interval;

				if ((ipAddress[1] >= b) && (ipAddress[1] < a)) {
					networkAddressAux = String.valueOf(ipAddress[0]) + "." + String.valueOf(b) + "." + String.valueOf(0)
							+ "." + String.valueOf(0);
					break;
				}

			}
		}
		if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 0) && (mascAddressAux[2] == 0)
				&& (mascAddressAux[3] == 0)) {

			interval = 256 - mascAddressAux[0];

			for (d = 1; d < 255; d++) {

				a = interval * d;
				b = a - interval;

				if ((ipAddress[0] >= b) && (ipAddress[0] < a)) {
					networkAddressAux = String.valueOf(ipAddress[0]) + "." + String.valueOf(0) + "." + String.valueOf(0)
							+ "." + String.valueOf(0);
					break;
				}

			}
		}
		networkAddress = networkAddressAux;
		return networkAddress;
	}

	public String getSubNetRangeFirst() {
		
		String subNetRangeFirst;
		String subNetRangeAux ="" ;
		
		int[] ipAddress = new int[4];
		ipAddress = this.getOctet();
		
		MascAddress mascAddress = new MascAddress();
		mascAddress = this.getMascAdress();
		
		int[] mascAddressAux = new int[4];
		mascAddressAux = mascAddress.getOctet();
		
		int interval = 0;
		int a = 0, b = 0,d = 0;
		
		if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] == 255) && (mascAddressAux[3] >= 0)) {
			
			interval = 256 - mascAddressAux[3];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[3] >= b) && (ipAddress[3] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
					+"."+ String.valueOf(ipAddress[2])+"." + String.valueOf(b+1);
					break;
				}
				
			}
		}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] >= 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[2];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[2] >= b) && (ipAddress[2] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
					+"."+ String.valueOf(b)+"." + String.valueOf(1);
					break;
				}
				
			}
		}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] >= 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[1];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[1] >= b) && (ipAddress[1] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(b)
					+"."+ String.valueOf(0)+"." + String.valueOf(1);
					break;
				}
				
			}
		}if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[0];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[0] >= b) && (ipAddress[0] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(0)
					+"."+ String.valueOf(0)+"." + String.valueOf(1);
					break;
				}
				
			}
		}
		subNetRangeFirst = subNetRangeAux;
		return subNetRangeFirst;
	}
	
public String getSubNetRangeLast() {
		
		String subNetRangeLast;
		String subNetRangeAux ="" ;
		
		int[] ipAddress = new int[4];
		ipAddress = this.getOctet();
		
		MascAddress mascAddress = new MascAddress();
		mascAddress = this.getMascAdress();
		
		int[] mascAddressAux = new int[4];
		mascAddressAux = mascAddress.getOctet();
		
		int interval = 0;
		int a = 0, b = 0,d = 0;
		
		if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] == 255) && (mascAddressAux[3] >= 0)) {
			
			interval = 256 - mascAddressAux[3];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[3] >= b) && (ipAddress[3] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
					+"."+ String.valueOf(ipAddress[2])+"." + String.valueOf((b+interval)-2);
					break;
				}
				
			}
		}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] >= 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[2];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[2] >= b) && (ipAddress[2] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
					+"."+ String.valueOf((b+interval)-1)+"." + String.valueOf(254);
					break;
				}
				
			}
		}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] >= 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[1];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[1] >= b) && (ipAddress[1] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf((b+interval)-1)
					+"."+ String.valueOf(255)+"." + String.valueOf(254);
					break;
				}
				
			}
		}if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
			
			interval = 256 - mascAddressAux[0];
			
			for (d = 1; d < 255; d++) {
				
				a = interval * d;
				b = a - interval;
				
				if ((ipAddress[0] >= b) && (ipAddress[0] < a)) {
					subNetRangeAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(255)
					+"."+ String.valueOf(255)+"." + String.valueOf(254);
					break;
				}
				
			}
		}
		subNetRangeLast = subNetRangeAux;
		return subNetRangeLast;
	}

public String getBroadcast() {
	
	String broadcast;
	String broadcastAux ="" ;
	
	int[] ipAddress = new int[4];
	ipAddress = this.getOctet();
	
	MascAddress mascAddress = new MascAddress();
	mascAddress = this.getMascAdress();
	
	int[] mascAddressAux = new int[4];
	mascAddressAux = mascAddress.getOctet();
	
	int interval = 0;
	int a = 0, b = 0,d = 0;
	
	if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] == 255) && (mascAddressAux[3] >= 0)) {
		
		interval = 256 - mascAddressAux[3];
		
		for (d = 1; d < 255; d++) {
			
			a = interval * d;
			b = a - interval;
			
			if ((ipAddress[3] >= b) && (ipAddress[3] < a)) {
				broadcastAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
				+"."+ String.valueOf(ipAddress[2])+"." + String.valueOf((b+interval)-1);
				break;
			}
			
		}
	}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 255) && (mascAddressAux[2] >= 0) && (mascAddressAux[3] == 0)) {
		
		interval = 256 - mascAddressAux[2];
		
		for (d = 1; d < 255; d++) {
			
			a = interval * d;
			b = a - interval;
			
			if ((ipAddress[2] >= b) && (ipAddress[2] < a)) {
				broadcastAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(ipAddress[1])
				+"."+ String.valueOf((b+interval)-1)+"." + String.valueOf(255);
				break;
			}
			
		}
	}else if ((mascAddressAux[0] == 255) && (mascAddressAux[1] >= 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
		
		interval = 256 - mascAddressAux[1];
		
		for (d = 1; d < 255; d++) {
			
			a = interval * d;
			b = a - interval;
			
			if ((ipAddress[1] >= b) && (ipAddress[1] < a)) {
				broadcastAux = String.valueOf(ipAddress[0])+"."+ String.valueOf((b+interval)-1)
				+"."+ String.valueOf(255)+"." + String.valueOf(255);
				break;
			}
			
		}
	}if ((mascAddressAux[0] == 255) && (mascAddressAux[1] == 0) && (mascAddressAux[2] == 0) && (mascAddressAux[3] == 0)) {
		
		interval = 256 - mascAddressAux[0];
		
		for (d = 1; d < 255; d++) {
			
			a = interval * d;
			b = a - interval;
			
			if ((ipAddress[0] >= b) && (ipAddress[0] < a)) {
				broadcastAux = String.valueOf(ipAddress[0])+"."+ String.valueOf(255)
				+"."+ String.valueOf(255)+"." + String.valueOf(255);
				break;
			}
			
		}
	}
	broadcast = broadcastAux;
	return broadcast;
}

/*######################################################################################
#                               Metodo to String                                       #
######################################################################################*/

@Override
public String toString() {
	return "IpAddress [IpAddress=" + ipAddress + ", validAddress=" + validAddress + ", mascAdress=" + mascAdress
			+ ", getIpClass()=" + getIpClass() + ", getIpType()=" + getIpType() + ", getNetworkAddress()="
			+ getNetworkAddress() + ", getSubNetRangeFirst()=" + getSubNetRangeFirst() + ", getSubNetRangeLast()="
			+ getSubNetRangeLast() + ", getBroadcast()=" + getBroadcast() + "]";
}

/*######################################################################################
#                               Metodo hasCode e Equals                                #
######################################################################################*/

@Override
public int hashCode() {
	return Objects.hash(ipAddress, mascAdress);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	IpAddress other = (IpAddress) obj;
	return Objects.equals(ipAddress, other.ipAddress) && Objects.equals(mascAdress, other.mascAdress);
}


}
