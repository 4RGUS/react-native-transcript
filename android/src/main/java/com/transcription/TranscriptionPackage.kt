package com.transcription

import TranscriptionModule
import TranscriptionModuleLegacy
import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import java.util.HashMap

class TranscriptionPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
    return if (name == TranscriptionModule.NAME) {
      if (useTurboModules()) {
        TranscriptionModule(reactContext)
      } else {
        TranscriptionModuleLegacy(reactContext)
      }
    } else {
      null
    }
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
    return ReactModuleInfoProvider {
      val moduleInfos: MutableMap<String, ReactModuleInfo> = HashMap()
      moduleInfos[TranscriptionModule.NAME] = ReactModuleInfo(
        TranscriptionModule.NAME,
        TranscriptionModule.NAME,
        false,  // canOverrideExistingModule
        false,  // needsEagerInit
        false,  // isCxxModule
        true // isTurboModule
      )
      moduleInfos
    }
  }

  private fun useTurboModules(): Boolean {
    return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
  }
}
