package lk.ijse.gdse.pos.pos_server_javaEE.bo;

import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse.pos.pos_server_javaEE.bo.custom.impl.PlaceOrderBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private  BoFactory(){

    }
    public static BoFactory getBoFactory(){
        return (boFactory==null)?boFactory=new BoFactory() :boFactory;
    }

    public enum BOType{
        CUSTOMER_BO,ITEM_BO,ORDER_BO,PLACE_ORDER_BO,
    }

    public <T extends SuperBO> T getBO(BOType boType){
        switch (boType){
            case CUSTOMER_BO:
                return (T) new CustomerBOImpl();
            case ITEM_BO:
                return (T) new ItemBOImpl();
            case ORDER_BO:
                return (T) new OrderBOImpl();
            case PLACE_ORDER_BO:
                return (T) new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
