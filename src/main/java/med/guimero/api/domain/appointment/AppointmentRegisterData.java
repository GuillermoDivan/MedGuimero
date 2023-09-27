package med.guimero.api.domain.appointment;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.guimero.api.domain.doctor.Specialty;


import java.time.LocalDateTime;

public record AppointmentRegisterData(
        @NotNull Long patientId,
        Long doctorId,
        @NotNull @Future LocalDateTime date,
        @NotNull Specialty specialty){

}
