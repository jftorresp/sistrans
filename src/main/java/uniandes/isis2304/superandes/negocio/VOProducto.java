package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de PRODUCTO.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOProducto {

	/*****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El identificador del producto
	 */
	public long getId();
	
	/**
	 * @return El nombre del producto
	 */
	public String getNombre();
	
	/**
	 * @return La marca del producto
	 */
	public String getMarca();
	
	/**
	 * @return La presentación del producto
	 */
	public String getPresentacion();
	
	/**
	 * @return LA unidad de medida del producto
	 */
	public String getUnidadMedida();
	
	/**
	 * @return La categoría del producto
	 */
	public String getCategoria();
	
	/**
	 * @return El tipo del producto
	 */
	public String getTipo();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
}
