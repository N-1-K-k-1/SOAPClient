package ru.n1kk1.kotlinSOAPClient

import org.slf4j.LoggerFactory
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback
import ru.n1kk1.kotlinSOAPClient.wsdl.*

class CustomerClient : WebServiceGatewaySupport() {
    private val uri = "http://192.168.0.34:8081/ws"
    private val timeCode: TimeCode = TimeCode()

    fun getCustomer(id: Long): GetCustomerByIdResponse {
        log.info("\nRequesting customer with id = $id")
        return timeCode.timeIt {
            val request = GetCustomerByIdRequest()
            request.id = id
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/GetCustomerByIdRequest")) as GetCustomerByIdResponse
        }
    }

    fun deleteCustomer(id: Long): DeleteCustomerResponse {
        log.info("\nDeleting customer with id = $id")
        return timeCode.timeIt {
            val request = DeleteCustomerRequest()
            request.id = id
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/DeleteCustomerRequest")) as DeleteCustomerResponse
        }
    }

    fun all4Requests(id: Long): DeleteCustomerResponse {
        log.info("\nAdding Requesting Updating Deleting customer with id = $id")
        return timeCode.timeIt {
            lateinit var request: Any
            request = AddCustomerRequest()
            request.id = id
            request.firstName = "Alex"
            request.lastName = "Mayfield"
            request.salary = 3000
            request.city = "San-Francisco"
            request.country = "USA"
            request.email = "AlexMfld123@gmail.com"
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/AddCustomerRequest")) as AddCustomerResponse

            request = GetCustomerByIdRequest()
            request.id = id
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/GetCustomerByIdRequest")) as GetCustomerByIdResponse

            request = UpdateCustomerRequest()
            request.id = id
            request.firstName = "updatedFirstName"
            request.lastName = "updatedLastName"
            request.salary = 0
            request.city = "updatedCity"
            request.country = "updatedCountry"
            request.email = "updatedEmail"
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/UpdateCustomerRequest")) as UpdateCustomerResponse

            request = DeleteCustomerRequest()
            request.id = id
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/DeleteCustomerRequest")) as DeleteCustomerResponse
        }
    }

    fun addCustomer(id: Long): AddCustomerResponse {
        log.info("\nAdding a new customer")
        return timeCode.timeIt {
            val request = AddCustomerRequest()
            request.id = id
            request.firstName = "Alex"
            request.lastName = "Mayfield"
            request.salary = 3000
            request.city = "San-Francisco"
            request.country = "USA"
            request.email = "AlexMfld123@gmail.com"
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/AddCustomerRequest")) as AddCustomerResponse
        }
    }

    fun updateCustomer(id: Long): UpdateCustomerResponse {
        log.info("\nUpdating a new customer")
        return timeCode.timeIt {
            val request = UpdateCustomerRequest()
            request.id = id
            request.firstName = "updatedFirstName"
            request.lastName = "updatedLastName"
            request.salary = 0
            request.city = "updatedCity"
            request.country = "updatedCountry"
            request.email = "updatedEmail"
            webServiceTemplate
                    .marshalSendAndReceive(uri, request,
                            SoapActionCallback(
                                    "http://spring.io/guides/gs-producing-web-service/UpdateCustomerRequest")) as UpdateCustomerResponse
        }
    }

    fun getFile(fileName: String): GetImageResponse {
        log.info("\nReceiving image")
        val request = GetImageRequest()
        request.imageName = fileName
        return webServiceTemplate.marshalSendAndReceive(uri, request,
                SoapActionCallback(
                        "http://spring.io/guides/gs-producing-web-service/UpdateCustomerRequest")) as GetImageResponse
    }

    companion object {
        val log: org.slf4j.Logger = LoggerFactory.getLogger(CustomerClient::class.java)
    }
}