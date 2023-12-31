package med.guimero.api.domain.doctor;
import jakarta.persistence.*;
import lombok.*;
import med.guimero.api.domain.address.Address;
import med.guimero.api.domain.appointment.Appointment;
import java.util.List;

@Entity(name = "Doctor")
@Table(name = "doctors")
@Data
@NoArgsConstructor
//@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    private String license;
    private String phone;
    private String email;
    private Boolean active;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public Doctor(DoctorRegisterData doctorRegisterData) {
        this.name = doctorRegisterData.name();
        this.license = doctorRegisterData.license();
        this.phone = doctorRegisterData.phone();
        this.email = doctorRegisterData.email();
        this.active = true;
        this.specialty = doctorRegisterData.specialty();
        if (doctorRegisterData.address()!=null) {
            this.address = new Address(doctorRegisterData.address());
        }
    }
}
