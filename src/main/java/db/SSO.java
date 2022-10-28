package db;

import com.sun.jna.platform.win32.Sspi;
import com.sun.jna.platform.win32.Sspi.SecBufferDesc;

import waffle.windows.auth.IWindowsSecurityContext;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;
import waffle.windows.auth.impl.WindowsSecurityContextImpl;

import java.sql.Connection;


public class SSO {
  Connection con = new sql_connect().intern_connect();
  sql_statment sql = new sql_statment();

    public int bekommeMitarbeiterID(){
      try {
        String adresse= identifizieren();
        if (adresse==null) return -1;
        String[] daten = adresse.split("\\\\");
        String domain = daten[0];
        String user = daten[1];
        System.out.println("User: "+user+" Domain: "+domain);
        String id = sql.select_arr(Einstellungen.sso,"ms_m_id","WHERE ms_uname=\""+user+"\" AND ms_domain=\""+domain+"\"",con)[0][0];
        if (id == null) return -1;
        System.err.println(id);
        return Integer.parseInt(id);
      }catch (Exception e){
        System.err.println("!ERROR! Fehler im SSO:"+e);
        return -1;
      }

  }

    public String identifizieren() {  // Identifiziert den Nutzer via SSO
      try {
        // initialize a security context on the client
        IWindowsSecurityContext clientContext = WindowsSecurityContextImpl.getCurrent( "Negotiate", "localhost" );

        // create an auth provider and a security context for the client
        // on the server
        WindowsAuthProviderImpl provider = new WindowsAuthProviderImpl();
        IWindowsSecurityContext serverContext = null;

        // now you would send the byte[] token to the server and the server will
        // response with another byte[] token, which the client needs to answer again
        do {

          // Step 2: If you have already build an initial security context for the client
          // on the server, send a token back to the client, which the client needs to
          // accept and send back to the server again (a handshake)
          if (serverContext != null) {
            byte[] tokenForTheClientOnTheServer = serverContext.getToken();
            SecBufferDesc continueToken = new SecBufferDesc(Sspi.SECBUFFER_TOKEN, tokenForTheClientOnTheServer);
            clientContext.initialize(clientContext.getHandle(), continueToken, "localhost");
          }

          // Step 1: accept the token on the server and build a security context
          // representing the client on the server
          byte[] tokenForTheServerOnTheClient = clientContext.getToken();
          serverContext = provider.acceptSecurityToken("server-connection", tokenForTheServerOnTheClient, "Negotiate");

        } while (clientContext.isContinue());

        // at the end of this handshake, we know on the server side who the
        // client is, only by exchanging byte[] arrays
        return serverContext.getIdentity().getFqn();
      }catch (Exception e){
        System.err.println("Fehler in indentifizieren: "+e);
        return null;
      }

    }
  }

