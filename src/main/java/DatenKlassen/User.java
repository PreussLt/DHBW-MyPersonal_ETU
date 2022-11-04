package DatenKlassen;

import lombok.Data;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
public class User {
  private String id;
  private String prename;
  private String lastname;
  private String prsnumber;
  private String abtnumber;
  private String passhash;
  private String salt;
  public User(){}
  public User(String id, String prename, String lastname){
    this.id = id;
    this.prename = prename;
    this.lastname = lastname;
  }
}
