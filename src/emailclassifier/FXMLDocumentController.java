/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclassifier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author oktay
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField adress;
     @FXML
    private PasswordField password;
   
     @FXML
    private CheckBox sosyal,reklam,ticari;
      
      
    @FXML
    private void btnGiris(ActionEvent event) {
       String host = "outlook.office365.com";// change accordingly
     
      String username = adress.getText();
      String pass = password.getText();
      
      RecieveMail recieve=new RecieveMail();
      if(sosyal.isSelected()){
          recieve.check(host, username, pass,sosyal.getText());
      }
      
      if(ticari.isSelected()){
          recieve.check(host, username, pass,ticari.getText());
      }
      if(reklam.isSelected()){
          recieve.check(host, username, pass,reklam.getText());
      }
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
