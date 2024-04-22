//package it.polimi.ingsw;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//public class JsonTest {
  //  private String fileName;
 //   private JsonObject jsonObject;
  //  public JsonTest(String fileName) {
  //     this.fileName= fileName;
 //   }
  //  public void loadData() throws FileNotFoundException {
 //       JsonParser parser = new JsonParser();
 //       Object obj = parser.parse(new FileReader(this.fileName));
  //      this.jsonObject= (JsonObject) obj;
 //   }

    //creo un iteratore, un oggetto che cicla lungo un contenitore:
    //questo iteratore saprà scorrere su tutti gli elementi che contiene
 //   public List<String> getResourceCardnames() {
  //      List<String> carte= new ArrayList<>();
        //prendi jsonObject, recuperane le chiavi e creo un iteratore
        //che può scorrere lungo quelle chiavi
   //     Iterator iter = this.jsonObject.keySet().iterator();
     //   while (iter.hasNext()) {
       //     String keyCarta= (String) iter.next();
         //   carte.add(keyCarta);
        //}
        //return carte;
    //}
    //public static void main(String[] args) throws FileNotFoundException {
     //   System.out.println("JSON TEST: leggo e scrivo un json");
       // JsonTest jsonTest= new JsonTest("");
      //  jsonTest.loadData();
       // System.out.println(jsonTest.getResourceCardnames());
    //}
//}
