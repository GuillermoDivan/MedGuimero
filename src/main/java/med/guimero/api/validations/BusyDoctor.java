package med.guimero.api.validations;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusyDoctor implements AppointmentValidator{
    private final AppointmentRepository appointmentRepository;

    public void validate(AppointmentRegisterData data){
        if (data.doctorId() == null) {return; }

        var begins = data.date();
        var ends = data.date().plusHours(1);
        var busyDoctor = appointmentRepository.existsByDoctorIdAndDateBetween
                (data.doctorId(), begins, ends);
        if(busyDoctor) {throw new ValidationException
                ("No se pudo programar la cita. El médico tiene una consulta programada para ese horario.");}

    }
}
