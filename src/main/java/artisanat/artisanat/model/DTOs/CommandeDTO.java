package artisanat.artisanat.model.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommandeDTO {

    Long numeroTel;
    String willaya;
    String address;
    String typeDePaiement;
    double totalAmount;
}
