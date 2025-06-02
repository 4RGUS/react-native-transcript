import Transcription, {
  TranscriptionEventEmitter,
} from './NativeTranscription';

export const startListening = (language?: string) => {
  Transcription.startListening(language ?? 'en-US');
};

export const stopListening = () => {
  Transcription.stopListening();
};

export const destroyRecognizer = () => {
  Transcription.destroyRecognizer();
};

export { TranscriptionEventEmitter };
