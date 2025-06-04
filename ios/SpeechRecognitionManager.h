#import <Foundation/Foundation.h>

@interface SpeechRecognitionManager : NSObject

- (void)startListeningWithLanguage:(NSString *)language
                         onPartial:(void (^)(NSString *result))onPartial
                          onResult:(void (^)(NSString *result))onResult
                            onError:(void (^)(NSError *error))onError;

- (void)stopListening;
- (void)destroy;

@end
