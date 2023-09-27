package med.guimero.api.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import med.guimero.api.domain.address.Address;
import med.guimero.api.domain.appointment.Appointment;
import java.time.LocalDate;
import java.util.List;

@Entity (name = "Patient")
@Table (name = "patients")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthdate;
        private String email;
        private String phone;
    @Embedded
        private Address address;
        private boolean active;
    @OneToMany(mappedBy = "patient")
    //@JoinColumn(name = "appointments_id", referencedColumnName = "patient_id")
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(PatientRegisterData data){
        this.name = data.name();
        this.dni = data.dni();
        this.birthdate = data.birthdate();
        this.phone = data.phone();
        this.email = data.email();
        if (data.address()!=null) {
            this.address = new Address(data.address());
        }
        this.active = true;
    }

    public Patient(PatientUpdateData data){
        this.id = data.id();
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
        if (data.address() != null) {
        this.address = new Address(data.address()); }
    }


}
