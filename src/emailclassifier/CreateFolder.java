/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclassifier;

import javax.mail.Folder;
import javax.mail.MessagingException;

/**
 *
 * @author oktay
 */
public class CreateFolder {
    public boolean createFolder(Folder parent, String folderName)    throws MessagingException
	{   
	    boolean isCreated = true;   

	    try  
	    {   
	        Folder newFolder = parent.getFolder(folderName);   
	        isCreated = newFolder.create(Folder.HOLDS_MESSAGES);   
	        System.out.println("created: " + isCreated);   

	    } catch (Exception e)   
	    {   
	        System.out.println("Error creating folder: " + e.getMessage());   
	        e.printStackTrace();   
	        isCreated = false;   
	    }   
	    return isCreated;   
	}
}
