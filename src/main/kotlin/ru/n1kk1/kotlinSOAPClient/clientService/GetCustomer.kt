package ru.n1kk1.kotlinSOAPClient.clientService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.n1kk1.kotlinSOAPClient.CustomerClient
import ru.n1kk1.kotlinSOAPClient.TimeCode
import ru.n1kk1.kotlinSOAPClient.wsdl.GetCustomerByIdResponse
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Component
@Scope("prototype")
class GetCustomer : Runnable {
    @Autowired
    val quoteClient: CustomerClient = CustomerClient()

    override fun run() {
        try {
            val correlationId: Int = getCorrelationId()
            getCustomer(correlationId)
        } catch (ex: Exception) {
            Logger.getLogger(PostCustomer::class.java.name).log(Level.SEVERE, null, ex)
        }
    }

    fun getCustomer(correlationId: Int) {
        Logger.getAnonymousLogger().info("GET@" + Date().time + " for correlationId = " + correlationId + " from " + Thread.currentThread().name)
        val r: GetCustomerByIdResponse? = correlationId.let { quoteClient.getCustomer(it.toLong()) }
        Logger.getAnonymousLogger().info("EXIT GET@" + Date().time + " for correlationId = " + correlationId +  " from " + Thread.currentThread().name)
        println("Response time for correlationId = $correlationId is ${TimeCode.responseTime} ms")
        response = "<id>${r?.customer?.id}</id>\n" +
                "<firstName>${r?.customer?.firstName}</firstName>\n" +
                "<lastName>${r?.customer?.lastName}</lastName>\n" +
                "<salary>${r?.customer?.salary}</salary>\n" +
                "<city>${r?.customer?.city}</city>\n" +
                "<country>${r?.customer?.country}</country>\n" +
                "<email>${r?.customer?.email}</email>\n\n" + response
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