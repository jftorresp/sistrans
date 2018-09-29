package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de PROMOCION.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOPromocion {


	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El identificador de la promoción
	 */
	public long getId();
	
	/**
	 * @return El precio de la promoción
	 */
	public double getPrecio();

	/**
	 * @return La descripción de la promoción
	 */
	public String getDescripcion();
	
	/**
	 * @return La fecha de inicio de la promoción
	 */
	public Timestamp getFechaInicio();
	
	/**
	 * @return La fecha de fin de la promoción
	 */
	public Timestamp getFechaFin();
	
	/**
	 * @return Las unidades disponibles para la venta de la promoción
	 */
	public int getUnidadesDisponibles();
	
	/**
	 * @return El identificador del producto en promoción.
	 */
	public long getProducto();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();

}