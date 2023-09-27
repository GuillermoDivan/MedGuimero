package med.guimero.api.domain.doctor;
public record DoctorShowData(Long id, String name, Specialty specialty, String license, String phone) {

    public DoctorShowData(Doctor doctor){
        this(doctor.getId(), doctor.getName(), doctor.getSpecialty(), doctor.getLicense(), doctor.getPhone());
    }

}
