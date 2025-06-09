# @4rgus/react-native-transcription

A cross-platform **speech-to-text** module for React Native apps, powered by native speech recognition APIs on both **Android** and **iOS**.



---

## ✨ Features

- 🎙️ Real-time speech-to-text
- 📱 Supports Android & iOS
- 🔄 Handles partial and final results
- 📡 Emits lifecycle events: start, result, error, end
- 🧩 Easy to integrate with any React Native app

---

yarn add @4rgus/react-native-transcription
## 📦 Installation

```bash
with npm:
npm install @4rgus/react-native-transcription

or with Yarn:
yarn add @4rgus/react-native-transcription
```

## ⚙️ Usage
```
import {
  TranscriptionEventEmitter,
  startListening,
  stopListening,
  destroyRecognizer,
  language,
} from '@4rgus/react-native-transcription';

const resultListener = TranscriptionEventEmitter.addListener(
    'onSpeechResults',
    event => {
    console.log('Result from native:', event);
    },
);
const partialResultListener = TranscriptionEventEmitter.addListener(
    'onSpeechPartialResults',
    event => {
    console.log('Partial result', event);
    },
);
const errorListener = TranscriptionEventEmitter.addListener(
    'onSpeechError',
    event => {
    console.log('onSpeechError', event);
    },
);

startListening(language.englishIN)
stopListening()
```

## 🧪 API Reference
```
1. startListening(language?: string | undefined): void
2. stopListening()
3. TranscriptionEventEmitter.addListener(
    'onSpeechError',
    event => {
    console.log('onSpeechError', event);
    },
)
4. TranscriptionEventEmitter.addListener(
    'onSpeechPartialResults',
    event => {
    console.log('Partial result', event);
    },
)
```

## 🚦 Permissions

<b>Android</b>
Add to your AndroidManifest.xml:
```
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

<b>iOS</b>
In your Info.plist:
```
<key>NSMicrophoneUsageDescription</key>
<string>This app requires access to the microphone for speech recognition.</string>
<key>NSSpeechRecognitionUsageDescription</key>
<string>This app uses speech recognition to convert speech to text.</string>
```