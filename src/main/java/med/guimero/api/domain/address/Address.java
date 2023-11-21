package med.guimero.api.domain.address;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Address {
   private String city;
   private String district;
   private String street;
   private String number;
   private String complement;

   public Address(AddressData addressData) {
      this.city = addressData.city();
      this.district = addressData.district();
      this.street = addressData.street();
      this.number = addressData.number();
      this.complement = addressData.complement();
   }
}
