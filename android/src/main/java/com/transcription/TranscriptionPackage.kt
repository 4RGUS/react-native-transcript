package com.transcription

import TranscriptionModule
import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import java.util.HashMap

class TranscriptionPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
    return if (name == TranscriptionModule.NAME) {
      TranscriptionModule(reactContext)
    } else {
      null
    }
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
    return ReactModuleInfoProvider {
      val moduleInfos: MutableMap<String, ReactModuleInfo> = HashMap()
      val isTurboModule: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
      moduleInfos[TranscriptionModule.NAME] = ReactModuleInfo(
        TranscriptionModule.NAME, TranscriptionModule.NAME, canOverrideExistingModule = false,  // canOverrideExistingModule
        needsEagerInit = false,
        isCxxModule = false,
        isTurboModule=isTurboModule
      )
      moduleInfos
    }
  }
}
