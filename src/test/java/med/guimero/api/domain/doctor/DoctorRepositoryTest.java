package med.guimero.api.domain.doctor;

import med.guimero.api.domain.appointment.Appointment;
import med.guimero.api.domain.patient.Patient;
import med.guimero.api.domain.patient.PatientRegisterData;
import med.guimero.api.repositories.DoctorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Test de base de datos.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Para no modificar la base de datos real.
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em; //Elemento de JUnit que permite manejar conexión a base de datos.

    @Test
    @DisplayName("Should return null if doctor is busy in other appointment")
    void chooseDoctorBySpecialtyAndDateScenario1() {

        var nextMonday10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var patient = addPatient("Roberto Giordano", "20202020",
                LocalDate.now().plusYears(-20), null, "robertogiordano@gmail.com");
        var doctor = addDoctor("Julia Roberts", Specialty.CARDIOLOGIA,
                "3489", null, "juliaroberts@gmail.com"
        );
        addAppointment(patient, doctor, nextMonday10am);

        var availableDoctor = doctorRepository.chooseDoctorBySpecialtyAndDate
                (Specialty.CARDIOLOGIA, nextMonday10am);

        assertNull(availableDoctor);
    }

    @Test
    @DisplayName("Should return the doctor because is available appointment")
    void chooseDoctorBySpecialtyAndDateScenario2() {

        var nextMonday10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var doctor = addDoctor("Julia Roberts", Specialty.CARDIOLOGIA,
                "3489", null, "juliaroberts@gmail.com"
                );

        var availableDoctor = doctorRepository.chooseDoctorBySpecialtyAndDate
                (Specialty.CARDIOLOGIA, nextMonday10am);

        assertEquals(availableDoctor, doctor);
    }

    private void addAppointment(Patient patient, Doctor doctor, LocalDateTime date){
        em.persist(new Appointment(null, patient, doctor, date, Specialty.CARDIOLOGIA));
    }

    private Doctor addDoctor(String name, Specialty specialty, String license, String phone, String email){
        var doctor = new Doctor(new DoctorRegisterData(name, specialty, license, phone, email, null ));
        em.persist(doctor);
        return doctor;
    }

    private Patient addPatient(String name, String dni, LocalDate birthdate, String phone, String email){
        var patient = new Patient(new PatientRegisterData(name, dni, birthdate, phone, email, null));
        em.persist(patient);
        return patient;
    }

}