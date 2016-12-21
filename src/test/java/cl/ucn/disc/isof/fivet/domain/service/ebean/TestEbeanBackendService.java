package cl.ucn.disc.isof.fivet.domain.service.ebean;

import cl.ucn.disc.isof.fivet.domain.model.Paciente;
import cl.ucn.disc.isof.fivet.domain.model.Persona;
import cl.ucn.disc.isof.fivet.domain.service.BackendService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * Clase de testing del {@link BackendService}.
 */
@Slf4j
@FixMethodOrder(MethodSorters.DEFAULT)
public class TestEbeanBackendService {

    /**
     * Todos los test deben terminar antes de 60 segundos.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(60);

    /**
     * Configuracion de la base de datos:  h2, hsql, sqlite
     * WARN: hsql no soporta ENCRYPT
     */
    private static final String DB = "h2";

    /**
     * Backend
     */
    private BackendService backendService;

    /**
     * Cronometro
     */
    private Stopwatch stopWatch;

    /**
     * Antes de cada test
     */
    @Before
    public void beforeTest() {

        stopWatch = Stopwatch.createStarted();
        log.debug("Initializing Test Suite with database: {}", DB);

        backendService = new EbeanBackendService(DB);
        backendService.initialize();
    }

    /**
     * Despues del test
     */
    @After
    public void afterTest() {

        log.debug("Test Suite done. Shutting down the database ..");
        backendService.shutdown();

        log.debug("Test finished in {}", stopWatch.toString());
    }

    /**
     * Test de la persona
     */
    @Test
    public void testPersona() {

        final String rut = "1-1";
        final String nombre = "Este es mi nombre";

        // Insert into backend
        {
            final Persona persona = Persona.builder()
                    .nombre(nombre)
                    .rut(rut)
                    .password("durrutia123")
                    .tipo(Persona.TipoPersona.CLIENTE)
                    .build();

            persona.insert();

            log.debug("Persona to insert: {}", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
        }

        // Get from backend v1
        {
            final Persona persona = backendService.getPersona(rut);
            log.debug("Persona founded: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
            Assert.assertEquals("Nombre distintos!", nombre, persona.getNombre());
            Assert.assertNotNull("Pacientes null", persona.getPacientes());
            Assert.assertTrue("Pacientes != 0", persona.getPacientes().size() == 0);

        }

    }

    /**
     * Test del paciente
     */
    @Test
    public void testPaciente(){

        final Integer numero = 1111;
        final String nombre = "Esmeralda";

        //se inserta el paciente
        {
            //se crea al Paciente
            final Paciente paciente = Paciente.builder()
                    .numero(numero)
                    .nombre(nombre)
                    .sexo(Paciente.Sexo.HEMBRA)
                    .build();

            //se inserta el paciente
            paciente.insert();

            //se despliega la operacion a realizar
            log.debug("Paciente a insertar: {}", paciente);
            //el paciente insertado debe existir, se comprueba con su id
            Assert.assertNotNull("Objeto sin id", paciente.getId());
        }

        //se obtiene al paciente resien ingresado
        {
            final Paciente paciente = backendService.getPaciente(numero);
            log.debug("Paciente encontrado: {}", paciente);
            Assert.assertNotNull("Paciente no encontrado", paciente);
            Assert.assertNotNull("Objeto no encontrado", paciente.getId());
            Assert.assertEquals("Nombres distinto", nombre, paciente.getNombre());
        }

        //se obtiene la lista actual de pacientes
        {
            final List<Paciente> pacientes = backendService.getPacientes();
            log.debug("Pacientes encontrados", pacientes);
            Assert.assertNotNull("No se encontraron pacientes", pacientes);
            Assert.assertTrue("Hay mas pacientes de lo esperado", pacientes.size() == 1);
        }

        //se obtienen pacientes segun nombre
        {
            final List<Paciente> pacientes = backendService.getPacientesPorNombre(nombre);
            log.debug("Pacientes encontrados con el nombre "+ nombre, pacientes);
            Assert.assertNotNull("No se encontraron pacientes", pacientes);
            Assert.assertTrue("Hay mas pacientes con ese nombre", pacientes.size() == 1);
            Assert.assertEquals("Nombres diferentes!", nombre, pacientes.get(0).getNombre());
        }
    }


}
