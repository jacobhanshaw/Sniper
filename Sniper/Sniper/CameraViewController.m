//
//  CameraViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "CameraViewController.h"
#import "CameraOverlayView.h"

@implementation CameraViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    picker = [[UIImagePickerController alloc] init];
    picker.sourceType = UIImagePickerControllerSourceTypeCamera;
 //   picker.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    picker.showsCameraControls = NO;
    picker.delegate = self;
    
    NSArray *nibs = [[NSBundle mainBundle] loadNibNamed:@"CameraOverlayView"
                                                  owner:nil
                                                options:nil];
    if([nibs count]){
        id nib = [nibs objectAtIndex:0];
        if([nib isKindOfClass:[CameraOverlayView class]]){
            CameraOverlayView *view = (CameraOverlayView*)nib;
            view.delegate = self;
            view.picker = picker;
            picker.cameraOverlayView = view;
        }
    }
}

- (void)viewWillAppear:(BOOL)animated{
    [self presentViewController:picker animated:YES completion:nil];
}

- (void)takePicture {
    [picker takePicture];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
