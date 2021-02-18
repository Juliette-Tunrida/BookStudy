import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class fileWorks{
  public static void newBook(String text,boolean newb){
    tools tool = new tools();
    String slash = tool.betweenFiles();
    try {

      FileWriter fw = new FileWriter("Data"+ slash +"CurrentBook.txt");
      PrintWriter out = new PrintWriter(fw);

      if (newb == true) {
        out.print(text + "\n0");
      }else {
        out.print(text);
      }

      out.flush();

      out.close();

      fw.close();

    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String readBook(){
    tools tool = new tools();
    String slash = tool.betweenFiles();
    String line = "";
    String text = "";

    try {
      BufferedReader br = new BufferedReader(new FileReader("Data"+slash+"CurrentBook.txt"));
      while((line = br.readLine())!= null){
        text = text + line + "\n";
      }
    }catch (IOException e) {
      e.printStackTrace();
    }
    return text;
  }

  public static void newNotes(String name){
    tools tool = new tools();
    String slash = tool.betweenFiles();

    name = name.replace("!new","");
    name = name.replace(" ","");

    String file = "Data"+slash + name + ".txt";


    try {
      //Creating new file
      FileWriter fw = new FileWriter(file);
      PrintWriter out = new PrintWriter(fw);

      out.print("Notes for: " + name);

      out.flush();

      out.close();

      fw.close();

      //Adding note to note list

      String[] nl = noteList();

      FileWriter fwr = new FileWriter("Data"+slash+"noteList.txt");
      PrintWriter outt = new PrintWriter(fwr);

      for (int i = 0;i < nl.length ;i++ ) {
        outt.print(nl[i] + "\n");
      }
      outt.print(name + ".txt\n");

      outt.flush();
      outt.close();
      fwr.close();

      //selects new note as Default
      newDefault(name);
    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void newDefault(String name){
    tools tool = new tools();
    String slash = tool.betweenFiles();
    name = name.replace("!change","");


    String file = "";

    if (!name.contains(".txt")) {
      file = name + ".txt";
    }else {
      file = name;
    }

    try {
      //Creating new file
      FileWriter fw = new FileWriter("Data"+slash+"selectedNote.txt");
      PrintWriter out = new PrintWriter(fw);

      out.print(file);

      out.flush();

      out.close();

      fw.close();


    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String[] noteList(){
    tools tool = new tools();
    String slash = tool.betweenFiles();
    String list = "";
    String line = "";

    try {
      BufferedReader br = new BufferedReader(new FileReader("Data"+slash+"noteList.txt"));
      while((line = br.readLine())!=null){
        list = list + line + "\n";
      }
      return list.split("\n");

    }catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String readFile(String filename){
    //returns the currently selected note
    String line = "";
    String sel = "";
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      while((line = br.readLine())!=null){
        sel = sel + line + "\n";
      }
      return sel;
    }catch (IOException e) {
      e.printStackTrace();
    }
    return sel;
  }

  public static void writeNote(String note){
    tools tool = new tools();
    String slash = tool.betweenFiles();

    //Writes a note to a note file
    note = note.replace("!note","");
    note = note.replace("!n","\n");

    String selnote = readFile("Data"+slash+"selectedNote.txt");
    selnote = selnote.replace(" ","");
    selnote = selnote.replace("\n","");
    selnote = "Data" + slash + selnote;

    try {
      //Gets notes from file
      String noteText = readFile(selnote);

      noteText = noteText + "\n" + note;

      FileWriter fw = new FileWriter(selnote);
      PrintWriter pw = new PrintWriter(fw);

      //ovewrites file with notes and new note
      String[] arrText = noteText.split("\n");

      for (int i = 0; i < arrText.length; i++) {
        pw.print(arrText[i] + "\n");
      }

      pw.flush();
      pw.close();
      fw.close();

    }catch (IOException e) {
      e.printStackTrace();
    }
  }
}
