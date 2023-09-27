package med.guimero.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.appointment.AppointmentRepository;
import org.springframework.stereotype.Component;

@Component
public class BusyDoctor implements AppointmentValidator{
    private AppointmentRepository appointmentRepository;
    public BusyDoctor(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void validate(AppointmentRegisterData data){
        if (data.doctorId() == null) {return; }

        var busyDoctor = appointmentRepository.existsByDoctorIdAndDate
                (data.doctorId(), data.date());
        if(busyDoctor) {throw new ValidationException
                ("El médico tiene una consulta programada para ese horario.");}

    }
}
