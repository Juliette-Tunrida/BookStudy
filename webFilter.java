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

    //Removing sections

    String splby = "-------";

    String[] secRemove = {"<img " + splby + "/>","<hr " + splby + "/>"
                          ,"<p " + splby + ">","<span " + splby + ">"
                          ,"<a " + splby + ">"
                          ,"<div " + splby + ">","<table " + splby + ">"
                          ,"<sup " + splby + ">","<P " + splby + ">"
                          ,"<hr " + splby + ">"};

    for (int i = 0;i<secRemove.length ;i++ ) {
      //splits the beginning and the end into two strings
      String temp1 = secRemove[i].split(splby)[0];
      String temp2 = secRemove[i].split(splby)[1];

      String[] arrTemp = body.split(temp1);

      body = "";

      for (int j = 0;j <  arrTemp.length;j++ ) {
        arrTemp[j] = temp1 + arrTemp[j];

        int in1 = arrTemp[j].indexOf(temp1);
        int in2 = arrTemp[j].indexOf(temp2);

        System.out.println(in1 + " - " + in2);

        if(in1 != -1 && in2 != -1){
          in2 += temp2.length();

          String sub = arrTemp[j].substring(in1,in2);
          System.out.println(sub);
          arrTemp[j] = arrTemp[j].replace(sub,"");
        }
        body = body + arrTemp[j];

      }


    }

    body = body.replace("&#8220;","\"");
    body = body.replace("&#8221;","\"");
    body = body.replace("&ldquo;","\"");
    body = body.replace("&rdquo;","\"");
    body = body.replace("&#8217;","'");
    body = body.replace("&#8216;","'");
    body = body.replace("&#8212;","-");
    body = body.replace("&#8211;","-");
    body = body.replace("&auml;","ae");
    body = body.replace("&uuml;","ue");
    body = body.replace("&ouml;","oe");
    body = body.replace("&amp;","&");

    String[] toRemove = {"<p>","<br />","<br>","</br>","<br/>"
                        ,"<h1>","<h2>","<h3>","<h4>","<h5>","<h6>"
                        ,"</h1>","</h2>","</h3>","</h4>","</h5>","</h6>"
                        ,"</span>","</sup>","</a>","<em>","</em>"
                        ,"<hr>","<tbody>","</blockquote>","&#160;","</div>"};

    for (int i = 0;i < toRemove.length ;i++ ) {
      body = body.replace(toRemove[i],"");
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
