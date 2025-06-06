import { language } from './language';
import Transcription, {
  TranscriptionEventEmitter,
} from './NativeTranscription';

export const startListening = (languageString?: string) => {
  Transcription.startListening(languageString ?? 'en-US');
};

export const stopListening = () => {
  Transcription.stopListening();
};

export const destroyRecognizer = () => {
  Transcription.destroyRecognizer();
};

export const getAvailableServices = () => {
  return Transcription.getAvailableServices();
};

export { TranscriptionEventEmitter };
export { language };
export default Transcription;
