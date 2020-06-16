package ru.n1kk1.kotlinSOAPClient

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.CountDownLatch


@Configuration
@EnableAsync
class Config {
    @Bean
    fun marshaller(): Jaxb2Marshaller {
        val marshaller = Jaxb2Marshaller()
        // this package must match the package in the <generatePackage> specified in
// pom.xml
        marshaller.contextPath = "ru.n1kk1.kotlinSOAPClient.wsdl"
        return marshaller
    }

    @Bean
    fun customerClient(marshaller: Jaxb2Marshaller): CustomerClient {
        val client = CustomerClient()
        client.defaultUri = "http://192.168.0.83:8081/ws"
        client.marshaller = marshaller
        client.unmarshaller = marshaller
        return client
    }

    @Bean
    fun taskExecutor(): ThreadPoolTaskExecutor{
        return ThreadPoolTaskExecutor()
    }

}