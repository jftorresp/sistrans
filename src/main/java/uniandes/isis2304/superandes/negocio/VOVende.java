package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de VENDE.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOVende {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
	 * @return El identificador de la sucursal que vende los productos
	 */
	public long getSucursal();
	
	/**
	 * @return El identificador del producto que se vende
	 */
	public long getProducto();
	
	/**
	 * @return La cantidad mínima de productos antes de pedir más a los proveedores
	 */
	public int getNivelReorden();
	
	/**
	 * @return El precio unitario de un producto en una sucursal
	 */
	public double getPrecioUnitario();
	
	/**
	 * @return El precio por unidad de medida de un producto en una sucursal
	 */
	public double getPrecioPorUnidadMedida();
}
