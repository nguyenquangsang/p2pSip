/*
 * Copyright (C) 2005 Luca Veltri - University of Parma - Italy
 * 
 * This source code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Author(s):
 * Luca Veltri (luca.veltri@unipr.it)
 */

package local.server;


import org.zoolu.sip.address.*;
import org.zoolu.sip.header.WwwAuthenticateHeader;
import org.zoolu.sip.header.AuthorizationHeader;
import org.zoolu.sip.header.AuthenticationInfoHeader;
import org.zoolu.sip.message.*;
import org.zoolu.sip.provider.SipStack;
import org.zoolu.sip.authentication.DigestAuthentication;
import org.zoolu.tools.Log;
import org.zoolu.tools.LogLevel;
import org.zoolu.tools.Parser;
import org.zoolu.tools.MD5;


/** Class AuthenticationServerImpl implements an AuthenticationServer
  * for HTTP Digest authentication.
  */
public class AuthenticationServerImpl implements AuthenticationServer
{   

   /** Event logger. */
   protected Log log=null;

   /** The repository of users's authentication data. */
   protected AuthenticationService authentication_service;
   
   /** The authentication realm. */
   protected String realm;
   
   /** The authentication scheme. */
   protected String authentication_scheme="Digest";

   /** The authentication qop-options. */
   protected String qop_options="auth,auth-int";

   /** The current random value. */
   protected byte[] rand;

   /** DIGEST */
   //public static final String DIGEST="Digest";
   /** AKA */
   //public static final String AKA="AKA";
   /** CHAP */
   //public static final String CHAP="CHAP";


   /** Costructs a new AuthenticationServerImpl. */
   public AuthenticationServerImpl(String realm, AuthenticationService authentication_service, Log log)
   {  init(realm,authentication_service,log);
   }
 
   
   /** Inits the AuthenticationServerImpl. */
   private void init(String realm, AuthenticationService authentication_service, Log log)
   {  this.log=log;
      this.realm=realm;
      this.authentication_service=authentication_service;
      this.rand=pickRandBytes();
   }

   /** Gets the realm. */
   /*public String getRealm()
   {  return realm;
   }*/


   /** Gets the qop-options. */
   /*public String getQopOptions()
   {  return qop_options;
   }*/


   /** Gets the current rand value. */
   /*public String getRand()
   {  return HEX(rand);
   }*/


   /** Authenticates a SIP request.
     * @param msg is the SIP request to be authenticated
     * @return it returns the error Message in case of authentication failure,
     * or null in case of authentication success. */
   public Message authenticateRequest(Message msg)
   {  Message err_resp=null;

      //String username=msg.getFromHeader().getNameAddress().getAddress().getUserName();
      //String user=username+"@"+realm;

      AuthorizationHeader ah=msg.getAuthorizationHeader();
         
      if (ah!=null && ah.getNonceParam().equals(HEX(rand)))
      {
         //String username=ah.getUsernameParam();
         String realm=ah.getRealmParam();
         String nonce=ah.getNonceParam();
         String username=ah.getUsernameParam();
         String scheme=ah.getAuthScheme();
         
         String user=username+"@"+realm;
         
         if (authentication_service.hasUser(user))
         {  
            if (authentication_scheme.equalsIgnoreCase(scheme))
            {  
               DigestAuthentication auth=new DigestAuthentication(msg.getRequestLine().getMethod(),ah,null,keyToPasswd(authentication_service.getUserKey(user)));

               // check user's authentication response
               boolean is_authorized=auth.checkResponse(); 

               rand=pickRandBytes();        
                  
               if (!is_authorized)
               {  // authentication/authorization failed
                  int result=403; // response code 403 ("Forbidden")
                  err_resp=MessageFactory.createResponse(msg,result,SipResponses.reasonOf(result),null,null);
                  printLog("LOGIN ERROR: Authentication of '"+user+"' failed",LogLevel.HIGH);
               }
               else
               {  // authentication/authorization successed
                  printLog("Authentication of '"+user+"' successed",LogLevel.HIGH);
               }
            }
            else
            {  // authentication/authorization failed
               int result=400; // response code 400 ("Bad request")
               err_resp=MessageFactory.createResponse(msg,result,SipResponses.reasonOf(result),null,null);
               printLog("Authentication method '"+scheme+"' not supported.",LogLevel.HIGH);
            }
         }
         else
         {  // no authentication credential found for this user
            int result=404; // response code 404 ("Not Found")
            err_resp=MessageFactory.createResponse(msg,result,SipResponses.reasonOf(result),null,null);  
         }
      }
      else
      {  // no Authorization header found
         int result=401; // response code 401 ("Unauthorized")
         err_resp=MessageFactory.createResponse(msg,result,SipResponses.reasonOf(result),null,null);
         WwwAuthenticateHeader wah=new WwwAuthenticateHeader("Digest");
         wah.addRealmParam(realm);
         wah.addQopOptionsParam(qop_options);
         wah.addNonceParam(HEX(rand));
         err_resp.setWwwAuthenticateHeader(wah); 
      }
      return err_resp;
   }


   /** Gets AuthenticationInfoHeader. */
   public AuthenticationInfoHeader getAuthenticationInfoHeader()
   {  AuthenticationInfoHeader aih=new AuthenticationInfoHeader();
      aih.addRealmParam(realm);
      aih.addQopOptionsParam(qop_options);
      aih.addNextnonceParam(HEX(rand));
      return aih;
   }


   /** Picks a random array of 16 bytes. */
   private static byte[] pickRandBytes()
   {  return MD5(Long.toHexString(org.zoolu.tools.Random.nextLong()));
   }

   /** Converts the byte[] key in a String passwd. */
   private static String keyToPasswd(byte[] key)
   {  return new String(key);
   }

   /** Calculates the MD5 of a String. */
   private static byte[] MD5(String str)
   {  return MD5.digest(str);
   }

   /** Calculates the MD5 of an array of bytes. */
   private static byte[] MD5(byte[] bb)
   {  return MD5.digest(bb);
   }

   /** Calculates the HEX of an array of bytes. */
   private static String HEX(byte[] bb)
   {  return MD5.asHex(bb);
   }

   // ****************************** Logs *****************************

   /** Adds a new string to the default Log */
   private void printLog(String str, int level)
   {  if (log!=null) log.println("AuthenticationServer: "+str,level+SipStack.LOG_LEVEL_UA);  
   }

}