
import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.util.EncriptacionUtil;
import com.teamj.distribuidas.util.ValidationUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jose Guaman
 */
public class Test {

    public static void main(String[] args) {
        try {
            System.out.println("La clave es:");
        System.out.println(EncriptacionUtil.encriptarMD5("jeguaman"));
//            System.out.println(ValidationUtil.getFechaYHora());
            
//            FacadeNegocio.retrieveTodosSistemas();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
