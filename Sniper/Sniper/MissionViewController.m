//
//  MissionViewController.m
//  Sniper
//
//  Created by Jacob Hanshaw on 12/2/12.
//  Copyright (c) 2012 Jacob Hanshaw. All rights reserved.
//

#import "MissionViewController.h"
#import "CameraOverlayView.h"
#import "RootTabBarController.h"
#import "UIImage+Scale.h"
#import "UIImage+Resize.h"
#import "UIImage+fixOrientation.h"
#import <ImageIO/ImageIO.h>
#import "Target.h"

@implementation MissionViewController

@synthesize machineGunBulletsLeft, shots, zoom;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        shots = [[NSMutableArray alloc] init];
        zoom = 1;
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    picker = [[UIImagePickerController alloc] init];
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) picker.sourceType = UIImagePickerControllerSourceTypeCamera;
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
           // view.delegate = self;
            view.picker = picker;
            [view update];
            picker.cameraOverlayView = view;
        }
    }
}

- (void) viewWillAppear:(BOOL)animated {
    lbl_targetName.text = ((Target *)[[AppModel sharedAppModel].currentGame.currentTargets objectAtIndex:0]).name;
    NSString *string = [NSString stringWithFormat:@"%d", [AppModel sharedAppModel].currentGame.currentTargets.count];
    lbl_numberTargetsLeft.text = string;
    missionDescriptionTextBox.text = @"You are to find and assassinate the target. Get the mission done quickly and quietly.";
    targetImageView =((Target *)[[AppModel sharedAppModel].currentGame.currentTargets objectAtIndex:0]).imageView;
}

- (void)viewDidAppear:(BOOL)animated{
    targetImageView =((Target *)[[AppModel sharedAppModel].currentGame.currentTargets objectAtIndex:0]).imageView;
   // [self presentViewController:picker animated:YES completion:nil];
}

- (void)takePicture {
    [picker takePicture];
}

- (IBAction)presentCameraButtonPressed:(id)sender {
    [self presentViewController:picker animated:YES completion:nil];
}


- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    [[self shots] addObject:info];
    machineGunBulletsLeft--;
    if(machineGunBulletsLeft > 0) [self performSelector:@selector(takePicture) withObject:nil afterDelay:0.2];
    else [self savePhotoObjects];
}

- (void) savePhotoObjects {
    
    for(NSDictionary *info in [self shots]){
        PhotoObject *photo = [[PhotoObject alloc] init];
        UIImage *image = [info objectForKey:UIImagePickerControllerOriginalImage];
        
        if(image.size.height > image.size.width){
            if(image.size.height > 856)
                image = [image scaleToSize:CGSizeMake(image.size.width*(856/image.size.height), 856)];
            if(image.size.width > 640)
                image = [image scaleToSize:CGSizeMake(640, image.size.height*(640/image.size.width))];
        }
        else
        {
            if(image.size.width > 856)
                image = [image scaleToSize:CGSizeMake(856, image.size.height*(856/image.size.width))];
            if(image.size.height > 640)
                image = [image scaleToSize:CGSizeMake(image.size.width*(640/image.size.height), 640)];
        }
        
        photo.image = image;
        
        UIView *overallShot = [[UIView alloc] initWithFrame:CGRectMake(0, 0, photo.image.size.width, photo.image.size.height)];
        [self.view addSubview:overallShot];
        UIImageView *faceShot = [[UIImageView alloc] initWithImage:photo.image];
        faceShot.frame = overallShot.frame;
        faceShot.contentMode = UIViewContentModeScaleAspectFit;
        faceShot.transform = CGAffineTransformScale(CGAffineTransformIdentity,zoom, zoom);
        [overallShot addSubview:faceShot];
        UIImageView *targetReticle = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"targetReticle.png"]];
        targetReticle.frame = overallShot.frame;
        targetReticle.contentMode = UIViewContentModeScaleAspectFill;
        [overallShot addSubview:targetReticle];
        [overallShot removeFromSuperview];
        
        UIGraphicsBeginImageContext(overallShot.bounds.size);
        [overallShot.layer renderInContext:UIGraphicsGetCurrentContext()];
        photo.image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        photo.photoData = UIImageJPEGRepresentation(photo.image, 0.4);
        photo.mediaFileName = [NSString stringWithFormat:@"%@image.jpg",[NSDate date]]; //ADD USER ID
        // NSString *newFilePath =[NSTemporaryDirectory() stringByAppendingString: photo.mediaFileName];
        //  photo.imageURL = [[NSURL alloc] initFileURLWithPath: newFilePath];
        //  if (photo.photoData != nil) [photo.photoData writeToURL:photo.imageURL atomically:YES];
        
        //Image Meta Data
        NSMutableDictionary *newMetadata = [[NSMutableDictionary alloc] initWithDictionary:[info objectForKey:UIImagePickerControllerMediaMetadata]];
        // CLLocation * location = [AppModel sharedAppModel].playerLocation;
        //[newMetadata setLocation:location];
        // NSString *descript = [[NSString alloc] initWithFormat: @"%@ %@: %@. %@: %@", NSLocalizedString(@"CameraImageTakenKey", @""), NSLocalizedString(@"CameraGameKey", @""), gameName, NSLocalizedString(@"CameraPlayerKey", @""), [[AppModel sharedAppModel] userName]];
        // [newMetadata setDescription: descript];
        
        // If image not selected from camera roll, save image with metadata to camera roll
        if ([info objectForKey:UIImagePickerControllerReferenceURL] == NULL)
        {
            ALAssetsLibrary *al = [[ALAssetsLibrary alloc] init];
            [al writeImageDataToSavedPhotosAlbum:photo.photoData metadata:newMetadata completionBlock:^(NSURL *assetURL, NSError *error) {
                // once image is saved, get asset from assetURL
                [al assetForURL:assetURL resultBlock:^(ALAsset *asset) {
                    if (!asset) return;
                    
                    // save image to temporary directory to be able to upload it
                    ALAssetRepresentation *defaultRep = [asset defaultRepresentation];
                    UIImage * image = [UIImage imageWithCGImage:[defaultRep fullResolutionImage]];
                    NSData *imageData = UIImageJPEGRepresentation(image, 0.4);
                    imageData = [self dataWithEXIFUsingData:imageData];
                    
                    NSString *newFilePath =[NSTemporaryDirectory() stringByAppendingString: photo.mediaFileName];
                    NSURL *imageURL = [[NSURL alloc] initFileURLWithPath: newFilePath];
                    
                    [imageData writeToURL:imageURL atomically:YES];
                    //upload HERE
                    
                    [self uploadImage:imageData withFileName: photo.mediaFileName];
                }
                   failureBlock:^(NSError *error) {}
                 ];
            }];
        }
        else{
            [self uploadImage:photo.photoData withFileName:photo.mediaFileName];
        }
        
        [[[AppModel sharedAppModel] photoObjects] addObject:photo];
    }
    [picker dismissViewControllerAnimated:YES completion:nil];
}

