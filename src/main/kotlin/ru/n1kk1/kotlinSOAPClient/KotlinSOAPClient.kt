package ru.n1kk1.kotlinSOAPClient

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class KotlinSOAPClient : Application() {
	private lateinit var springContext: ConfigurableApplicationContext
	private lateinit var root: Parent

	override fun init() {
		springContext = runApplication<KotlinSOAPClient>()
		root = FXMLLoader(javaClass.classLoader.getResource("sample.fxml"))
				.apply {
					setControllerFactory { springContext.getBean(it) }
				}
				.load()
	}

	override fun start(primaryStage: Stage?) {
		primaryStage?.apply {
			title = "SOAP Client"
			scene = Scene(root)
		}?.show() ?: throw IllegalStateException("Failed to show primary stage")
	}

	override fun stop() {
		springContext.stop()
	}
}

fun main(args: Array<String>) {
	launch(KotlinSOAPClient::class.java, *args)
}