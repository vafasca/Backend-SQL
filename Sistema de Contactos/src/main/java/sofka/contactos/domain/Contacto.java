package sofka.contactos.domain;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * Entidad del Contacto
 *
 * @version 1.0.0 2022-08-08
 * @author Bruno Alejandro Ortiz Velasquez <baov1995@gmail.com>
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "contacto")
public class Contacto implements Serializable {

    /**
     * Variable usada para manejar el tema del identificador de la tupla (consecutivo)
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la tupla
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnt_id", columnDefinition = "INT UNSIGNED not null")
    private Integer id;

    /**
     * Nombre del contacto
     */
    @Column(name = "cnt_nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Email del contacto
     */
    @Column(name = "cnt_email", nullable = false, length = 100)
    private String email;

    /**
     * Fecha de nacimiento del contacto
     */
    @Column(name = "cnt_fecha_nacimiento", nullable = false, length = 100)
    private String fecha_nacimiento;

    /**
     * Telefono del contacto
     */
    @Column(name = "cnt_telefono", nullable = false, length = 100)
    private int telefono;

    /**
     * Estado del contacto
     */
    @Column(name = "cnt_estado")
    private boolean estado;

    /**
     * Fecha y hora en que la tupla ha sido creada
     */
    @Column(name = "cnt_created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Fecha y hora en que la tupla ha sido actualizada por última vez
     */
    @Column(name = "cnt_updated_at")
    private Instant updatedAt;
}