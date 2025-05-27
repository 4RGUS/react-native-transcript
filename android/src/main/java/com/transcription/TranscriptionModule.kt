package com.transcription

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.modules.core.DeviceEventManagerModule

@ReactModule(name = TranscriptionModule.NAME)
class TranscriptionModule(reactContext: ReactApplicationContext) :
  NativeTranscriptionSpec(reactContext) {

  private var speechRecognizer: SpeechRecognizer? = null
  private var recognizerIntent: Intent? = null

  override fun getName(): String {
    return NAME
  }

  fun addListener(eventName: String?) {
    // Required for RN NativeEventEmitter compatibility
  }

  fun removeListeners(count: Int) {
    // Required for RN NativeEventEmitter compatibility
  }

  @ReactMethod
  override fun startListening() {
    reactApplicationContext.runOnUiQueueThread {
      if (speechRecognizer == null) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(reactApplicationContext)
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
          putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, reactApplicationContext.packageName)
          putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
      }

      speechRecognizer?.startListening(recognizerIntent)
    }
  }

  @ReactMethod
  override fun stopListening() {
    reactApplicationContext.runOnUiQueueThread {
      speechRecognizer?.stopListening()
    }
  }

  @ReactMethod
  override fun destroyRecognizer() {
    speechRecognizer?.destroy()
    speechRecognizer = null
    recognizerIntent = null
  }

  private fun sendEvent(eventName: String, params: WritableMap?) {
    Log.d("Transcript:sendEvent", "Sending event: $eventName with params: $params")
    reactApplicationContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

  companion object {
    const val NAME = "Transcription"
  }
}
