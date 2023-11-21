package med.guimero.api.validations;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.repositories.DoctorRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveDoctor implements AppointmentValidator {
    private final DoctorRepository doctorRepository;

    public void validate(AppointmentRegisterData data){
        if (data.doctorId() == null) {return;}
        var activeDoctor =doctorRepository.findByIdAndActive(data.doctorId(), true);
        if (!activeDoctor.isPresent()) { throw new ValidationException
                ("El doctor seleccionado no se encuentra activo. Elija otro.");}
    }

}