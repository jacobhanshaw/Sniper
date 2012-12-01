//
//  CameraViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import "AppModel.h"

@interface CameraViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
    UIImagePickerController *picker;
    int                      machineGunBulletsLeft;
}

@property (readwrite) int machineGunBulletsLeft;

- (void)takePicture;
- (void)unique;

@end
