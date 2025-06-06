package com.transcription

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class TranscriptionModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val manager = SpeechRecognitionManager(reactContext)

  override fun getName() = NAME

  @ReactMethod
  fun startListening(language: String?) {
    reactContext.runOnUiQueueThread {
      manager.startListening(language ?: "en-US")
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

  @ReactMethod
  fun getAvailableServices(promise: Promise) {
    try {
      val services = manager.getAvailableRecognitionServices()
      val array = Arguments.createArray()
      services.forEach { array.pushString(it) }
      promise.resolve(array)
    } catch (e: Exception) {
      promise.reject("E_FETCH_SERVICES", "Failed to fetch services", e)
    }
  }

  companion object {
    const val NAME = "Transcription"
  }
}
