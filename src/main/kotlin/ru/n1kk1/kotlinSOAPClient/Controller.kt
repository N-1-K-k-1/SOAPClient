package ru.n1kk1.kotlinSOAPClient

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.stage.Window
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import ru.n1kk1.kotlinSOAPClient.clientService.*
import java.io.File
import java.util.concurrent.TimeUnit


@Component
class Controller {
    @FXML
    lateinit var postButton1: Button
    @FXML
    lateinit var getAllButton: Button
    @FXML
    lateinit var updateButton: Button
    @FXML
    lateinit var deleteButton: Button
    @FXML
    lateinit var all4Button: Button
    @FXML
    lateinit var unsetCorrIdButton: Button
    @FXML
    lateinit var throughputTestButton: Button
    @FXML
    lateinit var responseField: TextArea
    @FXML
    lateinit var enterIdField: TextField
    @FXML
    lateinit var enterIdLabel: Label
    @FXML
    lateinit var responseTimeText: Text
    @FXML
    lateinit var responseTime: Text
    @FXML
    lateinit var responseTimePerRequest: TextArea
    @FXML
    lateinit var minResponseText: Text
    @FXML
    lateinit var minResponse: Text
    @FXML
    lateinit var maxResponseText: Text
    @FXML
    lateinit var maxResponse: Text
    @FXML
    lateinit var correlationId: Text
    @FXML
    lateinit var fileSize: Text
    @FXML
    lateinit var throughputTest: Text
    @FXML
    lateinit var imageView: ImageView
    @FXML
    lateinit var responseThroughput: Text
    @FXML
    lateinit var image: Image
    @FXML
    lateinit var receivedImage: Text
    @FXML
    lateinit var chooseButton: Button
    @FXML
    lateinit var fileChooser: FileChooser
    @FXML
    lateinit var resetOpenedFile: Button
    @FXML
    lateinit var openedFile: Text

    private var action: Int = 0
    private var clientQuantity: Int = 0
    private var file: File? = null

    @Autowired
    private val ctx: ApplicationContext? = null

    @Autowired
    private lateinit var taskExecutor: ThreadPoolTaskExecutor

    @Autowired
    private val throughput: Throughput? = null

