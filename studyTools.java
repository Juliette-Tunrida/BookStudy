import java.util.Scanner;

public class studyTools{
  public static void readLoop(){
    //The main loop for reading the current book
    fileWorks fwo = new fileWorks();
    tools tool = new tools();
    writer wr = new writer();

    String book = fwo.readBook();

    String[] paragraphs = book.split("\n");

    //Selecting paragraph
    String para = paragraphs[paragraphs.length-1]; //Selected paragraph is stored in the last line of the book

    int paraInt = 0;
    if (tool.isNumber(para)) {
      paraInt = Integer.parseInt(para);
    }

    //Making sure book isnt too long. also should be compatible with previous versions
    String newParas = "";
    int maxleng = 500;

    for (int i = paraInt;i < paragraphs.length;i++ ) {

      //checks if the paragraph is longer than 500 characters.
      if(paragraphs[i].length() > maxleng){

        String[] tempParas = paragraphs[i].split("");
        int g = 0;
        for (int j = 0;j < tempParas.length; j++) {
          newParas = newParas + tempParas[j];
          g ++;

          if(g > maxleng && tempParas[j].equals(".")){
            newParas = newParas + "\n";
            g=0;
          }
        }
        newParas = newParas + "\n";

      }else{
        newParas = newParas + "\n" + paragraphs[i];
      }
    }


    String[] newParagraphs = newParas.split("\n");


    //Read the book starting at the Selected paragraph
    for (int i = paraInt; i < newParagraphs.length; i++) {
      wr.printLetterFilter(newParagraphs[i] + "\n",false);

      //updating the number in the bookfile in case the terminal is terminated
      String outBook ="";
      for (int j = 0;j < newParagraphs.length -1;j++ ) {
        outBook = outBook + newParagraphs[j] + "\n";
      }
      outBook = outBook + "\n" + (i+1);

      fwo.newBook(outBook,false);

      betweenPara();



    }

  }


  public static void betweenPara(){
    tools tool = new tools();
    Scanner sc = new Scanner(System.in);
    writer wr = new writer();
    fileWorks fw = new fileWorks();


    String ret = sc.nextLine();

    //Checks if user entered !help. if they did it shows the commands then lets the user enter something new
    for (boolean selected = false;selected ==false; ) {
      if (ret.contains("!help")) {
        wr.printLetterBool("List of between paragraph commands:\n",true);
        wr.printLetterBool("    simply press return - continue to next paragraph\n",true);
        wr.printLetterBool("    !help - opens this list\n",true);
        wr.printLetterBool("    !note + your text - Writes a note in the current file (use !n for new lines)\n",true);
        wr.printLetterBool("    !new + name - Creates a new note File\n",true);
        wr.printLetterBool("    !change - gives you a list of note files and allows you to switch the selected one\n",true);
        wr.printLetterBool("    !speed - allows you to change the default text speed\n",true);


        ret = sc.nextLine();
      }else if (ret.contains("!new")) {
        //Allows you to create a new note file
        fw.newNotes(ret);
        ret = sc.nextLine();
      }else if (ret.contains("!change")) {
        //Allows you to change the note file

        String[] names = fw.noteList();

        //returns all options
        boolean sel = false;
        while (sel == false) {
          for (int i = 0;i < names.length ;i++ ) {
            wr.printLetterBool(i + " " + names[i] + "\n",true);

          }

          //lets user choose options
          ret = sc.nextLine();

          //checks if user returned an int and if so selects default
          if (tool.isNumber(ret)) {
           int retInt = Integer.parseInt(ret);

           if (retInt >= 0 && retInt < names.length) {
             sel = true;
             fw.newDefault(names[retInt]);
             wr.printLetterBool("Selected: " + names[retInt] + " as default note\n",true);

           }
          }
          if (sel == false) {
            wr.printLetterBool("Sorry that was not an option.",true);
          }
        }
        ret = sc.nextLine();
      }else if (ret.contains("!speed")) {
        changeSpeed();
        ret = sc.nextLine();
      }else{

        selected = true;
      }
    }


    if (ret.contains("!note")) {
      //Allows you to enter a new note
      fw.writeNote(ret);
    }

  }

  public static void changeSpeed(){
    writer wr = new writer();
    fileWorks fw = new fileWorks();
    Scanner sc = new Scanner(System.in);
    tools tool = new tools();
    String slash = tools.betweenFiles();
    int newspeed = fw.textSpeed();

    for (boolean done = false; done == false ; ) {
      wr.printLetters("Current Speed is " + newspeed + "\nenter your new speed or enter !done\n",newspeed);
      String ret = sc.nextLine();

      if (tool.isNumber(ret)) {
        newspeed = Integer.parseInt(ret);
        fw.writeToFile(String.valueOf(newspeed),"Data"+slash+"textSpeed.data");
      }else if (ret.equals("!done")) {
        done = true;
      }else{
        wr.printLetters("Sorry that was not a number\n",newspeed);
      }
    }
  }



}
