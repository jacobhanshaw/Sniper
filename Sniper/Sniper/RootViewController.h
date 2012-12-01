//
//  RootViewController.h
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppModel.h"
#import "CameraViewController.h"
#import "CameraOverlayView.h"

#import <AVFoundation/AVFoundation.h>
#import <MediaPlayer/MediaPlayer.h>

@interface RootViewController : UIViewController <AVAudioSessionDelegate, AVAudioPlayerDelegate>{
    
    UIButton *startGame;
    AVAudioPlayer* p;
    
}

@property(nonatomic) IBOutlet UIButton *startGame;
@property (nonatomic, strong) AVAudioPlayer* p;

+ (RootViewController *)sharedRootViewController;

-(void) play;

@end
