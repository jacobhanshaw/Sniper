//
//  CameraOverlayView.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import "MissionViewController.h"
#import "AppModel.h"

@interface CameraOverlayView : UIView {
    
    IBOutlet UIImageView *targetReticleImageView;
    IBOutlet UISlider *zoomLevelSlider;
    BOOL               sliderIsInverted;
    
    MissionViewController *delegate;
    UIImagePickerController *picker;
}

@property (nonatomic) MissionViewController *delegate;
@property (nonatomic) UIImagePickerController *picker;

- (void)update;
- (IBAction)zoom:(id)sender;

@end
