//
//  CameraViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/1/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "CameraViewController.h"
#import "CameraOverlayView.h"
#import "RootViewController.h"

@implementation CameraViewController

@synthesize machineGunBulletsLeft;

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
    picker.wantsFullScreenLayout = YES;
    picker.delegate = self;
    
    [[NSNotificationCenter defaultCenter] addObserver: self selector: @selector(unique) name: @"VolumeButtonPressed" object: nil];
    
    NSArray *nibs = [[NSBundle mainBundle] loadNibNamed:@"CameraOverlayView"
                                                  owner:nil
                                                options:nil];
    if([nibs count]){
        id nib = [nibs objectAtIndex:0];
        if([nib isKindOfClass:[CameraOverlayView class]]){
            CameraOverlayView *view = (CameraOverlayView*)nib;
            view.delegate = self;
            view.picker = picker;
            [view update];
            picker.cameraOverlayView = view;
        }
    }
}

- (void)viewDidAppear:(BOOL)animated{
    [self presentViewController:picker animated:YES completion:nil];
    [((RootViewController *)self.presentingViewController) play];
}

- (void)takePicture {
    [picker takePicture];
}

-(void) unique {
    
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    [[[AppModel sharedAppModel] shots] addObject:[info objectForKey:UIImagePickerControllerOriginalImage]];
    machineGunBulletsLeft--;
    if(machineGunBulletsLeft >= 0) [self performSelector:@selector(takePicture) withObject:nil afterDelay:0.2];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
