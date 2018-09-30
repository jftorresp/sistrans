package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de PEDIDO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOPedido {
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El id del pedido
	 */
	public long getId();

	/**
	 * @return La sucursal que hace el pedido
	 */
	public long getSucursal();
	
	
	/**
	 * @return El proveedor al que se le hace el pedido
	 */
	public long getProveedor();

	/**
	 * @return La fecha de entrega
	 */
	public Timestamp getFechaEntrega();

	/**
	 * @return El estado de la orden
	 */
	public String getEstadoOrden();

	
	/**
	 * @return La calificación del pedido y el proveedor
	 */
	public int getCalificacion();

	/**
	 * @return Costo total del pedido
	 */
	public double getCostoTotal();
	

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();


}
