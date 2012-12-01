//
//  RootViewController.m
//  ProjectReality
//
//  Created by Jacob Hanshaw on 10/19/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "RootViewController.h"


@implementation RootViewController

@synthesize startGame, p;

+ (id)sharedRootViewController
{
    static dispatch_once_t pred = 0;
    __strong static id _sharedObject = nil;
    dispatch_once(&pred, ^{
        _sharedObject = [[self alloc] initWithFrame:[UIScreen mainScreen].bounds];//[UIScreen mainScreen].bounds]; // or some other init method
    });
    return _sharedObject;
}

- (id)initWithFrame:(CGRect)frame {
    self = [super init];
    if (self) {
        self.view.frame = frame;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    [[UIApplication sharedApplication] setStatusBarHidden:YES withAnimation:UIStatusBarAnimationNone];
    
    [self.startGame setTitle:@"SHOOT HER!" forState:UIControlStateNormal];
    [self.startGame addTarget:self action:@selector(startGameSelected:) forControlEvents:UIControlEventTouchUpInside];
    
}

- (void) viewDidAppear:(BOOL)animated {
    [self play];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) postVolumeChange {
    NSNotification *volumeRequestNotification = [NSNotification notificationWithName:@"VolumeButtonPressed" object:self];
	[[NSNotificationCenter defaultCenter] postNotification:volumeRequestNotification];
    [((CameraViewController *)self.presentedViewController) unique];
}

-(void) startGameSelected:(id)sender{
    CameraViewController *cameraView = [[CameraViewController alloc] init];
    [self presentViewController:cameraView animated:YES completion:nil];
}

- (void) play {
    NSError *error;
    // these 4 lines of code tell the system that "this app needs to play sound/music"
    NSURL* url = [NSURL fileURLWithPath:[[NSBundle mainBundle] pathForResource:@"pingtone" ofType:@"wav"]];
    
    p = [[AVAudioPlayer alloc] initWithContentsOfURL:url error:&error];
    if(error != nil) NSLog(@"%@", [error localizedDescription]);
    [[AVAudioSession sharedInstance] setCategory: AVAudioSessionCategoryPlayback error: &error];
        if(error != nil) NSLog(@"%@", [error localizedDescription]);
    [[AVAudioSession sharedInstance] setActive: YES error: &error];
        if(error != nil) NSLog(@"%@", [error localizedDescription]);
    [p setDelegate: self];
    [p prepareToPlay];
    [p play];
    [p stop];
    
    CGRect frame = CGRectMake(-1000, -1000, 100, 100);
    MPVolumeView *volumeView = [[MPVolumeView alloc] initWithFrame:frame];
    [volumeView sizeToFit];
    [self.view addSubview:volumeView];
    
    [[NSNotificationCenter defaultCenter]
     addObserver:self
     selector:@selector(postVolumeChange)
     name:@"AVSystemController_SystemVolumeDidChangeNotification"
     object:nil];
    
}

@end
