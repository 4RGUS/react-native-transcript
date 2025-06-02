package com.transcription

import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
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
      manager.startListening(language?: "en-US")
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

  companion object {
    const val NAME = "Transcription"
  }
}
