import {
  TurboModuleRegistry,
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';
import type { TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  startListening(): void;
  stopListening(): void;
  destroyRecognizer(): void;
}

// Try to use TurboModules if available, fall back to legacy module
const Transcription: Spec =
  (TurboModuleRegistry.get<Spec>('Transcription') as Spec) ??
  (NativeModules.Transcription as Spec);

export default Transcription;
export const TranscriptionEventEmitter = DeviceEventEmitter;
