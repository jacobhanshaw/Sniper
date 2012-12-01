//
//  CameraOverlayView.m
//  ARmarket
//
//  Created by Boguslaw Parol on 20.05.2012.
//  Copyright (c) 2012 mWorldApps.com. All rights reserved.
//

#import "CameraOverlayView.h" 

@implementation CameraOverlayView

@synthesize delegate = _delegate,backButton = _backButton,toolbar = _toolbar, zoomSlider = _zoomSlider, picker = _picker, cameraButton = _cameraButton, sunImageView = _sunImageView;

- (void)dealloc{
    [[NSNotificationCenter defaultCenter] removeObserver:self];    
    self.delegate = nil;
    self.backButton = nil;
    self.toolbar = nil;
    self.zoomSlider = nil;
    self.picker = nil;
    self.cameraButton = nil;
    self.sunImageView = nil;
    [super dealloc];
}

- (void)configure{

    UIPinchGestureRecognizer *pinch;
    pinch = [[[UIPinchGestureRecognizer alloc] initWithTarget:self
                                                       action:@selector(pinch:)] autorelease];
    pinch.delegate = self;
    [self addGestureRecognizer:pinch];  
}

- (IBAction)back:(id)sender{
    [self.delegate didFinished];
}


- (IBAction)camera:(id)sender{
    
}

- (void)pinch:(UIPinchGestureRecognizer*)recognizer{

    static CGFloat factor = 20;
    
    CGFloat scale = [recognizer scale];
    
    if(scale < 1){
        self.sunImageView.transform = CGAffineTransformScale(self.sunImageView.transform, 1 - (1 - scale)/factor, 1 - (1 - scale)/factor);
    }else{
        self.sunImageView.transform = CGAffineTransformScale(self.sunImageView.transform, 1 + (scale -  1)/factor,1 + (scale -  1)/factor);        
    }
}

#pragma mark GestureRecognizer

- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer{
    return YES;
}


- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer 
shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer{
    
    if(gestureRecognizer.delegate == self && 
       [otherGestureRecognizer isKindOfClass:[UIPinchGestureRecognizer class]]){
        return NO;
    }
    return YES;    
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch{
    return YES;
}

#pragma mark - User Actions

- (IBAction)zoom:(id)sender{
    UISlider *slider = (UISlider*)sender;
    CGFloat zoom =  1 + 4*slider.value;
    self.picker.cameraViewTransform = CGAffineTransformScale(CGAffineTransformIdentity,zoom, zoom);
}


@end
