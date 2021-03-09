public class writer{

  public static void sleepTool(int speed){
    //Tool to simplify the sleeping process
    try {
      Thread.sleep(speed);
    }catch (InterruptedException e) {
      System.out.println("Thread was interrupted");
    }
  }

  public static void printLetters(String letters,int speed){
    //Turn string into String array

    //Prints each letter with a short delay inbetween
    for (int i = 0;i < letters.length() ;i++ ) {
      System.out.print(letters.charAt(i));
      sleepTool(speed);
    }
  }

  public static void printLetterBool(String letters, boolean readFast){
    tools tool = new tools();

    //Simpler versin of printLetters with two preset speeds
    int speed = 95;
    if (readFast) {
      speed = 35;
    }
    printLetters(letters,speed);
  }

  public static void printLetterFilter(String letters, boolean readFast){
    tools tool = new tools();
    fileWorks fw = new fileWorks();
    //Change speed
    int speed = fw.textSpeed();
    if (readFast) {
      speed = 35;
    }

    for (int i = 0;i < letters.length() ;i++ ) {
      //Prints the letter
      char let = letters.charAt(i);
      System.out.print(let);
      //Deterining the Delay
      if (let == '.'||let == '!'||let == '?') {
        //Determining the use of the dot. (end of sentence, for numbers, or abbreviations)
        if (let == '.') {
          //I added a boolean to make sure it only sleeps once
          boolean slept = false;
          if (i > 0) {
            //Check if dot is before a letter
            if (tool.isNumber(Character.toString(letters.charAt(i-1))) == true) {
              sleepTool(speed);
              slept = true;
            }
          }
          if (i < letters.length() && slept == false && i > 2) {
            //Checks if the next letter is not a space. Usefull for abbreviation and other
            if ((letters.charAt(i + 1)) != ' '||letters.charAt(i-2) == '.') {
              sleepTool(speed);
            }else {
              sleepTool(speed*15);
            }
          }else if(slept == false){
            sleepTool(speed*15);
          }
        }else {
          sleepTool(speed*15);
        }
      }else{
        sleepTool(speed);
      }
    }

  }

  }
