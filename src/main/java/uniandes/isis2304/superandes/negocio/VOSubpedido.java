package uniandes.isis2304.superandes.negocio;

/**
 * Interfaz para los métodos get de SUBPEDIDO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOSubpedido {
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * @return El producto del que se hace el pedido
	 */
	public long getProducto();
	
	/**
	 * @return El pedido al que está asociado
	 */
	public long getPedido();
	
	/**
	 * @return El numero de unidades solicitadas
	 */
	public int getCantidad();
	
	/**
	 * @return El costo de las unidades solicitadas
	 */
	public int getCosto();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
	
}
