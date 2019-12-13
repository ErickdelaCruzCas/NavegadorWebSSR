package navegadorweb;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class NavegadorWeb {
	
	//codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url

	public static void descargarImagen(String path, String web) throws IOException {
		String path1 = "C:/Users/erick/Desktop/PruebasNavegador";
		String nombreImagen = getNombreArchivo("/", path);
		File file = new File(path1 + path.replace(nombreImagen, ""));
		file.mkdirs();
		File imagen = new File(path1 + path);
		URL obj = new URL(web + path);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		System.out.println("Response Code: " + con.getResponseCode());
		System.out.println("Content-Type = " + con.getContentType());
        System.out.println("Content-Length = " + con.getContentLength());
		String tipo = nombreImagen.split("\\.")[1];
		BufferedImage image = ImageIO.read(obj);
		ImageIO.write(image, tipo, imagen);
		System.out.println(" [OK] " + nombreImagen);

	}

	public static String getNombreArchivo(String a, String b) {
		String[] splitres = b.split(a);
		String substring = splitres[splitres.length - 1];
		return substring;
	}

	public static void main(String[] args) throws IOException {
		int exit = 0;
		Scanner sc;
		while (true) {
			sc = new Scanner(System.in);
			System.out.println("Introduce la web y el puerto: ");
			String web = sc.nextLine();						
			if(web.equals("exit")) {
				break;
			}
			
			URL obj = new URL(web);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode + ".");
			if (responseCode == HttpURLConnection.HTTP_OK) {
				
				
				System.out.println("Content-Type = " + con.getContentType());
	            System.out.println("Content-Length = " + con.getContentLength());
	            Map<String, List<String>> headers = con.getHeaderFields();
				
				String[] nombre = web.split("\\.");
//			String path = "C:/Users/Usuario/Desktop/PruebasNavegador/" + nombre[1] + ".html";  Alex
				String path = "C:/Users/erick/Desktop/PruebasNavegador/" + nombre[1] + ".html";

				File file = new File(path);
				BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
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
								// System.out.println(urlimg);
								if (!urlimg.contains(".gif")) {
									descargarImagen(urlimg, web);
									inputLine = inputLine.replace(urlimg, urlimg.substring(1));
								}

								// System.out.println(inputLine.replace(urlimg, urlimg.substring(1)));
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
		
		if(sc != null) {
			sc.close();
		}
		
	}
}

