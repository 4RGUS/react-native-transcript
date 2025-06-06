package com.transcription

import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Arguments
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = TranscriptionModule.NAME)
class TranscriptionModule(reactContext: ReactApplicationContext) :
  NativeTranscriptionSpec(reactContext), NativeModule {

  private val manager = SpeechRecognitionManager(reactContext)

  override fun getName() = NAME

  fun addListener(eventName: String?) {}
  fun removeListeners(count: Int) {}

  @ReactMethod
  override fun startListening(language: String?) {
    reactApplicationContext.runOnUiQueueThread {
      manager.startListening(language ?: "en-US")
    }
  }

  @ReactMethod
  override fun stopListening() {
    reactApplicationContext.runOnUiQueueThread {
      manager.stopListening()
    }
  }

  @ReactMethod
  override fun destroyRecognizer() {
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
