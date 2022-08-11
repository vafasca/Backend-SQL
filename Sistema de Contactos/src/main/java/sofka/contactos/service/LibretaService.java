package sofka.contactos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sofka.contactos.domain.Contacto;
import sofka.contactos.repository.ContactoRepository;
import sofka.contactos.service.interfaces.ILibreta;

import java.time.Instant;
import java.util.List;

/**
 * Clase tipo Servicio para el manejo de la libreta
 *
 * @version 1.0.0 2022-11-22
 * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
 * @since 1.0.0
 */
@Service
public class LibretaService implements ILibreta {

    /**
     * Repositorio de Contacto
     */
    @Autowired
    private ContactoRepository contactoRepository;

    /**
     * Devuelve una lista de Contactos con todos contactos del sistema
     *
     * @return
     *
     * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
     * @since 1.0.0
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contacto> getList() {
        return (List<Contacto>) contactoRepository.findByStatus();
    }

    /**
     * Crea un contacto en el sistema
     *
     * @param contacto Objeto del contacto a crear
     * @return Objeto del contacto creado
     *
     * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
     * @since 1.0.0
     */
    @Override
    @Transactional
    public Contacto createContacto(Contacto contacto) {
    contacto.setCreatedAt(Instant.now());
    contacto.setEstado(true);
        return contactoRepository.save(contacto);
    }

    /**
     * Actualiza a un contacto
     *
     * @param id Identificador del contacto a actualizar
     * @param contacto Objeto del contacto a actualizar
     * @return Devuelve al contacto actualizado
     *
     * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
     * @since 1.0.0
     */
    @Override
    @Transactional
    public Contacto updateContacto(Integer id, Contacto contacto) {
    contacto.setId(id);
    contacto.setUpdatedAt(Instant.now());
    contacto.setEstado(true);
        return contactoRepository.save(contacto);
    }

    /**
     *
     * @param id Identificcdor del contacto a actualizar el estado
     * @return Devuelve al contacto con el estado cambiado
     *
     * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
     * @since 1.0.0
     */
    @Transactional
    public Contacto updateStatus(Integer id){
        var contacto = contactoRepository.findById(id);
        if (contacto.isPresent()){
            contactoRepository.updateStatus(id);
            return contacto.get();
        }else {
            return null;
        }
    }

    /**
     * Borra a un contacto
     *
     * @param id Identificación del contacto a borrar
     * @return Devuelve al contacto borrado si existe
     *
     * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
     * @since 1.0.0
     */
    @Override
    @Transactional
    public Contacto deleteContacto(Integer id) {
        var contacto = contactoRepository.findById(id);
        if (contacto.isPresent()){
            contactoRepository.delete(contacto.get());
            return contacto.get();
        }else {
            return null;
        }
    }
}
