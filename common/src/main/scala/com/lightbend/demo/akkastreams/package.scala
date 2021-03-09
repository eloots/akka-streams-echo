package org.applied

import java.io.File

import akka.stream.scaladsl.{Sink, Source}
import uk.co.labbookpages.WavFile

package object akkastreams {
  type MQueue[A] = scala.collection.mutable.Queue[A]
  val MQueue = scala.collection.mutable.Queue

  type Iterable[+A] = scala.collection.immutable.Iterable[A]
  val Iterable = scala.collection.immutable.Iterable

  implicit class FilterStageOps(val s: (Int, Double)) extends AnyVal {
    def toFilterStage: FilterStage = FilterStage(s._1, s._2)
  }

  object WavWriter {
    def apply(wavFile: WavFile) = {
      val buf = new Array[Double](1)
      Sink.foreach[Double] { s =>
        buf(0) = s
        println(buf.toVector)
        //wavFile.writeFrames(buf, 1)
      }
    }
  }

  object WaveOutputFile {
    def apply(wavFileName: String, wavSettings: WaveSettings): WavFile = {
      WavFile.newWavFile(
        new File(wavFileName),
        wavSettings.numChannels,
        wavSettings.numFrames,
        wavSettings.validBits,
        wavSettings.sampleRate)
    }
  }

  object WaveSourceFromFile {
    def apply(wavFileName: String): WaveSource = {
      val BUFSIZE = 256
      val wavFile = WavFile.openWavFile(new File(wavFileName))
      val numChannels = wavFile.getNumChannels
      val numFrames = wavFile.getNumFrames
      val validBits= wavFile.getValidBits
      val sampleRate = wavFile.getSampleRate
      println(s"Number of channels = $numChannels, number of frames: $numFrames, sampleRate: $sampleRate")
      val buffer = new Array[Double](256 * numChannels)

      @scala.annotation.tailrec
      def rf(wavFile: WavFile, buf: Vector[Double] = Vector.empty[Double]): Vector[Double] = {
        wavFile.readFrames(buffer, BUFSIZE) match {
          case 0 =>
            buf
          case BUFSIZE =>
            rf(wavFile, buf ++ buffer.toVector)
          case n =>
            buf ++ buffer.toVector.take(n)
        }
      }
      val source = Source(rf(wavFile))
      wavFile.close()
      println(s"Source audio from $wavFileName")
      WaveSource(source, WaveSettings(numChannels, numFrames, validBits, sampleRate))
    }
  }
}
