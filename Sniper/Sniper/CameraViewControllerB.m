//
//  CameraViewController.m
//  CustomCamera
//
//  Created by Carlos Balduz Bernal on 23/12/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "CameraViewController.h"
#import "CustomOverlayView.h"

#define CAMERA_TRANSFORM_X 1
#define CAMERA_TRANSFORM_Y 1

// Screen dimensions
#define SCREEN_WIDTH  320
#define SCREEN_HEIGTH 480

@implementation CameraViewController
{
    CustomOverlayView *overlay;
    BOOL didCancel;
}

@synthesize picker;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    overlay = [[CustomOverlayView alloc]
               initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGTH)];
    
    overlay.delegate = self;
    
	self.picker = [[UIImagePickerController alloc] init];
    self.picker.delegate = self;
    self.picker.navigationBarHidden = YES;
	self.picker.toolbarHidden = YES;
	self.picker.wantsFullScreenLayout = YES;
    
    void (^assetEnumerator)(ALAsset *, NSUInteger, BOOL *) = ^(ALAsset *result, NSUInteger index, BOOL *stop) {
        if(result) {
            [assets addObject:result];
            
        } else {
            [self performSelectorOnMainThread:@selector(showCamera) withObject:nil waitUntilDone:NO];
            ALAsset *lastPicture = (ALAsset *)[assets lastObject]; 
            [overlay.lastPicture setImage:[UIImage imageWithCGImage:[lastPicture thumbnail]] forState:UIControlStateNormal];
        }
    };
    
    void (^assetGroupEnumerator)(ALAssetsGroup *, BOOL *) =  ^(ALAssetsGroup *group, BOOL *stop) {
        if(group) {
            [group enumerateAssetsUsingBlock:assetEnumerator];
        }
    };
    
    assets = [[NSMutableArray alloc] init];
    ALAssetsLibrary *library = [[ALAssetsLibrary alloc] init];
    [library enumerateGroupsWithTypes:ALAssetsGroupSavedPhotos
                           usingBlock:assetGroupEnumerator
                         failureBlock: ^(NSError *error) {
                             NSLog(@"Failure");
                         }];
}

- (void) showCamera
{    
	self.picker.sourceType = UIImagePickerControllerSourceTypeCamera;
    
    // Must be NO.
    self.picker.showsCameraControls = NO;

	self.picker.cameraViewTransform =
	CGAffineTransformScale(self.picker.cameraViewTransform, CAMERA_TRANSFORM_X, CAMERA_TRANSFORM_Y);

    // When showCamera is called, it will show by default the back camera, so if the flashButton was
    // hidden because the user switched to the front camera, you have to show it again.
    if (overlay.flashButton.hidden) {
        overlay.flashButton.hidden = NO;
    }
    
    self.picker.cameraOverlayView = overlay;

    // If the user cancelled the selection of an image in the camera roll, we have to call this method
    // again.
    if (!didCancel) {
        [self presentModalViewController:self.picker animated:YES];	
    } else {
        didCancel = NO;
    }
    
}

- (void)takePicture
{
    [picker takePicture];
}

- (void) imagePickerController:(UIImagePickerController *)aPicker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    UIImage *aImage = (UIImage *)[info objectForKey:UIImagePickerControllerOriginalImage];
    
    if (aPicker.sourceType == UIImagePickerControllerSourceTypeCamera) {
        UIImageWriteToSavedPhotosAlbum (aImage, nil, nil , nil);
        overlay.pictureButton.enabled = YES;
        [overlay.lastPicture setImage:aImage forState:UIControlStateNormal];
    } else {
        // clear the previous imageView
        [imageView removeFromSuperview];
        imageView = nil;
        
        // reset our zoomScale
        
        CGRect applicationFrame = [[UIScreen mainScreen] applicationFrame];
        imageView = [[UIImageView alloc] initWithImage:aImage];
        scrollView.contentSize = aImage.size;
        
        scrollView.bounces = NO;
        
        scrollView.delegate = self;
        
        // set up our content size and min/max zoomscale
        CGFloat xScale = applicationFrame.size.width / aImage.size.width;    // the scale needed to perfectly fit the image width-wise
        CGFloat yScale = applicationFrame.size.height / aImage.size.height;  // the scale needed to perfectly fit the image height-wise
        CGFloat minScale = MIN(xScale, yScale);                 // use minimum of these to allow the image to become fully visible
        
        // on high resolution screens we have double the pixel density, so we will be seeing every pixel if we limit the
        // maximum zoom scale to 0.5.
        CGFloat maxScale = 1.0 / [[UIScreen mainScreen] scale];
        
        // don't let minScale exceed maxScale. (If the image is smaller than the screen, we don't want to force it to be zoomed.) 
        if (minScale > maxScale) {
            minScale = maxScale;
        }
        
        scrollView.contentSize = aImage.size;
        scrollView.maximumZoomScale = maxScale;
        scrollView.minimumZoomScale = minScale;
        scrollView.zoomScale = minScale;
        
        
        //////////////
        
        CGSize boundsSize = applicationFrame.size;
        CGRect frameToCenter = imageView.frame;
        
        // center horizontally
        if (frameToCenter.size.width < boundsSize.width)
            frameToCenter.origin.x = (boundsSize.width - frameToCenter.size.width) / 2;
        else
            frameToCenter.origin.x = 0;
        
        // center vertically
        if (frameToCenter.size.height < boundsSize.height)
            frameToCenter.origin.y = (boundsSize.height - frameToCenter.size.height) / 2;
        else
            frameToCenter.origin.y = 0;
        
        //////////////
        
        imageView.frame = frameToCenter;
        
        [scrollView addSubview:imageView];
        
        [self.picker dismissModalViewControllerAnimated:YES];
    }    
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    didCancel = YES;
    [self showCamera];
}

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
	return imageView;
}

- (IBAction) backButton:(id)sender
{
    self.picker.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
    [self presentModalViewController:self.picker animated:YES];	
}

- (IBAction)doneButton:(id)sender
{
    self.picker.sourceType = UIImagePickerControllerSourceTypeCamera;
    if (overlay.flashButton.hidden) {
        overlay.flashButton.hidden = NO;
    }
    [self presentModalViewController:self.picker animated:YES];	
}

- (void) changeFlash:(id)sender
{
    switch (self.picker.cameraFlashMode) {
        case UIImagePickerControllerCameraFlashModeAuto:
            [(UIButton *)sender setImage:[UIImage imageNamed:@"flash01"] forState:UIControlStateNormal];
            self.picker.cameraFlashMode = UIImagePickerControllerCameraFlashModeOn;
            break;
            
        case UIImagePickerControllerCameraFlashModeOn:
            [(UIButton *)sender setImage:[UIImage imageNamed:@"flash03"] forState:UIControlStateNormal];
            self.picker.cameraFlashMode = UIImagePickerControllerCameraFlashModeOff;
            break;
            
        case UIImagePickerControllerCameraFlashModeOff:
            [(UIButton *)sender setImage:[UIImage imageNamed:@"flash02"] forState:UIControlStateNormal];
            self.picker.cameraFlashMode = UIImagePickerControllerCameraFlashModeAuto;
            break;
    }
}

- (void)changeCamera
{
    if (self.picker.cameraDevice == UIImagePickerControllerCameraDeviceFront) {
        self.picker.cameraDevice = UIImagePickerControllerCameraDeviceRear;
        overlay.flashButton.hidden = NO;
    } else {
        self.picker.cameraDevice = UIImagePickerControllerCameraDeviceFront;
        overlay.flashButton.hidden = YES;
    }
}

- (void)showLibrary
{
    self.picker.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
}

@end
