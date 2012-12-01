//
//  CameraOverlayView.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import "CameraViewController.h"
#import "AppModel.h"

@interface CameraOverlayView : UIView {
    
    IBOutlet UIImageView *targetReticleImageView;
    IBOutlet UISlider *zoomLevelSlider;
    BOOL               sliderIsInverted;
    
    CameraViewController *delegate;
    UIImagePickerController *picker;
}

@property (nonatomic) CameraViewController *delegate;
@property (nonatomic) UIImagePickerController *picker;

- (void)update;
- (IBAction)zoom:(id)sender;

@end
