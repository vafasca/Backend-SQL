package sofka.contactos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sofka.contactos.domain.Contacto;
import sofka.contactos.service.LibretaService;
import sofka.contactos.utility.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para la libreta
 *
 * @version 1.0.0 2022-03-20
 * @author Bruno Ortiz <baov1995@gmail.com>
 * @since 1.0.0
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LibretaController {
    /**
     * Servicio para el manejo de la libreta
     */
    @Autowired
    private LibretaService libretaService;

    /**
     * Variable para el manejo de las respuestas de las API
     */
    private Response response = new Response();

    /**
     * Manejo del código HTTP que se responde en las API
     */
    private HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Listado de contactos
     */
    @GetMapping(path = "/contactos")
    public List<Contacto> listado(){
        var contactos = libretaService.getList();
        return contactos;
    }

    /**
     * Crea un nuevo contacto en el sistema
     *
     * @param contacto Objeto Contacto a crear
     * @return Objeto Response en formato JSON
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    @PostMapping(path = "/api/v1/contact")
    public ResponseEntity<Response> createContacto(@RequestBody Contacto contacto) {
        response.restart();
        try {
            log.info("Contacto a crear: {}", contacto);
            response.data = libretaService.createContacto(contacto);
            httpStatus = HttpStatus.CREATED;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Actualiza todos los campos de un contacto
     *
     * @param contacto Objeto contacto a actualizar
     * @param id Identificador del contacto a actualizar
     * @return Objeto Response en formato JSON
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    @PutMapping(path = "/api/v1/contact/{id}")
    public ResponseEntity<Response> updateContacto(@RequestBody Contacto contacto, @PathVariable(value="id") Integer id) {
        response.restart();
        try {
            response.data = libretaService.updateContacto(id, contacto);
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Borra logicamente a un contacto
     * @param id Identificador del contacto a borrar
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    @DeleteMapping("/api/v1/contact/logicDelete/{id}")
    public ResponseEntity<Response> deleteContactoLogico(@PathVariable(value="id") Integer id) {
        response.restart();
        try {
            response.data = libretaService.updateStatus(id);
            if (response.data == null) {
                response.message = "El contacto no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            }else {
                response.message = "El contacto fue removido exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Borra un contacto del sistema fisicamente
     *
     * @param id Identificador del contacto a borrar
     * @return Objeto Response en formato JSON
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    @DeleteMapping(path = "/api/v1/contact/physicalDelete/{id}")
    public ResponseEntity<Response> deleteContacto(@PathVariable(value="id") Integer id) {
        response.restart();
        try {
            response.data = libretaService.deleteContacto(id);
            if (response.data == null) {
                response.message = "El contacto no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            } else {
                response.message = "El contacto fue removido exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Administrador para las excepciones del sistema
     *
     * @param exception Objeto Exception
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageInternal(Exception exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Administrador para las excepciones a nivel de SQL con respecto al manejo del acceso a los datos
     *
     * @param exception Objeto DataAccessException
     *
     * @author Bruno Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageForResponse(DataAccessException exception) {
        response.error = true;
        if(exception.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) exception.getRootCause();
            var sqlErrorCode = sqlEx.getErrorCode();
            switch (sqlErrorCode) {
                case 1062:
                    response.message = "El dato ya está registrado";
                    break;
                case 1452:
                    response.message = "El usuario indicado no existe";
                    break;
                default:
                    response.message = exception.getMessage();
                    response.data = exception.getCause();
            }
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            response.message = exception.getMessage();
            response.data = exception.getCause();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
