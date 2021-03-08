public class tools{
  public static boolean isNumber(String number){
    try {
      int i = Integer.parseInt(number);
      return true;
    }catch (NumberFormatException nfe) {
        return false;
    }
  }

  public static String betweenFiles(){
    //returns \ if the OS is a windows version and / if not

    String osname = System.getProperty("os.name");

    if (osname.contains("Windows")) {
      return "\\";
    }else {
      return"/";
    }
  }
}
