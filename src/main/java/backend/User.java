package backend;

import lombok.Data;

@Data
public class User {
  private String id;
  private String prename;
  private String lastname;
  private String prsnumber;
  private String abtnumber;
  private String passhash;
  private String salt;
}
