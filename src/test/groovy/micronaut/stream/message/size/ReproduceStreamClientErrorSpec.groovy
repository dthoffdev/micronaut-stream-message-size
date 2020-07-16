package micronaut.stream.message.size

import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.StreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.sse.RxSseClient
import io.micronaut.http.sse.Event
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ReproduceStreamClientErrorSpec extends Specification {

  @Inject
  @Client("/")
  RxSseClient httpClient

  def "message size of 158 breaks on 4th message"() {
    when:
    List<Event> events = httpClient.eventStream("/stream", String.class).take(5).blockingIterable().toList()

    then:
    events[0].data == "AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|AAAAAAAAAAAAAAAAAAA|"
  }

}
