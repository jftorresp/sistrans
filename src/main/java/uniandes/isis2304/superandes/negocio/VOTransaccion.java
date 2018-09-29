package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de TRANSACCION.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOTransaccion {
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
	 * @return El numero de la factura
	 */
	public long getNumeroFactura();
	
	/**
	 * @return La cantidad de productos de la transacción
	 */
	public int getCantidad();
	
	/**
	 * @return El costo de la transacción
	 */
	public double getCosto();
	
	/**
	 * @return El producto de la transacción
	 */
	public long getProducto();
	
	/**
	 * @return La promoción de la transacción
	 */
	public long getPromocion();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();


}
