package med.guimero.api.validations;

import med.guimero.api.domain.appointment.AppointmentRegisterData;

public interface AppointmentValidator {

    public void validate(AppointmentRegisterData data);
}
