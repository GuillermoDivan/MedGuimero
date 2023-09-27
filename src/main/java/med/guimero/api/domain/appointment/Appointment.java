package med.guimero.api.domain.appointment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import med.guimero.api.domain.doctor.Doctor;
import med.guimero.api.domain.doctor.Specialty;
import med.guimero.api.domain.patient.Patient;

@Entity(name = "Appointment")
@Table(name = "appointments")
@Data
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    public Appointment() {}

    public Appointment(AppointmentRegisterData data){
        this.patient = new Patient();
        this.patient.setId(data.patientId());
        this.doctor = new Doctor();
        this.doctor.setId(data.doctorId());
        this.date = data.date();
        this.specialty = data.specialty();
    }


}
