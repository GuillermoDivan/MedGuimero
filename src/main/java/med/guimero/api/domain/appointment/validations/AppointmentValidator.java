package med.guimero.api.domain.appointment.validations;

import med.guimero.api.domain.appointment.AppointmentRegisterData;

public interface AppointmentValidator {

    public void validate(AppointmentRegisterData data);
}
