/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclassifier;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;

/**
 *
 * @author oktay
 */
public class RecieveMail {
    public static void check(String host,String user,
      String password,String folder) 
   {
	   Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
      try {
    	  
      Session emailSession = Session.getDefaultInstance(props,null);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("imaps");

      store.connect(host, user, password);

      //create the folder object and open it
      Folder inbox = store.getFolder("Inbox");
      inbox.open(Folder.READ_WRITE);
      
      Folder deneme=store.getFolder("deneme");
      if(!deneme.exists()){
          deneme.create(Folder.HOLDS_FOLDERS);
          
      }
       
      // retrieve the messages from the folder in an array and print it
      Message[] messages = inbox.getMessages();
      System.out.println("messages.length---" + messages.length);
      
      //Crate New Folder
      
      Folder defaultFolder=store.getDefaultFolder();
      CreateFolder create=new CreateFolder();
      create.createFolder(defaultFolder,folder);
      
      //get the message
      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];
         
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i + 1));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
         if(message.isMimeType("text/plain")){
        	   System.out.println("Text: " + message.getContent().toString());
         }else if(message.isMimeType("multipart/*")){
        	 MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        	 System.out.println("Text: " +getTextFromMimeMultipart(mimeMultipart));
         }
       
      } 
      
    //copy message to inbox to deneme 
      for(Message message:messages){
          
          int messageCount=messages.length;
     	 CopyMail copy=new CopyMail();
     	 copy.copy(message,deneme,messageCount);
      }
      
      
      
      
      //close the store and folder objects
      inbox.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   private static String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart) throws Exception{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
}