    @FXML
    fun postClick1(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        action = 1
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        PostCustomer.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun getClick(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        action = 3
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        GetCustomer.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun updateClick(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        action = 4
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        UpdateCustomer.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun deleteClick(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        action = 5
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        DeleteCustomer.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun all4Click(event: ActionEvent) {
        enterIdLabel.text = "Enter the number of clients:"
        enterIdField.text = ""
        fileSize.isVisible = false
        throughputTest.isVisible = false
        responseThroughput.isVisible = false
        receivedImage.isVisible = false
        imageView.image = null
        action = 2
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        All4Requests.unsetResponse()
        TimeCode.unsetCompanion()
    }

    @FXML
    fun unsetCorrIdClick(event: ActionEvent) {
        PostCustomer.unsetCorrelationId()
        GetCustomer.unsetCorrelationId()
        UpdateCustomer.unsetCorrelationId()
        DeleteCustomer.unsetCorrelationId()
        All4Requests.unsetCorrelationId()
        correlationId.text = "CorrelationId is: ${All4Requests.getCorrId()}"
    }

    @FXML
    fun chooseClick(event: ActionEvent) {
        val ownerWindow: Window = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null)
        fileChooser = FileChooser()
        file = fileChooser.showOpenDialog(ownerWindow)
        openedFile.isVisible = true
        openedFile.text = "Opened file: ${file!!.name}"
    }

    @FXML
    fun resetOpenedFileClick(event: ActionEvent) {
        openedFile.isVisible = false
        openedFile.text = ""
        file = null
    }

    @FXML
    fun throughputTestClick(event: ActionEvent) {
        responseTime.isVisible = false
        responseTimeText.isVisible = false
        minResponse.isVisible = false
        minResponseText.isVisible = false
        maxResponse.isVisible = false
        maxResponseText.isVisible = false
        action = 6
        enterIdField.opacity = 1.0
        enterIdLabel.opacity = 1.0
        enterIdLabel.text = "Enter file name:"
    }

    @FXML
    fun pressedEnter(event: ActionEvent) {
        when (action) {
            1 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                taskExecutor = ThreadPoolTaskExecutor()
                taskExecutor.corePoolSize = enterIdField.text.toInt()
                taskExecutor.maxPoolSize = enterIdField.text.toInt()
                taskExecutor.setThreadNamePrefix("Thread-")
                taskExecutor.afterPropertiesSet()
                println("Active threads before cycle: "+taskExecutor.activeCount)
                for (i in 0 until clientQuantity) {
                    ctx?.getBean(PostCustomer::class.java)?.let { taskExecutor.execute(it) }
                }
                while (taskExecutor.threadPoolExecutor.taskCount != taskExecutor.threadPoolExecutor.completedTaskCount) {
                    try {
                        println("Active = " + taskExecutor.threadPoolExecutor.activeCount + "" +
                                "\n Completed = " + taskExecutor.threadPoolExecutor.completedTaskCount)
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                taskExecutor.threadPoolExecutor.shutdown()
                taskExecutor.threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
                println("Active threads after cycle: " + taskExecutor.activeCount)

                for(i in 0 until TimeCode.responseTimeArr.size) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = PostCustomer.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/TimeCode.responseTimeArr.size)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString() + " ms"
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString() + " ms"
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${PostCustomer.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "POST", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("POST", clientQuantity)
                }
            }
            2 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                taskExecutor = ThreadPoolTaskExecutor()
                taskExecutor.corePoolSize = enterIdField.text.toInt()
                taskExecutor.maxPoolSize = enterIdField.text.toInt()
                taskExecutor.setThreadNamePrefix("Thread-")
                taskExecutor.afterPropertiesSet()
                println("Active threads before cycle: "+taskExecutor.activeCount)
                for (i in 0 until clientQuantity) {
                    ctx?.getBean(All4Requests::class.java)?.let { taskExecutor.execute(it) }
                }

                while (taskExecutor.threadPoolExecutor.taskCount != taskExecutor.threadPoolExecutor.completedTaskCount) {
                    try {
                        println("Active = " + taskExecutor.threadPoolExecutor.activeCount + "" +
                                "\n Completed = " + taskExecutor.threadPoolExecutor.completedTaskCount)
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                taskExecutor.threadPoolExecutor.shutdown()
                taskExecutor.threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
                println("Active threads after cycle: " + taskExecutor.activeCount)

                for(i in 0 until TimeCode.responseTimeArr.size) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = All4Requests.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/TimeCode.responseTimeArr.size)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString() + " ms"
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString() + " ms"
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${All4Requests.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "ALL FOUR REQUESTS", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("ALL FOUR REQUESTS", clientQuantity)
                }
            }
            3 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                taskExecutor = ThreadPoolTaskExecutor()
                taskExecutor.corePoolSize = enterIdField.text.toInt()
                taskExecutor.maxPoolSize = enterIdField.text.toInt()
                taskExecutor.setThreadNamePrefix("Thread-")
                taskExecutor.afterPropertiesSet()
                println("Active threads before cycle: "+taskExecutor.activeCount)
                for (i in 0 until clientQuantity) {
                    ctx?.getBean(GetCustomer::class.java)?.let { taskExecutor.execute(it) }
                }

                while (taskExecutor.threadPoolExecutor.taskCount != taskExecutor.threadPoolExecutor.completedTaskCount) {
                    try {
                        println("Active = " + taskExecutor.threadPoolExecutor.activeCount + "" +
                                "\n Completed = " + taskExecutor.threadPoolExecutor.completedTaskCount)
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                taskExecutor.threadPoolExecutor.shutdown()
                taskExecutor.threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
                println("Active threads after cycle: " + taskExecutor.activeCount)

                for(i in 0 until TimeCode.responseTimeArr.size) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = GetCustomer.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/TimeCode.responseTimeArr.size)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString() + " ms"
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString() + " ms"
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${GetCustomer.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "GET", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("GET", clientQuantity)
                }
            }
            4 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                taskExecutor = ThreadPoolTaskExecutor()
                taskExecutor.corePoolSize = enterIdField.text.toInt()
                taskExecutor.maxPoolSize = enterIdField.text.toInt()
                taskExecutor.setThreadNamePrefix("Thread-")
                taskExecutor.afterPropertiesSet()
                println("Active threads before cycle: "+taskExecutor.activeCount)
                for (i in 0 until clientQuantity) {
                    ctx?.getBean(UpdateCustomer::class.java)?.let { taskExecutor.execute(it) }
                }

                while (taskExecutor.threadPoolExecutor.taskCount != taskExecutor.threadPoolExecutor.completedTaskCount) {
                    try {
                        println("Active = " + taskExecutor.threadPoolExecutor.activeCount + "" +
                                "\n Completed = " + taskExecutor.threadPoolExecutor.completedTaskCount)
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                taskExecutor.threadPoolExecutor.shutdown()
                taskExecutor.threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
                println("Active threads after cycle: " + taskExecutor.activeCount)

                for(i in 0 until TimeCode.responseTimeArr.size) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = UpdateCustomer.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/TimeCode.responseTimeArr.size)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString() + " ms"
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString() + " ms"
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${UpdateCustomer.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "PUT", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("PUT", clientQuantity)
                }
            }
            5 -> {
                responseTime.isVisible = true
                responseTimeText.isVisible = true
                minResponse.isVisible = true
                minResponseText.isVisible = true
                maxResponse.isVisible = true
                maxResponseText.isVisible = true
                responseTimePerRequest.text = ""
                responseField.text = ""
                clientQuantity = enterIdField.text.toInt()

                taskExecutor = ThreadPoolTaskExecutor()
                taskExecutor.corePoolSize = enterIdField.text.toInt()
                taskExecutor.maxPoolSize = enterIdField.text.toInt()
                taskExecutor.setThreadNamePrefix("Thread-")
                taskExecutor.afterPropertiesSet()
                println("Active threads before cycle: "+taskExecutor.activeCount)
                for (i in 0 until clientQuantity) {
                    ctx?.getBean(DeleteCustomer::class.java)?.let { taskExecutor.execute(it) }
                }

                while (taskExecutor.threadPoolExecutor.taskCount != taskExecutor.threadPoolExecutor.completedTaskCount) {
                    try {
                        println("Active = " + taskExecutor.threadPoolExecutor.activeCount + "" +
                                "\n Completed = " + taskExecutor.threadPoolExecutor.completedTaskCount)
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                taskExecutor.threadPoolExecutor.shutdown()
                taskExecutor.threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)
                println("Active threads after cycle: " + taskExecutor.activeCount)

                for(i in 0 until TimeCode.responseTimeArr.size) {
                    responseTimePerRequest.text += TimeCode.responseTimeArr[i]
                }
                responseField.text = DeleteCustomer.response
                responseTimeText.isVisible = true
                responseTime.text = "${(TimeCode.responseTimeArrLong.sum()/TimeCode.responseTimeArr.size)}" + " ms"
                responseTime.fill = Color.ORANGE
                minResponseText.isVisible = true
                minResponse.text = TimeCode.responseMin.toString() + " ms"
                minResponse.fill = Color.GREEN
                maxResponseText.isVisible = true
                maxResponse.text = TimeCode.responseMax.toString() + " ms"
                maxResponse.fill = Color.RED
                correlationId.text = "CorrelationId is: ${DeleteCustomer.getCorrId()}"

                val csvWriter = WriteCSV()
                if (file != null) {
                    csvWriter.writeExistingCsvResponse(file!!.name, "DELETE", clientQuantity)
                }
                else if (file == null) {
                    csvWriter.writeNewCsvResponse("DELETE", clientQuantity)
                }
            }
            6 -> {
                fileSize.isVisible = true
                throughputTest.isVisible = true
                responseThroughput.isVisible = true
                receivedImage.isVisible = true

                val byteImage = throughput?.testThroughput(enterIdField.text)
                image = Image(byteImage?.inputStream())
                imageView.image = image
                fileSize.text = "File size is: " + (byteImage?.size?.div(1000)).toString() + " Kbyte"
                responseThroughput.text = "Response time is: " + throughput?.timeTest.toString() + " ms"
                throughputTest.text = "Throughput is: " + ((byteImage?.size?.div(1000))?.toFloat()?.div((throughput?.timeTest?.toFloat()?.div(1000)!!))) + " KB/s"

                val csvWriter = WriteCSV()
                if (file != null) {
                    byteImage?.size?.let { throughput?.timeTest?.let { it1 -> csvWriter.writeExistingCsvThroughput(file!!.name, it, it1) } }
                }
                else if (file == null) {
                    byteImage?.size?.let { throughput?.timeTest?.let { it1 -> csvWriter.writeNewCsvThroughput(it, it1) } }
                }
            }
            else -> {
                responseField.text = "Something went wrong"
                action = 0

                return
            }
        }

        enterIdField.opacity = 0.0
        enterIdLabel.opacity = 0.0
    }
}