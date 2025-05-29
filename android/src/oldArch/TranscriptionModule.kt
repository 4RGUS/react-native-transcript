import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.transcription.SpeechRecognitionManager

class TranscriptionModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val manager = SpeechRecognitionManager(reactContext)

  override fun getName() = NAME

  @ReactMethod
  fun startListening() {
    reactContext.runOnUiQueueThread {
      manager.startListening()
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
