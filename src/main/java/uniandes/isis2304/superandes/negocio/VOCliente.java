package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de Cliente.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOCliente {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
	 * @return El id del Cliente
	 */
	public long getId();

	/**
	 * @return El nombre del Cliente
	 */
	public String getNombre();
	
	/**
	 * @return La dirección del Cliente
	 */
	public String getDireccion();
	
	/**
	 * @return El correo del Cliente
	 */
	public String getCorreo();
	
	/**
	 * @return El tipo del Cliente
	 */
	public String getTipo();
}