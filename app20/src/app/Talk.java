package app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Talk {

	public static void main(String[] args) throws Exception {
		HttpURLConnection conn = null;
    	while (true) {
	    	try {
	    		System.out.println("[me]");
	    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    		String input = br.readLine();

	    		String data = "apikey=DZZRNwrJIxQ4I6y7HgWfK1uomHcW0F8O&query=" + input;

	    		URL url = new URL("https://api.a3rt.recruit-tech.co.jp/talk/v1/smalltalk");
	    		conn = (HttpURLConnection) url.openConnection();
	    		conn.setDoInput(true);
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            conn.setUseCaches(false);
	            conn.connect();

	            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
	                dos.writeBytes(data);
	            }

	            int rescode = conn.getResponseCode();
	            if (rescode == HttpURLConnection.HTTP_OK) {
	                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
	                    StringBuilder buf = new StringBuilder();
	                    String line;

	                    while ((line = reader.readLine()) != null) {
	                        buf.append(line);
	                    }
	                    System.out.println(convertToOiginal(buf.toString()));
	                }
	            }
	    	} catch(Exception e) {
	    		throw e;
	    	}
    	}

	}

	private static String convertToOiginal(String unicode) {
        String tmp = unicode;
        while (tmp.indexOf("\\u") > 0) {
            String str = tmp.substring(tmp.indexOf("\\u"), tmp.indexOf("\\u") + 6);
            int c = Integer.parseInt(str.substring(2), 16);
            tmp = tmp.replaceFirst("\\" + str, new String(new int[]{c}, 0, 1));
        }
        return tmp;
    }

}
