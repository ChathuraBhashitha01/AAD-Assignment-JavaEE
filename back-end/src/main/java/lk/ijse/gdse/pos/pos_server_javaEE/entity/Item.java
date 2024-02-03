package lk.ijse.gdse.pos.pos_server_javaEE.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String code;
    private String description;
    private int qtyOnHand;
    private double unitPrice;
}
