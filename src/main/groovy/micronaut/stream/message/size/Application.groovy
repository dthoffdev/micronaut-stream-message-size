package micronaut.stream.message.size

import io.micronaut.context.annotation.Bean
import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic

@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application)
    }

    @Bean
    StreamController streamController() {
        return new StreamController()
    }
}