package cl.ucn.disc.isof.fivet.domain.model;

import com.durrutia.ebean.BaseModel;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo on 20-12-2016.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Control extends BaseModel{

    /**
     * ID del control
     */
    @Getter
    @NotEmpty
    @Column(nullable = false)
    private String id;

    /**
     * Fecha en que se hizo el control
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private Date fecha;

    /**
     * Fecha del proximo control
     */
    @Getter
    @Setter
    @Column
    private Date fechaProximo;

    /**
     * Temperatura del paciente
     */
    @Getter
    @Setter
    @Column
    private Double temperatura;

    /**
     * Peso del paciente en KG
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private Double peso;

    /**
     * Altura del paciente en cms
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private Integer altura;

    /**
     * Diagnostico del paciente
     */
    @Getter
    @Setter
    @Column
    @NotNull
    private String diagnostico;

    /**
     * NOTA sobre el control realizado
     */
    @Getter
    @Setter
    @Column
    private String nota;

    /**
     * Examenes que el paciente se ha realizado o debe realizarse
     */
    @Getter
    @Setter
    private List< Examen > examenes;
}
