import { useEffect, useState } from 'react';
import {
  Button,
  Text,
  View,
  StyleSheet,
  PermissionsAndroid,
  Platform,
} from 'react-native';
import Transcription, {
  TranscriptionEventEmitter,
} from 'react-native-transcription';

export default function App() {
  const [results, setResults] = useState<string[]>([]);
  const [error, setError] = useState<number | null>(null);
  useEffect(() => {
    console.log(Platform.OS === 'android');
    const resultListener = TranscriptionEventEmitter.addListener(
      'onSpeechResults',
      (event) => {
        console.log('Result from native:', event);
        setResults(event.results || []);
      }
    );
    const errorListener = TranscriptionEventEmitter.addListener(
      'onSpeechError',
      (event) => {
        console.log('onSpeechError', event);
        setError(event.error);
      }
    );
    return () => {
      resultListener.remove();
      errorListener.remove();
      Transcription.destroyRecognizer();
    };
  }, []);

  // Request permission on Android
  const requestPermission = async () => {
    if (Platform.OS === 'android') {
      await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.RECORD_AUDIO
      );
    }
  };

  useEffect(() => {
    requestPermission();
  }, []);

  return (
    <View style={styles.container}>
      <Button
        title="Start Listening"
        onPress={() => Transcription.startListening()}
      />
      <Button
        title="Stop Listening"
        onPress={() => Transcription.stopListening()}
      />
      <Text>RealTime results: {}</Text>
      <Text>Final Results: {results.join(', ')}</Text>
      {error !== null && <Text>Error: {error}</Text>}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
