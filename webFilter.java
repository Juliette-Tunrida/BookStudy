import java.util.Scanner;
import java.net.URL;
import java.io.IOException;

public class webFilter{

  public static String getPageContents(String strUrl){
    //Function for getting the html data
    try{
      URL url = new URL(strUrl);
      Scanner term = new Scanner(System.in);
      Scanner sc = new Scanner(url.openStream());

      StringBuffer sb = new StringBuffer();
      while (sc.hasNext()) {
        sb.append(sc.next());
        sb.append(" ");
        //System.out.println(sc.next());
      }
      String result = sb.toString();
      return result;
    }catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String pageFilter(String page){
    //Split the title of the page
    String title = page.split("<head>")[1];
    title = title.split("</head>")[0];
    //because for some reason writing TITLE and title is the same
    if (title.contains("<title>")) {
      title = title.split("<title>")[1];
      title = title.split("</title")[0];
    }else if (title.contains("<TITLE>")) {
      title = title.split("<TITLE>")[1];
      title = title.split("</TITLE")[0];
    }


    //Splits the body from the useless code at the beginning and end of the text
    String body = "";
    String bd = "";
    if (page.contains("<body")) {
      body = page.split("<body>")[1];
    }else if (page.contains("<BODY>")) {
      body = page.split("<BODY>")[1];
    }
    if (page.contains("</body")) {
      body = body.split("</body>")[0];
    }else if (page.contains("</BODY>")) {
      body = body.split("</BODY>")[0];
    }

    //Removing and replacing code

    body = body.replaceAll("&#8220;","\"");
    body = body.replaceAll("&#8221;","\"");
    body = body.replaceAll("&#8217;","'");
    body = body.replaceAll("&#8216;","'");
    body = body.replaceAll("&#8212;","-");
    body = body.replaceAll("&#8211;","-");
    body = body.replaceAll("&auml;","ae");
    body = body.replaceAll("&uuml;","ue");
    body = body.replaceAll("&ouml;","oe");

    String[] toRemove = {"<p>","<br />","<br>","</br>","<br/>"
                        ,"<h1>","<h2>","<h3>","<h4>","<h5>","<h6>"
                        ,"</h1>","</h2>","</h3>","</h4>","</h5>","</h6>"
                        ,"</span>","</sup>","</a>","<em>","</em>"
                        ,"<hr>","<tbody>"};

    for (int i = 0;i < toRemove.length ;i++ ) {
      body = body.replaceAll(toRemove[i],"");
    }

    //Removing sections

    String splby = ">...<";

    String[] secRemove = {"<img" + splby + "/>","<hr" + splby + "/>"
                          ,"<p" + splby + ">","<span" + splby + ">"
                          ,"<a" + splby + ">"
                          ,"<div" + splby + ">","<table" + splby + ">"
                          ,"<sup" + splby + ">","<P" + splby + ">"};

    for (int i = 0;i< secRemove.length ;i++ ) {
      String temp1 = secRemove[i].split(splby)[0];
      String temp2 = secRemove[i].split(splby)[1];

      String[] arrTemp = body.split(temp1);

      body = "";

      for (int j = 0; j < arrTemp.length; j++) {
        String[] arrTemp2 = arrTemp[j].split(temp2,2);
        if (arrTemp2.length > 1) {
          arrTemp[j] = arrTemp2[1];
        }

        body = body + arrTemp[j];

      }
    }

    String[] arrBody = body.split("</p>");

    //Merging all the data back into one String
    page = title + "\n\n";
    for (int i = 0;i < arrBody.length;i++ ) {
      page = page + arrBody[i] + "\n";
    }

    return page;
  }

}
