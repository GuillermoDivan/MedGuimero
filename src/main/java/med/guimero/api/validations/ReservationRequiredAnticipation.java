package med.guimero.api.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ReservationRequiredAnticipation implements AppointmentValidator{
    public void validate(AppointmentRegisterData data){
        var now = LocalDateTime.now();
        var date = data.date();
        var anticipation = Duration.between(now, date).toMinutes()<30;
        if (anticipation) { throw new ValidationException("La consulta debe programarse con al menos 30 minutos de anticipación");
        }
    }
}
