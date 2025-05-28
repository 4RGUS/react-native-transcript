package com.transcription

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule

class SpeechRecognitionManager(private val reactContext: ReactContext) {

  private var speechRecognizer: SpeechRecognizer? = null
  private var recognizerIntent: Intent? = null

  fun startListening() {
    if (speechRecognizer == null) {
      speechRecognizer = SpeechRecognizer.createSpeechRecognizer(reactContext)
      speechRecognizer?.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
          sendEvent("onSpeechError", Arguments.createMap().apply {
            Log.d("Transcript:onSpeechError", error.toString())
            putInt("error", error)
          })
        }

        override fun onResults(results: Bundle?) {
          val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
          Log.d("Transcript:onResults", matches.toString())
          val writableArray = Arguments.createArray()
          matches?.forEach { writableArray.pushString(it) }
          sendEvent("onSpeechResults", Arguments.createMap().apply {
            putArray("results", writableArray)
          })
        }

        override fun onPartialResults(partialResults: Bundle?) {
          val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
          Log.d("Transcript:onPartialResults", matches.toString())
          val writableArray = Arguments.createArray()
          matches?.forEach { writableArray.pushString(it) }
          sendEvent("onSpeechPartialResults", Arguments.createMap().apply {
            putArray("results", writableArray)
          })
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
      })
    }

    if (recognizerIntent == null) {
      recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, reactContext.packageName)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
      }
    }

    speechRecognizer?.startListening(recognizerIntent)
  }

  fun stopListening() {
    speechRecognizer?.stopListening()
  }

  fun destroyRecognizer() {
    speechRecognizer?.destroy()
    speechRecognizer = null
    recognizerIntent = null
  }

  private fun sendEvent(eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
