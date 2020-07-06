package net.kuroppi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import net.kuroppi.impl.Http10processor;

public class HttpdServer {
    private final int listenPort;

    private final Semaphore throttle = new Semaphore(10);

    public HttpdServer(int listenPort) {
		this.listenPort = listenPort;
    }
    
    public void start() throws IOException{
        try (ServerSocket sockListen = new ServerSocket()) {
			sockListen.setReuseAddress(true);
			sockListen.bind(new InetSocketAddress("0.0.0.0", listenPort));

			while (true) {
				throttle.acquireUninterruptibly();

				final Socket sockAccept = sockListen.accept();

				new Thread() {

					public void run() {
						try {
							try (InputStream in = sockAccept.getInputStream();
									OutputStream out = sockAccept.getOutputStream();) {
                                    new Http10processor().runHttp(in, out);
							}
						} catch (Throwable e) {
							e.printStackTrace(System.err);
							throw new RuntimeException(e);
						} finally {
							throttle.release();
						}
					}
				}.start();
			}
		}
    }

}