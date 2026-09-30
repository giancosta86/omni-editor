package info.gianlucacosta.omnieditor

import java.io.File
import java.nio.file.Files
import javafx.stage.Stage

import info.gianlucacosta.helios.fx.workspace.Workspace

import scalafx.stage.FileChooser

private class OmniWorkspace(stage: Stage, documentFileChooser: FileChooser, codeEditor: StyledCodeEditor) extends Workspace(stage, documentFileChooser) {
  override def doNew(): Boolean = {
    codeEditor.clear()

    codeEditor.getUndoManager.forgetHistory()
    codeEditor.requestFocus()

    true
  }


  override def doOpen(sourceFile: File): Boolean = {
    val fileContent = new String(
      Files.readAllBytes(sourceFile.toPath),
      "utf-8"
    )

    codeEditor.setText(fileContent)

    codeEditor.getUndoManager.forgetHistory()
    codeEditor.requestFocus()

    true
  }


  override def doSave(targetFile: File): Boolean = {
    Files.write(
      targetFile.toPath,
      codeEditor.getText.getBytes("utf-8")
    )

    true
  }
}
