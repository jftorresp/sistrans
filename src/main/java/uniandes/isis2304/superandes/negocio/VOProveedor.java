package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de PROVEEDOR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOProveedor {

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El nit del proveedor
	 */
	public long getNit();
	
	/**
	 * @return El nombre del proveedor
	 */
	public String getNombre();
	
	/**
	 * @return La calificación del servicio del proveedor
	 */
	public int getCalificacion();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
