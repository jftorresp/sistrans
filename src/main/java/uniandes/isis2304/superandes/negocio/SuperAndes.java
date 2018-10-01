package uniandes.isis2304.superandes.negocio;

import java.sql.Timestamp;
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
	
	/* ****************************************************************
	 * 			Métodos para manejar las BODEGAS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente una bodega
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El identificador de la bodega
	 * @param capacidadVolumen - La capacidad en volumen de la bodega (en metros cúbicos)
     * @param capacidadPeso - La capacidad en peso de la bodega (en metros cuadrados)
	 * @param producto - El producto que está almacenado en la bodega
	 * @param sucursal - La sucursal a la que pertence la bodega
	 * @param existencias - Las unidades disponibles en la bodega
	 * @return El objeto Bodega adicionado. null si ocurre alguna Excepción
	 */
	public Bodega adicionarBodega(long idBodega, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias)
	{
        log.info ("Adicionando Bodega: " + idBodega);
        Bodega bodega = pp.adicionarBodega(capacidadVolumen, capacidadPeso, producto, sucursal, existencias);
        log.info ("Adicionando Bodega: " + bodega);
        return bodega;
	}
	
	/**
	 * Elimina una bodega por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El id de la bodega eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBodegaPorId (long idBodega)
	{
		log.info ("Eliminando Bodega por id: " + idBodega);
        long resp = pp.eliminarBodegaPorId(idBodega);	
        log.info ("Eliminando Bodega por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra una bodega y su información básica, según su identificador
	 * @param idBodega - El identificador de la bodega buscada
	 * @return Un objeto Bodega que corresponde con el id buscado y lleno con su información básica
	 * 			null, si una bodega con dicho id no existe
	 */
	public Bodega darBodegaPorId(long idBodega)
	{
        log.info ("Dar información de una bodega por id: " + idBodega);
        Bodega bodega = pp.darBodegaPorId(idBodega);
        log.info ("Buscando bodega por id: " + bodega != null ? bodega : "NO EXISTE");
        return bodega;
	}
	
	/**
	 * Encuentra la información básica de las bodegas, según su sucursal
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @return Una lista de Bodegas con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen bodegas con esa sucursal.
	 */
	public List<Bodega> darBodegasPorSucursal(long sucursal)
	{
        log.info ("Dar información de bodegas por sucursal: " + sucursal);
        List<Bodega> bodegas = pp.darBodegasPorSucursal(sucursal);
        log.info ("Dar información de Bodegas por sucursal: " + bodegas.size() + " bodegas con esa sucursal existentes");
        return bodegas;
 	}
	
	/**
	 * Encuentra todos las bodegas en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bodega con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Bodega> darBodegas()
	{
		log.info ("Consultando Bodegas");
        List<Bodega> bodegas = pp.darBodegas();	
        log.info ("Consultando Bodegas: " + bodegas.size() + " existentes");
        return bodegas;
	}
	
	/**
	 * Encuentra todos las bodegas en SuperAndes y los devuelve como una lista de VOBodega
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBodega con todas las bodegas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBodega> darVOBodega()
	{
		log.info ("Generando los VO de bodegas");        
        List<VOBodega> voBodegas = new LinkedList<VOBodega> ();
        for (Bodega bodega : pp.darBodegas())
        {
        	voBodegas.add (bodega);
        }
        log.info ("Generando los VO de Bodegas: " + voBodegas.size() + " existentes");
        return voBodegas;
	}
	
	/**
	 * Aumenta las existencias en 10 unidades de una bodega con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasBodegaEnDiez(long idBodega)
	{
		log.info("Aumentando eixstencias de la bodega en diez");
		long aumento = pp.aumentarExistenciasBodegaEnDiez(idBodega);
		log.info("Bodega con id: " + idBodega + "aumentada en 10 sus existencias");
		return aumento;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ESTANTES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un estante
	 * Adiciona entradas al log de la aplicación
	 * @param idEstante - El identificador del estante
	 * @param capacidadVolumen - La capacidad en volumen del estante(metros cúbicos)
	 * @param capacidadPeso - La capacidad en peso del estante (en kg)
	 * @param producto - Identificador del producto que almacena el estante
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @nivelabastecimientobodega - Cantidad de unidades mínimas que debe tener en la bidega por producto
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Estante adicionado. null si ocurre alguna Excepción
	 */
	public 	Estante adicionarEstante(long idEstante, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias)
	{
        log.info ("Adicionando Bodega: " + idEstante);
        Estante estante = pp.adicionarEstante(capacidadVolumen, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
        log.info ("Adicionando Bodega: " + estante);
        return estante;
	}
	
	/**
	 * Elimina un estante por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El id del estante a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstantePorId (long idEstante)
	{
		log.info ("Eliminando Estante por id: " + idEstante);
        long resp = pp.eliminarEstantePorId(idEstante);
        log.info ("Eliminando Estante por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra un estante y su información básica, según su identificador
	 * @param idEstante - El identificador del estante buscado
	 * @return Un objeto Estante que corresponde con el id buscado y lleno con su información básica
	 * 			null, si un estante con dicho id no existe
	 */
	public Estante darEstantePorId(long idEstante)
	{
        log.info ("Dar información de un estante por id: " + idEstante);
        Estante estante = pp.darEstantePorId(idEstante);
        log.info ("Buscando estante por id: " + estante != null ? estante : "NO EXISTE");
        return estante;
	}
	
	/**
	 * Encuentra la información básica de los estantes, según su sucursal
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @return Una lista de Estantess con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen estantes con esa sucursal.
	 */
	public List<Estante> darEstantesPorSucursal(long sucursal)
	{
        log.info ("Dar información de estantes por sucursal: " + sucursal);
        List<Estante> estantes = pp.darEstantesPorSucursal(sucursal);
        log.info ("Dar información de Estantes por sucursal: " + estantes.size() + " estantes con esa sucursal existentes");
        return estantes;
 	}
	
	/**
	 * Encuentra todos los estantes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Estante con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Estante> darEstantes()
	{
		log.info ("Consultando Estantes");
        List<Estante> estantes = pp.darEstantes();	
        log.info ("Consultando Estantes: " + estantes.size() + " existentes");
        return estantes;
	}
	
	/**
	 * Encuentra todos los estantes en SuperAndes y los devuelve como una lista de VOEstante
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOEstante con todas los estantes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEstante> darVOEsatnte()
	{
		log.info ("Generando los VO de estantes");        
        List<VOEstante> voEstantes = new LinkedList<VOEstante>();
        for (Estante estante : pp.darEstantes())
        {
        	voEstantes.add (estante);
        }
        log.info ("Generando los VO de Estantes: " + voEstantes.size() + " existentes");
        return voEstantes;
	}
	
	/**
	 * Aumenta las existencias en 10 unidades de un estante con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasEstanteEnDiez(long idEstante)
	{
		log.info("Aumentando eixstencias de la bodega en diez");
		long aumento = pp.aumentarExistenciasEstanteEnDiez(idEstante);
		log.info("Estante con id: " + idEstante + "aumentada en 10 sus existencias");
		return aumento;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación VENDE
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion del proveeodr
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	public Proveedor adicionarProveedor(String nombre, int calificacion)
	{
        log.info ("Adicionando Proveedor: " + nombre);
        Proveedor proveedor = pp.adicionarProveedor(nombre, calificacion);
        log.info ("Adicionando Bodega: " + proveedor);
        return proveedor;
	}
	
	/**
	 * Elimina un proveedor por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorNombre (String nombre)
	{
		log.info ("Eliminando Proveedor por nombre: " + nombre);
        long resp = pp.eliminarProoveedorPorNombre(nombre);	
        log.info ("Eliminando Proveedor por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un proveedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idProveedor - El id del proveedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorId (long idProveedor)
	{
		log.info ("Eliminando Proveedor por id: " + idProveedor);
        long resp = pp.eliminarProveedorPorId(idProveedor);	
        log.info ("Eliminando Proveedor por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra la información básica de los proveedores, según su nombre
	 * @param nombre - El nombre del proveedor
	 * @return Una lista de Proveedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen proveedores con ese nombre.
	 */
	public List<Proveedor> darProveedoresPorNombre (String nombre)
	{
        log.info ("Dar información de proveedores por nombre: " + nombre);
        List<Proveedor> proveedores = pp.darProveedoresPorNombre(nombre);
        log.info ("Dar información de Proveedores por nombre: " + proveedores.size() + " proveedores con ese nombre existentes");
        return proveedores;
 	}
	
	/**
	 * Encuentra todos los proveedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Proveedor con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Proveedor> darProveedores()
	{
		log.info ("Consultando Proveedores");
        List<Proveedor> proveedores = pp.darProveedores();	
        log.info ("Consultando Proveedores: " + proveedores.size() + " existentes");
        return proveedores;
	}
	
	/**
	 * Encuentra todos los proveedores en SuperAndes y los devuelve como una lista de VOProveedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProveedor con todas los proveedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProveedor> darVOProveedor()
	{
		log.info ("Generando los VO de proveedores");        
        List<VOProveedor> voProveedores = new LinkedList<VOProveedor> ();
        for (Proveedor proveedor : pp.darProveedores())
        {
        	voProveedores.add (proveedor);
        }
        log.info ("Generando los VO de Proveedores: " + voProveedores.size() + " existentes");
        return voProveedores;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PEDIDOS
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			M�todos para manejar las PROMOCIONES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una promocion
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion del proveeodr
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	
	/**
	 * Adiciona de manera persistente una promocion
	 * Adiciona entradas al log de la aplicación 
	 * @param nombre del producto asociado a la promocion
	 * @param marca del producto asociado a la promocion
	 * @param presentacion del producto asociado a la promocion
	 * @param codigobarras del producto asociado a la promocion
	 * @param unidadmedida del producto asociado a la promocion
	 * @param categoria del producto asociado a la promocion
	 * @param tipo del producto asociado a la promocion
	 * @param precio de la promocion	
	 * @param descripcion de la promocion
	 * @param fechaInicio de la promocion
	 * @param fechaFin de la promocion 
	 * @param unidadesdisponibles de la promocion
	 * @return El objeto Promocion adicionado. null si ocurre alguna Excepción
	 */
	public Promocion adicionarPromocion(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin, int unidadesdisponibles)
	{
        log.info ("Adicionando producto: " + nombre);
        Promocion promocion = pp.adicionarPromocion(nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles);
        log.info ("Adicionando promocion: " + promocion);
        return promocion;
	}
	
	
}
