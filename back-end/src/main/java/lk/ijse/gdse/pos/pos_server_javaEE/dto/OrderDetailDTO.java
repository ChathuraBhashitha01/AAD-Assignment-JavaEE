package lk.ijse.gdse.pos.pos_server_javaEE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private String orderID;
    private String itemCode;
    private int qty;
    private double price;
}
