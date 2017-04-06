/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclassifier;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author oktay
 */
public class CopyMail {
    public static void copy(Message message,Folder folder,int messageCount){
			
			try {
			    
			    message.getFolder().copyMessages(new Message[]{message}, folder);
                            
                            folder.open(Folder.READ_WRITE);
                            Message[] messages = folder.getMessages();
                            for(int i=(messages.length-1);i>(messages.length-(messageCount+1));i--){
                                         messages[i].setFlag(Flag.SEEN,false);
                            }
                            message.setFlag(Flag.DELETED,true);
                           
			    message.getFolder().expunge();
			} catch (MessagingException e) {
			    e.printStackTrace();
			}
		}
}
