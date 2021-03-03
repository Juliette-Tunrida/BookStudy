import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class fileWorks{

  public static void changeMainBook(){

      writer wr = new writer();
      Scanner sc = new Scanner(System.in);
      tools tool = new tools();

      File path = new File("Data");

      String[] contents = path.list();
      String conts = "";
      String spliby = ">-...-<";

      for (int i = 0;i<contents.length ;i++ ) {
        if (!contents[i].contains("Book")) {
          contents[i] = null;
        }else{
          conts += contents[i] + spliby;
        }
      }
      String[] books = conts.split(spliby);

      for (int i = 0;i < books.length ;i++ ) {
        wr.printLetterBool(i + " - " + books[i] + "\n",true);
      }
      int retInt = -1;
      String ret = null;

      while(ret == null){
        wr.printLetterBool("\nEnter the Number of a book to select it\n",true);

        ret = sc.nextLine();

        if (tool.isNumber(ret)==true) {
          retInt = Integer.parseInt(ret);

          if (retInt < 0 || retInt >= books.length) {
            ret = null;
          }

        }else {
          ret = null;
        }

      }

      wr.printLetterBool("Selected " + books[retInt] + " as the Default Book\n",true);

      //Finding wich book is selected
      String selectedBook = "CurrentBook.Book";

      String slash = tools.betweenFiles();

      String currBookIdent = readFile("Data" + slash + "CurrentBook.Book").split("\n")[0];


      for (int i = 0;i < books.length;i++ ) {
        if (!books[i].equals("CurrentBook.Book")) {
          String ident = readFile("Data" + slash + books[i]).split("\n")[0];

          if (ident.equals(currBookIdent)) {
            selectedBook = books[i];

          }

        }
      }

      //Saving previously selected Book

      String currText = readFile("Data" + slash + "CurrentBook.Book");
      writeToFile(currText,"Data" + slash + selectedBook);

      //Updating current Book

      currText = readFile("Data" + slash + books[retInt]);
      writeToFile(currText,"Data" + slash + "CurrentBook.Book");
  }

  public static void writeToFile(String text,String file){
    try {
      String[] lines = text.split("\n");
      FileWriter fw = new FileWriter(file);
      PrintWriter out = new PrintWriter(fw);

      for (int i = 0;i < lines.length ;i++ ) {
        out.print(lines[i] + "\n");
      }

      out.flush();
      out.close();
      fw.close();

    }catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void newBook(String text,boolean newb){
    tools tool = new tools();
    writer wr = new writer();
    Scanner sc = new Scanner(System.in);
    String slash = tool.betweenFiles();

    String bookname = "CurrentBook";

    if (newb == true) {
      wr.printLetterBool("Please Name the Book\n",true);
      bookname = sc.nextLine();
      bookname = bookname.replace(" ","-");
    }

    for(int i = 0;i < 2;i++){
      try {

        FileWriter fw = new FileWriter("Data"+ slash + bookname +".Book");
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
      if (bookname.contains("CurrentBook")) {
        i = 2;

      }else {
        bookname = "CurrentBook";
      }
    }
  }

  public static String readBook(){
    tools tool = new tools();
    String slash = tool.betweenFiles();
    String line = "";
    String text = "";

    try {
      BufferedReader br = new BufferedReader(new FileReader("Data"+slash+"CurrentBook.Book"));
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

    String file = "Data"+slash + name + ".Note";


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

      FileWriter fwr = new FileWriter("Data"+slash+"noteList.data");
      PrintWriter outt = new PrintWriter(fwr);

      for (int i = 0;i < nl.length ;i++ ) {
        outt.print(nl[i] + "\n");
      }
      outt.print(name + ".Note\n");

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

    if (!name.contains(".Note")) {
      file = name + ".Note";
    }else {
      file = name;
    }

    try {
      //Creating new file
      FileWriter fw = new FileWriter("Data"+slash+"selectedNote.data");
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
      BufferedReader br = new BufferedReader(new FileReader("Data"+slash+"noteList.data"));
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

    String selnote = readFile("Data"+slash+"selectedNote.data");
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
