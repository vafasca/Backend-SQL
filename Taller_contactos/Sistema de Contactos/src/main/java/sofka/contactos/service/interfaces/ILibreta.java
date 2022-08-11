package sofka.contactos.service.interfaces;

import sofka.contactos.domain.Contacto;

import java.util.List;

/**
 * Interface para el servicio de Libreta
 *
 * @version 1.0.0 2022-08-08
 * @author Bruno Alejandro Ortiz <baov1995@gmail.com>
 * @since 1.0.0
 */
public interface ILibreta {

    /**
     * Devuelve una lista de Contactos con todos contactos del sistema
     *
     * @return lista de contacto
     *
     * @author Bruno Alejandro Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
    public List<Contacto> getList();

    /**
     * Crea un contacto en el sistema
     *
     * @param contacto Objeto del contacto a crear
     * @return Objeto del contacto creado
     *
     * @author Bruno Alejandro Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
     public Contacto createContacto(Contacto contacto);

    /**
     * Actualiza una tupla completa de un contacto
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Objeto del contacto actualizado
     *
     * @author Bruno Alejandro Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
      public Contacto updateContacto(Integer id, Contacto contacto);

    /**
     * Borra un contacto del sistema basado en su identificador
     *
     * @param id Identificación del contacto a borrar
     * @return Objeto del contacto borrado
     *
     * @author Bruno Alejandro Ortiz <baov1995@gmail.com>
     * @since 1.0.0
     */
       public Contacto deleteContacto(Integer id);
}
