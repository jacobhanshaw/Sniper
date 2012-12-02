//
//  CameraViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <QuartzCore/QuartzCore.h>
#import "AppModel.h"
#import "PhotoObject.h"
#import "AFHTTPClient.h"

@interface CameraViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
    UIImagePickerController *picker;
    int                      machineGunBulletsLeft;
    NSMutableArray          *shots;
    CGFloat                  zoom;
}

@property (readwrite) CGFloat zoom;
@property (readwrite) int machineGunBulletsLeft;
@property (nonatomic) NSMutableArray *shots;

- (void)takePicture;

@end
