#import "Transcription.h"
#import "SpeechRecognitionManager.h"
#import <React/RCTEventEmitter.h>

@interface Transcription () {
    SpeechRecognitionManager *_manager;
}
@end

@implementation Transcription

RCT_EXPORT_MODULE()

- (instancetype)init {
    if (self = [super init]) {
        _manager = [[SpeechRecognitionManager alloc] init];
    }
    return self;
}

RCT_EXPORT_METHOD(startListening:(NSString *)language) {
    [_manager startListeningWithLanguage:language
                               onPartial:^(NSString *partial) {
                                   [self sendEventWithName:@"onSpeechPartialResults" body:@{@"results": @[partial]}];
                               }
                                onResult:^(NSString *final) {
                                   [self sendEventWithName:@"onSpeechResults" body:@{@"results": @[final]}];
                                }
                                onError:^(NSError *error) {
                                   [self sendEventWithName:@"onSpeechError" body:@{@"error": error.localizedDescription}];
                                }];
}

RCT_EXPORT_METHOD(stopListening) {
    [_manager stopListening];
}

RCT_EXPORT_METHOD(destroyRecognizer) {
    [_manager destroy];
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"onSpeechPartialResults", @"onSpeechResults", @"onSpeechError"];
}

@end
