package med.guimero.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.appointment.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class AppointmentPerDay implements AppointmentValidator {
    private AppointmentRepository appointmentRepository;
    public AppointmentPerDay(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void validate(AppointmentRegisterData data){
        var firstSchedule = data.date().withHour(7);
        var lastSchedule = data.date().withHour(18);
        var patientAlreadyHasAppointment = appointmentRepository.existsByPatientIdAndDateBetween
                (data.patientId(), firstSchedule, lastSchedule);
        if (patientAlreadyHasAppointment) { throw new ValidationException
                ("El paciente no puede tener más de una consulta por día.");}



    }
}
