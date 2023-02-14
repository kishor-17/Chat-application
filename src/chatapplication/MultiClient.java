package chatapplication;

import static chatapplication.MulticastClient.name;
import javax.swing.JOptionPane;

public class MultiClient {
    MultiClient() { 
        name= JOptionPane.showInputDialog("Please enter your name");
        boolean validUser=true;
        while( name==null || name.equals("") ) {
            if(name==null) {
                new ChatApp().setVisible(true);
                validUser=false;
                break;
            }
            if(name.equals("")) {
                JOptionPane.showMessageDialog(null,"Please enter a proper name");
                name=JOptionPane.showInputDialog("Please enter your name");
            }
        }
        if(validUser==true) { 
            new MulticastClient().setVisible(true);
            Thread t1=new Thread(new Client());
            t1.start();
        }
    }
}
