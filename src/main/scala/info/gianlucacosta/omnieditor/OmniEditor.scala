package info.gianlucacosta.omnieditor

import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

import scalafx.application.Platform

case class OmniEditor(appStrategy: AppStrategy) {
  def start(primaryStage: Stage) {
    val loader = new FXMLLoader(getClass.getResource("MainWindow.fxml"))

    val root = loader.load[BorderPane]()
    val mainWindowController = loader.getController[MainWindowController]()

    val scene = new Scene(root)

    val syntaxCssUrl = appStrategy.syntaxCss
    scene.getStylesheets.add(syntaxCssUrl.toExternalForm)

    mainWindowController.init(primaryStage, appStrategy)

    Platform.runLater {
      primaryStage.setScene(scene)
      primaryStage.show()
    }
  }
}