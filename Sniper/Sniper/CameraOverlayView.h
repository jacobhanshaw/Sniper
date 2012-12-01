//
//  CameraOverlayView.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CameraViewController.h"

@interface CameraOverlayView : UIView {
    
    IBOutlet UIImageView *targetReticleImageView;
    IBOutlet UISlider *zoomLevelSlider;
    
    CameraViewController *delegate;
    UIImagePickerController *picker;
}

@property (nonatomic) CameraViewController *delegate;
@property (nonatomic) UIImagePickerController *picker;

- (void)update;

@end
