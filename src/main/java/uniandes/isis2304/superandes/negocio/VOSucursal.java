package uniandes.isis2304.superandes.negocio;

public interface VOSucursal {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
	 * @return El id de la sucursal
	 */
	public long getId();
	
	/**
	 * @return el nombre de la sucursal
	 */
	public String getNombre();
	
	/**
	 * @return la ciudad de la sucursal
	 */
	public String getCiudad();
	
	/**
	 * @return la direccion de la sucursal
	 */
	public String getDireccion();
	
	/**
	 * @return el segmento de mercado de la sucursal
	 */
	public String getSegmentoMercado();
	
	/**
	 * @return el tamano de la sucursal
	 */
	public int getTamano();
	
	/**
	 * @return el supermercado al que pertenece la sucursal
	 */
	public String getSupermercado();

}
