#import "SpeechRecognitionManager.h"
#import <Speech/Speech.h>
#import <AVFoundation/AVFoundation.h>

@interface SpeechRecognitionManager () <SFSpeechRecognizerDelegate>
@property (nonatomic, strong) SFSpeechRecognizer *speechRecognizer;
@property (nonatomic, strong) AVAudioEngine *audioEngine;
@property (nonatomic, strong) SFSpeechAudioBufferRecognitionRequest *recognitionRequest;
@property (nonatomic, strong) SFSpeechRecognitionTask *recognitionTask;
@end

@implementation SpeechRecognitionManager

- (void)startListeningWithLanguage:(NSString *)language
                         onPartial:(void (^)(NSString *result))onPartial
                          onResult:(void (^)(NSString *result))onResult
                           onError:(void (^)(NSError *error))onError {

    if (self.audioEngine.isRunning) {
        [self stopListening];
    }

    NSLocale *locale = [NSLocale localeWithLocaleIdentifier:language];
    self.speechRecognizer = [[SFSpeechRecognizer alloc] initWithLocale:locale];
    self.audioEngine = [[AVAudioEngine alloc] init];
    self.recognitionRequest = [[SFSpeechAudioBufferRecognitionRequest alloc] init];

    AVAudioInputNode *inputNode = self.audioEngine.inputNode;
    if (!inputNode) {
        if (onError) onError([NSError errorWithDomain:@"Speech" code:100 userInfo:@{NSLocalizedDescriptionKey: @"Audio input not available"}]);
        return;
    }

    self.recognitionRequest.shouldReportPartialResults = YES;

    __weak typeof(self) weakSelf = self;
    self.recognitionTask = [self.speechRecognizer recognitionTaskWithRequest:self.recognitionRequest resultHandler:^(SFSpeechRecognitionResult * _Nullable result, NSError * _Nullable error) {
        if (result) {
            if (result.isFinal && onResult) {
                onResult(result.bestTranscription.formattedString);
            } else if (onPartial) {
                onPartial(result.bestTranscription.formattedString);
            }
        }

        if (error && onError) {
            onError(error);
            [weakSelf stopListening];
        }
    }];

    AVAudioFormat *recordingFormat = [inputNode outputFormatForBus:0];
    [inputNode installTapOnBus:0 bufferSize:1024 format:recordingFormat block:^(AVAudioPCMBuffer *buffer, AVAudioTime *when) {
        [weakSelf.recognitionRequest appendAudioPCMBuffer:buffer];
    }];

    [self.audioEngine prepare];
    NSError *startError = nil;
    [self.audioEngine startAndReturnError:&startError];
    if (startError && onError) {
        onError(startError);
    }
}

- (void)stopListening {
    [self.audioEngine stop];
    [self.audioEngine.inputNode removeTapOnBus:0];
    [self.recognitionRequest endAudio];
    self.recognitionTask = nil;
}

- (void)destroy {
    [self stopListening];
    self.audioEngine = nil;
    self.recognitionRequest = nil;
    self.speechRecognizer = nil;
}

@end
