package ru.n1kk1.kotlinSOAPClient.clientService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.n1kk1.kotlinSOAPClient.CustomerClient
import ru.n1kk1.kotlinSOAPClient.wsdl.GetImageResponse

@Component
class Throughput {
    @Autowired
    val quoteClient: CustomerClient = CustomerClient()

    var timeTest: Long = 0

    fun testThroughput(fileName: String): ByteArray? {
        val start = System.currentTimeMillis()
        val r: GetImageResponse = fileName.let { quoteClient.getFile(it) }
        val end = System.currentTimeMillis()
        timeTest = (end - start)
        return if (r.serviceStatus.statusCode == "SUCCESS") {
            r.image
        }
        else
            null
    }

}