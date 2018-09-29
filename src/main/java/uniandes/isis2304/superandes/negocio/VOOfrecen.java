package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de OFRECEN.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOOfrecen {
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El identificador del producto.
	 */
	public long getProducto();
	
	/**
	 * @return El identificador del proveedor.
	 */
	public long getProveedor();
	
	/**
	 * @return El costo unitario del producto
	 */
	public double getCosto();
	
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
