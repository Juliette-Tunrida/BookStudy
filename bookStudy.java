import java.util.Scanner;

public class bookStudy{

  public static void options(){
    Scanner sc = new Scanner(System.in);
    tools tool = new tools();
    writer wr = new writer();
    fileWorks fwo = new fileWorks();
    webFilter wf = new webFilter();
    studyTools st = new studyTools();

    //Switching between the current book or the new book
    for (boolean Selected = false;Selected ==false ; ) {
      String ret = sc.nextLine();
      if (tool.isNumber(ret)) {
        int retInt = Integer.parseInt(ret);
        if (retInt == 1) {
          //new book
          Selected = true;
          wr.printLetters("\nPlease enter the chapters url\n"
                          + "(If there are errors feel free to copy the text into Data/CurrentBook.txt)\n",30);
          ret = sc.nextLine();
          fwo.newBook(wf.pageFilter(wf.getPageContents(ret)),true);
          st.readLoop();
        }else if (retInt ==2) {
          //current book
          Selected = true;
          st.readLoop();
        }
      }
    }

  }

  public static void main(String[] args) {
    writer wr = new writer ();

    wr.printLetters("\n\n------------------------------------------- \n",10);
    wr.printLetters(" \n\n",50);
    wr.printLetters("   Welcome to Juliettes Book Study tool\n\n",50);
    wr.printLetters("       recommended for marxists.org\n",50);
    wr.printLetters(" \n\n",50);
    wr.printLetters("------------------------------------------- \n",10);
    wr.printLetters("\n\n[1] New Book\n[2] Previous Book\n",50);
    options();
  }
}
