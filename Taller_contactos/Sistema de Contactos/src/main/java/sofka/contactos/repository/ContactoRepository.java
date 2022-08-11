package sofka.contactos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sofka.contactos.domain.Contacto;
import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    /**
     * Query para buscar de forma descendente a los contactos
     * @return Devuelve una lista de contactos
     */
    @Query(value = "SELECT cnt " +
            "FROM Contacto cnt " +
            "WHERE (cnt.estado=true) " +
            "ORDER BY cnt.nombre DESC")
    public List<Contacto> findByStatus();

    /**
     *Query para cambiar el estado para el borrado logico
     * @param id del contacto
     */
    @Modifying
    @Query("update Contacto cnt set cnt.estado = false, cnt.updatedAt = CURRENT_DATE () where cnt.id = :id")
    public void updateStatus(
            @Param(value = "id") Integer id
    );
}