//
//  MissionViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/2/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#pragma once

#import <UIKit/UIKit.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <QuartzCore/QuartzCore.h>
#import "AppModel.h"
#import "PhotoObject.h"
#import "AFHTTPClient.h"
#import "UIImageView+AFNetworking.h"

@interface MissionViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
    UIImagePickerController *picker;
    int                      machineGunBulletsLeft;
    NSMutableArray          *shots;
    CGFloat                  zoom;
    IBOutlet UILabel *lbl_targetName;
    IBOutlet UIImageView *targetImageView;
    IBOutlet UITextView *missionDescriptionTextBox;
    IBOutlet UILabel *lbl_numberTargetsLeft;
    IBOutlet UIButton *presentCameraButton;
}

@property (readwrite) CGFloat zoom;
@property (readwrite) int machineGunBulletsLeft;
@property (nonatomic) NSMutableArray *shots;

- (void)takePicture;

- (IBAction)presentCameraButtonPressed:(id)sender;


@end
