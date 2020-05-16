package ru.n1kk1.kotlinSOAPClient.clientService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.n1kk1.kotlinSOAPClient.CustomerClient
import ru.n1kk1.kotlinSOAPClient.TimeCode
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Component
@Scope("prototype")
class PostCustomer : Runnable {
    @Autowired
    val quoteClient: CustomerClient = CustomerClient()

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            postCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(PostCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    fun postCustomer(correlationId: Int) {
        Logger.getAnonymousLogger().info("POST@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        response = correlationId.let { quoteClient.addCustomer(it.toLong()) }.serviceStatus.message + "\n" + response
        Logger.getAnonymousLogger().info("EXIT POST@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
        println("Response time for correlationId = $correlationId is ${TimeCode.responseTime} ms")
    }

    companion object {
        @JvmStatic
        private var correlationId: Int = 1
        var response: String = ""

        @Synchronized private fun getCorrelationId(): Int {
            return correlationId++
        }

        fun unsetCorrelationId() {
            correlationId = 1
        }

        fun getCorrId(): Int {
            return correlationId
        }

        fun unsetResponse() {
            response = ""
        }
    }

}