package uniandes.isis2304.superandes.negocio;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.persistencia.PersistenciaSuperAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author n.cobos, jf.torresp
 */
public class SuperAndes {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(SuperAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaSuperAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public SuperAndes()
	{
		pp = PersistenciaSuperAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public SuperAndes(JsonObject tableConfig)
	{
		pp = PersistenciaSuperAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los SUPERMERCADOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un supermercado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado adicionado. null si ocurre alguna Excepción
	 */
	public Supermercado adicionarSupermercado(String nombre)
	{
        log.info ("Adicionando Supermercado: " + nombre);
        Supermercado supermercado = pp.adicionarSupermercado(nombre);	
        log.info ("Adicionando Supermercado: " + supermercado);
        return supermercado;
	}
	
	/**
	 * Elimina un supermercado por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercado a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorNombre (String nombre)
	{
		log.info ("Eliminando Supermercado por nombre: " + nombre);
        long resp = pp.eliminarSupermercadoPorNombre(nombre);		
        log.info ("Eliminando Supermercado por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los supermercados en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Supermercados con todos los supermercados que conoce la aplicación, llenos con su información básica
	 */
	public List<Supermercado> darSupermercados()
	{
		log.info ("Consultando Supermercados");
        List<Supermercado> supermercados = pp.darSupermercados();	
        log.info ("Consultando Supermercados: " + supermercados.size() + " existentes");
        return supermercados;
	}
	
	/**
	 * Encuentra todos los supermercados en SuperAndes y los devuelve como una lista de VOSupermercado
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSupermercado con todos los supermercados que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSupermercado> darVOSupermercado()
	{
		log.info ("Generando los VO de supermercados");        
        List<VOSupermercado> voSupermercados = new LinkedList<VOSupermercado> ();
        for (Supermercado sm : pp.darSupermercados())
        {
        	voSupermercados.add (sm);
        }
        log.info ("Generando los VO de Supermercados: " + voSupermercados.size() + " existentes");
        return voSupermercados;
	}

	/**
	 * Encuentra un supermercado y su información básica, según su nombre
	 * @param nombre - El nombre del supermercado buscado
	 * @return Un objeto Supermercado que corresponde con el nombre buscado y lleno con su información básica
	 * 			null, si un supermercado con dicho nombre no existe
	 */
	public Supermercado darSupermercadoPorNombre (String nombre)
	{
        log.info ("Dar información de un supermercado por nombre: " + nombre);
        Supermercado sm = pp.darSupermercadoPorNombre(nombre);
        log.info ("Buscando supermercado por nombre: " + sm != null ? sm : "NO EXISTE");
        return sm;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSALES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente una sucursal
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - La ciudad de la sucursal
	 * @param direccion - La direccion de la sucursal
	 * @param segmentomercado - El segmento de mercado de la sucursal
	 * @param tamano - El tamaño de la sucursal (en metros cuadrados)
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return El objeto Sucursal adicionado. null si ocurre alguna Excepción
	 */
	public Sucursal adicionarSucursal(String nombre, String ciudad, String direccion, String segmentomercado, int tamano, String supermercado)
	{
        log.info ("Adicionando Sucursal: " + nombre);
        Sucursal sucursal = pp.adicionarSucursal(nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
        log.info ("Adicionando Sucursal: " + sucursal);
        return sucursal;
	}
	
	/**
	 * Elimina una sucursal por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSucursalPorNombre (String nombre)
	{
		log.info ("Eliminando Sucursal por nombre: " + nombre);
        long resp = pp.eliminarSucursalPorNombre(nombre);	
        log.info ("Eliminando Sucursal por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina una sucursal por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idSucursal - El id de la sucursal a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSucursalPorId (long idSucursal)
	{
		log.info ("Eliminando Sucursal por id: " + idSucursal);
        long resp = pp.eliminarSucursalPorId(idSucursal);		
        log.info ("Eliminando Sucursal por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra una sucursal y su información básica, según su identificador
	 * @param idSucursal - El identificador de la sucursal buscada
	 * @return Un objeto Sucursal que corresponde con el id buscado y lleno con su información básica
	 * 			null, si una sucursal con dicho id no existe
	 */
	public Sucursal darSucursalPorId(long idSucursal)
	{
        log.info ("Dar información de una sucursal por id: " + idSucursal);
        Sucursal sucursal = pp.darSucursalPorId(idSucursal);
        log.info ("Buscando sucursal por id: " + sucursal != null ? sucursal : "NO EXISTE");
        return sucursal;
	}
	
	/**
	 * Encuentra la información básica de las sucursales, según su supermercado
	 * @param supermercado - El supermercado al que pertence la sucursal
	 * @return Una lista de Sucursales con su información básica, donde todos tienen el supermercado buscado.
	 * 	La lista vacía indica que no existen sucursales con ese supermercado.
	 */
	public List<Sucursal> darSucursalesPorSupermercado (String supermercado)
	{
        log.info ("Dar información de sucursales por supermercado: " + supermercado);
        List<Sucursal> sucursales = pp.darSucursalesPorSupermercado(supermercado);
        log.info ("Dar información de Sucursales por supermercado: " + sucursales.size() + " sucursales con ese supermercado existentes");
        return sucursales;
 	}
	
	/**
	 * Encuentra todos las sucursales en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Sucurusal con todos las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<Sucursal> darSucursales()
	{
		log.info ("Consultando Sucursales");
        List<Sucursal> sucursales = pp.darSucursales();	
        log.info ("Consultando Sucursales: " + sucursales.size() + " existentes");
        return sucursales;
	}
	
	/**
	 * Encuentra todas las sucursales en SuperAndes y los devuelve como una lista de VOSucursal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSucursal con todas las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSucursal> darVOSucursal()
	{
		log.info ("Generando los VO de sucursales");        
        List<VOSucursal> voSucursales = new LinkedList<VOSucursal> ();
        for (Sucursal sucursal : pp.darSucursales())
        {
        	voSucursales.add (sucursal);
        }
        log.info ("Generando los VO de Sucursales: " + voSucursales.size() + " existentes");
        return voSucursales;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un producto
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto
	 * @param marca - La marca del producto
     * @param presentacion - La presentacion del producto
	 * @param codigobarras - El código de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - Ls categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo del producto por categoria
	 * @return El objeto Producto adicionado. null si ocurre alguna Excepción
	 */
	public Producto adicionarProducto(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo)
	{
        log.info ("Adicionando Producto: " + nombre);
        Producto producto = pp.adicionarProducto(nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
        log.info ("Adicionando Producto: " + producto);
        return producto;
	}
	
	/**
	 * Elimina un producto por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoPorNombre (String nombre)
	{
		log.info ("Eliminando Producto por nombre: " + nombre);
        long resp = pp.eliminarProductoPorNombre(nombre);	
        log.info ("Eliminando Producto por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un producto por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El id del producto a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoPorId (long idProducto)
	{
		log.info ("Eliminando Producto por id: " + idProducto);
        long resp = pp.eliminarProductoPorId(idProducto);		
        log.info ("Eliminando Producto por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra la información básica de los productos, según su nombre
	 * @param nombre - El nombre del producto
	 * @return Una lista de Productos con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen productos con ese nombre.
	 */
	public List<Producto> darProductosPorNombre (String nombre)
	{
        log.info ("Dar información de productos por nombre: " + nombre);
        List<Producto> productos = pp.darProductosPorNombre(nombre);
        log.info ("Dar información de Productos por nombre: " + productos.size() + " productos con ese nombre existentes");
        return productos;
 	}
	
	/**
	 * Encuentra todos los productos en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Producto con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Producto> darProductos()
	{
		log.info ("Consultando Productos");
        List<Producto> productos = pp.darProductos();	
        log.info ("Consultando Productos: " + productos.size() + " existentes");
        return productos;
	}
	
	/**
	 * Encuentra todos los productos en SuperAndes y los devuelve como una lista de VOProducto
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProducto con todas los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProducto> darVOProducto()
	{
		log.info ("Generando los VO de prodcutos");        
        List<VOProducto> voProductos = new LinkedList<VOProducto> ();
        for (Producto producto : pp.darProductos())
        {
        	voProductos.add (producto);
        }
        log.info ("Generando los VO de Productos: " + voProductos.size() + " existentes");
        return voProductos;
	}

}
