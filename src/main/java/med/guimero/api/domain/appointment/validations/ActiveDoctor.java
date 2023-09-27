package med.guimero.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.doctor.DoctorRepository;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctor implements AppointmentValidator {
    private DoctorRepository doctorRepository;
    public ActiveDoctor(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public void validate(AppointmentRegisterData data){
        if (data.doctorId() == null) {return;}
        var activeDoctor =doctorRepository.findByIdAndActive(data.doctorId(), true);
        if (!activeDoctor.isPresent()) { throw new ValidationException
                ("El doctor debe estar activo.");}
    }

}