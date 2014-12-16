/*
 * Copyright (C) 2005 Luca Veltri - University of Parma - Italy
 * 
 * This file is part of MjSip (http://www.mjsip.org)
 * 
 * MjSip is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * MjSip is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MjSip; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Author(s):
 * Luca Veltri (luca.veltri@unipr.it)
 */

package org.zoolu.sip.transaction;


import org.zoolu.sip.message.Message;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.tools.LogLevel;



/** ACK client transaction should follow an INVITE client transaction within an INVITE Dialog in a SIP UAC.
  * The AckTransactionClient simply sends an ACK request message and terminates.
  */ 
public class AckTransactionClient extends Transaction
{  

   /** the TransactionClientListener that captures the events fired by the AckTransactionClient */
   TransactionClientListener transaction_listener=null;


   /** Creates a new AckTransactionClient. */
   public AckTransactionClient(SipProvider provider, Message ack, TransactionClientListener tr_listener)
   {  super(provider);
      transaction_listener=tr_listener;
      method=new Message(ack);
      transaction_id=ack.getTransactionId();
      printLog("created",LogLevel.HIGH);
   }
   
   /** Starts the AckTransactionClient and sends the ACK request. */
   public void request()
   {  printLog("start",LogLevel.LOW);
      sip_provider.sendMessage(method);
      changeStatus(STATE_TERMINATED);  
      //if (transaction_listener!=null) transaction_listener.onAckCltTerminated(this); 
      // (CHANGE-040421) free the link to transaction_listener
      transaction_listener=null;
   }     
         
   /** Method used to drop an active transaction. */
   public void terminate()
   {  changeStatus(STATE_TERMINATED);  
      // (CHANGE-040421) free the link to transaction_listener
      transaction_listener=null;
   }
  

   //**************************** Logs ****************************/

   /** Adds a new string to the default Log */
   void printLog(String str, int level)
   {  super.printLog("Client: Ack: "+str,level);
   }
}