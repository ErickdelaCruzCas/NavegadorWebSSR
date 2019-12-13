package navegadorweb;

import java.awt.Desktop;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class NavegadorWeb {

	public static void descargarImagen(String path, String web) throws IOException {
		String path1 = "C:/Users/Usuario/Desktop/PruebasNavegador";
		String nombreImagen = getNombreArchivo("/", path);
		File file = new File(path1 + path.replace(nombreImagen,""));
		file.mkdirs();
		File imagen = new File(path1 + path);
		URL obj = new URL(web + path);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		String tipo = nombreImagen.split("\\.")[1];
		BufferedImage image = ImageIO.read(obj);
		ImageIO.write(image, tipo , imagen);
		System.out.println(" [OK] " +  nombreImagen);

	}

	public static String getNombreArchivo(String a, String b) {
		String[] splitres = b.split(a);
		String substring = splitres[splitres.length - 1];
		
		return substring;
	}

	public static void main(String[] args) throws IOException {
	    Scanner sc = new Scanner(System.in);
		System.out.println("Introduce la web y el puerto: ");
		String web = sc.nextLine();
		sc.close();
		URL obj = new URL(web);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		String[] nombre = web.split("\\.");
		String path = "C:/Users/Usuario/Desktop/PruebasNavegador/" + nombre[1] + ".html";
		File file = new File(path);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file), StandardCharsets.UTF_8));
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		String urlimg;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains("<img") && inputLine.contains("src")) {
				urlimg = "";
				for (int i = 0; i < inputLine.length(); i++) {
					if (inputLine.substring(i).startsWith("src")) {
						urlimg = inputLine.substring(i);
						urlimg = urlimg.split("\"")[1];
						//System.out.println(urlimg);
						if(!urlimg.contains(".gif")) {
							descargarImagen(urlimg, web);
							inputLine = inputLine.replace(urlimg, urlimg.substring(1));
						}
						
						//System.out.println(inputLine.replace(urlimg, urlimg.substring(1)));
					}
				}
			}
			bw.write(inputLine);

		}
		bw.close();
		in.close();
		System.out.println("[DONE]");
		Desktop.getDesktop().browse(file.toURI());

	}

}
