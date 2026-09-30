package info.gianlucacosta.omnieditor

import java.time.Duration

import info.gianlucacosta.helios.concurrency.AtomicStringBuilder

import scalafx.application.Platform

private case class OutputThread(
                                 refreshRate: Duration,
                                 outputBuffer: AtomicStringBuilder,
                                 outputAction: String => Unit
                               ) extends Thread {

  override def run(): Unit = {
    while (!Thread.interrupted) {
      {
        tryToOutput()
        try {
          Thread.sleep(refreshRate.toMillis)
        }
        catch {
          case e: InterruptedException =>
            tryToOutput()
            return
        }
      }
    }
  }

  private def tryToOutput(): Unit = {
    val textToOutput = outputBuffer.extract()
    if (!textToOutput.isEmpty) {
      Platform.runLater {
        outputAction(textToOutput)
      }
    }
  }
}
