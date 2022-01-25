package bauducco.java.classes;

import java.util.Objects;

public class MascAddress {
	
	/*######################################################################################
	  #                               Atributos do Objeto                                  #
	  ######################################################################################*/
	
	private String mascAddress;
	private boolean validMasc;
	
	/*######################################################################################
	  #                               Metodos Get's and Set's                              #
	  ######################################################################################*/
	
	public String getMascAddress() {
		return mascAddress;
	}

	public void setMascAddress(String mascAddress) {
		this.mascAddress = mascAddress;
	}

	public boolean isValidMasc() {
		this.getOctet();
		return validMasc;
	}
	
	public int[] getOctet() {

		int[] octet = new int[4];
		String[] octetAux;
		boolean stopVerification = false;

		if (!this.mascAddress.isEmpty()) {

			octetAux = this.mascAddress.split("\\.");

			if (octet.length == octetAux.length) {

				for (int i = 0; i < octetAux.length; i++) {

					if ((octetAux[i].matches("[0-9]*")) && (!octetAux[i].isEmpty())) {
						
						octet[i] = Integer.parseInt(octetAux[i]);
						
					} else {
						this.validMasc = false;
						break;
					}

					/* Metodo verifica se IP é valido */
					int d, e = 0;

					for (d = 0; d < 8; d++) {

						e = 256 - ((int) (Math.pow(2, d)));

						if (octet[i] == 255 && i==0) {
							break;
							
						}else if(octet[i] != 255 && i==0) {
							stopVerification = true;
							
						}
						if (i > 0) {
							if ((((octet[i] == e)
									&& ((octet[i] < octet[i - 1]) || (octet[i] == 255 && octet[i - 1] == 255)
											|| (octet[i] == 0 && octet[i - 1] == 0)))
									|| (octet[i] == 0)) && (i > 0)) {
								if (i >= 3) {
									this.validMasc = true;
								}
								break;
							} else if (d == 7) {
								this.validMasc = false;
								stopVerification = true;
								break;
							}
						}
					}
					if (stopVerification) {
						break;
					}
				}
			} else {
				this.validMasc = false;
			}
		} else {
			this.validMasc = false;
		}
		return octet;
	}
	
	public int getCIDRNotation() {

		int CIDRNotation = 0;
		int[] octet = new int[4];
		octet = this.getOctet();
		int a = 0, b = 0;

		if (this.validMasc) {

			if ((octet[0] == 255) && (octet[1] == 255) && (octet[2] == 255) && (octet[3] != 0)) {

				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[3] == b) {
						CIDRNotation = 32 - a;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] == 255) && (octet[2] != 0) && (octet[3] == 0)) {
				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[2] == b) {
						CIDRNotation = 24 - a;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] != 0) && (octet[2] == 0) && (octet[3] == 0)) {
				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[1] == b) {
						CIDRNotation = 16 - a;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] == 0) && (octet[2] == 0) && (octet[3] == 0)) {
				CIDRNotation = 8;
			}

		}

		return CIDRNotation;
	}

	public int getUsebleHosts() {

		int UsebleHosts = 0;
		int[] octet = new int[4];
		octet = this.getOctet();
		int a = 0, b = 0;

		if (this.validMasc) {

			if ((octet[0] == 255) && (octet[1] == 255) && (octet[2] == 255) && (octet[3] != 0)) {

				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[3] == b) {
						UsebleHosts = (int) (Math.pow(2, a)) - 2;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] == 255) && (octet[2] != 0) && (octet[3] == 0)) {
				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[2] == b) {
						UsebleHosts = (int) (Math.pow(2, a + 8)) - 2;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] != 0) && (octet[2] == 0) && (octet[3] == 0)) {
				for (a = 0; a < 8; a++) {
					b = 256 - ((int) (Math.pow(2, a)));
					if (octet[1] == b) {
						UsebleHosts = (int) (Math.pow(2, a + 16)) - 2;
						break;
					}

				}
			} else if ((octet[0] == 255) && (octet[1] == 0) && (octet[2] == 0) && (octet[3] == 0)) {
				UsebleHosts = (int) (Math.pow(2, 24)) - 2;
			}

		}

		return UsebleHosts;
	}
	
	/*######################################################################################
	#                               Metodo to String                                       #
	######################################################################################*/
	
	@Override
	public String toString() {
		return "MascAddress [mascAddress=" + mascAddress + ", validMasc=" + validMasc + ", getCIDRNotation()="
				+ getCIDRNotation() + ", getUsebleHosts()=" + getUsebleHosts() + "]";
	}
	

	/*######################################################################################
	#                               Metodo hasCode e Equals                                #
	######################################################################################*/

	@Override
	public int hashCode() {
		return Objects.hash(mascAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MascAddress other = (MascAddress) obj;
		return Objects.equals(mascAddress, other.mascAddress);
	}
	

}
