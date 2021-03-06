package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de FACTURA.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOFactura {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El numero de la factura
	 */
	public long getNumero();
	
	/**
	 * @return La fecha de la factura
	 */
	public Timestamp getFecha();
	
	/**
	 * @return El cliente de la factura
	 */
	public long getCliente();
	
	/**
	 * @return La sucursal de la factura
	 */
	public long getSucursal();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();
}
