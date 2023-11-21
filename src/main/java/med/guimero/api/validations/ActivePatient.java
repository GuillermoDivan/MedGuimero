package med.guimero.api.validations;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.repositories.PatientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivePatient implements AppointmentValidator {

    private final PatientRepository patientRepository;

    public void validate(AppointmentRegisterData data){
        if (data.patientId() == null) {return;}
        var activePatient =patientRepository.findByIdAndActive(data.patientId(), true);
        if (!activePatient.isPresent()) { throw new ValidationException
                ("El paciente seleccionado no está activo. Revise el estado de su cuenta.");}
    }
}
