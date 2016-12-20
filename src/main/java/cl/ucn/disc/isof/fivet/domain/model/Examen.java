package cl.ucn.disc.isof.fivet.domain.model;

import com.durrutia.ebean.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Rodrigo on 20-12-2016.
 */
public class Examen extends BaseModel{

    /**
     * ID del examen
     */
    @Getter
    @NotEmpty
    @Column(nullable = false)
    private String id;

    /**
     * Nombre del examen
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private String nombre;

    /**
     * Fecha en que se hizo el examen
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private Date fecha;

    /**
     * Resultado del examen
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private String resultado;
}
