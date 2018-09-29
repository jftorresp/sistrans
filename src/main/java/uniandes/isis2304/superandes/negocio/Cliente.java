package uniandes.isis2304.superandes.negocio;

import java.util.LinkedList;

/**
 * Clase para modelar el concepto Cliente del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Cliente implements VOCliente {

	/*
	 * **************************************************************** Atributos
	 *****************************************************************/

	/**
	 * El identificador ÚNICO de los clientes
	 */
	private long id;

	/**
	 * El nombre del cliente
	 */
	private String nombre;

	/**
	 * El correo del cliente
	 */
	private String correo;

	/**
	 * El tipo del cliente
	 */
	private String tipo;

	/**
	 * La dirección del cliente
	 */
	private String direccion;

	/*
	 * **************************************************************** Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Cliente() {
		this.id = 0;
		this.nombre = "";
		this.correo = "";
		this.direccion = "";
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * @param id - El id del cliente
	 * @param nombre - El nombre del cliente
	 * @param direccion - La direccion del cliente
	 * @param correo - El correo del cliente 
  	 * @param correo - El tipo del cliente (empresa, persona)

	 */
	public Cliente(long id, String nombre, String direccion, String correo, String tipo) 
	{
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.direccion = direccion;
		this.tipo = tipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return Una cadena de caracteres con la información básica del cliente
	 */
	@Override
	public String toString() 
	{
		return "Cliente [id=" + id + ", nombre=" + nombre + ", dirección=" + direccion + ", correo=" + correo + ", tipo=" + tipo + "]";
	}
}
