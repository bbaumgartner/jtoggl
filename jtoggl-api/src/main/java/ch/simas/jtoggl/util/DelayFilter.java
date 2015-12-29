package ch.simas.jtoggl.util;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

/**
 * A ClientFilter implementation that delays for a fixed period of time before allowing the next
 * filter to be called. This can be used to throttle every request sent to an API endpoint with rate
 * limitations, or to perhaps as a simple simulation of network issues.
 * 
 * @author cewing
 *
 */
public class DelayFilter extends ClientFilter {

	private long throttlePeriod;

	public DelayFilter(long throttlePeriod) {
		if (throttlePeriod <= 0L) {
			throw new IllegalArgumentException("Must be positive throttlePeriod");
		}
		this.throttlePeriod = throttlePeriod;
	}

	@Override
	public ClientResponse handle(ClientRequest cr) throws ClientHandlerException {
		try {
			Thread.sleep(throttlePeriod);
		} catch (InterruptedException e) {
			// ignore, except to propagate
			Thread.currentThread().interrupt();
		}
		return getNext().handle(cr);
	}

}
