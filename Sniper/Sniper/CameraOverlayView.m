//
//  CameraOverlayView.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "CameraOverlayView.h"
#import "RootTabBarController.h"

#define DEGREES_TO_RADIANS(x) (M_PI * x / 180.0)

@implementation CameraOverlayView

@synthesize delegate, picker;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

-(void) update{
    //zoomLevelSlider.hidden = YES;
    
    [[NSNotificationCenter defaultCenter] addObserver: self selector: @selector(unique) name: @"VolumeButtonPressed" object: nil];
    
    [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
    [[NSNotificationCenter defaultCenter] addObserver: self selector: @selector(correctSlider) name: UIDeviceOrientationDidChangeNotification object: nil];
    sliderIsInverted = NO;
    [self correctSlider];
}

- (IBAction)zoom:(id)sender {
    UISlider *slider = (UISlider*)sender;
    CGFloat zoom =  1 + 4*slider.value;
    self.delegate.zoom = zoom;
    self.picker.cameraViewTransform = CGAffineTransformScale(CGAffineTransformIdentity,zoom, zoom);
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [self takePicture];
}

- (void) takePicture {
    self.delegate.machineGunBulletsLeft = [AppModel sharedAppModel].machineGunRounds;
    [[self.delegate shots] removeAllObjects];
    [self.delegate takePicture];
}

- (void)correctSlider{
    [UIView beginAnimations:@"correctSlider" context:nil];
	[UIView setAnimationDuration:0.5];
	UIDeviceOrientation orientation = [[UIDevice currentDevice] orientation];
    if((orientation == UIInterfaceOrientationLandscapeLeft || orientation == UIInterfaceOrientationPortraitUpsideDown) && !sliderIsInverted){
        CGAffineTransform trans = CGAffineTransformMakeRotation(DEGREES_TO_RADIANS(180));
        zoomLevelSlider.transform = trans;
        sliderIsInverted = YES;
    }
    else if ((orientation == UIInterfaceOrientationLandscapeRight || orientation == UIInterfaceOrientationPortrait) && sliderIsInverted){
        CGAffineTransform trans = CGAffineTransformIdentity;
        zoomLevelSlider.transform = trans;
        sliderIsInverted = NO;
    }
	[UIView commitAnimations];
    
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
