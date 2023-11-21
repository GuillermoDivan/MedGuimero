package med.guimero.api.validations;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientHasOtherAppointment implements AppointmentValidator {
    private final AppointmentRepository appointmentRepository;

    public void validate(AppointmentRegisterData data){
        var begins = data.date();
        var ends = data.date().plusHours(1);
        var patientAlreadyHasAppointment = appointmentRepository.existsByPatientIdAndDateBetween
                (data.patientId(), begins, ends);
        if (patientAlreadyHasAppointment) { throw new ValidationException
                ("No se pudo programar la cita. El paciente tiene una consulta programada para ese horario.");}

    }
}
