import {
  TurboModuleRegistry,
  NativeModules,
  DeviceEventEmitter,
  Platform,
  NativeEventEmitter,
} from 'react-native';
import type { DeviceEventEmitterStatic, TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  startListening(language?: string): void;
  stopListening(): void;
  destroyRecognizer(): void;
}

// Try to use TurboModules if available, fall back to legacy module
const Transcription: Spec =
  (TurboModuleRegistry.get<Spec>('Transcription') as Spec) ??
  (NativeModules.Transcription as Spec);

type TransctiptionEventEmitterType =
  | DeviceEventEmitterStatic
  | NativeEventEmitter;

export default Transcription;
export const TranscriptionEventEmitter: TransctiptionEventEmitterType =
  Platform.OS === 'android'
    ? DeviceEventEmitter
    : new NativeEventEmitter(NativeModules.Transcription);
