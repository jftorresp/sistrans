package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto PROMOCION del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Promocion implements VOPromocion{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 *  El identificador de la promoción
	 */
	private long id;
	
	/**
	 *  El precio de la promoción
	 */
	private double precio;

	/**
	 *  La descripción de la promoción
	 */
	private String descripcion;
	
	/**
	 *  La fecha de inicio de la promoción
	 */
	private Timestamp fechaInicio;
	
	/**
	 *  La fecha de fin de la promoción
	 */
	private Timestamp fechaFin;
	
	/**
	 *  Las unidades disponibles para la venta de la promoción
	 */
	private int unidadesDisponibles;
	
	/**
	 *  El identificador del producto en promoción.
	 */
	private long producto;
	
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Promocion() {
		this.id = 0;
		this.precio = 0;
		this.descripcion = "";
		this.fechaInicio = new Timestamp(0);
		this.fechaFin = new Timestamp(0);
		this.unidadesDisponibles = 0;
		this.producto = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param id
	 * @param precio
	 * @param descripcion
	 * @param fechaInicio
	 * @param fechaFin
	 * @param unidadesDisponibles
	 * @param producto
	 */
	public Promocion(long id, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin,
			int unidadesDisponibles, long producto) {
		this.id = id;
		this.precio = precio;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.unidadesDisponibles = unidadesDisponibles;
		this.producto = producto;
	}
	
	
	


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fechaInicio
	 */
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Timestamp getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the unidadesDisponibles
	 */
	public int getUnidadesDisponibles() {
		return unidadesDisponibles;
	}

	/**
	 * @param unidadesDisponibles the unidadesDisponibles to set
	 */
	public void setUnidadesDisponibles(int unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}

	/**
	 * @return the producto
	 */
	public long getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(long producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Promocion [id=" + id + ", precio=" + precio + ", descripcion=" + descripcion + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", unidadesDisponibles=" + unidadesDisponibles
				+ ", producto=" + producto + "]";
	}
}