-(void) uploadImage:(NSData *)imageData withFileName:(NSString *)mediaFileName{
    NSURL *url = [NSURL URLWithString:@"http://winrar.upl.cs.wisc.edu:9876"];
    AFHTTPClient *httpClient = [[AFHTTPClient alloc] initWithBaseURL:url];
    NSMutableDictionary *parameters = [NSMutableDictionary dictionary];
    [parameters setObject:@"1" forKey:@"id"];
    NSMutableURLRequest *request = [httpClient multipartFormRequestWithMethod:@"POST" path:@"/killshot" parameters:parameters constructingBodyWithBlock: ^(id <AFMultipartFormData>formData) {
        [formData appendPartWithFileData:imageData name:@"image" fileName:mediaFileName mimeType:@"image/jpeg"];
    }];
    
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    //[operation setUploadProgressBlock:^(NSInteger bytesWritten, long long totalBytesWritten, long long totalBytesExpectedToWrite) {
    //    NSLog(@"Sent %lld of %lld bytes", totalBytesWritten, totalBytesExpectedToWrite);
    //}];
    [operation start];
}

- (NSMutableData*)dataWithEXIFUsingData:(NSData*)originalJPEGData {
    NSMutableData* newJPEGData = [[NSMutableData alloc] init];
    NSMutableDictionary* exifDict = [[NSMutableDictionary alloc] init];
    NSMutableDictionary* locDict = [[NSMutableDictionary alloc] init];
    NSDateFormatter* dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy:MM:dd HH:mm:ss"];
    
    CGImageSourceRef img = CGImageSourceCreateWithData((__bridge CFDataRef)originalJPEGData, NULL);
    //     CLLocationDegrees exifLatitude = [AppModel sharedAppModel].playerLocation.coordinate.latitude;
    //    CLLocationDegrees exifLongitude = [AppModel sharedAppModel].playerLocation.coordinate.longitude;
    
    //    NSString* datetime = [dateFormatter stringFromDate:[AppModel sharedAppModel].playerLocation.timestamp];
    
    //       [exifDict setObject:datetime forKey:(NSString*)kCGImagePropertyExifDateTimeOriginal];
    //        [exifDict setObject:datetime forKey:(NSString*)kCGImagePropertyExifDateTimeDigitized];
    
    //  [locDict setObject:[AppModel sharedAppModel].playerLocation.timestamp forKey:(NSString*)kCGImagePropertyGPSTimeStamp];
    
    /*     if (exifLatitude <0.0){
     exifLatitude = exifLatitude*(-1);
     [locDict setObject:@"S" forKey:(NSString*)kCGImagePropertyGPSLatitudeRef];
     }else{
     [locDict setObject:@"N" forKey:(NSString*)kCGImagePropertyGPSLatitudeRef];
     }
     [locDict setObject:[NSNumber numberWithFloat:exifLatitude] forKey:(NSString*)kCGImagePropertyGPSLatitude];
     
     if (exifLongitude <0.0){
     exifLongitude=exifLongitude*(-1);
     [locDict setObject:@"W" forKey:(NSString*)kCGImagePropertyGPSLongitudeRef];
     }else{
     [locDict setObject:@"E" forKey:(NSString*)kCGImagePropertyGPSLongitudeRef];
     }
     [locDict setObject:[NSNumber numberWithFloat:exifLongitude] forKey:(NSString*) kCGImagePropertyGPSLongitude]; */
    
    NSDictionary * properties = [[NSDictionary alloc] initWithObjectsAndKeys:
                                 locDict, (NSString*)kCGImagePropertyGPSDictionary,
                                 exifDict, (NSString*)kCGImagePropertyExifDictionary, nil];
    CGImageDestinationRef dest = CGImageDestinationCreateWithData((__bridge CFMutableDataRef)newJPEGData, CGImageSourceGetType(img), 1, NULL);
    CGImageDestinationAddImageFromSource(dest, img, 0, (__bridge CFDictionaryRef)properties);
    CGImageDestinationFinalize(dest);
    
    CFRelease(img);
    CFRelease(dest);
    
    return newJPEGData;
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    lbl_targetName = nil;
    targetImageView = nil;
    missionDescriptionTextBox = nil;
    lbl_numberTargetsLeft = nil;
    presentCameraButton = nil;
    [super viewDidUnload];
}
@end

