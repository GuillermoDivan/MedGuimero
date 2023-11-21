package med.guimero.api.validations;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.repositories.AppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CancelRequiredAnticipation implements CancelationValidator {
    private final AppointmentRepository appointmentRepository;

    @Override
    public void validate(Long id) {
        LocalDateTime now = LocalDateTime.now();
        var appointment = appointmentRepository.findById(id).get();
        var time = Duration.between(now, appointment.getDate());
        if ((time.toHours() < 24))
        { throw new ValidationException("La consulta no puede cancelarse y será cobrada con normalidad. Para cancelar una consulta requiere al menos de 24hs de anticipación.");
        }
    }
}
