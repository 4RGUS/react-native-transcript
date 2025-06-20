package com.transcription

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.transcription.SpeechRecognitionManager

class TranscriptionModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val manager = SpeechRecognitionManager(reactContext)

  override fun getName() = NAME

  @ReactMethod
  fun startListening(language: String?) {
    reactContext.runOnUiQueueThread {
      manager.startListening(language?: "en-US")
    }
  }

  @ReactMethod
  fun stopListening() {
    reactContext.runOnUiQueueThread {
      manager.stopListening()
    }
  }

  @ReactMethod
  fun destroyRecognizer() {
    manager.destroyRecognizer()
  }

  companion object {
    const val NAME = "Transcription"
  }
}
