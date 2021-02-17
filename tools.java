public class tools{
  public static boolean isNumber(String number){
    try {
      int i = Integer.parseInt(number);
      return true;
    }catch (NumberFormatException nfe) {
        return false;
    }
  }
}
