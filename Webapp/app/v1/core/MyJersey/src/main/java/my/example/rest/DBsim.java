package my.example.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 
public class DBsim {
 
static Map<Integer, User> db = new HashMap<Integer, User>();
 static {
 User user1 = new User();
 user1.setId(1);
 user1.setFirstName("Peter");
 user1.setLastName("Bratsh");
 
User user2 = new User();
 user2.setId(2);
 user2.setFirstName("John");
 user2.setLastName("Williams");
 
 db.put(1, user1);
 db.put(2, user2);
 
}
 
public static User getUser(int id) {
 return db.get(id);
 }
 
public static List<User> getAllUsers() {
 return new ArrayList<User>(db.values());
 }
}