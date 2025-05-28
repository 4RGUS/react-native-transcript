import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.module.annotations.ReactModule
import com.transcription.NativeTranscriptionSpec
import com.transcription.SpeechRecognitionManager

@ReactModule(name = TranscriptionModule.NAME)
class TranscriptionModule(reactContext: ReactApplicationContext) :
  NativeTranscriptionSpec(reactContext) {

  private val manager = SpeechRecognitionManager(reactContext)

  override fun getName() = NAME

  fun addListener(eventName: String?) {}
  fun removeListeners(count: Int) {}

  @ReactMethod
  override fun startListening() {
    reactApplicationContext.runOnUiQueueThread {
      manager.startListening()
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
