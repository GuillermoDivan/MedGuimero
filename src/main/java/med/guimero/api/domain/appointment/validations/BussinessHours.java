package med.guimero.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class BussinessHours implements AppointmentValidator {
    public void validate(AppointmentRegisterData data){
        var sunday = DayOfWeek.SUNDAY.equals(data.date().getDayOfWeek());
        var beforeOpening =data.date().getHour()<7;
        var afterClosing =data.date().getHour()>19;
        if(sunday || beforeOpening || afterClosing){ throw new ValidationException
                ("El horario de atención es de 7.00 a 19.00hs, Lunes a Sábado."); }
    }

}
