package uniandes.isis2304.superandes.test;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.superandes.negocio.SuperAndes;
import uniandes.isis2304.superandes.test.ConexionTest;
import org.apache.log4j.Logger;

/**
 * Clase con métodos de prueba de conexión a la base de datos
 * @author n.cobos, jf.torresp
 *
 */
public class ConexionTest {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(ConexionTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private SuperAndes superandes;
}
