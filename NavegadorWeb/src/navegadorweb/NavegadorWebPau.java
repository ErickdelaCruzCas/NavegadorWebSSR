package navegadorweb;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class NavegadorWebPau {

	public static String descargarImagen(String path) throws IOException {
		String path1 = "C:/Users/erick/Documents/NavegadorWebs/";
		String pathDir = getSubstring("/", path);
		File file = new File(path1 + pathDir);
		file.mkdirs();
		File imagen = new File(path1 + path);
		URL obj = new URL("http://localhost:8080/" + path);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedImage image = ImageIO.read(obj);
		ImageIO.write(image, "png", imagen);
		return imagen.getPath();

	}

	public static String getSubstring(String a, String b) {
		String substring = b.split(a)[0];
		return substring;
	}

	public static void main(String[] args) throws IOException {
		URL obj = new URL("http://localhost:8080");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		String path = "C:/Users/erick/Documents/NavegadorWebs/argumentos.html";
		File file = new File(path);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		String urlimg;
		String nuevoPath;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("<img") && inputLine.contains("src")) {
				urlimg = "";
				for (int i = 0; i < inputLine.length(); i++) {
					if (inputLine.substring(i).startsWith("src")) {
						urlimg = inputLine.substring(i);
						urlimg = urlimg.split("\"")[1];
						//System.out.println(urlimg);
						nuevoPath = descargarImagen(urlimg);
						inputLine.replaceAll(urlimg, nuevoPath);
					}
				}
			}
			bw.write(inputLine);

		}
		bw.close();
		in.close();

	}

}
