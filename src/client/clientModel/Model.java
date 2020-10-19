package client.clientModel;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

//this class will be everything server related
public class Model {
	private Socket client_socket;
	private InputStream in;
	private OutputStream out;
	private BufferedReader br;
	private BufferedOutputStream bout;
	private DataInputStream din;
	private DataOutputStream dout;
	private Image gray = null;
	private boolean connected = false;
	private boolean check = true;
	
	//method for connecting client to server
	private void connect() {
		try {
			client_socket = new Socket("localhost",5000);
			out = client_socket.getOutputStream();
			in = client_socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			din = new DataInputStream(in);
			dout = new DataOutputStream(out);
			bout = new BufferedOutputStream(out);
			connected = true;
		}catch(UnknownHostException e)
		{
			connected = false;
		}catch(IOException e)
		{
			connected = false;
		}
	}
	
	//method for GrayScale
	public void grayScale(File img_file, String qryString) {
		String encoded_file = null;
		
		if (check == false) {
			img_file = new File("output.jpg");
		}else
			check = false;
		
		try {
			FileInputStream fis = new FileInputStream(img_file);
			byte[] bytes = new byte[(int)img_file.length()];
			fis.read(bytes);
			
			encoded_file = new String(Base64.getEncoder().encodeToString(bytes));
			byte[] bytesToSend=encoded_file.getBytes();
			
			dout.write(("POST " + qryString + " HTTP/1.1\r\n").getBytes());
			dout.write(("Content-Type: "+"application/text\r\n").getBytes());
			dout.write(("Content-Length: "+encoded_file.length()+"\r\n").getBytes());
			dout.write(("\r\n").getBytes());
			dout.write(bytesToSend);
			dout.flush();
			dout.write(("\r\n").getBytes());
			
			String response = "";
			String line = "";
			
			while(!(line = br.readLine()).equals(""))
			{
				response += line+"\n";
			}
			
			String imgData = "";
			while((line = br.readLine())!=null)
			{
				imgData += line;
			}
			System.out.println(imgData);
			
			String base64Str = imgData.substring(imgData.indexOf('\'')+1,imgData.lastIndexOf('}')-1);
			System.out.println(base64Str);
			byte[] decodedStr = Base64.getDecoder().decode(base64Str);
			save(decodedStr);
			
			gray = new Image(new ByteArrayInputStream(decodedStr), 300, 260, false,true);
			System.out.println(decodedStr);
			fis.close();
			bout.close();
			din.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//save image
	private void save(byte[] data) {
		BufferedImage bImage;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			BufferedImage bImage2 = ImageIO.read(bis);
			ImageIO.write(bImage2, "jpg", new File("output.jpg") );
			System.out.println("image created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//getters
	public boolean getConnected() {
		connect();
		return (connected);
	}
	
	public Image getImage() {
		return (gray);
	}
	
	//method to reset
	public void reset() {
		check = true;
	}
	
}
