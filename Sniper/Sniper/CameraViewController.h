//
//  CameraViewController.h
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CameraViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
    UIImagePickerController *picker;
}

- (void)takePicture;

@end
